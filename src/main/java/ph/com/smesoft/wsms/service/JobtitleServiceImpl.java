package ph.com.smesoft.wsms.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.Barangay;
import ph.com.smesoft.wsms.domain.Jobtitle;
import ph.com.smesoft.wsms.repository.JobtitleRepository;

@Service
@Transactional
public class JobtitleServiceImpl implements JobtitleService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    JobtitleRepository JobtitleRepository;

	public long countAllJobtitles() {
       return JobtitleRepository.count();
   }

	public void deleteJobtitle(Jobtitle Jobtitle) {
       JobtitleRepository.delete(Jobtitle);
   }

	public Jobtitle findJobtitle(Long id) {
       return JobtitleRepository.findOne(id);
   }

	public List<Jobtitle> findAllJobtitles() {
       return JobtitleRepository.findAll();
   }

	public List<Jobtitle> findJobtitleEntries(int firstResult, int maxResults) {
       return JobtitleRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveJobtitle(Jobtitle Jobtitle) {
       JobtitleRepository.save(Jobtitle);
   }

	public Jobtitle updateJobtitle(Jobtitle Jobtitle) {
       return JobtitleRepository.save(Jobtitle);
   }
	
	/*public List<Floor> findFloorbyFloorNumber(String searchKeyword){
	    TypedQuery<Floor> searchResult = em.createNamedQuery("findFloorByFloorNum", Floor.class);
	    searchResult.setParameter("searchKeyword",'%'+searchKeyword+'%');
	    List<Floor> result=searchResult.getResultList();
	    return result;
	 }*/
/*	public String findFloorbyFloorNumber(String searchString){
	    TypedQuery<String> searchResult = em.createNamedQuery("findFloorByFloorNum", String.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    String result=searchResult.getSingleResult();
	    return result;
	 }*/
	
	public List<Jobtitle> findJobtitlebyJobtitleNumber(String searchString){
	    TypedQuery<Jobtitle> searchResult = em.createNamedQuery("findJobtitleByJobtitleNum", Jobtitle.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Jobtitle> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfJobtitleExist(String searchString){
	    TypedQuery<Jobtitle> searchResult = em.createNamedQuery("countJobtitle", Jobtitle.class);
	    searchResult.setParameter("search",searchString);
	    List<Jobtitle> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
	

	public List<Jobtitle> findAllJobtitleByDepartmentId(Long departmentId){
	    TypedQuery<Jobtitle> searchResult = em.createNamedQuery("JobtitleByDepartmentId", Jobtitle.class);
	    searchResult.setParameter("departmentId",departmentId);
	    List<Jobtitle> result=searchResult.getResultList();
	    return result;
	 }

	@Override
	public List<Jobtitle> findAllBarangayByCityId(Long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
