package com.example.demo.product;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @OneToOne(mappedBy = "product", orphanRemoval = true)
    private ProductDetail productDetail;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ProductImage> productImages;

    void addImage(ProductImage productImage) {
        if (!productImages.contains(productImage)) {
            productImages.add(productImage);
            productImage.setProduct(this);
        }
    }

}
