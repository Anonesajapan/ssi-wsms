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

import ph.com.smesoft.wsms.domain.Area;
import ph.com.smesoft.wsms.repository.AreaRepository;

@Service
@Transactional
public class AreaServiceImpl implements AreaService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    AreaRepository AreaRepository;

	public long countAllAreas() {
       return AreaRepository.count();
   }

	public void deleteArea(Area Area) {
       AreaRepository.delete(Area);
   }

	public Area findArea(Long id) {
       return AreaRepository.findOne(id);
   }

	public List<Area> findAllAreas() {
       return AreaRepository.findAll();
   }

	public List<Area> findAreaEntries(int firstResult, int maxResults) {
       return AreaRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveArea(Area Area) {
       AreaRepository.save(Area);
   }

	public Area updateArea(Area Area) {
       return AreaRepository.save(Area);
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
	
	public List<Area> findAreabyAreaNumber(String searchString){
	    TypedQuery<Area> searchResult = em.createNamedQuery("findAreaByAreaNum", Area.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Area> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfAreaExist(String searchString){
	    TypedQuery<Area> searchResult = em.createNamedQuery("countArea", Area.class);
	    searchResult.setParameter("search",searchString);
	    List<Area> result = searchResult.getResultList();
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

