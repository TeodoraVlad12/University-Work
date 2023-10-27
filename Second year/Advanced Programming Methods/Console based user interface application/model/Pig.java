package model;

public class Pig implements IAnimal {
    private  float weight ;

    public Pig(float weight){
        this.weight = weight;

    }

    @Override
    public float getWeight(){
        return this.weight;
    }

    @Override
    public void setWeight(float weight){this.weight = weight;}

    @Override
    public boolean compareWeightGreater(float weight){
        return this.weight > weight;
    }

    public String toString(){
        return "Pig of " + this.weight + " kg.";
    }
}
