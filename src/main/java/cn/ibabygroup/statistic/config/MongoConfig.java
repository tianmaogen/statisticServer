package cn.ibabygroup.statistic.config;

import cn.ibabygroup.statistic.service.PageDataService;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 16:05
 */
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoTemplate mongoTemplate(Mongo mongo) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, database);
        PageDataService.setMongoTemplate(mongoTemplate);
        return mongoTemplate;
    }
}
