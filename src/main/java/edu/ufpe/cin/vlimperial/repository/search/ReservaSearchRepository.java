package edu.ufpe.cin.vlimperial.repository.search;

import edu.ufpe.cin.vlimperial.domain.Reserva;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reserva entity.
 */
public interface ReservaSearchRepository extends ElasticsearchRepository<Reserva, Long> {
}
