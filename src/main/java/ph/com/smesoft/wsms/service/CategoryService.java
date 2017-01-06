package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Category;

public interface CategoryService {

	public abstract long countAllCategorys();


	public abstract void deleteCategory(Category category);


	public abstract Category findCategory(Long id);


	public abstract List<Category> findAllCategorys();


	public abstract List<Category> findCategoryEntries(int firstResult, int maxResults);


	public abstract void saveCategory(Category category);


	public abstract Category updateCategory(Category category);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Category> findCategorybyCategoryNumber(String searchString);
	public List<Category> findAllCategoryByBarangayId(Long barangayId);
}
