package com.example.application.ui.components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

/**
 * Compound component that captures the number of adults and children travelling together.
 */
public class PassengerSelector extends HorizontalLayout {

    private final IntegerField adultField = new IntegerField("Adults");
    private final IntegerField childField = new IntegerField("Children");

    public PassengerSelector() {
        configureField(adultField, 1, 9, 1);
        configureField(childField, 0, 9, 0);

        setAlignItems(Alignment.END);
        setSpacing(true);
        add(adultField, childField);
    }

    private void configureField(IntegerField field, int min, int max, int defaultValue) {
        field.setMin(min);
        field.setMax(max);
        field.setHasControls(true);
        field.setValue(defaultValue);
        field.setStep(1);
        field.setWidth("120px");
    }

    public int getAdultCount() {
        return adultField.getValue();
    }

    public int getChildCount() {
        return childField.getValue();
    }

    public void setAdultCount(int adults) {
        adultField.setValue(adults);
    }

    public void setChildCount(int children) {
        childField.setValue(children);
    }
}
