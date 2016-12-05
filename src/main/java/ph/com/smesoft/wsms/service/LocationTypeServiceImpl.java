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

import ph.com.smesoft.wsms.domain.LocationType;
import ph.com.smesoft.wsms.repository.LocationTypeRepository;

@Service
@Transactional
public class LocationTypeServiceImpl implements LocationTypeService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    LocationTypeRepository LocationTypeRepository;

	public long countAllLocationTypes() {
       return LocationTypeRepository.count();
   }

	public void deleteLocationType(LocationType LocationType) {
       LocationTypeRepository.delete(LocationType);
   }

	public LocationType findLocationType(Long id) {
       return LocationTypeRepository.findOne(id);
   }

	public List<LocationType> findAllLocationTypes() {
       return LocationTypeRepository.findAll();
   }

	public List<LocationType> findLocationTypeEntries(int firstResult, int maxResults) {
       return LocationTypeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveLocationType(LocationType LocationType) {
       LocationTypeRepository.save(LocationType);
   }

	public LocationType updateLocationType(LocationType LocationType) {
       return LocationTypeRepository.save(LocationType);
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
	
	public List<LocationType> findLocationTypebyLocationTypeNumber(String searchString){
	    TypedQuery<LocationType> searchResult = em.createNamedQuery("findLocationTypeByLocationTypeNum", LocationType.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<LocationType> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfLocationTypeExist(String searchString){
	    TypedQuery<LocationType> searchResult = em.createNamedQuery("countLocationType", LocationType.class);
	    searchResult.setParameter("search",searchString);
	    List<LocationType> result = searchResult.getResultList();
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

