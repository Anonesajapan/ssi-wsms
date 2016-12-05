package ph.com.smesoft.wsms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ph.com.smesoft.wsms.domain.Area;

@Repository
public interface AreaRepository extends JpaSpecificationExecutor<Area>, JpaRepository<Area, Long> {

}
