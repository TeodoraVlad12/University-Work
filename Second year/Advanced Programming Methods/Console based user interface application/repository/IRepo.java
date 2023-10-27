package repository;
import model.IAnimal;
import Exceptions.MyException;

public interface IRepo {
    void add(IAnimal a) throws MyException;
    void remove(int index) throws MyException;
    IAnimal[] getAnimals();
    int getSize();
    IAnimal[] getByWeight(float weight);

}
