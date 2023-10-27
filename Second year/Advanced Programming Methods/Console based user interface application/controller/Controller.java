package controller;

import Exceptions.MyException;
import repository.IRepo;
import model.IAnimal;
import model.Bird;
import model.Cow;
import model.Pig;

import java.util.Objects;

public class Controller {
    private IRepo repo;

    public Controller(IRepo repository) {
        this.repo = repository;

    }

    public void add(String type, float weight) {

        try {
            if (Objects.equals(type, "bird")) {
                Bird new_bird = new Bird(weight);
                this.repo.add(new_bird);
            }

            if (Objects.equals(type, "cow")) {
                Cow new_cow = new Cow(weight);

                this.repo.add(new_cow);

            }

            if (Objects.equals(type, "pig")) {
                Pig new_pig = new Pig(weight);

                this.repo.add(new_pig);

            }
        } catch (MyException e) {
            System.err.println(" Exception: " + e);
        }
    }


    public void remove(int index) {
        try {
            this.repo.remove(index);
        } catch (MyException e) {
            System.err.println(" Exception: " + e);
        }
    }

    public IAnimal[] getAnimals() {
        return this.repo.getAnimals();
    }

    public int getSize() {
        return this.repo.getSize();
    }

    public String getFiltered(float weight) {
        String result = "";
        for (IAnimal a : this.repo.getByWeight(weight)) {
            result += a.toString();
            result += "\n";
        }
        return result;

    }
}
