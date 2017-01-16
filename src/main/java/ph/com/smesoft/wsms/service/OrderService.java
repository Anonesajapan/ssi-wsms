package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.OrderDetails;

public interface OrderService {

	public abstract long countAllOrderdetails();

	public abstract void deleteOrderdetails(OrderDetails order);

	public abstract OrderDetails findOrder(Long id);

	public abstract List<OrderDetails> findAllOrder();

	public abstract List<OrderDetails> findOrderEntries(int firstResult, int maxResults);

	public abstract void saveOrder(OrderDetails order);

	public abstract OrderDetails updateOrder(OrderDetails order);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	public abstract List<OrderDetails> findProductDetailsBySalesId(Long id);
}
