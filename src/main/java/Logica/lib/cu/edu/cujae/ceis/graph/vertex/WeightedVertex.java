package Logica.lib.cu.edu.cujae.ceis.graph.vertex;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class WeightedVertex extends Vertex {
    private Object weight;

    public WeightedVertex(Object info, Object weight) {
        super(info);
        this.weight = weight;
    }

    public Object getWeight() {
        return this.weight;
    }
}
