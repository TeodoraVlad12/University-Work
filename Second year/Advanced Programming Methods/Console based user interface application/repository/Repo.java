package repository;
import Exceptions.MyException;
import model.IAnimal;

public class Repo implements IRepo {

    private IAnimal[] animals;
    private int nrAnimals;

    public Repo(int size){
        /*if (size <= 0) {
            throw new MyException("The capacity of the repository cannot be 0 or less.");
        }*/
        this.animals = new IAnimal[size];
        this.nrAnimals = 0;

    }

    @Override
    public void add(IAnimal a) throws MyException{

        if (nrAnimals == animals.length)
            throw new MyException("Full array!");

        this.animals[nrAnimals] = a;
        nrAnimals++;
    }

    @Override
    public void remove(int index) throws MyException{
        if (index < 0 || index > this.nrAnimals)
            throw new MyException("Invalid index!");
        IAnimal[] animalsCopy = new IAnimal[this.nrAnimals - 1];
        for (int i = 0, j = 0; i < this.nrAnimals; i++) {
            if (i != index) {
                animalsCopy[j] = this.animals[i];
                j++;
            }
        }
        this.animals = animalsCopy;
        this.nrAnimals--;
    }

    @Override
    public IAnimal[] getAnimals() {
        return this.animals;
    }

    @Override
    public int getSize() {
        return this.nrAnimals;
    }

    @Override
    public IAnimal[] getByWeight(float weight){
        IAnimal[] new_animals = new IAnimal[this.animals.length];
        int index = 0;

        for (IAnimal a : this.animals){
            if (a != null )
                if (a.getWeight() > weight )
                    new_animals[index++] = a;
        }

        IAnimal[] final_animals = new IAnimal[index];
        System.arraycopy(new_animals, 0, final_animals, 0, index);
        return final_animals;
    }

}
