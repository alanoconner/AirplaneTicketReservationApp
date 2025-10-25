# Skyward Reservation

Skyward Reservation is a Spring Boot + Vaadin demo that showcases how to structure a reservation
workflow using Clean Architecture and SOLID principles. The code base is intentionally crafted to be
interview-ready: classes are small, responsibilities are well defined and the layers are isolated by
interfaces.

The application lets you:

* Search for available flights based on departure/arrival cities, travel dates and passenger counts.
* Review outbound and return flight options in a dedicated results view.
* Capture basic passenger information before confirming the reservation.

All data is served from an in-memory catalogue so the application runs without any external
infrastructure or database dependencies.

## Getting started

```bash
./mvnw spring-boot:run
```

The application boots on [http://localhost:8080](http://localhost:8080). Because the project uses the
Vaadin development server, hot reload is available for server-side changes.

## Architectural overview

The code is organised around four primary layers:

| Layer            | Package                                        | Responsibility |
| ---------------- | ----------------------------------------------- | -------------- |
| **Domain**       | `com.example.application.domain`               | Immutable models (`Flight`, `SearchCriteria`, `PassengerGroup`) and repository ports that describe the business vocabulary. |
| **Application**  | `com.example.application.application`          | Use cases (`SearchFlightsUseCase`) and session services (`ReservationDraftService`) that orchestrate workflows while depending only on domain abstractions. |
| **Infrastructure** | `com.example.application.infrastructure`     | Concrete adapters such as the in-memory `InMemoryFlightRepository` that satisfies the domain repository port. |
| **UI**           | `com.example.application.ui`                   | Vaadin views, layouts and reusable components. Each view depends exclusively on application services to keep the UI thin. |

This separation keeps dependencies pointing inwards and enables individual components to be unit
or integration tested in isolation.

### Clean boundaries

* The UI layer never talks directly to infrastructure classes; it collaborates with services defined
  behind interfaces (e.g. `SearchFlightsUseCase`).
* Session state is encapsulated in `ReservationDraftService`, a `@VaadinSessionScope` bean that
  persists user progress across views.
* `FlightRepository` acts as the gateway interface. Swapping the in-memory implementation with a
  database-backed adapter requires no UI changes.

### SOLID highlights

* **Single Responsibility** – each class focuses on a single concern: `FlightCatalogService` exposes
  read models for the UI, `FlightResultsView` renders data, and `ReservationDraftService` stores
  state.
* **Open/Closed** – behaviour can be extended by implementing additional repository adapters or use
  cases without modifying existing code.
* **Liskov Substitution** – all application services depend on the `FlightRepository` interface,
  allowing any compliant implementation to be substituted transparently.
* **Interface Segregation** – the domain exposes lean interfaces (`FlightRepository`,
  `SearchFlightsUseCase`) tailored to the needs of each layer.
* **Dependency Inversion** – high-level policies (use cases) rely on abstractions, not concrete
  infrastructure classes.

## Key classes

* `FlightSearchService` – default use-case implementation that delegates to the repository port.
* `ReservationDraftService` – session-scoped state holder that coordinates selections between views.
* `SearchView`, `FlightResultsView`, `ReservationView` – Vaadin views that guide the user through the
  reservation workflow.
* `PassengerSelector` & `SearchSummary` – UI components that encapsulate reusable view logic.

## Extending the demo

The project intentionally keeps the data source simple, but the boundaries make it easy to integrate
real systems:

* Replace `InMemoryFlightRepository` with a JDBC/JPA implementation to fetch live schedules.
* Add validation, pricing rules or loyalty perks inside new domain services without touching the UI.
* Introduce additional views (e.g. booking history) that reuse the existing use cases.

For additional background, read [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## Running the tests

The project relies on Vaadin's default testing support. Execute unit tests using:

```bash
./mvnw test
```

## License

This project is distributed under the MIT License. See [LICENSE.md](LICENSE.md) for details.
