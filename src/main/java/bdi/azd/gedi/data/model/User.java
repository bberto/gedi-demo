package bdi.azd.gedi.data.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  private String username;
  
  private String nome;
  private String cognome;
  
  @ManyToOne
  private Group group;
  
  @OneToMany(mappedBy = "owner")
  @JsonBackReference
  private List<Protocollo> protocolliAssegnati;
  
  @OneToMany(mappedBy = "owner")
  @JsonBackReference
  private List<Dossier> dossierAssegnati;  
  
  @Transient
  private Set<Group> allGroups;
  
  public void addToAllGroups(Collection<Group> ids) {
    if(allGroups == null) {
      allGroups = new HashSet<>();
    }
    allGroups.addAll(ids);
  }
}
