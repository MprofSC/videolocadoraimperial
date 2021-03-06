package edu.ufpe.cin.vlimperial.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ItemFilmeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ItemFilmeSearchRepositoryMockConfiguration {

    @MockBean
    private ItemFilmeSearchRepository mockItemFilmeSearchRepository;

}
