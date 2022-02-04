package com.example.application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
          VerticalLayout departAndDateV = new VerticalLayout();
          HorizontalLayout datesV = new HorizontalLayout();
          HorizontalLayout destinationsV = new HorizontalLayout();
          HorizontalLayout thirdV = new HorizontalLayout();
          VerticalLayout passNumV = new VerticalLayout();// route and passengers num

        //Date and destination
        ComboBox<String> dep = new ComboBox<>("Departure");
        ComboBox<String> arr = new ComboBox<>("Arrival");
        DatePicker startDate = new DatePicker("Select date");
        DatePicker endDate = new DatePicker("Select date");
        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        startDate.setI18n(singleFormat);
        endDate.setI18n(singleFormat);
        singleFormat.setDateFormat("yyyy.mm.dd");
        startDate.addValueChangeListener(e->endDate.setMin(e.getValue()));
        endDate.addValueChangeListener(e->startDate.setMax(e.getValue()));
        startDate.setWidth(150, Unit.PIXELS);
        endDate.setWidth(150,Unit.PIXELS);
        dep.setWidth(230,Unit.PIXELS);
        arr.setWidth(230,Unit.PIXELS);


        //Buttons
        Button changeBtn = new Button("Two-way");// one or two-way ticket
        AtomicInteger counter= new AtomicInteger(1);
        changeBtn.addClickListener(clickEvent->{
            counter.getAndIncrement();
            if(counter.get() %2==0)changeBtn.setText("One-way");
            else changeBtn.setText("Two-way");
        });
        changeBtn.setSizeFull();



        //Number of passengers
        HorizontalLayout adultL = new HorizontalLayout();
        HorizontalLayout childL = new HorizontalLayout();
        IntegerField adultNum = new IntegerField();
        IntegerField childNum = new IntegerField();
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


        passNumV.add(adultL,childL);


        thirdV.add(changeBtn,passNumV);
        thirdV.setAlignItems(Alignment.CENTER);



        datesV.add(startDate,endDate);


        destinationsV.add(dep,arr);


        departAndDateV.add(destinationsV, datesV, thirdV);
        departAndDateV.setHeightFull();
        departAndDateV.setAlignItems(Alignment.CENTER);


        add(

                new H1(""),
                departAndDateV

        );



    }
}
