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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@NamedQueries({
		@NamedQuery(name = "findAllCategorysBySearch", 
				query = "SELECT s FROM Category s WHERE LOWER(s.categoryName) LIKE LOWER(:searchString) "
				+ "OR LOWER(s.barangay.barangayName) LIKE LOWER(:searchString)"
				+ "OR LOWER(s.city.cityName) LIKE LOWER(:searchString)"	
		),
		@NamedQuery(
				name = "countCategorybyCategoryyName",
				query = "SELECT COUNT(s) FROM Category s WHERE s.city.cityName = :cityName"
				),
		
		
		@NamedQuery(name = "countCategorybyCategoryName", 
		query = "SELECT COUNT(s) FROM Category s WHERE s.barangay.barangayName = :barangayName"
		),
		@NamedQuery(
			    name = "firstCategoryInsertedRecord",
			   query = "SELECT s FROM Category s ORDER BY s.id ASC"
			),
		@NamedQuery(
			    name = "checkIfCategoryExist",
			   query = "SELECT COUNT(s.categoryName) FROM Category s WHERE s.categoryName = :categoryName"
			),
		
		@NamedQuery(
				name = "findAllCategorysByCityId",
				query = "SELECT s FROM Category s WHERE s.city.id = :cityId"
				),
	    @NamedQuery(
	            name = "findAllCategorysByBarangayId",
	            query = "SELECT s FROM Category s WHERE s.barangay.id = :barangayId"
	            ),
	    @NamedQuery(
	    		name = "countArea",
	    		query = "SELECT b.AreaName FROM Area b WHERE LOWER(b.AreaName) = LOWER(:search)"
	    		),
	    
	    @NamedQuery(
	             name = "categoryByBarangayId",
	             query = "SELECT b.id, b.categoryName FROM Category b, Barangay c "
	               + "WHERE b.barangay = c and c.id = :barangayId"
	             
	             )
	
	   
})

@Entity
@Configurable
public class Category {

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	/**
	 */
	@NotEmpty
	@Column(unique=true, nullable=false) 
	@Size(max = 1000)
	private String categoryName;

	/**
	 */
	@ManyToOne
	private Barangay barangay;
	@ManyToOne
	private City city;

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

	/*public Long getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}*/
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public Barangay getBarangay() {
		return this.barangay;
	}

	public void setBarangay(Barangay barangay) {
		this.barangay = barangay;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(this);
	}

	public static Category fromJsonToCategory(String json) {
		return new JSONDeserializer<Category>().use(null, Category.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Category> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<Category> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<Category> fromJsonArrayToCategorys(String json) {
		return new JSONDeserializer<List<Category>>().use("values", Category.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("categoryName", "barangay","city");

	public static final EntityManager entityManager() {
		EntityManager em = new Barangay().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCategorys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Category o", Long.class).getSingleResult();
	}

	public static List<Category> findAllCategorys() {
		return entityManager().createQuery("SELECT o FROM Category o", Category.class).getResultList();
	}

	public static List<Category> findAllCategorys(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Category o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Category.class).getResultList();
	}

	public static Category findCategory(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Category.class, id);
	}

	public static List<Category> findCategoryEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Category o", Category.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<Category> findCategoryEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Category o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Category.class).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Category attached = Category.findCategory(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public Category merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Category merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
