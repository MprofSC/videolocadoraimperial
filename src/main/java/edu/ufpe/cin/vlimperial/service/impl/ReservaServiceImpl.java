package edu.ufpe.cin.vlimperial.service.impl;

import edu.ufpe.cin.vlimperial.service.ReservaService;
import edu.ufpe.cin.vlimperial.domain.Reserva;
import edu.ufpe.cin.vlimperial.repository.ReservaRepository;
import edu.ufpe.cin.vlimperial.repository.search.ReservaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Reserva.
 */
@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

    private final Logger log = LoggerFactory.getLogger(ReservaServiceImpl.class);

    private final ReservaRepository reservaRepository;

    private final ReservaSearchRepository reservaSearchRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, ReservaSearchRepository reservaSearchRepository) {
        this.reservaRepository = reservaRepository;
        this.reservaSearchRepository = reservaSearchRepository;
    }

    /**
     * Save a reserva.
     *
     * @param reserva the entity to save
     * @return the persisted entity
     */
    @Override
    public Reserva save(Reserva reserva) {
        log.debug("Request to save Reserva : {}", reserva);
        Reserva result = reservaRepository.save(reserva);
        reservaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the reservas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll() {
        log.debug("Request to get all Reservas");
        return reservaRepository.findAll();
    }


    /**
     * Get one reserva by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> findOne(Long id) {
        log.debug("Request to get Reserva : {}", id);
        return reservaRepository.findById(id);
    }

    /**
     * Delete the reserva by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reserva : {}", id);
        reservaRepository.deleteById(id);
        reservaSearchRepository.deleteById(id);
    }

    /**
     * Search for the reserva corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reserva> search(String query) {
        log.debug("Request to search Reservas for query {}", query);
        return StreamSupport
            .stream(reservaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
