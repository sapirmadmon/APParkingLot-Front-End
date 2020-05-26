package com.example.apparkinglot.logic.Boundaries.Element;


import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;

import java.util.Date;
import java.util.Map;


public class ElementBoundary {
    private ElementIdBoundary elementId;
    private String type;
    private String name;
    private Boolean active;
    private Date createdTimestamp;
    private Map<String, UserIdBoundary> createdBy;
    private Location location;
    private Map<String, Object> elementAttributes;

    public ElementBoundary(ElementIdBoundary elementId, String type, String name, Boolean active, Date timeStamp,
                           Location location, Map<String, Object> elemntAttributes,  Map<String, UserIdBoundary>  createdBy) {
        super();
        this.elementId = elementId;
        this.type = type;
        this.name = name;
        this.active = active;
        this.createdTimestamp = timeStamp;
        this.createdBy = createdBy;
        this.location = location;
        this.elementAttributes = elemntAttributes;
    }


    public ElementBoundary() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Map<String, Object> getElementAttributes() {
        return elementAttributes;
    }

    public void setElementAttributes(Map<String, Object> elementAttributes) {
        this.elementAttributes = elementAttributes;
    }

    public ElementIdBoundary getElementId() {
        return elementId;
    }

    public void setElementId(ElementIdBoundary elementId) {
        this.elementId = elementId;
    }

    public Location getLocation() {
        return location;
    }

    public Map<String, UserIdBoundary> getCreatedBy() {
        return createdBy;
    }

    public void setCreateBy(Map<String, UserIdBoundary> createdBy) {
        this.createdBy = createdBy;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
