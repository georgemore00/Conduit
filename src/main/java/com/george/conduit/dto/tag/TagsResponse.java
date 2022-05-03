package com.george.conduit.dto.tag;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("tags")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class TagsResponse {

    private List<String> tags;

    public TagsResponse(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagsResponse{" +
                "tags=" + tags +
                '}';
    }
}
