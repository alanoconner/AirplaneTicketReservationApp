package com.example.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import org.apache.tomcat.util.buf.UriUtil;
import org.springframework.web.util.UriUtils;

import java.sql.SQLException;

@Route("/flightList")
public class FlightListView extends VerticalLayout {

    MainView mainView;

    {
        try {
            mainView = new MainView();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public FlightListView() throws SQLException {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout firstL = new HorizontalLayout();


        //firstLayer
        TextField departTxt = new TextField();
        TextField arriveTxt = new TextField();
        TextField dates = new TextField();
        Button wayType = new Button(mainView.changeBtn.getText());
        Button adultNum = new Button(mainView.adultNum.getValue().toString());// stopped here, this didnt work
        Button childNum = new Button("0");
        departTxt.setValue("FUK");
        departTxt.setReadOnly(true);
        departTxt.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        arriveTxt.setValue("LA");
        arriveTxt.setReadOnly(true);
        arriveTxt.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        wayType.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        adultNum.addThemeVariants(ButtonVariant.LUMO_ICON);
        childNum.addThemeVariants(ButtonVariant.LUMO_ICON);
        adultNum.setIcon(new Icon(VaadinIcon.MALE));
        childNum.setIcon(new Icon(VaadinIcon.CHILD));
        adultNum.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        childNum.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        dates.setValue("2022.3.12 ~ 2022.4.11");
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
}
