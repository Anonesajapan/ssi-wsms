package ph.com.smesoft.wsms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.wsms.domain.Businesscontact;

public interface BusinesscontactRepository extends JpaSpecificationExecutor<Businesscontact>, JpaRepository<Businesscontact, Long> {

}