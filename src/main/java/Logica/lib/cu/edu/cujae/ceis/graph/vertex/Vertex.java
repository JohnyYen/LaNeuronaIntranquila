package Logica.lib.cu.edu.cujae.ceis.graph.vertex;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Logica.lib.cu.edu.cujae.ceis.graph.edge.Edge;

import java.util.Iterator;
import java.util.LinkedList;

public class Vertex {
    private Object info;
    private LinkedList<Edge> edgeList;

    public Vertex(Object info) {
        this.info = info;
        this.edgeList = new LinkedList();
    }

    public boolean deleteEdge(Vertex vertex) {
        boolean success = false;
        Iterator<Edge> iter = this.edgeList.iterator();

        while(!success && iter.hasNext()) {
            if (((Edge)iter.next()).getVertex().equals(vertex)) {
                iter.remove();
                success = true;
            }
        }

        return success;
    }

    public LinkedList<Edge> getEdgeList() {
        return this.edgeList;
    }

    public Object getInfo() {
        return this.info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public LinkedList<Vertex> getAdjacents() {
        LinkedList<Vertex> vertices = new LinkedList();
        Iterator<Edge> iter = this.edgeList.iterator();

        while(iter.hasNext()) {
            vertices.add(((Edge)iter.next()).getVertex());
        }

        return vertices;
    }

    public boolean isAdjacent(Vertex vertex) {
        boolean adjacent = false;
        Iterator<Edge> iter = this.edgeList.iterator();

        while(!adjacent && iter.hasNext()) {
            if (((Edge)iter.next()).getVertex().equals(vertex)) {
                adjacent = true;
            }
        }

        return adjacent;
    }

    public String toString() {
        return this.info.toString();
    }
}
