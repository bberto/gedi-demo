package bdi.azd.gedi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import bdi.azd.gedi.data.model.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 8969707268098393474L;

  private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
  }
  
  private @NonNull String username;
  private String password = "{noop}pwd";
  private boolean accountNonExpired = true;
  private boolean accountNonLocked = true;
  private boolean credentialsNonExpired = true;
  private boolean enabled = true;
  
  private User user;
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AUTHORITIES;
  }

}
