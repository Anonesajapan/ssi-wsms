package ph.com.smesoft.wsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ph.com.smesoft.wsms.domain.IndustryType;

public interface IndustryTypeRepository extends JpaSpecificationExecutor<IndustryType>, JpaRepository<IndustryType, Long> {

}
