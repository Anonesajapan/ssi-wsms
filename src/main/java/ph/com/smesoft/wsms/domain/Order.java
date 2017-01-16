package ph.com.smesoft.wsms.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@NamedQueries({
	@NamedQuery(
		name = "findLastOrderNum", 
		query = "SELECT s FROM Order s"
	),
	@NamedQuery(
		name = "showSales",
		query = "SELECT s FROM Order s"
	)
})
public class Order {

	@ManyToOne
	private Customer customer;
	
	private String orderDate;
	
	private String totalAmount;
	private String amountRender;
	

	@PersistenceContext
    transient EntityManager entityManager;
	
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList();
	 
	public static final EntityManager entityManager() {
	        EntityManager em = new Order().entityManager;
	        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	        return em;
	}
	 
	 public static long countOrder() {
	        return entityManager().createQuery("SELECT COUNT(o) FROM Order o", Long.class).getSingleResult();
	}
	    
	public static List<Order> findAllOrder() {
	        return entityManager().createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}

	public static List<Order> findAllOrder(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Order o WHERE o.customer IS NOT NULL";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Order.class).getResultList();
    }
	
	 public static Order findOrder(Long id) {
	        if (id == null) return null;
	        return entityManager().find(Order.class, id);
	    }

	 
	public static List<Order> findOrderEntries(int firstResult, int maxResults) {
	        return entityManager().createQuery("SELECT o FROM Order o", Order.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	public static List<Order> findOrderEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Order o WHERE o.customer IS NOT NULL";
         if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC". equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Order.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
	
	
	public static Order findOrderId(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Order.class, id);
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
	            Order attached = Order.findOrder(this.id);
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
	    public Order merge() {
	        if (this.entityManager == null) this.entityManager = entityManager();
	        Order merged = this.entityManager.merge(this);
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
	    
	    public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		public String getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(String date) {
			this.orderDate = date;
		}

		
		public String getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}

		public String getAmountRender() {
			return amountRender;
		}

		public void setAmountRender(String amountRender) {
			this.amountRender = amountRender;
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

		public static Order fromJsonToSales(String json) {
		        return new JSONDeserializer<Order>()
		        .use(null, Order.class).deserialize(json);
		}

		public static String toJsonArray(Collection<Order> collection) {
		        return new JSONSerializer()
		        .exclude("*.class").deepSerialize(collection);
		}

		public static String toJsonArray(Collection<Order> collection, String[] fields) {
		        return new JSONSerializer()
		        .include(fields).exclude("*.class").deepSerialize(collection);
		}

		public static Collection<Order> fromJsonArrayToSales(String json) {
		        return new JSONDeserializer<List<Order>>()
		        .use("values", Order.class).deserialize(json);
		}


}
