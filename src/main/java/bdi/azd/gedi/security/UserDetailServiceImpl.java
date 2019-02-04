package bdi.azd.gedi.security;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import bdi.azd.gedi.data.model.Group;
import bdi.azd.gedi.data.model.User;
import bdi.azd.gedi.data.repository.GroupRepository;
import bdi.azd.gedi.data.repository.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepo;

  @Autowired
  GroupRepository groupRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optUser = userRepo.findByUsername(username);
    if (!optUser.isPresent()) {
      throw new UsernameNotFoundException(username + "not found");
    }
    User user = optUser.get();
    UserDetailsImpl userDetails = new UserDetailsImpl(user.getUsername());
    userDetails.setUser(user);
    userDetails.getAuthorities().add(new SimpleGrantedAuthority("ROLE_USER"));
    if (user.getGroup() != null) {
      List<Group> groups = groupRepo.findGroupHierarchy(user.getGroup().getName());
      user.addToAllGroups(groups);
      if(groups.size() > 1) {
        userDetails.getAuthorities().add(new SimpleGrantedAuthority("ROLE_ESPERTO"));
      } else {
        userDetails.getAuthorities().add(new SimpleGrantedAuthority("ROLE_ADDETTO"));
      }
    }
    return userDetails;
  }

}