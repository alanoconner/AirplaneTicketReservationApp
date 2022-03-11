package com.example.application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Route("/personalInformation")
public class PersonalInformationView extends VerticalLayout {
    Statement statement;
    int id;
    String type;
    String depCity;
    String depDate;
    String arrCity;
    String returnDate;
    int adult_n;
    int child_n;
    int price1;
    int price2;
    int total;
    String time1;
    String time2;
    String ticketID1;
    String ticketID2;
    TextField departTxt1;
    TextField arriveTxt1;
    TextField departTxt2;
    TextField arriveTxt2;
    TextField date1;
    TextField date2;
    TextField time1F;
    TextField time2F;
    AtomicReference<String> date;// date string
    Button wayType;
    Button adultNum1;
    Button childNum1;
    Button adultNum2;
    Button childNum2;
    Button price1Button;
    Button price2Button;
    Checkbox checkBox;
    Button keisan;

    public PersonalInformationView() throws ClassNotFoundException, SQLException {
        //Connecting to DBs
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbcon = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airWaysWebApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "airwayuser", "alnukod1993");
        statement=dbcon.createStatement();


        //Getting saved Attributes
        id = (int) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("id");
        type = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("waytype").toString();
        depCity = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("depcity").toString();
        arrCity = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("arrcity").toString();
        adult_n = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("adult");
        child_n = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("child");
        depDate = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("depdate").toString().replace("-","/");
        returnDate = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("retdate").toString().replace("-","/");
        ticketID1 = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("ticket1").toString();
        if(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("ticket2")!=null) {
            ticketID2 = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("ticket2").toString();
        }
        //

        //Clearing these attributes in session cache, it is Important
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("ticket1",null);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("ticket2",null);
        //

        //Getting Attributes form DBs
        if(ticketID2==null){
            ResultSet resultSet = statement.executeQuery("Select * from flightplan1 where id="+ticketID1+";");
            while (resultSet.next()){
                price1 = resultSet.getInt("price");
                time1 = resultSet.getString("departtime");
                time1 = new StringBuilder(time1).insert(time1.length()-2, ":").toString();
            }
        }else{
            ResultSet resultSet = statement.executeQuery("Select * from flightplan1 where id="+ticketID1+" or id="+ticketID2+";");
            List<Integer> prices = new ArrayList<>();
            List<String> times = new ArrayList<>();
            while (resultSet.next()){
                prices.add(resultSet.getInt("price"));
                times.add(resultSet.getString("departtime"));
            }
            price1 = prices.get(0);
            time1 = times.get(0);
            price2 = prices.get(1);
            time2 = times.get(1);

            time1 = new StringBuilder(time1).insert(time1.length()-2, ":").toString();
            time2 = new StringBuilder(time2).insert(time2.length()-2,":").toString();
        }

        //


        //Initializing variables
        date = new AtomicReference<>(depDate);
        childNum1 = new Button(Integer.toString(child_n));
        adultNum1 = new Button(Integer.toString(adult_n));
        childNum2 = new Button(Integer.toString(child_n));
        adultNum2 = new Button(Integer.toString(adult_n));
        wayType = new Button(type);
        date1 = new TextField();
        date2 = new TextField();
        arriveTxt1 = new TextField();
        departTxt1 = new TextField();
        arriveTxt2 = new TextField();
        departTxt2 = new TextField();
        price1Button = new Button();
        price2Button = new Button();
        time1F = new TextField();
        time2F = new TextField();
        checkBox = new Checkbox();
        keisan = new Button();

        //Ticket information component Setting
        departTxt1.setValue(depCity);
        departTxt1.setReadOnly(true);
        departTxt1.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        departTxt2.setValue(arrCity);
        departTxt2.setReadOnly(true);
        departTxt2.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        arriveTxt1.setValue(arrCity);
        arriveTxt1.setReadOnly(true);
        arriveTxt1.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        arriveTxt2.setValue(depCity);
        arriveTxt2.setReadOnly(true);
        arriveTxt2.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        wayType.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        adultNum1.addThemeVariants(ButtonVariant.LUMO_ICON);
        childNum1.addThemeVariants(ButtonVariant.LUMO_ICON);
        adultNum1.setIcon(new Icon(VaadinIcon.MALE));
        childNum1.setIcon(new Icon(VaadinIcon.CHILD));
        adultNum1.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        childNum1.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        adultNum2.addThemeVariants(ButtonVariant.LUMO_ICON);
        childNum2.addThemeVariants(ButtonVariant.LUMO_ICON);
        adultNum2.setIcon(new Icon(VaadinIcon.MALE));
        childNum2.setIcon(new Icon(VaadinIcon.CHILD));
        adultNum2.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        childNum2.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        date1.setValue(depDate);
        date1.setWidth(125, Unit.PIXELS);
        date1.setReadOnly(true);
        date1.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        date2.setValue(returnDate);
        date2.setWidth(125, Unit.PIXELS);
        date2.setReadOnly(true);
        date2.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        price1Button.setText(price1+"$");
        price1Button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        price2Button.setText(price2+"$");
        price2Button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        price1Button.setWidth(100,Unit.PIXELS);
        price2Button.setWidth(100,Unit.PIXELS);
        time1F.setValue(time1);
        time1F.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        time1F.setReadOnly(true);
        time2F.setValue(time2);
        time2F.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        time2F.setReadOnly(true);
        checkBox.setLabel("I accept the terms and conditions");
        total = price1*(adult_n+child_n)+price2*(adult_n+child_n);
        keisan.setText(Integer.toString(total));
        keisan.setIcon(new Icon(VaadinIcon.DOLLAR));
        keisan.setIconAfterText(true);
        keisan.addThemeVariants(ButtonVariant.LUMO_CONTRAST);




        //Personal Information Field
        H2 pitextF = new H2("Please provide personal information of passengers");

        //List for all passengers
        List<HorizontalLayout> pistrokes = new ArrayList<>();
        for (int i = 0; i < (adult_n+child_n); i++) {
            HorizontalLayout pihlayout1 = new HorizontalLayout();
            VerticalLayout pivlayout1 = new VerticalLayout();
            TextField nameF = new TextField("Name");
            TextField surnameF = new TextField("Surname");
            DatePicker birthdateF = new DatePicker("Date of Birth");
            surnameF.setWidth(250,Unit.PIXELS);
            nameF.setRequiredIndicatorVisible(true);
            surnameF.setRequiredIndicatorVisible(true);
            birthdateF.setRequiredIndicatorVisible(true);
            nameF.setErrorMessage("This field is required");
            birthdateF.setErrorMessage("This field is required");
            surnameF.setErrorMessage("This field is required");
            pihlayout1.setAlignItems(Alignment.CENTER);
            pivlayout1.setAlignItems(Alignment.CENTER);
            pihlayout1.add(nameF,surnameF, birthdateF);
            pivlayout1.add(pihlayout1);
            pistrokes.add(pihlayout1);
        }

        //Payment Information
        H3 patextF = new H3("Please provide payment and contact information");
        VerticalLayout pavlayout1 = new VerticalLayout();
        VerticalLayout pavlayout2 = new VerticalLayout();
        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        TextField cardnameF = new TextField("Cardholder Name");
        TextField cardNumF = new TextField("Card Number");
        TextField cvvF = new TextField("CVV");
        TextField phoneNF = new TextField("Phone Number");
        EmailField mailF = new EmailField("Email Address");
        DatePicker exdateF = new DatePicker("Expiration Date");
        singleFormatI18n.setDateFormat("M/yy");
        exdateF.setI18n(singleFormatI18n);
        cardnameF.setRequiredIndicatorVisible(true);
        cardNumF.setRequiredIndicatorVisible(true);
        cvvF.setRequiredIndicatorVisible(true);
        phoneNF.setRequiredIndicatorVisible(true);
        mailF.setRequiredIndicatorVisible(true);
        exdateF.setRequiredIndicatorVisible(true);
        cardnameF.setErrorMessage("This field is required");
        cardNumF.setErrorMessage("This field is required");
        cvvF.setErrorMessage("This field is required");
        phoneNF.setErrorMessage("This field is required");
        mailF.setErrorMessage("This field is required");
        exdateF.setErrorMessage("This field is required");
        pavlayout2.setAlignItems(Alignment.END);
        pavlayout1.setAlignItems(Alignment.START);
        pavlayout1.add(cardNumF,cardnameF,mailF);
        pavlayout2.add(exdateF,cvvF,phoneNF);
        ////setting these components
        cardNumF.setWidth(250,Unit.PIXELS);
        mailF.setWidth(225,Unit.PIXELS);
        cardnameF.setWidth(250,Unit.PIXELS);
        cvvF.setWidth(80,Unit.PIXELS);
        ////
        //

        //FINAL Buttons
        Button buybutton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        buybutton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_LARGE);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR,ButtonVariant.LUMO_LARGE);
        cancelButton.addClickListener(click->UI.getCurrent().navigate(""));
        buybutton.addClickListener(click->{

        });
        //


        //Layouts
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout firstL = new HorizontalLayout();
        HorizontalLayout secondL = new HorizontalLayout();
        HorizontalLayout thirdL = new HorizontalLayout();
        HorizontalLayout forthL = new HorizontalLayout();
        HorizontalLayout fifthL = new HorizontalLayout();
        HorizontalLayout sixthL = new HorizontalLayout();
        HorizontalLayout seventhL = new HorizontalLayout();
        VerticalLayout forthL_V = new VerticalLayout();
        HorizontalLayout eighthL = new HorizontalLayout();
        HorizontalLayout ninethL = new HorizontalLayout();
        //
        firstL.add(date1, departTxt1,new Icon(VaadinIcon.ARROW_RIGHT), arriveTxt1,time1F, adultNum1, childNum1,price1Button);
        secondL.add(date2,departTxt2, new Icon(VaadinIcon.ARROW_RIGHT),arriveTxt2,time2F, adultNum2, childNum2,price2Button);
        thirdL.add(pitextF);
        for(HorizontalLayout i: pistrokes) forthL_V.add(i);
        forthL.add(forthL_V);
        fifthL.add(patextF);
        sixthL.add(pavlayout1,pavlayout2);
        seventhL.add(new Text("Total amount: "),keisan);
        eighthL.add(checkBox);
        ninethL.add(cancelButton,buybutton);


        firstL.setAlignItems(Alignment.CENTER);
        secondL.setAlignItems(Alignment.CENTER);
        thirdL.setAlignItems(Alignment.END);
        forthL.setAlignItems(Alignment.CENTER);
        fifthL.setAlignItems(Alignment.CENTER);
        sixthL.setAlignItems(Alignment.CENTER);
        seventhL.setAlignItems(Alignment.CENTER);
        eighthL.setAlignItems(Alignment.CENTER);
        ninethL.setAlignItems(Alignment.CENTER);


        verticalLayout.add(firstL, secondL, thirdL,forthL,fifthL,sixthL,seventhL,eighthL,ninethL);
        verticalLayout.setAlignItems(Alignment.CENTER);

        add(
            verticalLayout
        );

    }
}
