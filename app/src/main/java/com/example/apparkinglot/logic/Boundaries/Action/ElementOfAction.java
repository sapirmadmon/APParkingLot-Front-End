package com.example.apparkinglot.logic.Boundaries.Action;


import com.example.apparkinglot.logic.Boundaries.Element.ElementIdBoundary;

public class ElementOfAction {
    ElementIdBoundary elementId;

    public ElementOfAction(ElementIdBoundary element) {
        super();
        this.elementId = element;
    }

    public ElementOfAction() {

    }

    public ElementIdBoundary getElement() {
        return elementId;
    }

    public void setElement(ElementIdBoundary element) {
        this.elementId = element;
    }
}
