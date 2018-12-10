package com.sde.videolocadoraimperial.service.impl;

import com.sde.videolocadoraimperial.service.FilmeService;
import com.sde.videolocadoraimperial.domain.Filme;
import com.sde.videolocadoraimperial.repository.FilmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Filme.
 */
@Service
@Transactional
public class FilmeServiceImpl implements FilmeService {

    private final Logger log = LoggerFactory.getLogger(FilmeServiceImpl.class);

    private final FilmeRepository filmeRepository;

    public FilmeServiceImpl(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    /**
     * Save a filme.
     *
     * @param filme the entity to save
     * @return the persisted entity
     */
    @Override
    public Filme save(Filme filme) {
        log.debug("Request to save Filme : {}", filme);
        return filmeRepository.save(filme);
    }

    /**
     * Get all the filmes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Filme> findAll() {
        log.debug("Request to get all Filmes");
        return filmeRepository.findAll();
    }


    /**
     * Get one filme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Filme> findOne(Long id) {
        log.debug("Request to get Filme : {}", id);
        return filmeRepository.findById(id);
    }

    /**
     * Delete the filme by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Filme : {}", id);
        filmeRepository.deleteById(id);
    }
}
