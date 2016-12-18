package ru.itmo.ipm.model;

import java.util.List;

/**
 * Created by alexander.sokolov on 2016-12-18.
 */
public class PlaceType {
    private String resourceUrl;
    private String title;
    private String parentTypeUrl;
    private List<PlaceType> subTypes;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentTypeUrl() {
        return parentTypeUrl;
    }

    public void setParentTypeUrl(String parentTypeUrl) {
        this.parentTypeUrl = parentTypeUrl;
    }

    public List<PlaceType> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<PlaceType> subTypes) {
        this.subTypes = subTypes;
    }
}
