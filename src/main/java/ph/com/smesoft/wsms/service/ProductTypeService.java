package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.ProductType;

public interface ProductTypeService {

		public abstract long countAllProductTypes();


		public abstract void deleteProductType(ProductType productType);


		public abstract ProductType findProductType(Long id);


		public abstract List<ProductType> findAllProductTypes();


		public abstract List<ProductType> findProductTypeEntries(int firstResult, int maxResults);


		public abstract void saveProductType(ProductType productType);


		public abstract ProductType updateProductType(ProductType productType);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<ProductType> findProductTypebyProductTypeNumber(String searchString);


		public abstract long checkIfProductTypeExist(String ProductType);
		public boolean checkRegex(String input, String user_pattern);

}
