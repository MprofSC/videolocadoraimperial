package edu.ufpe.cin.vlimperial.service.impl;

import edu.ufpe.cin.vlimperial.service.ItemFilmeService;
import edu.ufpe.cin.vlimperial.domain.ItemFilme;
import edu.ufpe.cin.vlimperial.repository.ItemFilmeRepository;
import edu.ufpe.cin.vlimperial.repository.search.ItemFilmeSearchRepository;
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
 * Service Implementation for managing ItemFilme.
 */
@Service
@Transactional
public class ItemFilmeServiceImpl implements ItemFilmeService {

    private final Logger log = LoggerFactory.getLogger(ItemFilmeServiceImpl.class);

    private final ItemFilmeRepository itemFilmeRepository;

    private final ItemFilmeSearchRepository itemFilmeSearchRepository;

    public ItemFilmeServiceImpl(ItemFilmeRepository itemFilmeRepository, ItemFilmeSearchRepository itemFilmeSearchRepository) {
        this.itemFilmeRepository = itemFilmeRepository;
        this.itemFilmeSearchRepository = itemFilmeSearchRepository;
    }

    /**
     * Save a itemFilme.
     *
     * @param itemFilme the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemFilme save(ItemFilme itemFilme) {
        log.debug("Request to save ItemFilme : {}", itemFilme);
        ItemFilme result = itemFilmeRepository.save(itemFilme);
        itemFilmeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the itemFilmes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemFilme> findAll() {
        log.debug("Request to get all ItemFilmes");
        return itemFilmeRepository.findAll();
    }


    /**
     * Get one itemFilme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemFilme> findOne(Long id) {
        log.debug("Request to get ItemFilme : {}", id);
        return itemFilmeRepository.findById(id);
    }

    /**
     * Delete the itemFilme by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemFilme : {}", id);
        itemFilmeRepository.deleteById(id);
        itemFilmeSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemFilme corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemFilme> search(String query) {
        log.debug("Request to search ItemFilmes for query {}", query);
        return StreamSupport
            .stream(itemFilmeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
