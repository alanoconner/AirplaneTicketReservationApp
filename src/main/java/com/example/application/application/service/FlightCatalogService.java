package com.example.application.application.service;

import com.example.application.domain.model.Flight;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides read-only access to flight metadata used to populate UI components.
 */
@Service
public class FlightCatalogService {

    private final FlightSearchService flightSearchService;

    public FlightCatalogService(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    public List<String> getDepartureCities() {
        return flightSearchService.findAllFlights().stream()
                .map(Flight::getDepartureCity)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    public List<String> getArrivalCities(String departureCity) {
        return flightSearchService.findAllFlights().stream()
                .filter(flight -> flight.getDepartureCity().equalsIgnoreCase(departureCity))
                .map(Flight::getArrivalCity)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }
}
