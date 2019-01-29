package edu.ufpe.cin.vlimperial.service;

import edu.ufpe.cin.vlimperial.domain.Reserva;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Reserva.
 */
public interface ReservaService {

    /**
     * Save a reserva.
     *
     * @param reserva the entity to save
     * @return the persisted entity
     */
    Reserva save(Reserva reserva);

    /**
     * Get all the reservas.
     *
     * @return the list of entities
     */
    List<Reserva> findAll();


    /**
     * Get the "id" reserva.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Reserva> findOne(Long id);

    /**
     * Delete the "id" reserva.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reserva corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Reserva> search(String query);
}
