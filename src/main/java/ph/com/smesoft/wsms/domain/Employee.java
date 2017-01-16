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
   name = "findEmployeeByid",
   query = "SELECT c FROM Employee c WHERE LOWER(c.EmployeeName) LIKE LOWER(:searchString)"
       
   ),

		   
})
public class Employee {


	@NotEmpty
	@Size(max = 100)
	private String EmployeeName;
	
	
	public String getEmployeeName() {
		return EmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getMobilenumber() {
		return Mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		Mobilenumber = mobilenumber;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getWorkEmail() {
		return WorkEmail;
	}

	public void setWorkEmail(String workEmail) {
		WorkEmail = workEmail;
	}

	public String getHomeEmail() {
		return HomeEmail;
	}

	public void setHomeEmail(String homeEmail) {
		HomeEmail = homeEmail;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Jobtitle getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(Jobtitle jobtitle) {
		this.jobtitle = jobtitle;
	}






	@NotEmpty
	@Size(max = 100)
	private String Address;
	

	@NotEmpty
	@Size(max = 100)
	private String Dayoff1;
	

	@NotEmpty
	@Size(max = 100)
	private String Dayoff2;
	

	@NotEmpty
	@Size(max = 100)
	private String EmploymentStatus;
	
	
	
	
	
	public String getDayoff1() {
		return Dayoff1;
	}

	public void setDayoff1(String dayoff1) {
		Dayoff1 = dayoff1;
	}

	public String getDayoff2() {
		return Dayoff2;
	}

	public void setDayoff2(String dayoff2) {
		Dayoff2 = dayoff2;
	}

	public String getEmploymentStatus() {
		return EmploymentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		EmploymentStatus = employmentStatus;
	}






	@NotEmpty
	@Size(max = 100)
	private String Mobilenumber;
	
	
	@NotEmpty
	@Size(max = 100)
	private String Gender;
	
	
	@NotEmpty
	@Size(max = 100)
	private String WorkEmail;
	
	@NotEmpty
	@Size(max = 100)
	private String HomeEmail;
	
	@ManyToOne
	private Department department;


	@ManyToOne
	private Jobtitle jobtitle;
	
	
	
	
	
	
	
	
    
	


	@PersistenceContext
    transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("EmployeeName","Address", "Mobilenumber", "Gender","WorkEmail","HomeEmail","Department","Jobtitle","Dayoff1","Dayoff2","EmploymentStatus");
	 


	public static final EntityManager entityManager() {
	        EntityManager em = new Employee().entityManager;
	        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	        return em;
	}
	 
	 public static long countEmployee() {
	        return entityManager().createQuery("SELECT COUNT(o) FROM Employee o", Long.class).getSingleResult();
	}
	    
	public static List<Employee> findAllEmployee() {
	        return entityManager().createQuery("SELECT o FROM Employee o", Employee.class).getResultList();
	}
	
	public static List<Employee> findAllEmployee(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Employee o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Employee.class).getResultList();
    }
	
	 public static Employee findEmployee(Long id) {
	        if (id == null) return null;
	        return entityManager().find(Employee.class, id);
	    }

	 
	public static List<Employee> findEmployeeEntries(int firstResult, int maxResults) {
	        return entityManager().createQuery("SELECT o FROM Employee o", Employee.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		}
	
	
	public static List<Employee> findEmployeeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Employee o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC". equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Employee.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	
	
	public static Employee findEmployeeId(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Employee.class, id);
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
	        	Employee attached = Employee.findEmployee(this.id);
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
	    public Employee merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        Employee merged = this.entityManager.merge(this);
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

		public static Employee fromJsonToEmployee(String json) {
		        return new JSONDeserializer<Employee>()
		        .use(null, Employee.class).deserialize(json);
		}

		public static String toJsonArray(Collection<Employee> collection) {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(collection);
		}

		public static String toJsonArray(Collection<Employee> collection, String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(collection);
		}

		public static Collection<Employee> fromJsonArrayToEmployee(String json) {
		        return new JSONDeserializer<List<Employee>>()
		        .use("values", Employee.class).deserialize(json);
		}


}
