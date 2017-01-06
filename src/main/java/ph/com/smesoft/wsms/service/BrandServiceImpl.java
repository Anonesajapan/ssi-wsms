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

import ph.com.smesoft.wsms.domain.Brand;
import ph.com.smesoft.wsms.repository.BrandRepository;

@Service
@Transactional
public class BrandServiceImpl implements BrandService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    BrandRepository BrandRepository;

	public long countAllBrands() {
       return BrandRepository.count();
   }

	public void deleteBrand(Brand Brand) {
       BrandRepository.delete(Brand);
   }

	public Brand findBrand(Long id) {
       return BrandRepository.findOne(id);
   }

	public List<Brand> findAllBrands() {
       return BrandRepository.findAll();
   }

	public List<Brand> findBrandEntries(int firstResult, int maxResults) {
       return BrandRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveBrand(Brand Brand) {
       BrandRepository.save(Brand);
   }

	public Brand updateBrand(Brand Brand) {
       return BrandRepository.save(Brand);
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
	
	public List<Brand> findBrandbyBrandNumber(String searchString){
	    TypedQuery<Brand> searchResult = em.createNamedQuery("findBrandByBrandNum", Brand.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Brand> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfBrandExist(String searchString){
	    TypedQuery<Brand> searchResult = em.createNamedQuery("countBrand", Brand.class);
	    searchResult.setParameter("search",searchString);
	    List<Brand> result = searchResult.getResultList();
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
