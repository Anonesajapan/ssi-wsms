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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@NamedQueries({
@NamedQuery(
   name = "findBusinesscontactByBusinesscontactNum",
   query = "SELECT b FROM Businesscontact b WHERE LOWER(b.businessContactName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countBusinessContact",
		   query = "SELECT f.businessContactName FROM Businesscontact f WHERE LOWER(f.businessContactName) = LOWER(:search) "
),



})

@Configurable
@Entity
public class Businesscontact {

    /**
     */
                @NotEmpty
			    @Size(min=1, max = 30)
			    private String businessContactName;

                @NotEmpty
                @Size(min=1, max = 30)
                private String EmailName;
                
                @NotEmpty
                @Size(min=1, max = 30)
                private String PhoneoneName;
                
                @NotEmpty
                @Size(min=1, max = 30)
                private String PhonetwoName;
                
                @NotEmpty
                @Size(min=1, max = 30)
                private String FaxnoName;
                
                @NotEmpty
                @Size(min=1, max = 30)
                private String OffdayoneName;
                
                @NotEmpty
                @Size(min=1, max = 30)
                private String OffdaytwoName;
    
    

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("businessContactName","EmailName","PhoneoneName","PhonetwoName","FaxnoName","Offdayone","Offdaytwo");

    public static final EntityManager entityManager() {
        EntityManager em = new Businesscontact().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countBusinesscontact() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Businesscontact o", Long.class).getSingleResult();
    }

    public static List<Businesscontact> findAllBusinesscontacts() {
        return entityManager().createQuery("SELECT o FROM Businesscontact o", Businesscontact.class).getResultList();
    }

    public static List<Businesscontact> findAllBusinesscontacts(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Businesscontact o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Businesscontact.class).getResultList();
    }

    public static Businesscontact findBusinesscontact(Long id) {
        if (id == null) return null;
        return entityManager().find(Businesscontact.class, id);
    }

    public static List<Businesscontact> findBusinesscontactEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Businesscontact o", Businesscontact.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Businesscontact> findBusinesscontactEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Businesscontact o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Businesscontact.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
        	Businesscontact attached = Businesscontact.findBusinesscontact(this.id);
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
    public Businesscontact merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Businesscontact merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getBusinessContactName() {
		return businessContactName;
	}

	public void setBusinessContactName(String businessContactName) {
		this.businessContactName = businessContactName;
	}

	
	
	

	

	public String getEmailName() {
		return EmailName;
	}

	public void setEmailName(String emailName) {
		EmailName = emailName;
	}

	public String getPhoneoneName() {
		return PhoneoneName;
	}

	public void setPhoneoneName(String phoneoneName) {
		PhoneoneName = phoneoneName;
	}

	public String getPhonetwoName() {
		return PhonetwoName;
	}

	public void setPhonetwoName(String phonetwoName) {
		PhonetwoName = phonetwoName;
	}

	public String getFaxnoName() {
		return FaxnoName;
	}

	public void setFaxnoName(String faxnoName) {
		FaxnoName = faxnoName;
	}

	public String getOffdayoneName() {
		return OffdayoneName;
	}

	public void setOffdayoneName(String offdayoneName) {
		OffdayoneName = offdayoneName;
	}

	public String getOffdaytwoName() {
		return OffdaytwoName;
	}

	public void setOffdaytwoName(String offdaytwoName) {
		OffdaytwoName = offdaytwoName;
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

    public static Businesscontact fromJsonToBusinesscontact(String json) {
        return new JSONDeserializer<Businesscontact>()
        .use(null, Businesscontact.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Businesscontact> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Businesscontact> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Businesscontact> fromJsonArrayToBusinesscontacts(String json) {
        return new JSONDeserializer<List<Businesscontact>>()
        .use("values", Businesscontact.class).deserialize(json);
    }
}
	