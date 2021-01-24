package com.product.target.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.product.target.dao.ProductDao;
import com.product.target.domain.Product;
import com.product.target.entity.ProductEntity;
import com.product.target.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
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
        "Fetching all products within the price range between: "
            + lowerLimit
            + " and "
            + higherLimit);
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
    if (productEntity.isPresent()) return productEntity.get().toModel();
    else throw new ProductNotFoundException(id);
  }

  @Override
  public List<Product> fetchAllProductsByCategory(String productCategory)
      throws ProductNotFoundException {
    log.info(
        "Fetching all product details from database for product category : " + productCategory);
    Optional<List<ProductEntity>> productEntityList =
        productDao.findAllProductsByCategory(productCategory);
    if (productEntityList.isPresent())
      return productEntityList.get().stream()
          .map(ProductEntity::toModel)
          .collect(Collectors.toList());
    else throw new ProductNotFoundException(productCategory);
  }

  @Override
  public Product saveProduct(Product product) {
    log.info("Persisting product details into database : " + product);
    MongoCollection<Document> productCollection = getDatabaseConnection();
    productCollection.insertOne(documentToPersist(product));
    return product;
  }

  private Document documentToPersist(Product product) {
    return new Document()
        .append("productId", product.getProductId().toString())
        .append("name", product.getName())
        .append("category", product.getCategory())
        .append("price", product.getCurrentPrice().getValue().toString())
        .append("currency", product.getCurrentPrice().getCurrency());
  }

  private MongoCollection<Document> getDatabaseConnection() {
    MongoDatabase mongoDatabase = new MongoClient("localhost", 27017).getDatabase("product");
    return mongoDatabase.getCollection("product");
  }
}
