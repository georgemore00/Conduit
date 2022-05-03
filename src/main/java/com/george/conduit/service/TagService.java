package com.george.conduit.service;

import com.george.conduit.model.Tag;
import com.george.conduit.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    //If tag with same name already exists in database, bind the tag to that tag entity instead of creating a new one.
    public List<Tag> refresh(List<Tag> tags) {
        List<Tag> newList = new ArrayList<>();

        for (int i = 0; i < tags.size(); i++) {
            Tag currentTag = tags.get(i);
            currentTag = tagRepository.findByName(currentTag.getName()).orElse(currentTag);
            newList.add(currentTag);
        }
        return newList;
    }

    public List<String> getTags() {
        return tagRepository.findAll().stream().map(t -> t.getName()).collect(Collectors.toList());
    }
}
