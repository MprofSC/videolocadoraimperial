package edu.ufpe.cin.vlimperial.service.impl;

import edu.ufpe.cin.vlimperial.service.ItemFilmeService;
import edu.ufpe.cin.vlimperial.domain.ItemFilme;
import edu.ufpe.cin.vlimperial.repository.ItemFilmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ItemFilme.
 */
@Service
@Transactional
public class ItemFilmeServiceImpl implements ItemFilmeService {

    private final Logger log = LoggerFactory.getLogger(ItemFilmeServiceImpl.class);

    private final ItemFilmeRepository itemFilmeRepository;

    public ItemFilmeServiceImpl(ItemFilmeRepository itemFilmeRepository) {
        this.itemFilmeRepository = itemFilmeRepository;
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
        return itemFilmeRepository.save(itemFilme);
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
    }
}
