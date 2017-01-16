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

import ph.com.smesoft.wsms.domain.Product;
import ph.com.smesoft.wsms.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    ProductRepository ProductRepository;

	public long countAllProducts() {
       return ProductRepository.count();
   }

	public void deleteProduct(Product Product) {
       ProductRepository.delete(Product);
   }

	public Product findProduct(Long id) {
       return ProductRepository.findOne(id);
   }

	public List<Product> findAllProducts() {
       return ProductRepository.findAll();
   }

	public List<Product> findProductEntries(int firstResult, int maxResults) {
       return ProductRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveProduct(Product Product) {
       ProductRepository.save(Product);
   }

	public Product updateProduct(Product Product) {
       return ProductRepository.save(Product);
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
	
	public List<Product> findProductbyProductNumber(String searchString){
	    TypedQuery<Product> searchResult = em.createNamedQuery("findProductByProductNum", Product.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Product> result=searchResult.getResultList();
	    return result;
	 }
	public List<Product> findProductDetailsByProductId() {
		
		   TypedQuery<Product> searchResult = em.createNamedQuery("findProductDetailsById", Product.class);
		    List<Product> result = searchResult.getResultList();
		    return result;

	}
	

	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
