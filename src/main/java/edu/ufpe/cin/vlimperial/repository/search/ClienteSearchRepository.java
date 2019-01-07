package edu.ufpe.cin.vlimperial.repository.search;

import edu.ufpe.cin.vlimperial.domain.Cliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cliente entity.
 */
public interface ClienteSearchRepository extends ElasticsearchRepository<Cliente, Long> {
}
