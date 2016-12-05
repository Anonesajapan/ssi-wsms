package ph.com.smesoft.wsms.service;


import java.util.List;

import ph.com.smesoft.wsms.domain.LocationType;

public interface LocationTypeService {

		public abstract long countAllLocationTypes();


		public abstract void deleteLocationType(LocationType barangay);


		public abstract LocationType findLocationType(Long id);


		public abstract List<LocationType> findAllLocationTypes();


		public abstract List<LocationType> findLocationTypeEntries(int firstResult, int maxResults);


		public abstract void saveLocationType(LocationType barangay);


		public abstract LocationType updateLocationType(LocationType barangay);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<LocationType> findLocationTypebyLocationTypeNumber(String searchString);


		public abstract long checkIfLocationTypeExist(String LocationType);
		public boolean checkRegex(String input, String user_pattern);

}
