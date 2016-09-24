package ph.com.smesoft.hms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ph.com.smesoft.hms.domain.Door;

@Repository
public interface DoorRepository extends JpaRepository<Door, Long>, 

JpaSpecificationExecutor<Door> {
}