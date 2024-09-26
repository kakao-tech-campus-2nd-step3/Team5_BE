package ojosama.talkak.video.config;

import java.net.URISyntaxException;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheCacheManager cacheManager() throws URISyntaxException {
        // JCache (JSR-107) CachingProvider를 통해 CacheManager 생성
        CachingProvider cachingProvider = Caching.getCachingProvider();
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager(
            getClass().getResource("/ehcache.xml").toURI(),
            getClass().getClassLoader());
        return new JCacheCacheManager(cacheManager);
    }
}