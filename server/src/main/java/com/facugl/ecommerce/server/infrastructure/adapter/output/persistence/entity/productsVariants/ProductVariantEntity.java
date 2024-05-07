package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.productsVariants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products_variants")
public class ProductVariantEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "images", nullable = false)
	private List<String> images;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "sku", unique = true, nullable = false)
	private String sku;

	@Column(name = "stock", nullable = false)
	private Integer stock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "products_id", nullable = false)
	private ProductEntity product;

	@Builder.Default
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "products_details", joinColumns = @JoinColumn(name = "products_variants_id"), inverseJoinColumns = @JoinColumn(name = "variants_values_id"))
	private Set<VariantValueEntity> variantsValues = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductVariantEntity other = (ProductVariantEntity) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductVariantEntity{" +
				"id=" + id +
				", description='" + description + '\'' +
				", images=" + images +
				", price=" + price +
				", sku='" + sku + '\'' +
				", stock=" + stock +
				", product='" + product.getName() + '\'' +
				", variantsValues=" + variantsValues.stream().map(VariantValueEntity::getValue).toList() +
				"}";
	}

}
