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

import ph.com.smesoft.wsms.domain.IndustryType;
import ph.com.smesoft.wsms.repository.IndustryTypeRepository;

@Service
@Transactional
public class IndustryTypeServiceImpl implements IndustryTypeService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
   IndustryTypeRepository industryTypeRepository;

	public long countAllIndustrytypes() {
      return industryTypeRepository.count();
  }

	public void deleteIndustrytype(IndustryType industryType) {
      industryTypeRepository.delete(industryType);
  }

	public IndustryType findIndustrytype(Long id) {
      return industryTypeRepository.findOne(id);
  }

	public List<IndustryType> findAllIndustrytypes() {
      return industryTypeRepository.findAll();
  }

	public List<IndustryType> findIndustrytypeEntries(int firstResult, int maxResults) {
      return industryTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
  }

	public void saveIndustrytype(IndustryType industryType) {
      industryTypeRepository.save(industryType);
  }

	public IndustryType updateIndustrytype(IndustryType industryType) {
      return industryTypeRepository.save(industryType);
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
	
	public List<IndustryType> findIndustrytypebyIndustrytypeNumber(String searchString){
	    TypedQuery<IndustryType> searchResult = em.createNamedQuery("findIndustrytypeByIndustrytypeNum", IndustryType.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<IndustryType> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfIndustryTypeExist(String searchString){
	    TypedQuery<IndustryType> searchResult = em.createNamedQuery("countIndustryType", IndustryType.class);
	    searchResult.setParameter("search",searchString);
	    List<IndustryType> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
