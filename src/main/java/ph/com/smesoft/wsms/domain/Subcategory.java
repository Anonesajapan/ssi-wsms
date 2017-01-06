package ph.com.smesoft.wsms.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
   name = "findSubcategoryBySubcategoryNum",
   query = "SELECT b FROM Subcategory b WHERE LOWER(b.SubcategoryName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countSubcategory",
		   query = "SELECT b.SubcategoryName FROM Subcategory b WHERE LOWER(b.SubcategoryName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class Subcategory {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String SubcategoryName;
	
	private String subcategoryDescription;
    
	@ManyToOne
	private Category category;

    







	public String getSubcategoryName() {
		return SubcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		SubcategoryName = subcategoryName;
	}

	public String getSubcategoryDescription() {
		return subcategoryDescription;
	}

	public void setSubcategoryDescription(String subcategoryDescription) {
		this.subcategoryDescription = subcategoryDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}




	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("SubcategoryName");

    public static final EntityManager entityManager() {
        EntityManager em = new Subcategory().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countSubcategory() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Subcategory o", Long.class).getSingleResult();
    }

    public static List<Subcategory> findAllSubcategorys() {
        return entityManager().createQuery("SELECT o FROM Subcategory o", Subcategory.class).getResultList();
    }

    public static List<Subcategory> findAllSubcategorys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Subcategory o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Subcategory.class).getResultList();
    }

    public static Subcategory findSubcategory(Long id) {
        if (id == null) return null;
        return entityManager().find(Subcategory.class, id);
    }

    public static List<Subcategory> findSubcategoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Subcategory o", Subcategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Subcategory> findSubcategoryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Subcategory o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Subcategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Subcategory attached = Subcategory.findSubcategory(this.id);
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
    public Subcategory merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Subcategory merged = this.entityManager.merge(this);
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

    public static Subcategory fromJsonToSubcategory(String json) {
        return new JSONDeserializer<Subcategory>()
        .use(null, Subcategory.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Subcategory> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Subcategory> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Subcategory> fromJsonArrayToSubcategorys(String json) {
        return new JSONDeserializer<List<Subcategory>>()
        .use("values", Subcategory.class).deserialize(json);
    }
}
