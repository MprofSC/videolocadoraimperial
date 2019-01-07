package edu.ufpe.cin.vlimperial.service.impl;

import edu.ufpe.cin.vlimperial.service.FilmeService;
import edu.ufpe.cin.vlimperial.domain.Filme;
import edu.ufpe.cin.vlimperial.repository.FilmeRepository;
import edu.ufpe.cin.vlimperial.repository.search.FilmeSearchRepository;
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
 * Service Implementation for managing Filme.
 */
@Service
@Transactional
public class FilmeServiceImpl implements FilmeService {

    private final Logger log = LoggerFactory.getLogger(FilmeServiceImpl.class);

    private final FilmeRepository filmeRepository;

    private final FilmeSearchRepository filmeSearchRepository;

    public FilmeServiceImpl(FilmeRepository filmeRepository, FilmeSearchRepository filmeSearchRepository) {
        this.filmeRepository = filmeRepository;
        this.filmeSearchRepository = filmeSearchRepository;
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
        Filme result = filmeRepository.save(filme);
        filmeSearchRepository.save(result);
        return result;
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
        filmeSearchRepository.deleteById(id);
    }

    /**
     * Search for the filme corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Filme> search(String query) {
        log.debug("Request to search Filmes for query {}", query);
        return StreamSupport
            .stream(filmeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
