package com.example.application.domain.repository;

import com.example.application.domain.model.Flight;
import com.example.application.domain.model.SearchCriteria;

import java.util.List;
import java.util.Optional;

/**
 * Port describing how the application expects to retrieve flight data.
 */
public interface FlightRepository {

    List<Flight> findByCriteria(SearchCriteria criteria);

    Optional<Flight> findById(String id);

    List<Flight> findAll();
}
