package com.example.application;

import com.vaadin.flow.component.button.Button;

public class Flight {
    private Integer id;
    private String depart_city_name;
    private String arrival_city_name;
    private String depart_date;
    private String depart_time;
    private float flight_time;
    private Integer available_seats;
    private String price;



    private Button button;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepart_city_name() {
        return depart_city_name;
    }

    public void setDepart_city_name(String depart_city_name) {
        this.depart_city_name = depart_city_name;
    }

    public String getArrival_city_name() {
        return arrival_city_name;
    }

    public void setArrival_city_name(String arrival_city_name) {
        this.arrival_city_name = arrival_city_name;
    }

    public String getDepart_date() {
        return depart_date;
    }

    public void setDepart_date(String depart_date) {
        this.depart_date = depart_date;
    }

    public String getDepart_time() {
        return depart_time;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public float getFlight_time() {
        return flight_time;
    }

    public void setFlight_time(float flight_time) {
        this.flight_time = flight_time;
    }

    public Integer getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(Integer available_seats) {
        this.available_seats = available_seats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Button getButton() { return button; }

    public void setButton(Button button) {this.button = button;}

}
