package edu.ufpe.cin.vlimperial.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ufpe.cin.vlimperial.domain.ItemFilme;
import edu.ufpe.cin.vlimperial.service.ItemFilmeService;
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

/**
 * REST controller for managing ItemFilme.
 */
@RestController
@RequestMapping("/api")
public class ItemFilmeResource {

    private final Logger log = LoggerFactory.getLogger(ItemFilmeResource.class);

    private static final String ENTITY_NAME = "itemFilme";

    private final ItemFilmeService itemFilmeService;

    public ItemFilmeResource(ItemFilmeService itemFilmeService) {
        this.itemFilmeService = itemFilmeService;
    }

    /**
     * POST  /item-filmes : Create a new itemFilme.
     *
     * @param itemFilme the itemFilme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemFilme, or with status 400 (Bad Request) if the itemFilme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-filmes")
    @Timed
    public ResponseEntity<ItemFilme> createItemFilme(@Valid @RequestBody ItemFilme itemFilme) throws URISyntaxException {
        log.debug("REST request to save ItemFilme : {}", itemFilme);
        if (itemFilme.getId() != null) {
            throw new BadRequestAlertException("A new itemFilme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemFilme result = itemFilmeService.save(itemFilme);
        return ResponseEntity.created(new URI("/api/item-filmes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-filmes : Updates an existing itemFilme.
     *
     * @param itemFilme the itemFilme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemFilme,
     * or with status 400 (Bad Request) if the itemFilme is not valid,
     * or with status 500 (Internal Server Error) if the itemFilme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-filmes")
    @Timed
    public ResponseEntity<ItemFilme> updateItemFilme(@Valid @RequestBody ItemFilme itemFilme) throws URISyntaxException {
        log.debug("REST request to update ItemFilme : {}", itemFilme);
        if (itemFilme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemFilme result = itemFilmeService.save(itemFilme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemFilme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-filmes : get all the itemFilmes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of itemFilmes in body
     */
    @GetMapping("/item-filmes")
    @Timed
    public List<ItemFilme> getAllItemFilmes() {
        log.debug("REST request to get all ItemFilmes");
        return itemFilmeService.findAll();
    }

    /**
     * GET  /item-filmes/:id : get the "id" itemFilme.
     *
     * @param id the id of the itemFilme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemFilme, or with status 404 (Not Found)
     */
    @GetMapping("/item-filmes/{id}")
    @Timed
    public ResponseEntity<ItemFilme> getItemFilme(@PathVariable Long id) {
        log.debug("REST request to get ItemFilme : {}", id);
        Optional<ItemFilme> itemFilme = itemFilmeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemFilme);
    }

    /**
     * DELETE  /item-filmes/:id : delete the "id" itemFilme.
     *
     * @param id the id of the itemFilme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-filmes/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemFilme(@PathVariable Long id) {
        log.debug("REST request to delete ItemFilme : {}", id);
        itemFilmeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
