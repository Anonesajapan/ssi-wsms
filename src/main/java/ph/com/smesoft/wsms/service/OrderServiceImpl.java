package ph.com.smesoft.wsms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.OrderDetails;
import ph.com.smesoft.wsms.domain.Customer;
import ph.com.smesoft.wsms.repository.OrderRepository;
import ph.com.smesoft.wsms.repository.CustomerRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerRepository customerRepository;
	
	public long countAllOrderdetails() {
        return orderRepository.count();
    }
	
	public void deleteOrderdetails(OrderDetails order) {
		orderRepository.delete(order);
    }
	
	public OrderDetails findOrder(Long id) {
        return orderRepository.findOne(id);
    }

	public List<OrderDetails> findAllOrder() {
        return orderRepository.findAll();
    }

	public List<OrderDetails> findOrderEntries(int firstResult, int maxResults) {
        return orderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveOrderdetails(OrderDetails order) {
		orderRepository.save(order);
    }

	public OrderDetails updateOrderdetails(OrderDetails order) {
        return orderRepository.save(order);
    }
	
	public List<OrderDetails> findOrderbyid(String searchString){
	    TypedQuery<OrderDetails> searchResult = em.createNamedQuery("findOrderdetailsByid", OrderDetails.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<OrderDetails> result=searchResult.getResultList();
	    return result;
	 }

	public List<OrderDetails> findOrderdetailsByCustomerId(Long search){
		TypedQuery<OrderDetails> searchResult = em.createNamedQuery("findAllOrderdetailsByCustomerId", OrderDetails.class);
		searchResult.setParameter("customerId", search);
		List<OrderDetails> result = searchResult.getResultList();
		return result;
	}
		
	public List<OrderDetails> findProductDetailsBySalesId(Long id){
	    TypedQuery<OrderDetails> searchResult = em.createNamedQuery("findProductBySalesId", OrderDetails.class);
	    id = id - 1;
	    searchResult.setParameter("salesId", id);
	    List<OrderDetails> result = searchResult.getResultList();
	    System.out.println(result);
	    
	    return result;
	 }

	@Override
	public void saveOrder(OrderDetails order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderDetails updateOrder(OrderDetails order) {
		// TODO Auto-generated method stub
		return null;
	}
}
