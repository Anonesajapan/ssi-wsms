package ph.com.smesoft.wsms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ph.com.smesoft.wsms.domain.Jobtitle;

@Repository
public interface JobtitleRepository extends JpaSpecificationExecutor<Jobtitle>, JpaRepository<Jobtitle, Long> {

}
