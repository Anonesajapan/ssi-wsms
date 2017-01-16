package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Product;

public interface ProductService {

		public abstract long countAllProducts();


		public abstract void deleteProduct(Product product);


		public abstract Product findProduct(Long id);


		public abstract List<Product> findAllProducts();


		public abstract List<Product> findProductEntries(int firstResult, int maxResults);


		public abstract void saveProduct(Product product);


		public abstract Product updateProduct(Product product);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Product> findProductbyProductNumber(String searchString);


		public boolean checkRegex(String input, String user_pattern);
		
		public List<Product> findProductDetailsByProductId();

}
