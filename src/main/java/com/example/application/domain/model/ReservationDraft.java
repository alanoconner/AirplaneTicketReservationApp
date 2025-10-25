package com.example.application.domain.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents an in-progress reservation that is being built by the user.
 */
public final class ReservationDraft {

    private final SearchCriteria criteria;
    private final String outboundFlightId;
    private final String returnFlightId;

    private ReservationDraft(Builder builder) {
        this.criteria = Objects.requireNonNull(builder.criteria, "criteria");
        this.outboundFlightId = builder.outboundFlightId;
        this.returnFlightId = builder.returnFlightId;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public Optional<String> getOutboundFlightId() {
        return Optional.ofNullable(outboundFlightId);
    }

    public Optional<String> getReturnFlightId() {
        return Optional.ofNullable(returnFlightId);
    }

    public boolean isComplete() {
        if (!criteria.isRoundTrip()) {
            return outboundFlightId != null;
        }
        return outboundFlightId != null && returnFlightId != null;
    }

    public Builder toBuilder() {
        return builder().withCriteria(criteria)
                .withOutboundFlightId(outboundFlightId)
                .withReturnFlightId(returnFlightId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SearchCriteria criteria;
        private String outboundFlightId;
        private String returnFlightId;

        private Builder() {
        }

        public Builder withCriteria(SearchCriteria criteria) {
            this.criteria = criteria;
            return this;
        }

        public Builder withOutboundFlightId(String outboundFlightId) {
            this.outboundFlightId = outboundFlightId;
            return this;
        }

        public Builder withReturnFlightId(String returnFlightId) {
            this.returnFlightId = returnFlightId;
            return this;
        }

        public ReservationDraft build() {
            return new ReservationDraft(this);
        }
    }
}
