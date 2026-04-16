package com.mypack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    //connection setup
    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration);
    }


    //to work with redis using template
    @Bean
    public RedisTemplate<String,Object> template(){
        RedisTemplate<String,Object> temp=new RedisTemplate<>();
        temp.setConnectionFactory(connectionFactory());
        temp.setKeySerializer(new StringRedisSerializer());
        temp.setHashKeySerializer(new StringRedisSerializer());
        temp.setHashKeySerializer(new JdkSerializationRedisSerializer());
        temp.setValueSerializer(new JdkSerializationRedisSerializer());
        temp.afterPropertiesSet();
        temp.setEnableTransactionSupport(true);
        return temp;
    }
}
