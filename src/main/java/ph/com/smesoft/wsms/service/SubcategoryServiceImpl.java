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

import ph.com.smesoft.wsms.domain.Subcategory;
import ph.com.smesoft.wsms.repository.SubcategoryRepository;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    SubcategoryRepository SubcategoryRepository;

	public long countAllSubcategorys() {
       return SubcategoryRepository.count();
   }

	public void deleteSubcategory(Subcategory Subcategory) {
       SubcategoryRepository.delete(Subcategory);
   }

	public Subcategory findSubcategory(Long id) {
       return SubcategoryRepository.findOne(id);
   }

	public List<Subcategory> findAllSubcategorys() {
       return SubcategoryRepository.findAll();
   }

	public List<Subcategory> findSubcategoryEntries(int firstResult, int maxResults) {
       return SubcategoryRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveSubcategory(Subcategory Subcategory) {
       SubcategoryRepository.save(Subcategory);
   }

	public Subcategory updateSubcategory(Subcategory Subcategory) {
       return SubcategoryRepository.save(Subcategory);
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
	
	public List<Subcategory> findSubcategorybySubcategoryNumber(String searchString){
	    TypedQuery<Subcategory> searchResult = em.createNamedQuery("findSubcategoryBySubcategoryNum", Subcategory.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Subcategory> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfSubcategoryExist(String searchString){
	    TypedQuery<Subcategory> searchResult = em.createNamedQuery("countSubcategory", Subcategory.class);
	    searchResult.setParameter("search",searchString);
	    List<Subcategory> result = searchResult.getResultList();
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
