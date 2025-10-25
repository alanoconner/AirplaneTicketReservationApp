# Architecture notes

This document provides extra colour around the responsibilities of each layer and how the Vaadin
views interact with the use cases.

## Flow overview

1. **SearchView** collects the user input and converts it into a `SearchCriteria` value object.
2. The criteria is stored inside `ReservationDraftService`, a session-scoped component that keeps the
   workflow state for the current Vaadin session.
3. **FlightResultsView** retrieves the draft, executes `SearchFlightsUseCase.search(...)` to obtain
   outbound/return flight lists and saves the selected identifiers back into the draft service.
4. **ReservationView** guards access by verifying that the draft is complete. It then fetches the
   selected flights through `SearchFlightsUseCase.findById(...)`, renders a summary and collects
   passenger contact details.
5. When the reservation is confirmed the draft is cleared, ready for the next booking.

## Key design decisions

* **Immutable domain models** – `Flight`, `SearchCriteria` and `PassengerGroup` are immutable, making
  them safe to share between threads and easy to test.
* **Ports and adapters** – `FlightRepository` is the inbound port for retrieving flight data. The
  `InMemoryFlightRepository` adapter can be swapped with a persistent implementation without touching
  the UI or use cases.
* **Encapsulated session state** – only `ReservationDraftService` reads/writes session attributes. The
  UI interacts with it via methods that enforce the correct workflow order.
* **Composable UI components** – frequently reused snippets such as passenger selectors and search
  summaries are extracted into dedicated classes to keep the views uncluttered.

## Future enhancements

* Add validation annotations and unit tests around the use cases.
* Integrate a pricing engine by introducing a new `PricingService` use case that the UI can query.
* Persist completed reservations by implementing an outbound port (e.g. `ReservationRepository`).

