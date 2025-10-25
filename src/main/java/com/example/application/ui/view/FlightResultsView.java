package com.example.application.ui.view;

import com.example.application.application.service.ReservationDraftService;
import com.example.application.application.usecase.SearchFlightsUseCase;
import com.example.application.domain.model.Flight;
import com.example.application.domain.model.ReservationDraft;
import com.example.application.domain.model.SearchCriteria;
import com.example.application.ui.components.SearchSummary;
import com.example.application.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Displays the search results and allows the user to pick outbound/return flights.
 */
@Route(value = "flights", layout = MainLayout.class)
@PageTitle("Available flights")
public class FlightResultsView extends VerticalLayout implements BeforeEnterObserver {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final SearchFlightsUseCase searchFlightsUseCase;
    private final ReservationDraftService reservationDraftService;

    private final Grid<Flight> outboundGrid = new Grid<>(Flight.class, false);
    private final Grid<Flight> returnGrid = new Grid<>(Flight.class, false);
    private final Button continueButton = new Button("Continue");

    public FlightResultsView(SearchFlightsUseCase searchFlightsUseCase,
                             ReservationDraftService reservationDraftService) {
        this.searchFlightsUseCase = searchFlightsUseCase;
        this.reservationDraftService = reservationDraftService;
        configureLayout();
    }

    private void configureLayout() {
        setWidthFull();
        setSpacing(true);
        setPadding(true);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        outboundGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        outboundGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        configureColumns(outboundGrid);
        outboundGrid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(flight -> {
            reservationDraftService.selectOutboundFlight(flight.getId());
            updateContinueState();
        }));

        returnGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        returnGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        configureColumns(returnGrid);
        returnGrid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(flight -> {
            reservationDraftService.selectReturnFlight(flight.getId());
            updateContinueState();
        }));

        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        continueButton.setEnabled(false);
        continueButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(ReservationView.class)));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<ReservationDraft> draftOptional = reservationDraftService.getDraft();
        if (draftOptional.isEmpty()) {
            Notification notification = Notification.show("Start by searching for flights.");
            notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            event.forwardTo(SearchView.class);
            return;
        }

        removeAll();
        ReservationDraft draft = draftOptional.get();
        SearchCriteria criteria = draft.getCriteria();

        add(new H2("Review your options"), new SearchSummary(criteria));

        List<Flight> outboundFlights = searchFlightsUseCase.search(criteria);
        if (outboundFlights.isEmpty()) {
            add(new Paragraph("No outbound flights were found for the selected criteria."));
        } else {
            outboundGrid.setItems(outboundFlights);
            draft.getOutboundFlightId().ifPresent(id -> outboundFlights.stream()
                    .filter(flight -> flight.getId().equals(id))
                    .findFirst()
                    .ifPresent(outboundGrid::select));
            add(outboundSection());
        }

        if (criteria.isRoundTrip() && criteria.getReturnDate().isPresent()) {
            SearchCriteria returnCriteria = criteria.toBuilder()
                    .withDepartureCity(criteria.getArrivalCity())
                    .withArrivalCity(criteria.getDepartureCity())
                    .withDepartureDate(criteria.getReturnDate().get())
                    .withReturnDate(null)
                    .build();
            List<Flight> returnFlights = searchFlightsUseCase.search(returnCriteria);
            if (returnFlights.isEmpty()) {
                add(new Paragraph("No return flights were found for the selected dates."));
            } else {
                returnGrid.setItems(returnFlights);
                draft.getReturnFlightId().ifPresent(id -> returnFlights.stream()
                        .filter(flight -> flight.getId().equals(id))
                        .findFirst()
                        .ifPresent(returnGrid::select));
                add(returnSection());
            }
        }

        HorizontalLayout actions = new HorizontalLayout(continueButton);
        actions.setJustifyContentMode(JustifyContentMode.END);
        actions.setWidthFull();
        add(actions);
        updateContinueState();
    }

    private VerticalLayout outboundSection() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.add(new H2("Outbound"), outboundGrid);
        return layout;
    }

    private VerticalLayout returnSection() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.add(new H2("Return"), returnGrid);
        return layout;
    }

    private void configureColumns(Grid<Flight> grid) {
        grid.addColumn(Flight::getFlightNumber).setHeader("Flight").setAutoWidth(true);
        grid.addColumn(flight -> TIME_FORMATTER.format(flight.getDepartureTime()))
                .setHeader("Departure").setAutoWidth(true);
        grid.addColumn(flight -> formatDurationHours(flight))
                .setHeader("Duration").setAutoWidth(true);
        grid.addColumn(Flight::getAvailableSeats).setHeader("Seats left").setAutoWidth(true);
        grid.addColumn(flight -> "" + flight.getPrice().setScale(2, RoundingMode.HALF_UP) + " €")
                .setHeader("Price").setAutoWidth(true);
    }

    private String formatDurationHours(Flight flight) {
        long hours = flight.getDuration().toHours();
        long minutes = flight.getDuration().toMinutesPart();
        return minutes == 0 ? hours + "h" : hours + "h " + minutes + "m";
    }

    private void updateContinueState() {
        continueButton.setEnabled(false);
        reservationDraftService.getDraft().ifPresent(draft -> {
            boolean outboundSelected = draft.getOutboundFlightId().isPresent();
            boolean canContinue = outboundSelected;
            if (draft.getCriteria().isRoundTrip()) {
                canContinue = outboundSelected && draft.getReturnFlightId().isPresent();
            }
            continueButton.setEnabled(canContinue);
        });
    }
}
