package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "products_details")
public class ProductDetailEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "products_variants_id")
    private ProductVariantEntity productVariant;

    @ManyToOne
    @JoinColumn(name = "variants_values_id")
    private VariantValueEntity variantValue;

}
