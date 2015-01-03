package com.kaiinui.appenginetest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Key;
import org.slim3.datastore.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kaiinui on 2014/12/30.
 */
@Model(schemaVersion = 1)
public class Book implements Serializable {
    private static final Long serialVersionUID = 1L;

    public Book() {}

    public Book(String title, String authorName) {
        this.title = title;
        this.authorName = authorName;
    }

    @JsonIgnore
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute
    private String title;

    @Attribute
    private String authorName;

    @JsonIgnore
    @Attribute(version = true)
    private Long version;

    @Attribute(listener = ModificationDate.class)
    private Date updatedAt;

    @Attribute(listener = CreationDate.class)
    private Date createdAt;

    @JsonProperty
    public String getId() {
        return getKey().getName();
    }

    @JsonProperty
    public void setId(String id) {
        setKey(Datastore.createKey(Book.class, id));
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
