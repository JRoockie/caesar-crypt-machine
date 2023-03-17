
package com.javarush.cryptanalyzer.kylikov.Controller;

import com.javarush.cryptanalyzer.kylikov.Model.Model;

public class Controller {
    private static Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void getOperation() {
        model.getOperation();
    }
}