package Logica.lib.cu.edu.cujae.ceis.graph.edge;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Logica.lib.cu.edu.cujae.ceis.graph.vertex.Vertex;

public class WeightedEdge extends Edge {
    private Object weight;

    public WeightedEdge(Vertex vertex, Object weight) {
        super(vertex);
        this.weight = weight;
    }

    public Object getWeight() {
        return this.weight;
    }
}

