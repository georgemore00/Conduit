package com.george.conduit.controller;

import com.george.conduit.dto.tag.TagsResponse;
import com.george.conduit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "api/v1/tags")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getTags() {
        return new ResponseEntity<>(new TagsResponse(tagService.getTags()), HttpStatus.OK);
    }
}
