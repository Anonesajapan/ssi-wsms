package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Subcategory;

public interface SubcategoryService {

		public abstract long countAllSubcategorys();


		public abstract void deleteSubcategory(Subcategory subcategory);


		public abstract Subcategory findSubcategory(Long id);


		public abstract List<Subcategory> findAllSubcategorys();


		public abstract List<Subcategory> findSubcategoryEntries(int firstResult, int maxResults);


		public abstract void saveSubcategory(Subcategory subcategory);


		public abstract Subcategory updateSubcategory(Subcategory subcategory);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Subcategory> findSubcategorybySubcategoryNumber(String searchString);


		public abstract long checkIfSubcategoryExist(String Subcategory);
		public boolean checkRegex(String input, String user_pattern);

}
