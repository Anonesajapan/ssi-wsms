package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.CustomerType;

public interface CustomerTypeService {

		public abstract long countAllCustomerTypes();


		public abstract void deleteCustomerType(CustomerType barangay);


		public abstract CustomerType findCustomerType(Long id);


		public abstract List<CustomerType> findAllCustomerTypes();


		public abstract List<CustomerType> findCustomerTypeEntries(int firstResult, int maxResults);


		public abstract void saveCustomerType(CustomerType barangay);


		public abstract CustomerType updateCustomerType(CustomerType barangay);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<CustomerType> findCustomerTypebyCustomerTypeNumber(String searchString);


		public abstract long checkIfCustomerTypeExist(String CustomerType);
		public boolean checkRegex(String input, String user_pattern);

}
