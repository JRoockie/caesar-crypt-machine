package com.javarush.cryptanalyzer.kylikov;
import com.javarush.cryptanalyzer.kylikov.Controller.Controller;
import com.javarush.cryptanalyzer.kylikov.Model.Model;
import com.javarush.cryptanalyzer.kylikov.View.UserView;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        UserView userView = new UserView(controller);
        userView.getOperation();
    }
}