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
   name = "findDepartmentByDepartmentNum",
   query = "SELECT b FROM Department b WHERE LOWER(b.DepartmentName) LIKE LOWER(:searchString) "
   ),
@NamedQuery(
		   name = "countDepartment",
		   query = "SELECT b.DepartmentName FROM Department b WHERE LOWER(b.DepartmentName) = LOWER(:search) "
)
})

@Configurable
@Entity
public class Department {

    /**
     */
	@Column(unique=true)
    @Size(min=1, max=30)
    private String DepartmentName;
	
	private String departmentDescription;
    
    

    




	@PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("DepartmentName");

    public static final EntityManager entityManager() {
        EntityManager em = new Department().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countDepartment() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Department o", Long.class).getSingleResult();
    }

    public static List<Department> findAllDepartments() {
        return entityManager().createQuery("SELECT o FROM Department o", Department.class).getResultList();
    }

    public static List<Department> findAllDepartments(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Department o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Department.class).getResultList();
    }

    public static Department findDepartment(Long id) {
        if (id == null) return null;
        return entityManager().find(Department.class, id);
    }

    public static List<Department> findDepartmentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Department o", Department.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Department> findDepartmentEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Department o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Department.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Department attached = Department.findDepartment(this.id);
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
    public Department merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Department merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

   


	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}
	
	public String getDepartmentDescription() {
		return departmentDescription;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
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

    public static Department fromJsonToDepartment(String json) {
        return new JSONDeserializer<Department>()
        .use(null, Department.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Department> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

    public static String toJsonArray(Collection<Department> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Department> fromJsonArrayToDepartments(String json) {
        return new JSONDeserializer<List<Department>>()
        .use("values", Department.class).deserialize(json);
    }
}
