package edu.java.scrapper.kafkaTests;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("testKafka")
public class CacheConfig {

    @Bean
    public CacheManager cacheManagerKafka() {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        MutableConfiguration<Object, Object> config = new MutableConfiguration<>();
        config.setStoreByValue(false).setStatisticsEnabled(true);
        cacheManager.createCache("rate-limit-buckets-scrapper", config);
        return cacheManager;
    }
}
