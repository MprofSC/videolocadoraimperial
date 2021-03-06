package edu.ufpe.cin.vlimperial.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(edu.ufpe.cin.vlimperial.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Cliente.class.getName() + ".clientes", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Filme.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Filme.class.getName() + ".itemfilmes", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.ItemFilme.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Cliente.class.getName() + ".reservas", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Cliente.class.getName() + ".locacaos", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.ItemFilme.class.getName() + ".locacaos", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Locacao.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Locacao.class.getName() + ".itemLocados", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Reserva.class.getName(), jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.Reserva.class.getName() + ".midiaDesejadas", jcacheConfiguration);
            cm.createCache(edu.ufpe.cin.vlimperial.domain.ItemFilme.class.getName() + ".reservas", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
