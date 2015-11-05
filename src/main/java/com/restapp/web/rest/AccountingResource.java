package com.restapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restapp.domain.Accounting;
import com.restapp.repository.AccountingRepository;
import com.restapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Accounting.
 */
@RestController
@RequestMapping("/api")
public class AccountingResource {

    private final Logger log = LoggerFactory.getLogger(AccountingResource.class);

    @Inject
    private AccountingRepository accountingRepository;

    /**
     * POST  /accountings -> Create a new accounting.
     */
    @RequestMapping(value = "/accountings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Accounting> createAccounting(@RequestBody Accounting accounting) throws URISyntaxException {
        log.debug("REST request to save Accounting : {}", accounting);
        if (accounting.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new accounting cannot already have an ID").body(null);
        }
        Accounting result = accountingRepository.save(accounting);
        return ResponseEntity.created(new URI("/api/accountings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("accounting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accountings -> Updates an existing accounting.
     */
    @RequestMapping(value = "/accountings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Accounting> updateAccounting(@RequestBody Accounting accounting) throws URISyntaxException {
        log.debug("REST request to update Accounting : {}", accounting);
        if (accounting.getId() == null) {
            return createAccounting(accounting);
        }
        Accounting result = accountingRepository.save(accounting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("accounting", accounting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accountings -> get all the accountings.
     */
    @RequestMapping(value = "/accountings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Accounting> getAllAccountings() {
        log.debug("REST request to get all Accountings");
        return accountingRepository.findAll();
    }

    /**
     * GET  /accountings/:id -> get the "id" accounting.
     */
    @RequestMapping(value = "/accountings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Accounting> getAccounting(@PathVariable Long id) {
        log.debug("REST request to get Accounting : {}", id);
        return Optional.ofNullable(accountingRepository.findOne(id))
            .map(accounting -> new ResponseEntity<>(
                accounting,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /accountings/:id -> delete the "id" accounting.
     */
    @RequestMapping(value = "/accountings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAccounting(@PathVariable Long id) {
        log.debug("REST request to delete Accounting : {}", id);
        accountingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("accounting", id.toString())).build();
    }
}
