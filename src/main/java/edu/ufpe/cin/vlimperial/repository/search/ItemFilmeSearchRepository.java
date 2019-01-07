package edu.ufpe.cin.vlimperial.repository.search;

import edu.ufpe.cin.vlimperial.domain.ItemFilme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ItemFilme entity.
 */
public interface ItemFilmeSearchRepository extends ElasticsearchRepository<ItemFilme, Long> {
}
