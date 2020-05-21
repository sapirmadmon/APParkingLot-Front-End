package com.example.apparkinglot.logic.Boundaries.Action;

public class ActionIdBoundary {
    private String domain;
    private String id;

    public ActionIdBoundary(String domain, String id) {
        super();
        this.domain = domain;
        this.id = id;
    }

    public ActionIdBoundary() {

    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getDomain() + "#" + this.getId();
    }
}
