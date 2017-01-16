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

import ph.com.smesoft.wsms.domain.Unit;
import ph.com.smesoft.wsms.repository.UnitRepository;

@Service
@Transactional
public class UnitServiceImpl implements UnitService{
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
    UnitRepository UnitRepository;

	public long countAllUnits() {
       return UnitRepository.count();
   }

	public void deleteUnit(Unit Unit) {
       UnitRepository.delete(Unit);
   }

	public Unit findUnit(Long id) {
       return UnitRepository.findOne(id);
   }

	public List<Unit> findAllUnits() {
       return UnitRepository.findAll();
   }

	public List<Unit> findUnitEntries(int firstResult, int maxResults) {
       return UnitRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
   }

	public void saveUnit(Unit Unit) {
       UnitRepository.save(Unit);
   }

	public Unit updateUnit(Unit Unit) {
       return UnitRepository.save(Unit);
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
	
	public List<Unit> findUnitbyUnitNumber(String searchString){
	    TypedQuery<Unit> searchResult = em.createNamedQuery("findUnitByUnitNum", Unit.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Unit> result=searchResult.getResultList();
	    return result;
	 }
	
	public long checkIfUnitExist(String searchString){
	    TypedQuery<Unit> searchResult = em.createNamedQuery("countUnit", Unit.class);
	    searchResult.setParameter("search",searchString);
	    List<Unit> result = searchResult.getResultList();
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
