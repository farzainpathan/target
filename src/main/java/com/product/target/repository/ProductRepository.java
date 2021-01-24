package com.product.target.repository;

import com.product.target.dao.ProductDao;
import com.product.target.domain.Product;
import com.product.target.entity.ProductEntity;
import com.product.target.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
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
  public Product fetchProductByProductId(Long productId) throws ProductNotFoundException {
    log.info("Fetching product details from database for product Id : " + productId);
    Optional<ProductEntity> entity = productDao.findByProductId(productId.toString());
    if (entity.isPresent()) return entity.get().toModel();
    else throw new ProductNotFoundException(productId);
  }

  @Override
  public List<Product> fetchAllProductsWithinPriceRange(Double lowerLimit, Double higherLimit)
      throws ProductNotFoundException {
    log.info(
        "Fetching all products within the price range between: " + lowerLimit + " and " + higherLimit);
    List<ProductEntity> entityList =
        mongoTemplate.find(getQueryForPriceRange(lowerLimit, higherLimit), ProductEntity.class);
    if (entityList.size() > 0)
      return entityList.stream().map(ProductEntity::toModel).collect(Collectors.toList());
    else throw new ProductNotFoundException(lowerLimit, higherLimit);
  }

  private Query getQueryForPriceRange(Double lowerLimit, Double higherLimit) {
    return new Query()
        .addCriteria(Criteria.where("price").gt(lowerLimit.toString()).lt(higherLimit.toString()));
  }

  @Override
  public Product fetchProductByProductName(String productName) throws ProductNotFoundException {
    log.info("Fetching product details from database for product name : " + productName);
    Optional<ProductEntity> productEntity = productDao.findByProductName(productName);
    if (productEntity.isPresent()) return productEntity.get().toModel();
    else throw new ProductNotFoundException(productName);
  }

  @Override
  public Product fetchProductById(String id) throws ProductNotFoundException {
    log.info("Fetching product details from database for product id : " + id);
    Optional<ProductEntity> productEntity = productDao.findById(id);
    if (productEntity.isPresent())
      return productEntity.get().toModel();
    else throw new ProductNotFoundException(id);
  }
}
