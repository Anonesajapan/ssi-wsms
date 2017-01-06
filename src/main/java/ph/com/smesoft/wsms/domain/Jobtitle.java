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
   name = "findJobtitleByJobtitleNum",
   query = "SELECT b FROM Jobtitle b WHERE LOWER(b.JobtitleName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countJobtitle",
		   query = "SELECT b.JobtitleName FROM Jobtitle b WHERE LOWER(b.JobtitleName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class Jobtitle {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String JobtitleName;
	
	private String jobtitleDescription;
    
    

    




	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("JobtitleName");

    public static final EntityManager entityManager() {
        EntityManager em = new Jobtitle().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countJobtitle() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Jobtitle o", Long.class).getSingleResult();
    }

    public static List<Jobtitle> findAllJobtitles() {
        return entityManager().createQuery("SELECT o FROM Jobtitle o", Jobtitle.class).getResultList();
    }

    public static List<Jobtitle> findAllJobtitles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Jobtitle o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Jobtitle.class).getResultList();
    }

    public static Jobtitle findJobtitle(Long id) {
        if (id == null) return null;
        return entityManager().find(Jobtitle.class, id);
    }

    public static List<Jobtitle> findJobtitleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Jobtitle o", Jobtitle.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Jobtitle> findJobtitleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Jobtitle o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Jobtitle.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Jobtitle attached = Jobtitle.findJobtitle(this.id);
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
    public Jobtitle merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Jobtitle merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getJobtitleName() {
		return JobtitleName;
	}

	public void setJobtitleName(String jobtitleName) {
		JobtitleName = jobtitleName;
	}
	
	public String getJobtitleDescription() {
		return jobtitleDescription;
	}

	public void setJobtitleDescription(String jobtitleDescription) {
		this.jobtitleDescription = jobtitleDescription;
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

    public static Jobtitle fromJsonToJobtitle(String json) {
        return new JSONDeserializer<Jobtitle>()
        .use(null, Jobtitle.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Jobtitle> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Jobtitle> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Jobtitle> fromJsonArrayToJobtitles(String json) {
        return new JSONDeserializer<List<Jobtitle>>()
        .use("values", Jobtitle.class).deserialize(json);
    }
}
