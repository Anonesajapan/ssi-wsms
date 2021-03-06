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
   name = "findBrandByBrandNum",
   query = "SELECT b FROM Brand b WHERE LOWER(b.BrandName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countBrand",
		   query = "SELECT b.BrandName FROM Brand b WHERE LOWER(b.BrandName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class Brand {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String BrandName;
	
	private String brandDescription;
    
    

    




	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("BrandName");

    public static final EntityManager entityManager() {
        EntityManager em = new Brand().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countBrand() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Brand o", Long.class).getSingleResult();
    }

    public static List<Brand> findAllBrands() {
        return entityManager().createQuery("SELECT o FROM Brand o", Brand.class).getResultList();
    }

    public static List<Brand> findAllBrands(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Brand o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Brand.class).getResultList();
    }

    public static Brand findBrand(Long id) {
        if (id == null) return null;
        return entityManager().find(Brand.class, id);
    }

    public static List<Brand> findBrandEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Brand o", Brand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Brand> findBrandEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Brand o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Brand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Brand attached = Brand.findBrand(this.id);
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
    public Brand merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Brand merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getBrandName() {
		return BrandName;
	}

	public void setBrandName(String brandName) {
		BrandName = brandName;
	}
	
	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
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

    public static Brand fromJsonToBrand(String json) {
        return new JSONDeserializer<Brand>()
        .use(null, Brand.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Brand> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Brand> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Brand> fromJsonArrayToBrands(String json) {
        return new JSONDeserializer<List<Brand>>()
        .use("values", Brand.class).deserialize(json);
    }
}
