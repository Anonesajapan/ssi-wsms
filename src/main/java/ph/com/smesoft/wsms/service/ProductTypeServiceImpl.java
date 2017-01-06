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

import ph.com.smesoft.wsms.domain.ProductType;
import ph.com.smesoft.wsms.repository.ProductTypeRepository;

@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	ProductTypeRepository ProductTypeRepository;

	public long countAllProductTypes() {
       return ProductTypeRepository.count();
   }

	public void deleteProductType(ProductType ProductType) {
		ProductTypeRepository.delete(ProductType);
   }

	public ProductType findProductType(Long id) {
       return ProductTypeRepository.findOne(id);
   }

	public List<ProductType> findAllProductTypes() {
       return ProductTypeRepository.findAll();
   }

	public List<ProductType> findProductTypeEntries(int firstResult, int maxResults) {
       return ProductTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveProductType(ProductType ProductType) {
		ProductTypeRepository.save(ProductType);
   }

	public ProductType updateProductType(ProductType ProductType) {
       return ProductTypeRepository.save(ProductType);
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
	
	public List<ProductType> findProductTypebyProductTypeNumber(String searchString){
	    TypedQuery<ProductType> searchResult = em.createNamedQuery("findProductTypeByProductTypeNum", ProductType.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<ProductType> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfProductTypeExist(String searchString){
	    TypedQuery<ProductType> searchResult = em.createNamedQuery("countProductType", ProductType.class);
	    searchResult.setParameter("search",searchString);
	    List<ProductType> result = searchResult.getResultList();
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
