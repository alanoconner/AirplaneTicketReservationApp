package com.example.application.ui.view;

import com.example.application.application.service.FlightCatalogService;
import com.example.application.application.service.ReservationDraftService;
import com.example.application.domain.model.PassengerGroup;
import com.example.application.domain.model.SearchCriteria;
import com.example.application.ui.components.PassengerSelector;
import com.example.application.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entry point that allows the user to configure a flight search.
 */
@Route(value = "", layout = MainLayout.class)
@PageTitle("Search flights")
public class SearchView extends VerticalLayout {

    private final FlightCatalogService catalogService;
    private final ReservationDraftService reservationDraftService;

    private final ComboBox<String> departureCombo = new ComboBox<>("Departure city");
    private final ComboBox<String> arrivalCombo = new ComboBox<>("Arrival city");
    private final DatePicker departureDate = new DatePicker("Departure date");
    private final DatePicker returnDate = new DatePicker("Return date");
    private final Checkbox roundTrip = new Checkbox("Round trip");
    private final PassengerSelector passengerSelector = new PassengerSelector();
    private final Button searchButton = new Button("Search flights");

    public SearchView(FlightCatalogService catalogService, ReservationDraftService reservationDraftService) {
        this.catalogService = catalogService;
        this.reservationDraftService = reservationDraftService;

        configureLayout();
        configureComponents();
    }

    private void configureLayout() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(false);
        setPadding(true);
    }

    private void configureComponents() {
        departureCombo.setItems(catalogService.getDepartureCities());
        departureCombo.addValueChangeListener(event -> updateArrivalOptions(event.getValue()));
        departureCombo.setRequiredIndicatorVisible(true);

        arrivalCombo.setRequiredIndicatorVisible(true);
        arrivalCombo.setAllowCustomValue(false);

        departureDate.setMin(LocalDate.now());
        departureDate.setRequiredIndicatorVisible(true);

        returnDate.setMin(LocalDate.now());
        returnDate.setEnabled(false);

        roundTrip.addValueChangeListener(event -> {
            boolean enabled = Boolean.TRUE.equals(event.getValue());
            returnDate.setEnabled(enabled);
            if (!enabled) {
                returnDate.clear();
            }
        });

        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickListener(event -> executeSearch());

        VerticalLayout form = new VerticalLayout();
        form.setWidth("420px");
        form.setPadding(false);
        form.setSpacing(true);
        form.add(
                new H1("Find your next flight"),
                departureCombo,
                arrivalCombo,
                createTripLayout(),
                passengerSelector,
                searchButton
        );

        add(form);
    }

    private HorizontalLayout createTripLayout() {
        HorizontalLayout layout = new HorizontalLayout(roundTrip, departureDate, returnDate);
        layout.setAlignItems(Alignment.END);
        layout.setSpacing(true);
        return layout;
    }

    private void updateArrivalOptions(String departureCity) {
        if (departureCity == null) {
            arrivalCombo.clear();
            arrivalCombo.setItems();
            return;
        }
        arrivalCombo.setItems(catalogService.getArrivalCities(departureCity));
        arrivalCombo.clear();
    }

    private void executeSearch() {
        if (!validateInputs()) {
            Notification notification = Notification.show("Please complete all required fields.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        PassengerGroup passengers = new PassengerGroup(passengerSelector.getAdultCount(), passengerSelector.getChildCount());
        SearchCriteria criteria = SearchCriteria.builder()
                .withDepartureCity(Objects.requireNonNull(departureCombo.getValue()))
                .withArrivalCity(Objects.requireNonNull(arrivalCombo.getValue()))
                .withDepartureDate(Objects.requireNonNull(departureDate.getValue()))
                .withReturnDate(roundTrip.getValue() ? returnDate.getValue() : null)
                .withPassengerGroup(passengers)
                .build();

        reservationDraftService.start(criteria);
        getUI().ifPresent(ui -> ui.navigate(FlightResultsView.class));
    }

    private boolean validateInputs() {
        if (departureCombo.isEmpty() || arrivalCombo.isEmpty() || departureDate.isEmpty()) {
            return false;
        }
        if (Boolean.TRUE.equals(roundTrip.getValue())) {
            return !returnDate.isEmpty();
        }
        return true;
    }
}
