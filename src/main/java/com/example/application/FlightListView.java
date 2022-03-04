package com.example.application;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Route("/flightList")
public class FlightListView extends VerticalLayout {

    MainView mainView;
    Statement statement;
    int id = -1;
    String type;
    String depCity;
    String depDate;
    String arrCity;
    String returnDate;
    int adult_n;
    int child_n;

    {
        try {
            mainView = new MainView();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public FlightListView() throws SQLException, ClassNotFoundException {

        //DataBase
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbcon = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airWaysWebApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "airwayuser", "alnukod1993");
        statement=dbcon.createStatement();
        List<Flight> flightlist= new ArrayList<>();
        List<Flight> tableData = new ArrayList<>();//empty list


        //Taking data from previous page
        id = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("id");
        type = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("waytype").toString();
        depCity = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("depcity").toString();
        arrCity = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("arrcity").toString();
        adult_n = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("adult");
        child_n = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("child");
        depDate = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("depdate").toString().replace("-","/");
        returnDate = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("retdate").toString().replace("-","/");
        //

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout firstL = new HorizontalLayout();

        //firstLayer
        TextField departTxt = new TextField();
        TextField arriveTxt = new TextField();
        TextField dates = new TextField();
        AtomicReference<String> date = new AtomicReference<>(depDate);// date string
        Button wayType = new Button(type);
        Button adultNum = new Button(Integer.toString(adult_n));
        Button childNum = new Button(Integer.toString(child_n));
        departTxt.setValue(depCity);
        departTxt.setReadOnly(true);
        departTxt.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        arriveTxt.setValue(arrCity);
        arriveTxt.setReadOnly(true);
        arriveTxt.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        wayType.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        adultNum.addThemeVariants(ButtonVariant.LUMO_ICON);
        childNum.addThemeVariants(ButtonVariant.LUMO_ICON);
        adultNum.setIcon(new Icon(VaadinIcon.MALE));
        childNum.setIcon(new Icon(VaadinIcon.CHILD));
        adultNum.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        childNum.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        dates.setValue(date.get());
        dates.setWidth(275, Unit.PIXELS);
        dates.setReadOnly(true);
        dates.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);


        //Grid
        Grid<Flight> grid = new Grid<>();
        createGrid(grid);
        grid.addComponentColumn(Flight->{Button buybutton = new Button("Buy", click->{// navigates to next action depending on way type
            if(wayType.getText().equals("Two-way")){
                departTxt.setValue(arrCity);
                arriveTxt.setValue(depCity);
                date.set(returnDate);
                dates.setValue(returnDate);
            }
            try {
                grid.removeAllColumns();
                createGrid(grid);
                fillGrid(arrCity,returnDate,depCity,tableData,grid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
            return buybutton;
        });
        if(dates.getValue().equals(depDate)){fillGrid(depCity,date.toString(),arrCity,flightlist,grid);}



        //Setting Layouts
        firstL.add(departTxt,new Icon(VaadinIcon.ARROW_RIGHT),arriveTxt,dates, wayType,adultNum,childNum);
        firstL.setAlignItems(Alignment.CENTER);

        verticalLayout.add(firstL);
        verticalLayout.setAlignItems(Alignment.CENTER);

        add(
                verticalLayout,
                grid
        );
    }

    public void setId(int id){ this.id = id;}

    private void fillGrid(String depcity, String flightdate, String arrcity,List<Flight> flightlist, Grid grid) throws SQLException {
        //Filling grid

        ResultSet resultSet = statement.executeQuery("Select * from flightplan1 where departcity='"+depcity+
                "' and departdate='"+flightdate.replace("/",".")+
                "' and arrcity='"+arrcity+"';");
        while (resultSet.next()){

            Flight flight = new Flight();
            flight.setId(resultSet.getInt("id"));
            String deptime = resultSet.getString("departtime");
            deptime =new StringBuilder(deptime).insert(deptime.length()-2, ":").toString();
            flight.setDepart_time(deptime);
            flight.setFlight_time((int)resultSet.getFloat("flighttime"));
            flight.setPrice(resultSet.getInt("price"));
            flight.setDepart_city_name(depCity);
            flight.setArrival_city_name(arrCity);
            flight.setDepart_date(depDate);
            flightlist.add(flight);

        }
        grid.setItems(flightlist);
        //
    }

    private void createGrid(Grid<Flight> grid){
        grid.addColumn(Flight::getId).setHeader("Flight ID");
        grid.addColumn(Flight::getDepart_city_name).setHeader("Departure");
        grid.addColumn(Flight::getArrival_city_name).setHeader("Arrival");
        grid.addColumn(Flight::getDepart_date).setHeader("Date");
        grid.addColumn(Flight::getDepart_time).setHeader("Departure time");
        grid.addColumn(Flight::getFlight_time).setHeader("Flight time");
        grid.addColumn(Flight::getPrice).setHeader("Price(USD)");

    }


}
