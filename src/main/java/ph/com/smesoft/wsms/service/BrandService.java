package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Brand;

public interface BrandService {

		public abstract long countAllBrands();


		public abstract void deleteBrand(Brand brand);


		public abstract Brand findBrand(Long id);


		public abstract List<Brand> findAllBrands();


		public abstract List<Brand> findBrandEntries(int firstResult, int maxResults);


		public abstract void saveBrand(Brand brand);


		public abstract Brand updateBrand(Brand brand);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Brand> findBrandbyBrandNumber(String searchString);


		public abstract long checkIfBrandExist(String Brand);
		public boolean checkRegex(String input, String user_pattern);

}
