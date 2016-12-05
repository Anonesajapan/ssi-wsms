package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.IndustryType;

public interface IndustryTypeService {
	public abstract long countAllIndustrytypes();


	public abstract void deleteIndustrytype(IndustryType industry);


	public abstract IndustryType findIndustrytype(Long id);


	public abstract List<IndustryType> findAllIndustrytypes();


	public abstract List<IndustryType> findIndustrytypeEntries(int firstResult, int maxResults);


	public abstract void saveIndustrytype(IndustryType industry);


	public abstract IndustryType updateIndustrytype(IndustryType industry);

    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
    //public abstract String findFloorbyFloorNumber(String searchString);
	
	public abstract List<IndustryType> findIndustrytypebyIndustrytypeNumber(String searchString);
	
	public abstract long checkIfIndustryTypeExist(String industryType);
	
	public boolean checkRegex(String input, String user_pattern);

}
