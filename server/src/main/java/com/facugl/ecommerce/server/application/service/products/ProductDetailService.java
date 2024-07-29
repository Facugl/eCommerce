package com.facugl.ecommerce.server.application.service.products;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.products.productsDetails.CreateProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.DeleteProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.GetAllProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.GetProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.UpdateProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.application.port.output.products.ProductDetailOutputPort;
import com.facugl.ecommerce.server.application.port.output.products.ProductVariantOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.products.ProductDetail;
import com.facugl.ecommerce.server.domain.model.products.ProductDetail.ProductDetailBuilder;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductDetailRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ProductDetailService implements
        CreateProductDetailUseCase,
        GetProductDetailUseCase,
        GetAllProductDetailUseCase,
        UpdateProductDetailUseCase,
        DeleteProductDetailUseCase {
    private final ProductDetailOutputPort productDetailOutputPort;
    private final ProductVariantOutputPort productVariantOutputPort;
    private final VariantValueOutputPort variantValueOutputPort;

    @Transactional
    @Override
    public ProductDetail createProductDetail(ProductDetail productDetailToCreate) {
        return productDetailOutputPort.createProductDetail(productDetailToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDetail getProductDetailById(Long id) {
        return productDetailOutputPort.findProductDetailById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDetail> getAllProductsDetails() {
        return productDetailOutputPort.getAllProductDetail();
    }

    @Transactional
    @Override
    public ProductDetail updateProductDetail(Long id, ProductDetail productDetailToUpdate) {
        return productDetailOutputPort.updateProductDetail(id, productDetailToUpdate);
    }

    @Transactional
    @Override
    public void deleteProductDetailById(Long id) {
        productDetailOutputPort.deleteProductDetailById(id);
    }

    @Transactional
    public ProductDetail mapProductDetailRequestToProductDetail(ProductDetailRequest productDetailToCreate) {
        ProductDetailBuilder productDetail = ProductDetail.builder();

        if (productDetailToCreate.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantOutputPort
                    .findProductVariantById(productDetailToCreate.getProductVariantId());

            productDetail.productVariant(productVariant);
        }

        if (productDetailToCreate.getVariantValueId() != null) {
            VariantValue variantValue = variantValueOutputPort
                    .findVariantValueById(productDetailToCreate.getVariantValueId());

            productDetail.variantValue(variantValue);
        }

        return productDetail.build();
    }
}
