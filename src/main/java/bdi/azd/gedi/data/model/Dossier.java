package bdi.azd.gedi.data.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Dossier {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private StatoDossier stato;
  
  @ManyToOne
  private User owner;
  
  @ManyToMany
  private List<Group> visibilita;

}
