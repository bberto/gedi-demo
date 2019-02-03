package bdi.azd.gedi.data.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import bdi.azd.gedi.data.model.Protocollo;

public interface ProtocolloRepository extends JpaRepository<Protocollo, String>,
    JpaSpecificationExecutor<Protocollo>, QuerydslPredicateExecutor<Protocollo> {

  @Deprecated
  List<Protocollo> findByOwnerUsername(String username);

  @Deprecated
  List<Protocollo> findByGroupNameIn(Collection<String> groupNames);
  
  List<Protocollo> findAllById(Iterable<String> ids);

}
