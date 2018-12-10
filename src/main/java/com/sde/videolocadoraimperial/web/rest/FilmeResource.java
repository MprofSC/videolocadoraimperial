package com.sde.videolocadoraimperial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sde.videolocadoraimperial.domain.Filme;
import com.sde.videolocadoraimperial.service.FilmeService;
import com.sde.videolocadoraimperial.web.rest.errors.BadRequestAlertException;
import com.sde.videolocadoraimperial.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Filme.
 */
@RestController
@RequestMapping("/api")
public class FilmeResource {

    private final Logger log = LoggerFactory.getLogger(FilmeResource.class);

    private static final String ENTITY_NAME = "filme";

    private final FilmeService filmeService;

    public FilmeResource(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    /**
     * POST  /filmes : Create a new filme.
     *
     * @param filme the filme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filme, or with status 400 (Bad Request) if the filme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filmes")
    @Timed
    public ResponseEntity<Filme> createFilme(@RequestBody Filme filme) throws URISyntaxException {
        log.debug("REST request to save Filme : {}", filme);
        if (filme.getId() != null) {
            throw new BadRequestAlertException("A new filme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Filme result = filmeService.save(filme);
        return ResponseEntity.created(new URI("/api/filmes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filmes : Updates an existing filme.
     *
     * @param filme the filme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filme,
     * or with status 400 (Bad Request) if the filme is not valid,
     * or with status 500 (Internal Server Error) if the filme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filmes")
    @Timed
    public ResponseEntity<Filme> updateFilme(@RequestBody Filme filme) throws URISyntaxException {
        log.debug("REST request to update Filme : {}", filme);
        if (filme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Filme result = filmeService.save(filme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filmes : get all the filmes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filmes in body
     */
    @GetMapping("/filmes")
    @Timed
    public List<Filme> getAllFilmes() {
        log.debug("REST request to get all Filmes");
        return filmeService.findAll();
    }

    /**
     * GET  /filmes/:id : get the "id" filme.
     *
     * @param id the id of the filme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filme, or with status 404 (Not Found)
     */
    @GetMapping("/filmes/{id}")
    @Timed
    public ResponseEntity<Filme> getFilme(@PathVariable Long id) {
        log.debug("REST request to get Filme : {}", id);
        Optional<Filme> filme = filmeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filme);
    }

    /**
     * DELETE  /filmes/:id : delete the "id" filme.
     *
     * @param id the id of the filme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filmes/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilme(@PathVariable Long id) {
        log.debug("REST request to delete Filme : {}", id);
        filmeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
