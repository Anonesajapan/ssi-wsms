package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Businesscontact;

public interface BusinesscontactService {
	public abstract long countAllBusinesscontacts();


	public abstract void deleteBusinesscontact(Businesscontact business);


	public abstract Businesscontact findBusinesscontact(Long id);


	public abstract List<Businesscontact> findAllBusinesscontacts();


	public abstract List<Businesscontact> findBusinesscontactEntries(int firstResult, int maxResults);


	public abstract void saveBusinesscontact(Businesscontact business);


	public abstract Businesscontact updateBusinesscontact(Businesscontact business);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<Businesscontact> findBusinesscontactbyBusinesscontactNumber(String searchString);
	
	
	public boolean checkRegex(String input, String user_pattern);
	
	public abstract long checkIfBusinessContactNameExist(String name);

}
