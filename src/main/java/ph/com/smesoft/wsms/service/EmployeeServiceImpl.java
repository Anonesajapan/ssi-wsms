package ph.com.smesoft.wsms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.Employee;

import ph.com.smesoft.wsms.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public long countAllEmployee() {
        return employeeRepository.count();
    }
	
	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);
    }
	
	public Employee findEmployee(Long id) {
        return employeeRepository.findOne(id);
    }

	public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

	public List<Employee> findEmployeeEntries(int firstResult, int maxResults) {
        return employeeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
    }

	public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
	
	public List<Employee> findEmployeebyid(String searchString){
	    TypedQuery<Employee> searchResult = em.createNamedQuery("findEmployeeByid", Employee.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Employee> result=searchResult.getResultList();
	    return result;
	 }


		
	
	
	
}
