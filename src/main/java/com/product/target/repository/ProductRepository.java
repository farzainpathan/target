package com.product.target.repository;

import com.product.target.dao.ProductDao;
import com.product.target.domain.Product;
import com.product.target.entity.ProductEntity;
import com.product.target.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductRepository implements ProductPersistence {

  private final ProductDao productDao;
  private final MongoTemplate mongoTemplate;

  public ProductRepository(ProductDao productDao, MongoTemplate mongoTemplate) {
    this.productDao = productDao;
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Product> fetchAllProducts() {
    log.info("Fetching all product information from database");
    return productDao.findAll().stream().map(ProductEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Product fetchProductById(Long productId) throws ProductNotFoundException {
    log.info("Fetching product details from database for product Id : " + productId);
    Optional<ProductEntity> entity = productDao.findByProductId(productId.toString());
    if (entity.isPresent())
      return entity.get().toModel();
    else
      throw new ProductNotFoundException(productId);
  }

  /*Query query = new Query().addCriteria(Criteria.where("productId").is(productId.toString()));
    System.out.println("Mongo template " + mongoTemplate.find(query, ProductEntity.class));*/
}
