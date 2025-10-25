package com.example.application.domain.model;

import java.util.Objects;

/**
 * Value object describing the number of travelling passengers.
 */
public final class PassengerGroup {

    private final int adults;
    private final int children;

    public PassengerGroup(int adults, int children) {
        if (adults < 1) {
            throw new IllegalArgumentException("At least one adult passenger is required");
        }
        if (children < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative");
        }
        this.adults = adults;
        this.children = children;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getTotalPassengers() {
        return adults + children;
    }

    public PassengerGroup withAdults(int newAdultCount) {
        return new PassengerGroup(newAdultCount, children);
    }

    public PassengerGroup withChildren(int newChildrenCount) {
        return new PassengerGroup(adults, newChildrenCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassengerGroup)) {
            return false;
        }
        PassengerGroup that = (PassengerGroup) o;
        return adults == that.adults && children == that.children;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adults, children);
    }

    @Override
    public String toString() {
        return "PassengerGroup{" +
                "adults=" + adults +
                ", children=" + children +
                '}';
    }
}
