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

import ph.com.smesoft.wsms.domain.CustomerType;
import ph.com.smesoft.wsms.repository.CustomerTypeRepository;

@Service
@Transactional
public class CustomerTypeServiceImpl implements CustomerTypeService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    CustomerTypeRepository CustomerTypeRepository;

	public long countAllCustomerTypes() {
       return CustomerTypeRepository.count();
   }

	public void deleteCustomerType(CustomerType CustomerType) {
       CustomerTypeRepository.delete(CustomerType);
   }

	public CustomerType findCustomerType(Long id) {
       return CustomerTypeRepository.findOne(id);
   }

	public List<CustomerType> findAllCustomerTypes() {
       return CustomerTypeRepository.findAll();
   }

	public List<CustomerType> findCustomerTypeEntries(int firstResult, int maxResults) {
       return CustomerTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveCustomerType(CustomerType CustomerType) {
       CustomerTypeRepository.save(CustomerType);
   }

	public CustomerType updateCustomerType(CustomerType CustomerType) {
       return CustomerTypeRepository.save(CustomerType);
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
	
	public List<CustomerType> findCustomerTypebyCustomerTypeNumber(String searchString){
	    TypedQuery<CustomerType> searchResult = em.createNamedQuery("findCustomerTypeByCustomerTypeNum", CustomerType.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<CustomerType> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfCustomerTypeExist(String searchString){
	    TypedQuery<CustomerType> searchResult = em.createNamedQuery("countCustomerType", CustomerType.class);
	    searchResult.setParameter("search",searchString);
	    List<CustomerType> result = searchResult.getResultList();
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
