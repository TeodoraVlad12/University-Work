package model;

public class Bird implements IAnimal {
    private  float weight;    //final adica nu mai poate fi modificat dupa

    public Bird(float weight){
        this.weight = weight;
    }

    @Override
    public float getWeight(){
        return this.weight;
    }

    @Override
    public void setWeight(float weight) { this.weight= weight; }

    @Override
    public boolean compareWeightGreater(float weight){
        return this.weight > weight;
    }

    public String toString(){
        return "Bird of " + this.weight + " kg.";
    }
}
