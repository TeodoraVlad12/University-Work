package model;

public interface IAnimal {

    float getWeight();
    boolean compareWeightGreater(float weight);
    void setWeight(float weight);
    String toString();
}
