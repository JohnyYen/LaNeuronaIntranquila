package Logica.lib.cu.edu.cujae.ceis.graph;

import Logica.lib.cu.edu.cujae.ceis.graph.edge.Edge;
import Logica.lib.cu.edu.cujae.ceis.graph.edge.WeightedEdge;
import Logica.lib.cu.edu.cujae.ceis.graph.interfaces.*;
import Logica.lib.cu.edu.cujae.ceis.graph.vertex.Vertex;
import Logica.lib.cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.util.*;

public class LinkedGraph extends Graph implements ILinkedDirectedGraph, ILinkedNotDirectedGraph, ILinkedWeightedEdgeDirectedGraph, ILinkedWeightedEdgeNotDirectedGraph, ILinkedWeightedVertexDirectedGraph, ILinkedWeightedVertexNotDirectedGraph {
    private LinkedList<Vertex> verticesList = new LinkedList();

    public LinkedGraph() {
    }

    private boolean posInRange(int pos) {
        return pos > -1 && pos < this.verticesList.size();
    }

    private void deleteVertex(Vertex vertex) {
        this.verticesList.remove(vertex);
        Iterator<Vertex> iter = this.verticesList.iterator();

        while(iter.hasNext()) {
            ((Vertex)iter.next()).deleteEdge(vertex);
        }

    }

    private void selectVerticesInCascadeRecurs(Vertex vertex, LinkedList<Vertex> selected) {
        if (!selected.contains(vertex)) {
            LinkedList<Edge> edges = vertex.getEdgeList();
            Iterator<Edge> iter = edges.iterator();
            selected.add(vertex);

            while(iter.hasNext()) {
                Vertex current = ((Edge)iter.next()).getVertex();
                this.selectVerticesInCascadeRecurs(current, selected);
            }
        }

    }

    private boolean cycleInNodeDG(LinkedList<Edge> adjacents, Vertex vertex, LinkedList<Vertex> visited) {
        boolean cycle = false;
        Iterator<Edge> iter = adjacents.iterator();

        while(!cycle && iter.hasNext()) {
            Vertex current = ((Edge)iter.next()).getVertex();
            if (!current.equals(vertex)) {
                if (!visited.contains(current)) {
                    visited.add(current);
                    cycle = this.cycleInNodeDG(current.getEdgeList(), vertex, visited);
                }
            } else {
                cycle = true;
            }
        }

        return cycle;
    }

    private boolean cycleInNodeND(Vertex origin, Vertex vertex, LinkedList<Vertex> visited) {
        boolean cycle = false;
        if (!visited.contains(vertex)) {
            visited.add(vertex);
            Iterator<Edge> iter = vertex.getEdgeList().iterator();

            while(!cycle && iter.hasNext()) {
                Vertex current = ((Edge)iter.next()).getVertex();
                if (!current.equals(origin)) {
                    cycle = this.cycleInNodeND(vertex, current, visited);
                }
            }
        } else {
            cycle = true;
        }

        return cycle;
    }

    private boolean path(Vertex v1, Vertex v2, int length) {
        boolean found = false;
        if (length == 1) {
            found = v1.isAdjacent(v2);
        } else {
            Iterator<Vertex> iter = this.verticesList.iterator();

            while(!found && iter.hasNext()) {
                Vertex bridge = (Vertex)iter.next();
                if (bridge.isAdjacent(v2)) {
                    found = this.path(v1, bridge, length - 1);
                }
            }
        }

        return found;
    }

    public int getVertexIndex(Vertex vertex) {
        int count = 0;
        int index = -1;

        for(Iterator<Vertex> iter = this.verticesList.iterator(); index == -1 && iter.hasNext(); ++count) {
            if (((Vertex)iter.next()).equals(vertex)) {
                index = count;
            }
        }

        return index;
    }

    public boolean isEmpty() {
        return this.verticesList.isEmpty();
    }

    public boolean areAdjacents(int posTail, int posHead) {
        boolean adjacents = false;
        if (this.posInRange(posHead) && this.posInRange(posTail)) {
            adjacents = ((Vertex)this.verticesList.get(posTail)).isAdjacent((Vertex)this.verticesList.get(posHead));
        }

        return adjacents;
    }

    public boolean insertVertex(Object info) {
        return this.verticesList.add(new Vertex(info));
    }

    public boolean pathWithLength(int posTail, int posHead, int length) {
        boolean found = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            found = this.path((Vertex)this.verticesList.get(posTail), (Vertex)this.verticesList.get(posHead), length);
        }

        return found;
    }

    public LinkedList<Vertex> getVerticesList() {
        return this.verticesList;
    }

    public LinkedList<Vertex> adjacentsG(int pos) {
        LinkedList<Vertex> verts = new LinkedList();
        if (this.posInRange(pos)) {
            LinkedList<Edge> edges = ((Vertex)this.verticesList.get(pos)).getEdgeList();
            Iterator<Edge> iter = edges.iterator();

            while(iter.hasNext()) {
                verts.add(((Edge)iter.next()).getVertex());
            }
        }

        return verts;
    }

    public Vertex deleteVertex(int pos) {
        Vertex v = null;
        if (this.posInRange(pos)) {
            v = (Vertex)this.verticesList.get(pos);
            this.deleteVertex(v);
        }

        return v;
    }

    public LinkedList<Vertex> deleteVertexCascade(int pos) {
        LinkedList<Vertex> deleted = new LinkedList();
        if (this.posInRange(pos)) {
            this.selectVerticesInCascadeRecurs((Vertex)this.verticesList.get(pos), deleted);
            Iterator<Vertex> iter = deleted.iterator();

            while(iter.hasNext()) {
                this.deleteVertex(this.getVertexIndex((Vertex)iter.next()));
            }
        }

        return deleted;
    }

    public boolean cyclicDG() {
        boolean cycle = false;

        Vertex current;
        for(Iterator<Vertex> iter = this.verticesList.iterator(); !cycle && iter.hasNext(); cycle = this.cycleInNodeDG(current.getEdgeList(), current, new LinkedList())) {
            current = (Vertex)iter.next();
        }

        return cycle;
    }

    public int degreeDG(int pos) {
        int degree = -1;
        if (this.posInRange(pos)) {
            degree = this.inDegreeDG(pos) + this.outDegree(pos);
        }

        return degree;
    }

    public int inDegreeDG(int pos) {
        int degree = -1;
        if (this.posInRange(pos)) {
            degree = 0;
            Vertex vertex = (Vertex)this.verticesList.get(pos);
            Iterator<Vertex> iter = this.verticesList.iterator();

            while(iter.hasNext()) {
                if (((Vertex)iter.next()).isAdjacent(vertex)) {
                    ++degree;
                }
            }
        }

        return degree;
    }

    public int outDegree(int pos) {
        int degree = -1;
        if (this.posInRange(pos)) {
            degree = ((Vertex)this.verticesList.get(pos)).getAdjacents().size();
        }

        return degree;
    }

    public boolean deleteEdgeD(int posTail, int posHead) {
        boolean success = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            Vertex tail = (Vertex)this.verticesList.get(posTail);
            Vertex head = (Vertex)this.verticesList.get(posHead);
            success = tail.deleteEdge(head);
        }

        return success;
    }

    public boolean insertEdgeDG(int posTail, int posHead) {
        boolean success = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            Vertex tail = (Vertex)this.verticesList.get(posTail);
            Vertex head = (Vertex)this.verticesList.get(posHead);
            success = tail.getEdgeList().add(new Edge(head));
        }

        return success;
    }

    public LinkedList<Vertex> removeDisconnectVerticesDG() {
        LinkedList<Vertex> verts = new LinkedList();
        int i = 0;

        while(i < this.verticesList.size()) {
            if (this.degreeDG(i) == 0) {
                verts.add((Vertex)this.verticesList.get(i));
                this.verticesList.remove(i);
            } else {
                ++i;
            }
        }

        return verts;
    }

    public boolean cyclicND() {
        boolean cycle = false;

        Vertex current;
        for(Iterator<Vertex> iter = this.verticesList.iterator(); !cycle && iter.hasNext(); cycle = this.cycleInNodeND((Vertex)null, current, new LinkedList())) {
            current = (Vertex)iter.next();
        }

        return cycle;
    }

    public int degreeND(int pos) {
        int degree = -1;
        if (this.posInRange(pos)) {
            degree = ((Vertex)this.verticesList.get(pos)).getAdjacents().size();
        }

        return degree;
    }

    public boolean deleteEdgeND(int posTail, int posHead) {
        boolean success = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            success = this.deleteEdgeD(posTail, posHead);
            success &= this.deleteEdgeD(posHead, posTail);
        }

        return success;
    }

    public boolean insertEdgeNDG(int posTail, int posHead) {
        boolean success = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            success = this.insertEdgeDG(posTail, posHead);
            if (posTail != posHead) {
                success &= this.insertEdgeDG(posHead, posTail);
            }
        }

        return success;
    }

    public LinkedList<Vertex> removeDisconnectVerticesND() {
        LinkedList<Vertex> verts = new LinkedList();
        int i = 0;

        while(i < this.verticesList.size()) {
            if (this.degreeND(i) == 0) {
                verts.add((Vertex)this.verticesList.get(i));
                this.verticesList.remove(i);
            } else {
                ++i;
            }
        }

        return verts;
    }

    public boolean insertWEdgeDG(int posTail, int posHead, Object weight) {
        boolean success = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            Vertex tail = (Vertex)this.verticesList.get(posTail);
            Vertex head = (Vertex)this.verticesList.get(posHead);
            success = tail.getEdgeList().add(new WeightedEdge(head, weight));
        }

        return success;
    }

    public boolean insertWEdgeNDG(int posTail, int posHead, Object weight) {
        boolean success = false;
        if (this.posInRange(posTail) && this.posInRange(posHead)) {
            success = this.insertWEdgeDG(posTail, posHead, weight);
            if (posTail != posHead) {
                success &= this.insertWEdgeDG(posHead, posTail, weight);
            }
        }

        return success;
    }

    public boolean insertWVertex(Object info, Object weight) {
        return this.verticesList.add(new WeightedVertex(info, weight));
    }
}
