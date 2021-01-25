package com.product.target.dao;

import com.product.target.domain.Product;
import com.product.target.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends MongoRepository<ProductEntity, String> {

    @Query("{ 'productId' : ?0 }")
    Optional<ProductEntity> findByProductId(String productId);

    @Query("{ 'price' : { $gt: ?0, $lt: ?1 } }")
    Optional<List<ProductEntity>> findProductsByPriceBetween(String lowerLimit, String higherLimit);

    @Query("{ 'name' : ?0 }")
    List<ProductEntity> findByProductName(String productName);

    @Query("{ 'category' : ?0 }")
    Optional<List<ProductEntity>> findAllProductsByCategory(String productCategory);
}
