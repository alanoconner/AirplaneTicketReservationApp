package com.example.application.infrastructure.persistence;

import com.example.application.domain.model.Flight;
import com.example.application.domain.model.SearchCriteria;
import com.example.application.domain.repository.FlightRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simple in-memory repository that exposes a curated flight schedule.
 */
@Repository
public class InMemoryFlightRepository implements FlightRepository {

    private final List<Flight> flights;

    public InMemoryFlightRepository() {
        flights = initialiseFlights();
    }

    @Override
    public List<Flight> findByCriteria(SearchCriteria criteria) {
        return flights.stream()
                .filter(flight -> flight.getDepartureCity().equalsIgnoreCase(criteria.getDepartureCity()))
                .filter(flight -> flight.getArrivalCity().equalsIgnoreCase(criteria.getArrivalCity()))
                .filter(flight -> flight.getDepartureDate().equals(criteria.getDepartureDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Flight> findById(String id) {
        return flights.stream().filter(flight -> flight.getId().equals(id)).findFirst();
    }

    @Override
    public List<Flight> findAll() {
        return Collections.unmodifiableList(flights);
    }

    private List<Flight> initialiseFlights() {
        List<Flight> data = new ArrayList<>();
        LocalDate today = LocalDate.now();

        data.add(flight("JX100", "London", "New York", today.plusDays(7), LocalTime.of(8, 45), 7, 189, new BigDecimal("699")));
        data.add(flight("JX101", "London", "New York", today.plusDays(7), LocalTime.of(12, 15), 8, 42, new BigDecimal("749")));
        data.add(flight("JX102", "London", "New York", today.plusDays(14), LocalTime.of(9, 30), 7, 51, new BigDecimal("689")));
        data.add(flight("JX103", "London", "Tokyo", today.plusDays(10), LocalTime.of(22, 0), 13, 26, new BigDecimal("1199")));
        data.add(flight("JX104", "Tokyo", "London", today.plusDays(16), LocalTime.of(11, 25), 13, 19, new BigDecimal("1149")));
        data.add(flight("JX105", "New York", "London", today.plusDays(15), LocalTime.of(16, 50), 7, 33, new BigDecimal("699")));
        data.add(flight("JX106", "New York", "London", today.plusDays(15), LocalTime.of(21, 10), 7, 12, new BigDecimal("659")));
        data.add(flight("JX107", "London", "Berlin", today.plusDays(5), LocalTime.of(6, 30), 2, 74, new BigDecimal("199")));
        data.add(flight("JX108", "Berlin", "London", today.plusDays(9), LocalTime.of(14, 45), 2, 67, new BigDecimal("189")));
        data.add(flight("JX109", "London", "Madrid", today.plusDays(3), LocalTime.of(11, 5), 2, 58, new BigDecimal("159")));
        data.add(flight("JX110", "Madrid", "London", today.plusDays(6), LocalTime.of(9, 10), 2, 39, new BigDecimal("169")));
        data.add(flight("JX111", "Madrid", "New York", today.plusDays(8), LocalTime.of(14, 30), 8, 21, new BigDecimal("899")));
        data.add(flight("JX112", "New York", "Madrid", today.plusDays(12), LocalTime.of(10, 0), 8, 18, new BigDecimal("879")));
        data.add(flight("JX113", "Tokyo", "Sydney", today.plusDays(11), LocalTime.of(7, 15), 9, 15, new BigDecimal("999")));
        data.add(flight("JX114", "Sydney", "Tokyo", today.plusDays(17), LocalTime.of(13, 5), 9, 22, new BigDecimal("989")));
        data.add(flight("JX115", "London", "Tokyo", today.plusDays(12), LocalTime.of(13, 40), 13, 48, new BigDecimal("1249")));
        data.add(flight("JX116", "Tokyo", "London", today.plusDays(20), LocalTime.of(20, 55), 13, 44, new BigDecimal("1189")));

        return data;
    }

    private Flight flight(String number, String departure, String arrival, LocalDate date, LocalTime time,
                          int durationHours, int seats, BigDecimal price) {
        return Flight.builder()
                .withFlightNumber(number)
                .withDepartureCity(departure)
                .withArrivalCity(arrival)
                .withDepartureDate(date)
                .withDepartureTime(time)
                .withDuration(Duration.ofHours(durationHours))
                .withAvailableSeats(seats)
                .withPrice(price)
                .build();
    }
}
