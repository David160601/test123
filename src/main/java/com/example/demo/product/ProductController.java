package com.example.demo.product;

import com.example.demo.exception.ApiRequestException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final ModelMapper modelMapper;
    private final ProductRepo productRepo;
    private final ProductDetailRepo productDetailRepo;

    @Autowired
    public ProductController(ModelMapper modelMapper, ProductRepo productRepo, ProductDetailRepo productDetailRepo) {
        this.modelMapper = modelMapper;
        this.productRepo = productRepo;
        this.productDetailRepo = productDetailRepo;
    }

    @PostMapping
    ProductDto createProduct(@RequestBody() @Valid ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        ProductDetail productDetail = new ProductDetail();
        productDetail.setDescription(productDto.getDescription());
        product = productRepo.save(product);
        productDetail.setProduct(product);
        productDetail = productDetailRepo.save(productDetail);
        product.setProductDetail(productDetail);
        return modelMapper.map(product, ProductDto.class);
    }

    @GetMapping("/{id}")
    ProductDto getProductDto(@PathVariable("id") long id) {
        Product product = this.getProduct(id);
        return modelMapper.map(product, ProductDto.class);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") long id) {
        Product deleteProduct = this.getProduct(id);
        productRepo.delete(deleteProduct);
    }

    Product getProduct(long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ApiRequestException("Product not found", HttpStatus.NOT_FOUND));
        return product;
    }


}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotBlank
    String title;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    ProductDetailDto productDetail;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class ProductDetailDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotBlank
    String description;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class ProductImageDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotBlank
    String url;
}