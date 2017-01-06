package ph.com.smesoft.wsms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.wsms.domain.Businessinfo;

public interface BusinessinfoRepository extends JpaSpecificationExecutor<Businessinfo>, JpaRepository<Businessinfo, Long> {

}