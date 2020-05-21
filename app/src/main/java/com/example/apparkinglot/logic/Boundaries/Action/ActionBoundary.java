package com.example.apparkinglot.logic.Boundaries.Action;

import java.util.Date;
import java.util.Map;

public class ActionBoundary {
    private ActionIdBoundary actionId;
    private String type;
    private ElementOfAction element;
    private Date createdTimestamp;
    private InvokingUser invokedBy;
    private Map<String, Object> actionAttributes;

    public ActionBoundary() {
    }


    public ActionBoundary(ActionIdBoundary actionId, String type, ElementOfAction element, Date createdTimestamp,
                          InvokingUser invokedBy, Map<String, Object> actionAttributes) {

        this.actionId = actionId;
        this.type = type;
        this.element = element;
        this.setCreatedTimestamp(createdTimestamp);
        this.invokedBy = invokedBy;
        this.actionAttributes = actionAttributes;
    }

    public ActionIdBoundary getActionId() {
        return actionId;
    }

    public void setActionId(ActionIdBoundary actionId) {
        this.actionId = actionId;
    }

    public ElementOfAction getElement() {
        return element;
    }

    public void setElement(ElementOfAction element) {
        this.element = element;
    }

    public InvokingUser getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(InvokingUser invokedBy) {
        this.invokedBy = invokedBy;
    }

    public Map<String, Object> getActionAttributes() {
        return actionAttributes;
    }

    public void setActionAttributes(Map<String, Object> actionAttributes) {
        this.actionAttributes = actionAttributes;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
