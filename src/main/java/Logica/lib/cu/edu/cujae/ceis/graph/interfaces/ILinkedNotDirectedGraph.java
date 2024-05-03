package Logica.lib.cu.edu.cujae.ceis.graph.interfaces;

import Logica.lib.cu.edu.cujae.ceis.graph.vertex.Vertex;

import java.util.LinkedList;

public interface ILinkedNotDirectedGraph {
    boolean areAdjacents(int var1, int var2);

    boolean insertVertex(Object var1);

    boolean isEmpty();

    boolean pathWithLength(int var1, int var2, int var3);

    LinkedList<Vertex> getVerticesList();

    LinkedList<Vertex> adjacentsG(int var1);

    LinkedList<Vertex> deleteVertexCascade(int var1);

    Vertex deleteVertex(int var1);

    int degreeND(int var1);

    boolean cyclicND();

    boolean deleteEdgeND(int var1, int var2);

    boolean insertEdgeNDG(int var1, int var2);

    LinkedList<Vertex> removeDisconnectVerticesND();
}
