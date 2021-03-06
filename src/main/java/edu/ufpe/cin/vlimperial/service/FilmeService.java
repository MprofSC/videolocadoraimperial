package edu.ufpe.cin.vlimperial.service;

import edu.ufpe.cin.vlimperial.domain.Filme;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Filme.
 */
public interface FilmeService {

    /**
     * Save a filme.
     *
     * @param filme the entity to save
     * @return the persisted entity
     */
    Filme save(Filme filme);

    /**
     * Get all the filmes.
     *
     * @return the list of entities
     */
    List<Filme> findAll();


    /**
     * Get the "id" filme.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Filme> findOne(Long id);

    /**
     * Delete the "id" filme.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the filme corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Filme> search(String query);
}
