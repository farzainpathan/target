package com.product.target.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.product.target.dao.ProductDao;
import com.product.target.domain.Price;
import com.product.target.domain.Product;
import com.product.target.entity.ProductEntity;
import com.product.target.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    log.info("Fetching all products in price range between: " + lowerLimit + " and " + higherLimit);
    List<ProductEntity> entityList =
        mongoTemplate.find(priceRangeQuery(lowerLimit, higherLimit), ProductEntity.class);
    if (entityList.size() > 0)
      return entityList.stream().map(ProductEntity::toModel).collect(Collectors.toList());
    else throw new ProductNotFoundException(lowerLimit, higherLimit);
  }

  @Override
  public List<Product> fetchProductByProductName(String productName)
      throws ProductNotFoundException {
    log.info("Fetching product details from database for product name : " + productName);
    List<ProductEntity> productEntity = productDao.findByProductName(productName);
    if (productEntity.size() > 0)
      return productEntity.stream().map(ProductEntity::toModel).collect(Collectors.toList());
    else throw new ProductNotFoundException(productName);
  }

  @Override
  public Product fetchProductById(String id) throws ProductNotFoundException {
    log.info("Fetching product details from database for product id : " + id);
    Optional<Product> product = findDocumentWithId(id);
    if (product.isPresent()) return product.get();
    else throw new ProductNotFoundException(id);
  }

  @Override
  public List<Product> fetchAllProductsByCategory(String productCategory)
      throws ProductNotFoundException {
    log.info("Fetching all product details from database for category : " + productCategory);
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
    productCollection.insertOne(toDocument(product));
    Query query =
        new Query().addCriteria(Criteria.where("productId").is(product.getProductId().toString()));
    return mongoTemplate.find(query, ProductEntity.class).get(0).toModel();
  }

  @Override
  public Product updateProduct(Product updateProduct) throws ProductNotFoundException {
    log.info("Updating product details into database : " + updateProduct);
    MongoCollection<Document> productCollection = getDatabaseConnection();
    Document document = toDocument(updateProduct);
    BasicDBObject filter = new BasicDBObject("_id", new ObjectId(updateProduct.getId()));
    productCollection.updateOne(filter, new BasicDBObject("$set", document));
    Optional<Product> product = findDocumentWithId(updateProduct.getId());
    if (product.isPresent()) return product.get();
    else throw new ProductNotFoundException(updateProduct);
  }

  private Document toDocument(Product product) {
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

  private Query priceRangeQuery(Double lowerLimit, Double higherLimit) {
    return new Query()
        .addCriteria(Criteria.where("price").gt(lowerLimit.toString()).lt(higherLimit.toString()));
  }

  private Optional<Product> findDocumentWithId(String id) {
    MongoCollection<Document> productCollection = getDatabaseConnection();
    for (Document d : productCollection.find()) {
      if (d.get("_id").toString().equals(id)) {
        return Optional.of(
            Product.builder()
                .id(d.get("_id").toString())
                .productId(Long.parseLong(d.getString("productId").toString()))
                .name(d.get("name").toString())
                .category(d.get("category").toString())
                .currentPrice(
                    Price.builder()
                        .value(Double.parseDouble(d.get("price").toString()))
                        .currency(d.get("currency").toString())
                        .build())
                .build());
      }
    }
    return Optional.empty();
  }
}
