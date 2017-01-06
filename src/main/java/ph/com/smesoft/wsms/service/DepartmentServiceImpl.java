package ph.com.smesoft.wsms.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.Department;
import ph.com.smesoft.wsms.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    DepartmentRepository DepartmentRepository;

	public long countAllDepartments() {
       return DepartmentRepository.count();
   }

	public void deleteDepartment(Department Department) {
       DepartmentRepository.delete(Department);
   }

	public Department findDepartment(Long id) {
       return DepartmentRepository.findOne(id);
   }

	public List<Department> findAllDepartments() {
       return DepartmentRepository.findAll();
   }

	public List<Department> findDepartmentEntries(int firstResult, int maxResults) {
       return DepartmentRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveDepartment(Department Department) {
       DepartmentRepository.save(Department);
   }

	public Department updateDepartment(Department Department) {
       return DepartmentRepository.save(Department);
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
	
	public List<Department> findDepartmentbyDepartmentNumber(String searchString){
	    TypedQuery<Department> searchResult = em.createNamedQuery("findDepartmentByDepartmentNum", Department.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Department> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfDepartmentExist(String searchString){
	    TypedQuery<Department> searchResult = em.createNamedQuery("countDepartment", Department.class);
	    searchResult.setParameter("search",searchString);
	    List<Department> result = searchResult.getResultList();
	    int count = result.size();
	    return count;
	 }
	
	public boolean checkRegex(String input, String user_pattern){
		Pattern pattern = Pattern.compile(user_pattern);
		Matcher matcher;
		
		  matcher = pattern.matcher(input);
		  return matcher.matches();
	}
}
