package com.example.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import java.sql.*;

@Route("/flightList")
public class FlightListView extends VerticalLayout {

    MainView mainView;
    Statement statement;
    int id = -1;
    String type;
    String depCity;
    Date depDate;
    String arrCity;
    Date returnDate;
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
        //Taking data from previous page
        ResultSet checkIdSet = statement.executeQuery("select id from permanentData");
        checkIdSet.next();
        if (checkIdSet.getInt("id") == (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("id")) {
            ResultSet resultSet = statement.executeQuery("select * from permanentData;");
            while (resultSet.next()) {

                id = resultSet.getInt("id");
                type = resultSet.getString("type");
                depCity = resultSet.getString("depcity");
                arrCity = resultSet.getString("arrcity");
                adult_n = resultSet.getInt("adult");
                child_n = resultSet.getInt("child");
                depDate = resultSet.getDate("depdate");
                returnDate = resultSet.getDate("returndate");
            }
        }
        //

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout firstL = new HorizontalLayout();

        //firstLayer
        TextField departTxt = new TextField();
        TextField arriveTxt = new TextField();
        TextField dates = new TextField();
        Button wayType = new Button("way type");
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
        dates.setValue(depDate + " ~ "+returnDate);
        dates.setReadOnly(true);
        dates.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);



        firstL.add(departTxt,new Icon(VaadinIcon.ARROW_RIGHT),arriveTxt,dates, wayType,adultNum,childNum);
        firstL.setAlignItems(Alignment.CENTER);

        verticalLayout.add(firstL);
        verticalLayout.setAlignItems(Alignment.CENTER);

        add(
                verticalLayout
        );
    }

    public void setId(int id){ this.id = id;}
}
