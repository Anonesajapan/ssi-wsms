package ph.com.smesoft.wsms.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.NotNull;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@NamedQueries({
@NamedQuery(
   name = "findCustomerTypeByCustomerTypeNum",
   query = "SELECT b FROM CustomerType b WHERE LOWER(b.CustomerTypeName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countCustomerType",
		   query = "SELECT b.CustomerTypeName FROM CustomerType b WHERE LOWER(b.CustomerTypeName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class CustomerType {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String CustomerTypeName;
	
	private String customerTypeDescription;
    
    

    




	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("CustomerTypeName");

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerType() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerType o", Long.class).getSingleResult();
    }

    public static List<CustomerType> findAllCustomerTypes() {
        return entityManager().createQuery("SELECT o FROM CustomerType o", CustomerType.class).getResultList();
    }

    public static List<CustomerType> findAllCustomerTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM CustomerType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, CustomerType.class).getResultList();
    }

    public static CustomerType findCustomerType(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerType.class, id);
    }

    public static List<CustomerType> findCustomerTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerType o", CustomerType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<CustomerType> findCustomerTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM CustomerType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, CustomerType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CustomerType attached = CustomerType.findCustomerType(this.id);
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
    public CustomerType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getCustomerTypeName() {
		return CustomerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		CustomerTypeName = customerTypeName;
	}
	
	public String getCustomerTypeDescription() {
		return customerTypeDescription;
	}

	public void setCustomerTypeDescription(String customerTypeDescription) {
		this.customerTypeDescription = customerTypeDescription;
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

    public static CustomerType fromJsonToCustomerType(String json) {
        return new JSONDeserializer<CustomerType>()
        .use(null, CustomerType.class).deserialize(json);
    }

    public static String toJsonArray(Collection<CustomerType> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<CustomerType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<CustomerType> fromJsonArrayToCustomerTypes(String json) {
        return new JSONDeserializer<List<CustomerType>>()
        .use("values", CustomerType.class).deserialize(json);
    }
}
