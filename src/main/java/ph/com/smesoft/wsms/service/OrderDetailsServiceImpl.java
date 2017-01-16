package ph.com.smesoft.wsms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.OrderDetails;
import ph.com.smesoft.wsms.domain.Order;
import ph.com.smesoft.wsms.repository.OrderDetailsRepository;

@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	public long countAllOrderDetails() {
        return orderDetailsRepository.count();
    }

	public void deleteOrderDetails(Order orderDetails) {
		orderDetailsRepository.delete(orderDetails);
    }

	public Order findOrderDetails(Long id) {
		System.out.println(orderDetailsRepository.findOne(id));
        return orderDetailsRepository.findOne(id);
    }

	public List<Order> findAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

	public List<Order> findOrderDetailsEntries(int firstResult, int maxResults) {
        return orderDetailsRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveSales(Order orderDetails) {
		orderDetailsRepository.save(orderDetails);
    }

	public Order updateOrderDetails(Order orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
	
	
	public List<Order> findAllOrderDetailsBySearch(String searchString){
	    TypedQuery<Order> searchResult = em.createNamedQuery("findStreetByStreetNum", Order.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Order> result=searchResult.getResultList();
	    return result;
	 }

	public String findLastOrderNum(){
	    TypedQuery<Order> searchResult = em.createNamedQuery("findLastOrderNum", Order.class);
	    //searchResult.setParameter("searchString",'%'+searchString+'%');
	    int result = searchResult.getResultList().size();
	    System.out.println(result);
	    String orderFormat = "";
	    if(result == 0){
	    	orderFormat = String.format("%08d", result);		    
	    }
	    else{
	    	orderFormat = String.format("%08d", result + 1);
	    }
	    
	    return orderFormat;
	 }

	@Override
	public void saveOrderDetails(Order orderDetails) {
		// TODO Auto-generated method stub
		
	}
}
