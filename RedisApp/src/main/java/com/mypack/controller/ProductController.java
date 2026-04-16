package com.mypack.controller;

import com.mypack.entity.ProductItem;
import com.mypack.repository.ProductItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductItemDao dao;


    @PostMapping
    @CacheEvict(value = "ProductItemList")
    public ProductItem save(@RequestBody ProductItem productItem){
        return dao.save(productItem);
    }


    @Cacheable(value = "ProductItemList")
    @GetMapping
    public List<ProductItem> fetchAll(){
        return dao.fetchAll();
    }

    @Cacheable(key="#id",value = "ProductItem1")
    @GetMapping("/{id}")
    public ProductItem fetchById(@PathVariable Integer id){
        return dao.fetchById(id);
    }

    @DeleteMapping("/{id}")
    @Caching(evict= {
            @CacheEvict(key = "#id", value = "ProductItem1"),
            @CacheEvict(value = "ProductItemList", allEntries = true)
    })
    public String deleteById(@PathVariable int id){
        return dao.deleteById(id);
    }

}
