package in.gov.egs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.gov.egs.entity.RouteRoleMapping;

@Repository
public interface RouteRoleMappingRepository extends JpaRepository<RouteRoleMapping, Long> {
	List<RouteRoleMapping> findAll();
}
