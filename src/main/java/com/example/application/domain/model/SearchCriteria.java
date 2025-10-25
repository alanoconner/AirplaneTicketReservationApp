package com.example.application.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable criteria used to search flights.
 */
public final class SearchCriteria {

    private final String departureCity;
    private final String arrivalCity;
    private final LocalDate departureDate;
    private final LocalDate returnDate;
    private final PassengerGroup passengerGroup;

    private SearchCriteria(Builder builder) {
        this.departureCity = Objects.requireNonNull(builder.departureCity, "departureCity");
        this.arrivalCity = Objects.requireNonNull(builder.arrivalCity, "arrivalCity");
        this.departureDate = Objects.requireNonNull(builder.departureDate, "departureDate");
        this.returnDate = builder.returnDate;
        this.passengerGroup = Objects.requireNonNull(builder.passengerGroup, "passengerGroup");
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Optional<LocalDate> getReturnDate() {
        return Optional.ofNullable(returnDate);
    }

    public PassengerGroup getPassengerGroup() {
        return passengerGroup;
    }

    public boolean isRoundTrip() {
        return returnDate != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .withDepartureCity(departureCity)
                .withArrivalCity(arrivalCity)
                .withDepartureDate(departureDate)
                .withReturnDate(returnDate)
                .withPassengerGroup(passengerGroup);
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", passengerGroup=" + passengerGroup +
                '}';
    }

    public static final class Builder {
        private String departureCity;
        private String arrivalCity;
        private LocalDate departureDate;
        private LocalDate returnDate;
        private PassengerGroup passengerGroup = new PassengerGroup(1, 0);

        private Builder() {
        }

        public Builder withDepartureCity(String departureCity) {
            this.departureCity = departureCity;
            return this;
        }

        public Builder withArrivalCity(String arrivalCity) {
            this.arrivalCity = arrivalCity;
            return this;
        }

        public Builder withDepartureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder withReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder withPassengerGroup(PassengerGroup passengerGroup) {
            this.passengerGroup = passengerGroup;
            return this;
        }

        public SearchCriteria build() {
            return new SearchCriteria(this);
        }
    }
}
