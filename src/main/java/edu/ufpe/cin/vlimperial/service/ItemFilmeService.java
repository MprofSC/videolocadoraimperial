package edu.ufpe.cin.vlimperial.service;

import edu.ufpe.cin.vlimperial.domain.ItemFilme;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ItemFilme.
 */
public interface ItemFilmeService {

    /**
     * Save a itemFilme.
     *
     * @param itemFilme the entity to save
     * @return the persisted entity
     */
    ItemFilme save(ItemFilme itemFilme);

    /**
     * Get all the itemFilmes.
     *
     * @return the list of entities
     */
    List<ItemFilme> findAll();


    /**
     * Get the "id" itemFilme.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemFilme> findOne(Long id);

    /**
     * Delete the "id" itemFilme.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
