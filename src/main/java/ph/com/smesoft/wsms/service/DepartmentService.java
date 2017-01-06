package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Department;

public interface DepartmentService {

		public abstract long countAllDepartments();


		public abstract void deleteDepartment(Department department);


		public abstract Department findDepartment(Long id);


		public abstract List<Department> findAllDepartments();


		public abstract List<Department> findDepartmentEntries(int firstResult, int maxResults);


		public abstract void saveDepartment(Department department);


		public abstract Department updateDepartment(Department department);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Department> findDepartmentbyDepartmentNumber(String searchString);


		public abstract long checkIfDepartmentExist(String Department);
		public boolean checkRegex(String input, String user_pattern);

}
