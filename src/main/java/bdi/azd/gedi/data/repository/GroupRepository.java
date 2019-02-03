package bdi.azd.gedi.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import bdi.azd.gedi.data.model.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
  
  List<Group> findAll();
  
  //@formatter:off
  @Query(value = "WITH RECURSIVE hgrp(name, parent) AS ( " + 
      "SELECT :rootgroup, null " +
      "UNION ALL " +
      "SELECT child.name, child.parent_name FROM usergroup child, hgrp parent WHERE child.parent_name = parent.name " +
      ") SELECT usergroup.* FROM usergroup, hgrp WHERE usergroup.name = hgrp.name", nativeQuery = true)
  //@formatter:on
  List<Group> findGroupHierarchy(@Param("rootgroup") String groupName);

}
