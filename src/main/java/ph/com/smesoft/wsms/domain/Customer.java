package ph.com.smesoft.wsms.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@NamedQueries({
@NamedQuery(
   name = "findCustomerByid",
   query = "SELECT c FROM Customer c WHERE LOWER(c.CustomerName) LIKE LOWER(:searchString)"
       
   )
})
public class Customer {


	@NotEmpty
	@Size(max = 100)
	private String CustomerName;
	
	@NotEmpty
	@Size(max = 12)
	private String ContactNumber;
	
	@NotEmpty
	@Size(max = 100)
	private String Website;
	
	@NotEmpty
	@Size(max = 100)
	private String Email;
	
	
	
	
	
	@ManyToOne
	private IndustryType industryType;


	@ManyToOne
	private CustomerType customerType;
	@ManyToOne
	private LocationType locationType;
	
	@ManyToOne
	private City city;
	@ManyToOne 
	private Barangay barangay;
	@ManyToOne
	private Street street;
	@ManyToOne 
	private Area area;
	
	
	
	
	
	
    
		public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

		public String getCustomerName() {
			return this.CustomerName;
		}

		public void setCustomerName(String customerName) {
			this.CustomerName = customerName;
		}

		public IndustryType getIndustryType() {
			return industryType;
		}

		public void setIndustryType(IndustryType industryType) {
			this.industryType = industryType;
		}

		public CustomerType getCustomerType() {
			return customerType;
		}

		public void setCustomerType(CustomerType customerType) {
			this.customerType = customerType;
		}

		public LocationType getLocationType() {
			return locationType;
		}

		public void setLocationType(LocationType locationType) {
			this.locationType = locationType;
		}


	@PersistenceContext
    transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("CustomerName","IndustryType", "CustomerType", "LocationType","City","Barangay","Street","Area","ContactNumber", "Website", "Email");
	 
	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	public String getWebsite() {
		return Website;
	}

	public void setWebsite(String website) {
		Website = website;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Barangay getBarangay() {
		return barangay;
	}

	public void setBarangay(Barangay barangay) {
		this.barangay = barangay;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public static final EntityManager entityManager() {
	        EntityManager em = new Customer().entityManager;
	        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	        return em;
	}
	 
	 public static long countCustomer() {
	        return entityManager().createQuery("SELECT COUNT(o) FROM Customer o", Long.class).getSingleResult();
	}
	    
	public static List<Customer> findAllCustomer() {
	        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).getResultList();
	}
	
	public static List<Customer> findAllCustomer(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Customer o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Customer.class).getResultList();
    }
	
	 public static Customer findCustomer(Long id) {
	        if (id == null) return null;
	        return entityManager().find(Customer.class, id);
	    }

	 
	public static List<Customer> findCustomerEntries(int firstResult, int maxResults) {
	        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		}
	
	
	public static List<Customer> findCustomerEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Customer o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC". equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Customer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	
	
	public static Customer findCustomerId(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Customer.class, id);
	}
	
	 	@Transactional
	    public void persist() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        this.entityManager.persist(this);
	    }

	    @Transactional
	    public void remove() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        if (this.entityManager.contains(this)) {
	            this.entityManager.remove(this);
	        } else {
	            Customer attached = Customer.findCustomer(this.id);
	            this.entityManager.remove(attached);
	        }
	    }
	    
	    @Transactional
	    public void flush() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        this.entityManager.flush();
	    }

	    @Transactional
	    public void clear() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        this.entityManager.clear();
	    }
	    

	    @Transactional
	    public Customer merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        Customer merged = this.entityManager.merge(this);
	        this.entityManager.flush();
	        return merged;
	    }
	    
	    public String toString() {
	        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	    }





	
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id")
	    private Long id;
	


		@Version
	    @Column(name = "version")
	    private Integer version;

	

		public Long getId() {
	        return this.id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	   
		public Integer getVersion() {
	        return this.version;
	    }
	    public void setVersion(Integer version) {
	        this.version = version;
	    }
	    
	        
		public String toJson() {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(this);
		}

		public String toJson(String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(this);
		}

		public static Customer fromJsonToCustomer(String json) {
		        return new JSONDeserializer<Customer>()
		        .use(null, Customer.class).deserialize(json);
		}

		public static String toJsonArray(Collection<Customer> collection) {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(collection);
		}

		public static String toJsonArray(Collection<Customer> collection, String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(collection);
		}

		public static Collection<Customer> fromJsonArrayToCustomer(String json) {
		        return new JSONDeserializer<List<Customer>>()
		        .use("values", Customer.class).deserialize(json);
		}


}
