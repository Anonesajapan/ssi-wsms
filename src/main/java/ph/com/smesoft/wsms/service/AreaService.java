package ph.com.smesoft.wsms.service;



import java.util.List;

import ph.com.smesoft.wsms.domain.Area;

public interface AreaService {

		public abstract long countAllAreas();


		public abstract void deleteArea(Area barangay);


		public abstract Area findArea(Long id);


		public abstract List<Area> findAllAreas();


		public abstract List<Area> findAreaEntries(int firstResult, int maxResults);


		public abstract void saveArea(Area barangay);


		public abstract Area updateArea(Area barangay);
		
		public List<Area> findAllAreaByStreetId(Long streetId);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Area> findAreabyAreaNumber(String searchString);


		public abstract long checkIfAreaExist(String Area);
		public boolean checkRegex(String input, String user_pattern);

}
