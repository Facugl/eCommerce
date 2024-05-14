package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.productsVariants.ProductVariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "variants_values")
@Entity
public class VariantValueEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "value", unique = true, nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "variants_id", nullable = false)
	private VariantEntity variant;

	@Builder.Default
	@ManyToMany(mappedBy = "variantsValues", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<ProductVariantEntity> productsVariants = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		VariantValueEntity other = (VariantValueEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VariantValueEntity{" +
				"id=" + id +
				", value='" + value + '\'' +
				", variant=" + variant +
				", productsVariants=" + productsVariants.stream().map(ProductVariantEntity::getId).toList() +
				"}";
	}

}
