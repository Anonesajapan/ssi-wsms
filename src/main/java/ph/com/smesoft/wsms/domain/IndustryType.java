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
   name = "findIndustrytypeByIndustrytypeNum",
   query = "SELECT b FROM IndustryType b WHERE LOWER(b.industryTypeName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countIndustryType",
		   query = "SELECT f.industryTypeName FROM IndustryType f WHERE LOWER(f.industryTypeName) = LOWER(:search) "
)

})

@Configurable
@Entity
public class IndustryType {

    /**
     */
	
	@Column(unique=true)
    @Size(min=1, max=30)
	@NotEmpty
	private String industryTypeName;
	
	private String industryTypeDescription;
    
    


	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("industryTypeName");

    public static final EntityManager entityManager() {
        EntityManager em = new IndustryType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countIndustrytype() {
        return entityManager().createQuery("SELECT COUNT(o) FROM IndustryType o", Long.class).getSingleResult();
    }

    public static List<IndustryType> findAllIndustrytypes() {
        return entityManager().createQuery("SELECT o FROM IndustryType o", IndustryType.class).getResultList();
    }

    public static List<IndustryType> findAllIndustrytypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM IndustryType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, IndustryType.class).getResultList();
    }

    public static IndustryType findIndustrytype(Long id) {
        if (id == null) return null;
        return entityManager().find(IndustryType.class, id);
    }

    public static List<IndustryType> findIndustrytypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM IndustryType o", IndustryType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<IndustryType> findIndustrytypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM IndustryType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, IndustryType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            IndustryType attached = IndustryType.findIndustrytype(this.id);
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
    public IndustryType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        IndustryType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getIndustryTypeName() {
		return industryTypeName;
	}

	public void setIndustryTypeName(String industryTypeName) {
		this.industryTypeName = industryTypeName;
	}
	

    public String getIndustryTypeDescription() {
		return industryTypeDescription;
	}

	public void setIndustryTypeDescription(String industryTypeDescription) {
		this.industryTypeDescription = industryTypeDescription;
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

    public static IndustryType fromJsonToIndustrytype(String json) {
        return new JSONDeserializer<IndustryType>()
        .use(null, IndustryType.class).deserialize(json);
    }

    public static String toJsonArray(Collection<IndustryType> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<IndustryType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<IndustryType> fromJsonArrayToIndustrytypes(String json) {
        return new JSONDeserializer<List<IndustryType>>()
        .use("values", IndustryType.class).deserialize(json);
    }
}
