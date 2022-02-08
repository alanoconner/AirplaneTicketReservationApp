package com.example.application;

import java.sql.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Route("")
public class MainView extends VerticalLayout {

    // DataBases
    Statement statement;
    //

    //Date and destination
    ComboBox<String> dep = new ComboBox<>("Departure");
    ComboBox<String> arr = new ComboBox<>("Arrival");
    DatePicker startDate = new DatePicker("Select date");
    DatePicker endDate = new DatePicker("Select date");
    DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
    //Layouts
    VerticalLayout departAndDateV = new VerticalLayout();
    HorizontalLayout datesV = new HorizontalLayout();
    HorizontalLayout destinationsV = new HorizontalLayout();
    HorizontalLayout thirdV = new HorizontalLayout();
    VerticalLayout passNumV = new VerticalLayout();// route and passengers num
    HorizontalLayout forthV = new HorizontalLayout();
    //Buttons
    Button changeBtn = new Button("Two-way");// one or two-way ticket
    Button searchButton = new Button("Search", event -> UI.getCurrent().navigate("/flightList"));
    //Integer fields
    IntegerField adultNum = new IntegerField();
    IntegerField childNum = new IntegerField();

    public MainView() throws SQLException, ClassNotFoundException {
        //Initialize connection to DBs
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbcon = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airWaysWebApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "airwayuser", "alnukod1993");
        statement=dbcon.createStatement();


        //Date and destination
        startDate.setI18n(singleFormat);
        endDate.setI18n(singleFormat);
        startDate.addValueChangeListener(e->endDate.setMin(e.getValue()));
        endDate.addValueChangeListener(e->startDate.setMax(e.getValue()));
        startDate.setWidth(150, Unit.PIXELS);
        endDate.setWidth(150,Unit.PIXELS);
        dep.setWidth(230,Unit.PIXELS);
        arr.setWidth(230,Unit.PIXELS);
        //Adding values from databases to destination ComboBox
        List<String> cityList = new ArrayList<>();
        ResultSet resForCBox = statement.executeQuery("select * from flightplan1");
        while (resForCBox.next()){
            if (!cityList.contains(resForCBox.getString("departcity")))cityList.add(resForCBox.getString("departcity"));

        }
        dep.setItems(cityList);
        arr.setItems(cityList);


        //Buttons
        AtomicInteger counter= new AtomicInteger(1);
        changeBtn.addClickListener(clickEvent->{
            counter.getAndIncrement();
            if(counter.get() %2==0){
                changeBtn.setText("One-way");
                endDate.setReadOnly(true);
            }
            else {
                changeBtn.setText("Two-way");
                endDate.setReadOnly(false);
            }
            changeBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        });
        changeBtn.setSizeFull();
        changeBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        searchButton.addClickShortcut(Key.ENTER);
        searchButton.setIcon(new Icon(VaadinIcon.SEARCH));
        searchButton.setIconAfterText(true);
        searchButton.addThemeVariants(ButtonVariant.LUMO_LARGE);



        //Number of passengers
        HorizontalLayout adultL = new HorizontalLayout();
        HorizontalLayout childL = new HorizontalLayout();//local layouts
        Text adultLabel = new Text("Adult");
        Text childLabel = new Text("Child");
        adultNum.setValue(1);
        childNum.setValue(0);
        adultNum.setHasControls(true);
        childNum.setHasControls(true);
        adultL.add(adultLabel,adultNum);
        childL.add(childLabel,childNum);
        adultL.setAlignItems(Alignment.CENTER);
        childL.setAlignItems(Alignment.CENTER);
        adultNum.setWidth(95,Unit.PIXELS);
        childNum.setWidth(95,Unit.PIXELS);
        childL.setPadding(false);
        adultL.setPadding(false);

        //


        forthV.add(searchButton);

        passNumV.add(adultL,childL);


        thirdV.add(changeBtn,passNumV);
        thirdV.setAlignItems(Alignment.CENTER);


        datesV.add(startDate,endDate);


        destinationsV.add(dep,arr);


        departAndDateV.add(destinationsV, datesV, thirdV,forthV);

        departAndDateV.setAlignItems(FlexComponent.Alignment.CENTER);
        setHeightFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);



        add(

                new H1(""),
                departAndDateV

        );



    }
}
