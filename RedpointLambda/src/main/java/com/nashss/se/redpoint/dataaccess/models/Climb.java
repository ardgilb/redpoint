package com.nashss.se.redpoint.dataaccess.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a record in the climbs table.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Climb implements Comparable<Climb>{
    @JsonProperty("name")
    private String name;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("yds")
    private String grade;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("metadata")
    private Metadata metadata;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public int compareTo(Climb o) {
        return this.metadata.getLeftRightIndex().compareTo(o.metadata.getLeftRightIndex());
    }
}
