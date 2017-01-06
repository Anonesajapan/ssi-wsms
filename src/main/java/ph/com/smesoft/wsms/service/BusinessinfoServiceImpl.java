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

import ph.com.smesoft.wsms.domain.Businessinfo;
import ph.com.smesoft.wsms.repository.BusinessinfoRepository;

@Service
@Transactional
public class BusinessinfoServiceImpl implements BusinessinfoService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	BusinessinfoRepository businessInfoRepository;

	public long countAllBusinessinfos() {
      return businessInfoRepository.count();
  }

	public void deleteBusinessinfo(Businessinfo businessInfo) {
		businessInfoRepository.delete(businessInfo);
  }

	public Businessinfo findBusinessinfo(Long id) {
      return businessInfoRepository.findOne(id);
  }

	public List<Businessinfo> findAllBusinessinfos() {
      return businessInfoRepository.findAll();
  }

	public List<Businessinfo> findBusinessinfoEntries(int firstResult, int maxResults) {
      return businessInfoRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
  }

	public void saveBusinessinfo(Businessinfo businessInfo) {
      businessInfoRepository.save(businessInfo);
  }

	public Businessinfo updateBusinessinfo(Businessinfo businessInfo) {
      return businessInfoRepository.save(businessInfo);
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
	
	public List<Businessinfo> findBusinessinfobyBusinessinfoNumber(String searchString){
	    TypedQuery<Businessinfo> searchResult = em.createNamedQuery("findBusinessinfoByBusinessinfoNum", Businessinfo.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Businessinfo> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfBusinessInfoExist(String searchString){
	    TypedQuery<Businessinfo> searchResult = em.createNamedQuery("countBusinessInfo", Businessinfo.class);
	    searchResult.setParameter("search",searchString);
	    List<Businessinfo> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}

	public long checkIfBusinessInfoNameExist(String Businessinfo) {
		TypedQuery<Businessinfo> result =em.createNamedQuery("countBusinessInfo", Businessinfo.class);
		result.setParameter("search", Businessinfo);
		List<Businessinfo> res = result.getResultList();
		int count = res.size();
		return count;
	}


}
