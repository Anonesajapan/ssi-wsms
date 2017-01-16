package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Unit;

public interface UnitService {

		public abstract long countAllUnits();


		public abstract void deleteUnit(Unit unit);


		public abstract Unit findUnit(Long id);


		public abstract List<Unit> findAllUnits();


		public abstract List<Unit> findUnitEntries(int firstResult, int maxResults);


		public abstract void saveUnit(Unit unit);


		public abstract Unit updateUnit(Unit unit);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Unit> findUnitbyUnitNumber(String searchString);


		public abstract long checkIfUnitExist(String Unit);
		public boolean checkRegex(String input, String user_pattern);

}
