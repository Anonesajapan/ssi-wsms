package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Employee;


public interface EmployeeService {

	public abstract long countAllEmployee();


	public abstract void deleteEmployee(Employee employee);


	public abstract Employee findEmployee(Long id);


	public abstract List<Employee> findAllEmployee();


	public abstract List<Employee> findEmployeeEntries(int firstResult, int maxResults);


	public abstract Employee updateEmployee(Employee employee);


	public abstract void saveEmployee(Employee employee);
	
	public abstract List<Employee> findEmployeebyid(String searchString);
	
}