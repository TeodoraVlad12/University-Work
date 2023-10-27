package view;
import controller.Controller;

public class View {

    Controller controller;
    public View(Controller controller ){
        this.controller = controller;
    }

    public void start(){

        /*while (true){
            System.out.println("Menu\n");
            System.out.println("1. View all animals\n");
            System.out.println("2. Add an animal\n");
            System.out.println("3. Remove an animal\n");
            System.out.println("4. See animals filtered\n");
            System.out.println("5. Exit\n");
            System.out.println("\nChoose an option: \n");
        }*/

        controller.add("cow", 200);
        controller.add("pig", 100);
        controller.add("pig", 150);
        controller.add("bird", 10);
        controller.add("bird", 50);
        controller.add("bird", 1);
        controller.add("bird", 4);


        controller.remove(6);

        System.out.println(controller.getFiltered(3));
    }

}
