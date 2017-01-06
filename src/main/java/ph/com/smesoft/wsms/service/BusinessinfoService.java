package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Businessinfo;

public interface BusinessinfoService {
	public abstract long countAllBusinessinfos();


	public abstract void deleteBusinessinfo(Businessinfo business);


	public abstract Businessinfo findBusinessinfo(Long id);


	public abstract List<Businessinfo> findAllBusinessinfos();


	public abstract List<Businessinfo> findBusinessinfoEntries(int firstResult, int maxResults);


	public abstract void saveBusinessinfo(Businessinfo business);


	public abstract Businessinfo updateBusinessinfo(Businessinfo business);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Businessinfo> findBusinessinfobyBusinessinfoNumber(String searchString);
	
	
	public boolean checkRegex(String input, String user_pattern);
	
	public abstract long checkIfBusinessInfoNameExist(String name);

}
