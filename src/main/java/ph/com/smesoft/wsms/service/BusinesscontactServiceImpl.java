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

import ph.com.smesoft.wsms.domain.Businesscontact;
import ph.com.smesoft.wsms.repository.BusinesscontactRepository;

@Service
@Transactional
public class BusinesscontactServiceImpl implements BusinesscontactService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	BusinesscontactRepository businessContactRepository;

	public long countAllBusinesscontacts() {
      return businessContactRepository.count();
  }

	public void deleteBusinesscontact(Businesscontact businessContact) {
		businessContactRepository.delete(businessContact);
  }

	public Businesscontact findBusinesscontact(Long id) {
      return businessContactRepository.findOne(id);
  }

	public List<Businesscontact> findAllBusinesscontacts() {
      return businessContactRepository.findAll();
  }

	public List<Businesscontact> findBusinesscontactEntries(int firstResult, int maxResults) {
      return businessContactRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
  }

	public void saveBusinesscontact(Businesscontact businessContact) {
      businessContactRepository.save(businessContact);
  }

	public Businesscontact updateBusinesscontact(Businesscontact businessContact) {
      return businessContactRepository.save(businessContact);
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
	
	public List<Businesscontact> findBusinesscontactbyBusinesscontactNumber(String searchString){
	    TypedQuery<Businesscontact> searchResult = em.createNamedQuery("findBusinesscontactByBusinesscontactNum", Businesscontact.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Businesscontact> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfBusinessContactExist(String searchString){
	    TypedQuery<Businesscontact> searchResult = em.createNamedQuery("countBusinessContact", Businesscontact.class);
	    searchResult.setParameter("search",searchString);
	    List<Businesscontact> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}

	public long checkIfBusinessContactNameExist(String Businesscontact) {
		TypedQuery<Businesscontact> result =em.createNamedQuery("countBusinessContact", Businesscontact.class);
		result.setParameter("search", Businesscontact);
		List<Businesscontact> res = result.getResultList();
		int count = res.size();
		return count;
	}


}
