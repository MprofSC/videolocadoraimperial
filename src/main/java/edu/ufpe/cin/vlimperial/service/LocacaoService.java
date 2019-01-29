package edu.ufpe.cin.vlimperial.service;

import edu.ufpe.cin.vlimperial.domain.Locacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Locacao.
 */
public interface LocacaoService {

    /**
     * Save a locacao.
     *
     * @param locacao the entity to save
     * @return the persisted entity
     */
    Locacao save(Locacao locacao);

    /**
     * Get all the locacaos.
     *
     * @return the list of entities
     */
    List<Locacao> findAll();

    /**
     * Get all the Locacao with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Locacao> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" locacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Locacao> findOne(Long id);

    /**
     * Delete the "id" locacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the locacao corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Locacao> search(String query);
}
