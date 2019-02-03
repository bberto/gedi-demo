package bdi.azd.gedi.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bdi.azd.gedi.data.model.User;
import bdi.azd.gedi.data.repository.UserRepository;
import bdi.azd.gedi.security.UserDetailsImpl;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserRepository userRepo;
  
  @GetMapping("/profile")
  @PreAuthorize("hasRole('USER')")
  public ModelAndView profile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    Map<String, Object> model = new HashMap<>();
    User user = userRepo.findByUsername(userDetails.getUsername()).get();
    model.put("user", user);
    return new ModelAndView("user", model);
  }
}
