package edu.ufpe.cin.vlimperial.service.impl;

import edu.ufpe.cin.vlimperial.service.LocacaoService;
import edu.ufpe.cin.vlimperial.domain.Locacao;
import edu.ufpe.cin.vlimperial.repository.LocacaoRepository;
import edu.ufpe.cin.vlimperial.repository.search.LocacaoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Locacao.
 */
@Service
@Transactional
public class LocacaoServiceImpl implements LocacaoService {

    private final Logger log = LoggerFactory.getLogger(LocacaoServiceImpl.class);

    private final LocacaoRepository locacaoRepository;

    private final LocacaoSearchRepository locacaoSearchRepository;

    public LocacaoServiceImpl(LocacaoRepository locacaoRepository, LocacaoSearchRepository locacaoSearchRepository) {
        this.locacaoRepository = locacaoRepository;
        this.locacaoSearchRepository = locacaoSearchRepository;
    }

    /**
     * Save a locacao.
     *
     * @param locacao the entity to save
     * @return the persisted entity
     */
    @Override
    public Locacao save(Locacao locacao) {
        log.debug("Request to save Locacao : {}", locacao);
        Locacao result = locacaoRepository.save(locacao);
        locacaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the locacaos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Locacao> findAll() {
        log.debug("Request to get all Locacaos");
        return locacaoRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Locacao with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Locacao> findAllWithEagerRelationships(Pageable pageable) {
        return locacaoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one locacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Locacao> findOne(Long id) {
        log.debug("Request to get Locacao : {}", id);
        return locacaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the locacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Locacao : {}", id);
        locacaoRepository.deleteById(id);
        locacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the locacao corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Locacao> search(String query) {
        log.debug("Request to search Locacaos for query {}", query);
        return StreamSupport
            .stream(locacaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
