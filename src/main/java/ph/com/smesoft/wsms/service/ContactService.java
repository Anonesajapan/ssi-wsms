package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Contact;

public interface ContactService {

	public abstract long countAllContact();


	public abstract void deleteContact(Contact customer);


	public abstract Contact findContact(Long id);


	public abstract List<Contact> findAllContact();


	public abstract List<Contact> findContactrEntries(int firstResult, int maxResults);


	public abstract Contact updateContact(Contact customer);


	public abstract void saveContact(Contact customer);
	
	
	public abstract List<Contact> findContactbyid(String searchString);
	
	public List<Contact> findContactDetailsByCustomerId(Long search);
	
}