package com.example.application.application.service;

import com.example.application.domain.model.ReservationDraft;
import com.example.application.domain.model.SearchCriteria;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Session scoped service that holds the reservation state while the user navigates between views.
 */
@Component
@VaadinSessionScope
public class ReservationDraftService {

    private ReservationDraft draft;

    public void start(SearchCriteria criteria) {
        this.draft = ReservationDraft.builder()
                .withCriteria(criteria)
                .build();
    }

    public Optional<ReservationDraft> getDraft() {
        return Optional.ofNullable(draft);
    }

    public void selectOutboundFlight(String flightId) {
        ensureDraftExists();
        draft = draft.toBuilder()
                .withOutboundFlightId(flightId)
                .build();
    }

    public void selectReturnFlight(String flightId) {
        ensureDraftExists();
        draft = draft.toBuilder()
                .withReturnFlightId(flightId)
                .build();
    }

    public void clear() {
        draft = null;
    }

    private void ensureDraftExists() {
        if (draft == null) {
            throw new IllegalStateException("Reservation draft has not been started");
        }
    }
}
