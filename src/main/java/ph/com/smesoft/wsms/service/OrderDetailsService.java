package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Order;

public interface OrderDetailsService {

	public abstract long countAllOrderDetails();


	public abstract void deleteOrderDetails(Order orderDetails);


	public abstract Order findOrderDetails(Long id);


	public abstract List<Order> findAllOrderDetails();


	public abstract List<Order> findOrderDetailsEntries(int firstResult, int maxResults);


	public abstract void saveOrderDetails(Order orderDetails);


	public abstract Order updateOrderDetails(Order orderDetails);

 
	public abstract List<Order> findAllOrderDetailsBySearch(String searchString);

	public abstract String findLastOrderNum();
}
