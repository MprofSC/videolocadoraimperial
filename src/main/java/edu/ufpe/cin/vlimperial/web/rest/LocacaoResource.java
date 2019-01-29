package edu.ufpe.cin.vlimperial.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ufpe.cin.vlimperial.domain.Locacao;
import edu.ufpe.cin.vlimperial.service.LocacaoService;
import edu.ufpe.cin.vlimperial.web.rest.errors.BadRequestAlertException;
import edu.ufpe.cin.vlimperial.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Locacao.
 */
@RestController
@RequestMapping("/api")
public class LocacaoResource {

    private final Logger log = LoggerFactory.getLogger(LocacaoResource.class);

    private static final String ENTITY_NAME = "locacao";

    private final LocacaoService locacaoService;

    public LocacaoResource(LocacaoService locacaoService) {
        this.locacaoService = locacaoService;
    }

    /**
     * POST  /locacaos : Create a new locacao.
     *
     * @param locacao the locacao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locacao, or with status 400 (Bad Request) if the locacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locacaos")
    @Timed
    public ResponseEntity<Locacao> createLocacao(@Valid @RequestBody Locacao locacao) throws URISyntaxException {
        log.debug("REST request to save Locacao : {}", locacao);
        if (locacao.getId() != null) {
            throw new BadRequestAlertException("A new locacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locacao result = locacaoService.save(locacao);
        return ResponseEntity.created(new URI("/api/locacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locacaos : Updates an existing locacao.
     *
     * @param locacao the locacao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locacao,
     * or with status 400 (Bad Request) if the locacao is not valid,
     * or with status 500 (Internal Server Error) if the locacao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locacaos")
    @Timed
    public ResponseEntity<Locacao> updateLocacao(@Valid @RequestBody Locacao locacao) throws URISyntaxException {
        log.debug("REST request to update Locacao : {}", locacao);
        if (locacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locacao result = locacaoService.save(locacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locacao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locacaos : get all the locacaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of locacaos in body
     */
    @GetMapping("/locacaos")
    @Timed
    public List<Locacao> getAllLocacaos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Locacaos");
        return locacaoService.findAll();
    }

    /**
     * GET  /locacaos/:id : get the "id" locacao.
     *
     * @param id the id of the locacao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locacao, or with status 404 (Not Found)
     */
    @GetMapping("/locacaos/{id}")
    @Timed
    public ResponseEntity<Locacao> getLocacao(@PathVariable Long id) {
        log.debug("REST request to get Locacao : {}", id);
        Optional<Locacao> locacao = locacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locacao);
    }

    /**
     * DELETE  /locacaos/:id : delete the "id" locacao.
     *
     * @param id the id of the locacao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocacao(@PathVariable Long id) {
        log.debug("REST request to delete Locacao : {}", id);
        locacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/locacaos?query=:query : search for the locacao corresponding
     * to the query.
     *
     * @param query the query of the locacao search
     * @return the result of the search
     */
    @GetMapping("/_search/locacaos")
    @Timed
    public List<Locacao> searchLocacaos(@RequestParam String query) {
        log.debug("REST request to search Locacaos for query {}", query);
        return locacaoService.search(query);
    }

}
