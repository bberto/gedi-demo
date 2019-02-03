package bdi.azd.gedi.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bdi.azd.gedi.data.model.User;
import bdi.azd.gedi.security.CurrentUser;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping("/profile")
  @PreAuthorize("hasRole('USER')")
  public ModelAndView profile(@CurrentUser User user) {
    Map<String, Object> model = new HashMap<>();
    model.put("user", user);
    return new ModelAndView("user", model);
  }
}
