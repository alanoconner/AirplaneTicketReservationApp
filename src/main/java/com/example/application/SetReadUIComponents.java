package com.example.application;


import com.vaadin.flow.component.UI;

public class SetReadUIComponents extends UI {
    public void saveIntegerValue(SetReadUIComponents ui,String name, int value){ ui.getSession().setAttribute(name ,value);}
    public void saveCharSetValue(SetReadUIComponents ui,String name,  String value){ui.getSession().setAttribute(name,value);}

}
