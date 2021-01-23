package com.product.target.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.product.target.dao.ProductDao;
import com.product.target.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class DatabaseChangeLog {
    @ChangeSet(order = "001", id = "00.00.00.01", author = "farzain pathan")
    public void seedDatabase(ProductDao productDao) {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(ProductEntity.builder().productId(100L).name("Canada Dry Ginger Ale - 2 L Bottle").price(10.90).currency("USD").build());
        productList.add(ProductEntity.builder().productId(200L).name("Pepsi Zero - 15pk/12 fl oz Cans").price(20.36).currency("USD").build());
        productList.add(ProductEntity.builder().productId(300L).name("Darlah Firwood Table - Christopher Knight Home").price(38.39).currency("USD").build());
        productList.add(ProductEntity.builder().productId(400L).name("Urban Industrial Farmhouse Metal X Entry Table - Saracina Home").price(152.99).currency("USD").build());
        productList.add(ProductEntity.builder().productId(500L).name("8pc Foam Paint Brush Variety Pack - Hand Made Modern®").price(1.49).currency("USD").build());
        productList.add(ProductEntity.builder().productId(600L).name("Unfinished Natural Wood Tray Small - Hand Made Modern").price(7.99).currency("USD").build());
        productDao.insert(productList);
    }
}
