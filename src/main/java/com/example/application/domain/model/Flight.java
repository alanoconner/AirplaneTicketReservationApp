package com.example.application.domain.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain aggregate representing a flight that can be searched and reserved.
 */
public final class Flight {

    private final String id;
    private final String flightNumber;
    private final String departureCity;
    private final String arrivalCity;
    private final LocalDate departureDate;
    private final LocalTime departureTime;
    private final Duration duration;
    private final int availableSeats;
    private final BigDecimal price;

    private Flight(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID().toString();
        this.flightNumber = Objects.requireNonNull(builder.flightNumber, "flightNumber");
        this.departureCity = Objects.requireNonNull(builder.departureCity, "departureCity");
        this.arrivalCity = Objects.requireNonNull(builder.arrivalCity, "arrivalCity");
        this.departureDate = Objects.requireNonNull(builder.departureDate, "departureDate");
        this.departureTime = Objects.requireNonNull(builder.departureTime, "departureTime");
        this.duration = Objects.requireNonNull(builder.duration, "duration");
        this.availableSeats = builder.availableSeats;
        this.price = Objects.requireNonNull(builder.price, "price");
    }

    public String getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
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

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Builder toBuilder() {
        return builder()
                .withId(id)
                .withFlightNumber(flightNumber)
                .withDepartureCity(departureCity)
                .withArrivalCity(arrivalCity)
                .withDepartureDate(departureDate)
                .withDepartureTime(departureTime)
                .withDuration(duration)
                .withAvailableSeats(availableSeats)
                .withPrice(price);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flight)) {
            return false;
        }
        Flight flight = (Flight) o;
        return id.equals(flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", departureDate=" + departureDate +
                ", departureTime=" + departureTime +
                ", duration=" + duration +
                ", availableSeats=" + availableSeats +
                ", price=" + price +
                '}';
    }

    public static final class Builder {
        private String id;
        private String flightNumber;
        private String departureCity;
        private String arrivalCity;
        private LocalDate departureDate;
        private LocalTime departureTime;
        private Duration duration = Duration.ZERO;
        private int availableSeats;
        private BigDecimal price = BigDecimal.ZERO;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
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

        public Builder withDepartureTime(LocalTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder withDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder withAvailableSeats(int availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Flight build() {
            return new Flight(this);
        }
    }
}
