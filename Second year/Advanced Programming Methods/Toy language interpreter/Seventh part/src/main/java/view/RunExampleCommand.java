package view;

import controller.Controller;

import java.util.Objects;
import java.util.Scanner;

public class RunExampleCommand extends Command{
    private Controller controller;

    public RunExampleCommand(String k, String desc, Controller c) {
        super(k, desc);
        this.controller = c;
    }

    @Override
    public void execute() {
        try {
            this.controller.allSteps();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
