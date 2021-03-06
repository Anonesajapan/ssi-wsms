package ph.com.smesoft.wsms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.wsms.domain.Contact;
import ph.com.smesoft.wsms.domain.Customer;
import ph.com.smesoft.wsms.repository.ContactRepository;
import ph.com.smesoft.wsms.repository.CustomerRepository;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
	
	@PersistenceContext
	 public EntityManager em;
	
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	CustomerRepository customerRepository;
	
	public long countAllContact() {
        return contactRepository.count();
    }
	
	public void deleteContact(Contact contact) {
		contactRepository.delete(contact);
    }
	
	public Contact findContact(Long id) {
        return contactRepository.findOne(id);
    }

	public List<Contact> findAllContact() {
        return contactRepository.findAll();
    }

	public List<Contact> findContactrEntries(int firstResult, int maxResults) {
        return contactRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveContact(Contact contact) {
		contactRepository.save(contact);
    }

	public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }
	
	public List<Contact> findContactbyid(String searchString){
	    TypedQuery<Contact> searchResult = em.createNamedQuery("findContactByid", Contact.class);
	    searchResult.setParameter("searchString",'%'+searchString+'%');
	    List<Contact> result=searchResult.getResultList();
	    return result;
	 }
	
	public List<Contact> findContactDetailsByCustomerId(Long search){
		  TypedQuery<Contact> searchResult = em.createNamedQuery("findAllContactByCustomerId", Contact.class);
		  searchResult.setParameter("customerId", search);
		  List<Contact> result = searchResult.getResultList();
		  return result;
		 }

	
		

}
