package com.example.application.application.service;

import com.example.application.application.usecase.SearchFlightsUseCase;
import com.example.application.domain.model.Flight;
import com.example.application.domain.model.SearchCriteria;
import com.example.application.domain.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link SearchFlightsUseCase} that delegates to a repository.
 */
@Service
public class FlightSearchService implements SearchFlightsUseCase {

    private final FlightRepository flightRepository;

    public FlightSearchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> search(SearchCriteria criteria) {
        return flightRepository.findByCriteria(criteria);
    }

    @Override
    public Optional<Flight> findById(String id) {
        return flightRepository.findById(id);
    }

    public List<Flight> findAllFlights() {
        return flightRepository.findAll();
    }
}
