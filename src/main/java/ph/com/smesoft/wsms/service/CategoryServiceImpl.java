package ph.com.smesoft.wsms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.Barangay;
import ph.com.smesoft.wsms.domain.Category;
import ph.com.smesoft.wsms.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    CategoryRepository categoryRepository;

	public long countAllCategorys() {
        return categoryRepository.count();
    }

	public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

	public Category findCategory(Long id) {
        return categoryRepository.findOne(id);
    }

	public List<Category> findAllCategorys() {
        return categoryRepository.findAll();
    }

	public List<Category> findCategoryEntries(int firstResult, int maxResults) {
        return categoryRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

	public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
	
	/*public List<Floor> findFloorbyFloorNumber(String searchKeyword){
	    TypedQuery<Floor> searchResult = em.createNamedQuery("findFloorByFloorNum", Floor.class);
	    searchResult.setParameter("searchKeyword",'%'+searchKeyword+'%');
	    List<Floor> result=searchResult.getResultList();
	    return result;
	 }*/
/*	public String findFloorbyFloorNumber(String searchString){
	    TypedQuery<String> searchResult = em.createNamedQuery("findFloorByFloorNum", String.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    String result=searchResult.getSingleResult();
	    return result;
	 }*/
	
	public List<Category> findCategorybyCategoryNumber(String searchString){
	    TypedQuery<Category> searchResult = em.createNamedQuery("findCategoryByCategoryNum", Category.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Category> result=searchResult.getResultList();
	    return result;
	 }
	
	public List<Category> findAllCategoryByBarangayId(Long barangayId){
	    TypedQuery<Category> searchResult = em.createNamedQuery("categoryByBarangayId", Category.class);
	    searchResult.setParameter("barangayId",barangayId);
	    List<Category> result=searchResult.getResultList();
	    return result;
	 }
}
