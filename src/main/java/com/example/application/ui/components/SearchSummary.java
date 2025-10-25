package com.example.application.ui.components;

import com.example.application.domain.model.SearchCriteria;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.format.DateTimeFormatter;

/**
 * Visual summary of the current search parameters displayed above result grids.
 */
public class SearchSummary extends VerticalLayout {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public SearchSummary(SearchCriteria criteria) {
        setPadding(false);
        setSpacing(false);
        add(createLine("Route", criteria.getDepartureCity() + " → " + criteria.getArrivalCity()));
        add(createLine("Departure", DATE_FORMATTER.format(criteria.getDepartureDate())));
        criteria.getReturnDate().ifPresent(date -> add(createLine("Return", DATE_FORMATTER.format(date))));
        add(createLine("Passengers", String.valueOf(criteria.getPassengerGroup().getTotalPassengers())));
    }

    private HorizontalLayout createLine(String label, String value) {
        Span labelSpan = new Span(label + ":");
        labelSpan.getStyle().set("font-weight", "600");
        Span valueSpan = new Span(value);
        HorizontalLayout layout = new HorizontalLayout(labelSpan, valueSpan);
        layout.setAlignItems(Alignment.BASELINE);
        layout.setSpacing(true);
        return layout;
    }
}
