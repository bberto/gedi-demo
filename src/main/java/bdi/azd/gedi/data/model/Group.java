package bdi.azd.gedi.data.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usergroup")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Group {

  public Group(String name, List<Group> childs, Group parent, List<User> users) {
    super();
    this.name = name;
    this.childs = new ArrayList<>();
    this.setChilds(childs);
    this.parent = parent;
    this.users = users;
  }

  @Id
  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
  @JsonBackReference
  private List<Group> childs;

  @ManyToOne
  @JsonManagedReference
  private Group parent;

  @OneToMany(mappedBy = "group")
  @JsonBackReference
  private List<User> users;

  public void setChilds(List<Group> childs) {
    if (childs != null) {
      this.childs.addAll(childs);
      this.childs.stream().forEach(it -> it.setParent(this));
    }
  }

}
