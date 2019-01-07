package edu.ufpe.cin.vlimperial.service;

import edu.ufpe.cin.vlimperial.domain.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Cliente.
 */
public interface ClienteService {

    /**
     * Save a cliente.
     *
     * @param cliente the entity to save
     * @return the persisted entity
     */
    Cliente save(Cliente cliente);

    /**
     * Get all the clientes.
     *
     * @return the list of entities
     */
    List<Cliente> findAll();


    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Cliente> findOne(Long id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cliente corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Cliente> search(String query);
}
