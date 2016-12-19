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
   name = "findLocationTypeByLocationTypeNum",
   query = "SELECT b FROM LocationType b WHERE LOWER(b.LocationTypeName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countLocationType",
		   query = "SELECT b.LocationTypeName FROM LocationType b WHERE LOWER(b.LocationTypeName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class LocationType {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String LocationTypeName;
	
	private String LocationTypeDescription;
    



	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("LocationTypeName");

    public static final EntityManager entityManager() {
        EntityManager em = new LocationType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countLocationType() {
        return entityManager().createQuery("SELECT COUNT(o) FROM LocationType o", Long.class).getSingleResult();
    }

    public static List<LocationType> findAllLocationTypes() {
        return entityManager().createQuery("SELECT o FROM LocationType o", LocationType.class).getResultList();
    }

    public static List<LocationType> findAllLocationTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM LocationType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, LocationType.class).getResultList();
    }

    public static LocationType findLocationType(Long id) {
        if (id == null) return null;
        return entityManager().find(LocationType.class, id);
    }

    public static List<LocationType> findLocationTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM LocationType o", LocationType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<LocationType> findLocationTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM LocationType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, LocationType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            LocationType attached = LocationType.findLocationType(this.id);
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
    public LocationType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        LocationType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getLocationTypeName() {
		return LocationTypeName;
	}

	public void setLocationTypeName(String locationTypeName) {
		LocationTypeName = locationTypeName;
	}
	
	public String getLocationTypeDescription() {
		return LocationTypeDescription;
	}

	public void setLocationTypeDescription(String locationTypeDescription) {
		LocationTypeDescription = locationTypeDescription;
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

    public static LocationType fromJsonToLocationType(String json) {
        return new JSONDeserializer<LocationType>()
        .use(null, LocationType.class).deserialize(json);
    }

    public static String toJsonArray(Collection<LocationType> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<LocationType> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<LocationType> fromJsonArrayToLocationTypes(String json) {
        return new JSONDeserializer<List<LocationType>>()
        .use("values", LocationType.class).deserialize(json);
    }
}
