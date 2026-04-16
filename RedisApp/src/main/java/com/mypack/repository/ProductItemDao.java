package com.mypack.repository;

import com.mypack.entity.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductItemDao {

    public static final String HASH_KEY="ProductItem";

    @Autowired
    private RedisTemplate<String,Object> template;

    public ProductItem save(ProductItem productItem){
        template.opsForHash().put(HASH_KEY,productItem.getId(),productItem);
        return productItem;
    }

    public List<ProductItem> fetchAll(){
        System.out.println("fetchAll from DB");
        List<Object> list=template.opsForHash().values(HASH_KEY);
        return list.stream()
                .map(obj-> (ProductItem)obj).toList();

    }

    public ProductItem fetchById(Integer id){
        System.out.println("fetchById from DB");
        return (ProductItem) template.opsForHash().get(HASH_KEY,id);
    }

    public String deleteById(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "successfully deleted";
    }


}
