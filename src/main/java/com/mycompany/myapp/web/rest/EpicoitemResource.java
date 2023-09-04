package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Epicoitem;
import com.mycompany.myapp.repository.EpicoitemRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Epicoitem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EpicoitemResource {

    private final Logger log = LoggerFactory.getLogger(EpicoitemResource.class);

    private static final String ENTITY_NAME = "epicoitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EpicoitemRepository epicoitemRepository;

    public EpicoitemResource(EpicoitemRepository epicoitemRepository) {
        this.epicoitemRepository = epicoitemRepository;
    }

    /**
     * {@code POST  /epicoitems} : Create a new epicoitem.
     *
     * @param epicoitem the epicoitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new epicoitem, or with status {@code 400 (Bad Request)} if the epicoitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/epicoitems")
    public Mono<ResponseEntity<Epicoitem>> createEpicoitem(@RequestBody Epicoitem epicoitem) throws URISyntaxException {
        log.debug("REST request to save Epicoitem : {}", epicoitem);
        if (epicoitem.getId() != null) {
            throw new BadRequestAlertException("A new epicoitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return epicoitemRepository
            .save(epicoitem)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/epicoitems/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /epicoitems/:id} : Updates an existing epicoitem.
     *
     * @param id the id of the epicoitem to save.
     * @param epicoitem the epicoitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epicoitem,
     * or with status {@code 400 (Bad Request)} if the epicoitem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the epicoitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/epicoitems/{id}")
    public Mono<ResponseEntity<Epicoitem>> updateEpicoitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Epicoitem epicoitem
    ) throws URISyntaxException {
        log.debug("REST request to update Epicoitem : {}, {}", id, epicoitem);
        if (epicoitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epicoitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return epicoitemRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return epicoitemRepository
                    .save(epicoitem)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /epicoitems/:id} : Partial updates given fields of an existing epicoitem, field will ignore if it is null
     *
     * @param id the id of the epicoitem to save.
     * @param epicoitem the epicoitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epicoitem,
     * or with status {@code 400 (Bad Request)} if the epicoitem is not valid,
     * or with status {@code 404 (Not Found)} if the epicoitem is not found,
     * or with status {@code 500 (Internal Server Error)} if the epicoitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/epicoitems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Epicoitem>> partialUpdateEpicoitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Epicoitem epicoitem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Epicoitem partially : {}, {}", id, epicoitem);
        if (epicoitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epicoitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return epicoitemRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Epicoitem> result = epicoitemRepository
                    .findById(epicoitem.getId())
                    .map(existingEpicoitem -> {
                        if (epicoitem.getName() != null) {
                            existingEpicoitem.setName(epicoitem.getName());
                        }
                        if (epicoitem.getCategory() != null) {
                            existingEpicoitem.setCategory(epicoitem.getCategory());
                        }
                        if (epicoitem.getCostPrice() != null) {
                            existingEpicoitem.setCostPrice(epicoitem.getCostPrice());
                        }
                        if (epicoitem.getUnitPrice() != null) {
                            existingEpicoitem.setUnitPrice(epicoitem.getUnitPrice());
                        }
                        if (epicoitem.getPicFilename() != null) {
                            existingEpicoitem.setPicFilename(epicoitem.getPicFilename());
                        }

                        return existingEpicoitem;
                    })
                    .flatMap(epicoitemRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /epicoitems} : get all the epicoitems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of epicoitems in body.
     */
    @GetMapping(value = "/epicoitems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Epicoitem>> getAllEpicoitems() {
        log.debug("REST request to get all Epicoitems");
        return epicoitemRepository.findAll().collectList();
    }

    /**
     * {@code GET  /epicoitems} : get all the epicoitems as a stream.
     * @return the {@link Flux} of epicoitems.
     */
    @GetMapping(value = "/epicoitems", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Epicoitem> getAllEpicoitemsAsStream() {
        log.debug("REST request to get all Epicoitems as a stream");
        return epicoitemRepository.findAll();
    }

    /**
     * {@code GET  /epicoitems/:id} : get the "id" epicoitem.
     *
     * @param id the id of the epicoitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the epicoitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/epicoitems/{id}")
    public Mono<ResponseEntity<Epicoitem>> getEpicoitem(@PathVariable Long id) {
        log.debug("REST request to get Epicoitem : {}", id);
        Mono<Epicoitem> epicoitem = epicoitemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(epicoitem);
    }

    /**
     * {@code DELETE  /epicoitems/:id} : delete the "id" epicoitem.
     *
     * @param id the id of the epicoitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/epicoitems/{id}")
    public Mono<ResponseEntity<Void>> deleteEpicoitem(@PathVariable Long id) {
        log.debug("REST request to delete Epicoitem : {}", id);
        return epicoitemRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
