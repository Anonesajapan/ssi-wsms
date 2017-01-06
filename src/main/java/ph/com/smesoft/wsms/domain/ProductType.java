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
   name = "findzTypeByProductTypeNum",
   query = "SELECT b FROM ProductType b WHERE LOWER(b.productTypeName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countProductType",
		   query = "SELECT b.productTypeName FROM ProductType b WHERE LOWER(b.productTypeName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class ProductType {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String productTypeName;
	
	private String productTypeDescription;
    
    

    




	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("ProductTypeName");

    public static final EntityManager entityManager() {
        EntityManager em = new ProductType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countProductType() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProductType o", Long.class).getSingleResult();
    }

    public static List<ProductType> findAllProductTypes() {
        return entityManager().createQuery("SELECT o FROM ProductType o", ProductType.class).getResultList();
    }

    public static List<ProductType> findAllProductTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ProductType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ProductType.class).getResultList();
    }

    public static ProductType findProductType(Long id) {
        if (id == null) return null;
        return entityManager().find(ProductType.class, id);
    }

    public static List<ProductType> findProductTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProductType o", ProductType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<ProductType> findProductTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ProductType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ProductType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ProductType attached = ProductType.findProductType(this.id);
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
    public ProductType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProductType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String ProductTypeName) {
		productTypeName = ProductTypeName;
	}
	
	public String getProductTypeDescription() {
		return productTypeDescription;
	}

	public void setProductTypeDescription(String ProductTypeDescription) {
		this.productTypeDescription = ProductTypeDescription;
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

    public static ProductType fromJsonToProductType(String json) {
        return new JSONDeserializer<ProductType>()
        .use(null, ProductType.class).deserialize(json);
    }

    public static String toJsonArray(Collection<ProductType> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<ProductType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<ProductType> fromJsonArrayToProductTypes(String json) {
        return new JSONDeserializer<List<ProductType>>()
        .use("values", ProductType.class).deserialize(json);
    }
}
