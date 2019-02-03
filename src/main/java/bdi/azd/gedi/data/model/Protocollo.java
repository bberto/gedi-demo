package bdi.azd.gedi.data.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Protocollo {

  @Id
  private String id;
  
  private String oggetto;
  
  private StatoProtocollo stato;
  
  @ManyToOne
  private User owner;
  
  @ManyToOne
  private Group group;
}
