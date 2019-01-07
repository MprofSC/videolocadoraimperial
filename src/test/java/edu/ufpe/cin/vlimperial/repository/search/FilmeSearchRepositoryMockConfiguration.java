package edu.ufpe.cin.vlimperial.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of FilmeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FilmeSearchRepositoryMockConfiguration {

    @MockBean
    private FilmeSearchRepository mockFilmeSearchRepository;

}
