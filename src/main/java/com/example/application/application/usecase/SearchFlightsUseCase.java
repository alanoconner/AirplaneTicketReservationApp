package com.example.application.application.usecase;

import com.example.application.domain.model.Flight;
import com.example.application.domain.model.SearchCriteria;

import java.util.List;
import java.util.Optional;

/**
 * Application service boundary exposing the ability to search for flights.
 */
public interface SearchFlightsUseCase {

    List<Flight> search(SearchCriteria criteria);

    Optional<Flight> findById(String id);
}
