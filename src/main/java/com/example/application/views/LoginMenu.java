package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@Route("/login")
public class LoginMenu extends VerticalLayout {

    public Map<String, String> logpass = new HashMap<>();// using HashMap since I don't know how to work with Databases yet;

    public LoginMenu(){
        VerticalLayout verticalLayout = new VerticalLayout();
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginBtn = new Button("Login", click->{
            System.out.println(logpass);
            /*if(LogIn(loginField.getValue(), passwordField.getValue()))*/ UI.getCurrent().navigate("/Main");
//            else {
//                loginField.clear();
//                passwordField.clear();
//            }
        });
        Button registerBtn = new Button("Register",event -> UI.getCurrent().navigate("/registerField"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(registerBtn,loginBtn);
        verticalLayout.add(new H2("Log in"), loginField, passwordField, horizontalLayout);

        //Put everything in center
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        setHeightFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(

                verticalLayout
        );
    }


    public boolean LogIn(String login, String pass){
        if(logpass.containsKey(login)&&logpass.get(login).equals(pass)) return true;
        else return false;
    }
    public void setAccount(String login, String pass){logpass.put(login,pass);}
}
