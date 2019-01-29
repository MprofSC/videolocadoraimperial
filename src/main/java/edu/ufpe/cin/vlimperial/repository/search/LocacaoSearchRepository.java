package edu.ufpe.cin.vlimperial.repository.search;

import edu.ufpe.cin.vlimperial.domain.Locacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Locacao entity.
 */
public interface LocacaoSearchRepository extends ElasticsearchRepository<Locacao, Long> {
}
