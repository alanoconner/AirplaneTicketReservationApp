package com.example.application;

import com.example.application.views.LoginMenu;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("/registerField")
public class RegisterMenu extends VerticalLayout{
    VerticalLayout verticalLayout = new VerticalLayout();

    LoginMenu loginMenu = new LoginMenu();

    Button EnterButton;
    Button BackButton;

    TextField loginField = new TextField("login");
    EmailField emailField = new EmailField("Email address");
    PasswordField passwordField = new PasswordField("Password");
    PasswordField passwordConfirmField = new PasswordField("Confirm Password");

    public RegisterMenu(){
        EnterButton = new Button("Register", click->{
            if(passwordField.getValue().equals(passwordConfirmField.getValue())) loginMenu.setAccount(loginField.getValue(), passwordField.getValue());
            System.out.println(loginMenu.logpass);
        });
        BackButton = new Button("Back", event-> UI.getCurrent().navigate(""));


        verticalLayout.add(new H2("Create account"),
                new HorizontalLayout(loginField),
                new HorizontalLayout(emailField),
                new HorizontalLayout(passwordField),
                new HorizontalLayout(passwordConfirmField),
                new HorizontalLayout(BackButton, EnterButton));

        setHeightFull();
        setAlignItems(Alignment.CENTER);
        verticalLayout.setAlignItems(Alignment.CENTER);

        add(
                verticalLayout
        );

    }
}
