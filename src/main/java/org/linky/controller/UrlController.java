package org.linky.controller;

import org.linky.exceptions.AbstractBaseException;
import org.linky.model.services.UrlRequest;
import org.linky.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Standard CRUD controller for REST requests
 */
@RestController
@RequestMapping("/u/")
public class UrlController {

    @Autowired
    private UrlService service;

    @GetMapping(path = "{code}")
    public ResponseEntity getUrl(@PathVariable("code") String code) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", service.getUrlByCode(code).getStandardUrl()).build();
        } catch (AbstractBaseException e) {
            return e.buildResponse();
        }
    }

    @GetMapping
    public ResponseEntity getUserUrls() {
        try {
            return ResponseEntity.ok().body(service.getUserUrls());
        } catch (AbstractBaseException e) {
            return e.buildResponse();
        }
    }

    @PostMapping
    public ResponseEntity postUrl(@RequestBody UrlRequest body) {
        try {
            return ResponseEntity.ok().body(service.newUrl(body));
        } catch (AbstractBaseException e) {
            return e.buildResponse();
        }
    }

    @PutMapping(path = "{code}")
    public ResponseEntity putUrl(@RequestBody UrlRequest body, @PathVariable("code") String code) {
        try {
            return ResponseEntity.ok().body(service.editUrl(code, body));
        } catch (AbstractBaseException e) {
            return e.buildResponse();
        }
    }


    @DeleteMapping(path = "{code}")
    public ResponseEntity deleteUrl(@PathVariable("code") String code) {
        try {
            service.deleteUrl(code);
            return ResponseEntity.ok().build();
        } catch (AbstractBaseException e) {
            return e.buildResponse();
        }
    }

}
