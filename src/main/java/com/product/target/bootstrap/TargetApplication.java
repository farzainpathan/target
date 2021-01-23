package com.product.target.bootstrap;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.product.target.*")
@EntityScan("com.product.target.entity")
@EnableMongoRepositories("com.product.target.dao")
public class TargetApplication implements CommandLineRunner {

  /*
   * Swagger UI 	- 	http://localhost:8080/swagger-ui.html
   * local host 	- 	http://localhost:8080/v1/products
   */

  public static void main(String[] args) {
    SpringApplication.run(TargetApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    MongoClient mongoClient = getMongoClient();
    MongoDatabase database = mongoClient.getDatabase("product");
    MongoCollection<Document> productCollection = database.getCollection("product");
    productCollection.insertMany(seedDatabase());
  }

  private MongoClient getMongoClient() {
    return new MongoClient("localhost", 27017);
  }

  private List<Document> seedDatabase() {
    List<Document> documentList = new ArrayList<>();
    documentList.add(
        new Document()
            .append("productId", "100")
            .append("name", "Canada Dry Ginger Ale - 2 L Bottle")
            .append("price", "10.90")
            .append("currency", "USD"));
    documentList.add(
        new Document()
            .append("productId", "200")
            .append("name", "Pepsi Zero - 15pk/12 fl oz Cans")
            .append("price", "20.36")
            .append("currency", "USD"));
    documentList.add(
        new Document()
            .append("productId", "300")
            .append("name", "Darlah Firwood Table - Christopher Knight Home")
            .append("price", "38.39")
            .append("currency", "USD"));
    documentList.add(
        new Document()
            .append("productId", "400")
            .append("name", "Urban Industrial Farmhouse Metal X Entry Table - Saracina Home")
            .append("price", "152.99")
            .append("currency", "USD"));
    documentList.add(
        new Document()
            .append("productId", "500")
            .append("name", "8pc Foam Paint Brush Variety Pack - Hand Made Modern")
            .append("price", "1.49")
            .append("currency", "USD"));
    documentList.add(
        new Document()
            .append("productId", "600")
            .append("name", "Unfinished Natural Wood Tray Small - Hand Made Modern")
            .append("price", "7.99")
            .append("currency", "USD"));
    return documentList;
  }
}
