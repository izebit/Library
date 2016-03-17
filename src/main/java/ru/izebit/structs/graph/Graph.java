package ru.izebit.structs.graph;

import ru.izebit.structs.graph.components.Edge;

import java.util.Set;

/**
 * Date: 03.10.12
 * Time: 18:53
 *
 * @author Artem Konovalov
 */
public interface Graph<T> {
    public boolean addVertex(T vertex);

    public boolean removeVertex(T vertex);

    public boolean addEdge(T source, T target);

    public boolean addEdge(T source, T target, double weight);

    public boolean addEdge(Edge<T> edge);

    public boolean removeEdge(T source, T target);

    public boolean removeEdge(Edge<T> edge);

    public Set<Edge<T>> getEdges(T vertex);

    public Set<Edge<T>> getEdges();

    public Set<T> getVertices();

    public int getVertexCount();
}
