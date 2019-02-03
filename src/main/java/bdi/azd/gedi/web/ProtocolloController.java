package bdi.azd.gedi.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.querydsl.core.types.dsl.BooleanExpression;
import bdi.azd.gedi.data.model.QProtocollo;
import bdi.azd.gedi.data.model.User;
import bdi.azd.gedi.data.repository.ProtocolloRepository;
import bdi.azd.gedi.data.repository.UserRepository;
import bdi.azd.gedi.security.CurrentUser;

@Controller
@RequestMapping("/protocolli")
public class ProtocolloController {

  @Autowired
  ProtocolloRepository protocolloRepo;

  @Autowired
  UserRepository userRepo;

  private QProtocollo protocollo = QProtocollo.protocollo;
  private BooleanExpression ownerIsNull = protocollo.owner.isNull();

  private BooleanExpression isVisibleTo(User user) {
    return protocollo.group.in(user.getAllGroups());
  }

  private BooleanExpression isOwnedBy(User user) {
    return protocollo.owner.eq(user);
  }

  @GetMapping
  public ModelAndView all(@CurrentUser User user) {
    Map<String, Object> model = new HashMap<>();
    // model.put("protocolli", protocolloRepo.findByGroupNameIn(userDetails.getGroupNames()));
    model.put("protocolli", protocolloRepo.findAll(isVisibleTo(user)));
    return new ModelAndView("protocolli", model);
  }

  @GetMapping("/unassigned")
  public ModelAndView unassigned(@CurrentUser User user) {
    Map<String, Object> model = new HashMap<>();
    model.put("protocolli", protocolloRepo.findAll(isVisibleTo(user).and(ownerIsNull)));
    return new ModelAndView("protocolli", model);
  }

  @GetMapping("/assigned")
  public ModelAndView assigned(@CurrentUser User user) {
    Map<String, Object> model = new HashMap<>();
    model
        .put("protocolli", protocolloRepo.findAll(isVisibleTo(user).and(ownerIsNull.not())));
    return new ModelAndView("protocolli", model);
  }

  @GetMapping("/my")
  public ModelAndView my(@CurrentUser User user) {
    Map<String, Object> model = new HashMap<>();
    // model.put("protocolli", protocolloRepo.findByOwnerUsername(userDetails.getUsername()));
    model.put("protocolli", protocolloRepo.findAll(isOwnedBy(user)));
    return new ModelAndView("protocolli", model);
  }

  @PostMapping("/assign/me")
  public ModelAndView assignToMe(@RequestParam("ids") List<String> selectedIds,
      @CurrentUser User user) {
    protocolloRepo.findAllById(selectedIds).stream().map(it -> {
      it.setOwner(user);
      return it;
    }).forEach(it -> protocolloRepo.save(it));
    protocolloRepo.flush();
    return new ModelAndView("redirect:/protocolli/unassigned");
  }
  
  @PostMapping("/assign/{username}")
  public ModelAndView assignToMe(@RequestParam("ids") List<String> selectedIds,
      String username) {
    protocolloRepo.findAllById(selectedIds).stream().map(it -> {
      it.setOwner(userRepo.getOne(username));
      return it;
    }).forEach(it -> protocolloRepo.save(it));
    protocolloRepo.flush();
    return new ModelAndView("redirect:/protocolli/unassigned");
  }  
}
