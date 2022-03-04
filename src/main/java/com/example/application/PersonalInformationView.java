package com.example.application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Route("/personalInformation")
public class PersonalInformationView extends VerticalLayout {
    int id;
    String type;
    String depCity;
    String depDate;
    String arrCity;
    String returnDate;
    int adult_n;
    int child_n;
    int price;

    TextField departTxt1;
    TextField arriveTxt1;
    TextField departTxt2;
    TextField arriveTxt2;
    TextField date1;
    TextField date2;
    AtomicReference<String> date;// date string
    Button wayType;
    Button adultNum1;
    Button childNum1;
    Button adultNum2;
    Button childNum2;

    public PersonalInformationView() {

        //Getting saved Attributes
        id = (int) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("id");
        type = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("waytype").toString();
        depCity = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("depcity").toString();
        arrCity = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("arrcity").toString();
        adult_n = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("adult");
        child_n = (int)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("child");
        depDate = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("depdate").toString().replace("-","/");
        returnDate = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("retdate").toString().replace("-","/");

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
        TextField cardnameF = new TextField("Cardholder Name");
        TextField cardNumF = new TextField("Card Number");
        TextField cvvF = new TextField("CVV");
        TextField phoneNF = new TextField("Phone Number");
        EmailField mailF = new EmailField("Email Address");
        DatePicker exdateF = new DatePicker("Expiration Date");
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

        //Buttons
        Button buybutton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        buybutton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_LARGE);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR,ButtonVariant.LUMO_LARGE);
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

        firstL.add(date1, departTxt1,new Icon(VaadinIcon.ARROW_RIGHT), arriveTxt1, adultNum1, childNum1);
        secondL.add(date2,departTxt2, new Icon(VaadinIcon.ARROW_RIGHT),arriveTxt2, adultNum2, childNum2);
        thirdL.add(pitextF);
        for(HorizontalLayout i: pistrokes) forthL_V.add(i);
        forthL.add(forthL_V);
        fifthL.add(patextF);
        sixthL.add(pavlayout1,pavlayout2);
        seventhL.add(cancelButton,buybutton);

        firstL.setAlignItems(Alignment.CENTER);
        secondL.setAlignItems(Alignment.CENTER);
        thirdL.setAlignItems(Alignment.END);
        forthL.setAlignItems(Alignment.CENTER);
        fifthL.setAlignItems(Alignment.CENTER);
        sixthL.setAlignItems(Alignment.CENTER);
        seventhL.setAlignItems(Alignment.CENTER);

        verticalLayout.add(firstL, secondL, thirdL,forthL,fifthL,sixthL,seventhL);
        verticalLayout.setAlignItems(Alignment.CENTER);

        add(
            verticalLayout
        );

    }
}
