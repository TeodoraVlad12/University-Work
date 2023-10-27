package model;

public class Cow implements IAnimal {
    private float weight;

    public Cow(float weight){
        this.weight = weight;

    }

    @Override
    public float getWeight(){
        return this.weight;
    }

    @Override
    public void setWeight(float weight){this.weight= weight;}

    @Override
    public boolean compareWeightGreater(float weight){
        return this.weight > weight;
    }

    public String toString(){
        return "Cow of " + this.weight  + " kg.";
    }
}
