package Logica.lib.cu.edu.cujae.ceis.graph.edge;

import Logica.lib.cu.edu.cujae.ceis.graph.vertex.Vertex;

public class Edge {
    protected Vertex vertex;

    public Edge(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return this.vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
}
