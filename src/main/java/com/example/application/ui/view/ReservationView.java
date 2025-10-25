package com.example.application.ui.view;

import com.example.application.application.service.ReservationDraftService;
import com.example.application.application.usecase.SearchFlightsUseCase;
import com.example.application.domain.model.Flight;
import com.example.application.domain.model.ReservationDraft;
import com.example.application.ui.components.SearchSummary;
import com.example.application.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Final step of the workflow – collects contact information and confirms the reservation.
 */
@Route(value = "reservation", layout = MainLayout.class)
@PageTitle("Passenger details")
public class ReservationView extends VerticalLayout implements BeforeEnterObserver {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final SearchFlightsUseCase searchFlightsUseCase;
    private final ReservationDraftService reservationDraftService;

    private final VerticalLayout summaryContainer = new VerticalLayout();
    private final TextField fullNameField = new TextField("Full name");
    private final EmailField emailField = new EmailField("Email address");
    private final TextArea notesField = new TextArea("Special requests");
    private final Button confirmButton = new Button("Confirm reservation");

    public ReservationView(SearchFlightsUseCase searchFlightsUseCase,
                           ReservationDraftService reservationDraftService) {
        this.searchFlightsUseCase = searchFlightsUseCase;
        this.reservationDraftService = reservationDraftService;

        configureLayout();
        configureSummary();
        configureForm();
    }

    private void configureLayout() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }

    private void configureSummary() {
        summaryContainer.setPadding(false);
        summaryContainer.setSpacing(false);
        add(new H2("Reservation summary"), summaryContainer);
    }

    private void configureForm() {
        fullNameField.setRequiredIndicatorVisible(true);
        emailField.setRequiredIndicatorVisible(true);
        emailField.setErrorMessage("Please provide a valid email address");
        notesField.setMaxLength(250);
        notesField.setWidthFull();

        FormLayout formLayout = new FormLayout(fullNameField, emailField, notesField);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2)
        );
        formLayout.setColspan(notesField, 2);

        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        confirmButton.addClickListener(event -> handleConfirmation());

        HorizontalLayout actions = new HorizontalLayout(confirmButton);
        actions.setWidthFull();
        actions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(new H2("Passenger information"), formLayout, actions);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<ReservationDraft> draftOptional = reservationDraftService.getDraft();
        if (draftOptional.isEmpty()) {
            Notification notification = Notification.show("There is no reservation in progress.");
            notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            event.forwardTo(SearchView.class);
            return;
        }

        ReservationDraft draft = draftOptional.get();
        if (!draft.isComplete()) {
            Notification notification = Notification.show("Please select your flights first.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            event.forwardTo(FlightResultsView.class);
            return;
        }

        summaryContainer.removeAll();
        summaryContainer.add(new SearchSummary(draft.getCriteria()));
        draft.getOutboundFlightId().flatMap(searchFlightsUseCase::findById)
                .ifPresent(flight -> summaryContainer.add(new Paragraph(renderFlightSummary("Outbound", flight))));
        draft.getReturnFlightId().flatMap(searchFlightsUseCase::findById)
                .ifPresent(flight -> summaryContainer.add(new Paragraph(renderFlightSummary("Return", flight))));

        fullNameField.clear();
        emailField.clear();
        emailField.setInvalid(false);
        notesField.clear();
    }

    private void handleConfirmation() {
        if (fullNameField.isEmpty() || emailField.isEmpty() || emailField.isInvalid()) {
            Notification notification = Notification.show("Please provide contact information before confirming.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        reservationDraftService.clear();
        Notification notification = Notification.show("Reservation confirmed! A confirmation email is on its way.");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        getUI().ifPresent(ui -> ui.navigate(SearchView.class));
    }

    private String renderFlightSummary(String label, Flight flight) {
        return label + ": " + flight.getFlightNumber() + " • " +
                flight.getDepartureCity() + " → " + flight.getArrivalCity() + " on " +
                DATE_FORMATTER.format(flight.getDepartureDate());
    }
}
