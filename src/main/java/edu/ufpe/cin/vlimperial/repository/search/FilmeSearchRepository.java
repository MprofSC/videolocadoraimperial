package edu.ufpe.cin.vlimperial.repository.search;

import edu.ufpe.cin.vlimperial.domain.Filme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Filme entity.
 */
public interface FilmeSearchRepository extends ElasticsearchRepository<Filme, Long> {
}
