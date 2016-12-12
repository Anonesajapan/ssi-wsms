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


import javax.persistence.ManyToOne;


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
			name = "findAllAreaBySearch",
			query = "SELECT b FROM Area b WHERE LOWER(b.AreaName) LIKE LOWER(:searchString)"
			+  "OR LOWER(b.city.cityName) LIKE LOWER(:searchString)"
			+  "OR LOWER(b.barangay.barangayName) LIKE LOWER(:searchString)"
			+  "OR LOWER(b.street.streetName) LIKE LOWER(:searchString)"
			),
	@NamedQuery(
			name = "countAreabyAreasName",
			query = "SELECT COUNT(b) FROM Area b WHERE b.city.cityName = :cityName"
			),
	@NamedQuery(
			name = "countAreabyAreaNames",
			query ="SELECT COUNT(b) FROM Area b WHERE b.barangay.barangayName = :barangayName"
			),
	@NamedQuery(
			
			name = "countAreabyAreaNamed" ,
			query = "SELECT COUNT(b) FROM Area b WHERE b.street.streetName = :streetName"
			),
	@NamedQuery(
			name = "firstAreaInsertedRecord",
			query = "SELECT b FROM Area b ORDER BY b.id ASC"
			),
	@NamedQuery(
			name = "checkIfAreaExist",
			query = "SELECT COUNT(b.AreaName) FROM Area b WHERE b.AreaName = :areaName "
			),
	
	@NamedQuery(
			name = "findAllAreaByCityId",
			query = "SELECT b FROM Area b WHERE b.city.id = :cityId"
			),

	@NamedQuery(
			name = "findAllAreaByBarangayId",
			query = "SELECT b FROM Area b WHERE b.barangay.id = :barangayId"
			),

	@NamedQuery(
			name = "findAllAreaByStreetId",
			query = "SELECT b FROM Area b WHERE b.street.id = :streetId"
			),
	
	

})

@Configurable
@Entity
public class Area {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String AreaName;
	
	@ManyToOne
	private City city;
	@ManyToOne
	private Barangay barangay;
	@ManyToOne
	private Street street;
	
    
    

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("city","barangay","street","AreaName");

    public static final EntityManager entityManager() {
        EntityManager em = new Area().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
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

	public static long countArea() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Area o", Long.class).getSingleResult();
    }

    public static List<Area> findAllAreas() {
        return entityManager().createQuery("SELECT o FROM Area o", Area.class).getResultList();
    }

    public static List<Area> findAllAreas(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Area o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Area.class).getResultList();
    }

    public static Area findArea(Long id) {
        if (id == null) return null;
        return entityManager().find(Area.class, id);
    }

    public static List<Area> findAreaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Area o", Area.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Area> findAreaEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Area o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Area.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Area attached = Area.findArea(this.id);
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
    public Area merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Area merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getAreaName() {
		return AreaName;
	}

	public void setAreaName(String areaName) {
		AreaName = areaName;
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

    public static Area fromJsonToArea(String json) {
        return new JSONDeserializer<Area>()
        .use(null, Area.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Area> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Area> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Area> fromJsonArrayToAreas(String json) {
        return new JSONDeserializer<List<Area>>()
        .use("values", Area.class).deserialize(json);
    }
}
