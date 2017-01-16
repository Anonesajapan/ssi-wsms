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
	@NamedQuery(
			name = "findProductDetailsBySalesId",
			query = "SELECT o FROM OrderDetails o, Order s WHERE o.order = s and s.id = :orderDetailsId"
		)
})
@Entity
@Configurable
public class OrderDetails {
	
	private String productId;
	
	private String productName;
	
	private String brandName;

	private int quantity;
	
	private double price;
	
	private double subtotal;
	
	@ManyToOne
	private Order order;
	
	public OrderDetails() {
	}

	public OrderDetails(Order order, String productId, String productName, String brandName, int quantity, double price, double subtotal){
		this.order = order;
		this.productId = productId;
		this.productName = productName;
		this.brandName = brandName;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public static OrderDetails fromJsonToOrderdetails(String json) {
		return new JSONDeserializer<OrderDetails>().use(null, OrderDetails.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrderDetails> collection) {
		return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	}

	public static String toJsonArray(Collection<OrderDetails> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").deepSerialize(collection);
	}

	public static Collection<OrderDetails> fromJsonArrayToOrderdetailss(String json) {
		return new JSONDeserializer<List<OrderDetails>>().use("values", OrderDetails.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("productId", "quantity");

	public static final EntityManager entityManager() {
		EntityManager em = new OrderDetails().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countOrderdetailss() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Order o", Long.class).getSingleResult();
	}

	public static List<OrderDetails> findAllOrderdetailss() {
		return entityManager().createQuery("SELECT o FROM Order o", OrderDetails.class).getResultList();
	}

	public static List<OrderDetails> findAllOrderdetailss(String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Order o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, OrderDetails.class).getResultList();
	}

	public static OrderDetails findOrderdetails(Long id) {
		if (id == null)
			return null;
		return entityManager().find(OrderDetails.class, id);
	}

	public static List<OrderDetails> findOrderdetailsEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Order o", OrderDetails.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<OrderDetails> findOrderdetailsEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Order o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, OrderDetails.class).setFirstResult(firstResult).setMaxResults(maxResults)
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
			OrderDetails attached = OrderDetails.findOrderdetails(this.id);
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
	public OrderDetails merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrderDetails merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
