package view;

import controller.Controller;
import exceptions.MyException;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Do you want to display the steps?[y/n]");
            Scanner readOption = new Scanner(System.in);
            String option = readOption.next();
            controller.setDisplayFlag(Objects.equals(option, "y"));
            controller.allSteps();
        } catch (MyException | IOException exception) {
            System.out.println("\u001B[31m" + exception.getMessage() + "\u001B[0m");
        }
    }
}
