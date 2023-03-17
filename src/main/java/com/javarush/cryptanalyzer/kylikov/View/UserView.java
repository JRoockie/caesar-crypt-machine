package com.javarush.cryptanalyzer.kylikov.View;

import com.javarush.cryptanalyzer.kylikov.Controller.Controller;

import java.util.Scanner;


public class UserView {
    private Controller controller;

    public UserView(Controller controller) {
        this.controller = controller;
    }

    public void getOperation() {
        controller.getOperation();
    }
}
