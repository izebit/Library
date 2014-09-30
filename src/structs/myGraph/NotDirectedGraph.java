package structs.myGraph;

import structs.myGraph.components.Edge;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 03.10.12
 * Time: 19:09
 *
 * @author Artem Konovalov
 */
public class NotDirectedGraph<T> implements Graph<T> {
    private Map<T, Set<Edge<T>>> vertexs;
    private Set<Edge<T>> edges;

    private int countVertex;

    public NotDirectedGraph() {
        this(10);
    }

    public NotDirectedGraph(int countVertex) {
        vertexs = new HashMap<T, Set<Edge<T>>>(countVertex);
    }

    @Override
    public boolean addVertex(T vertex) {
        if (vertexs.keySet().contains(vertex)) {
            return false;
        }

        vertexs.put(vertex, new HashSet<Edge<T>>());
        countVertex++;

        return true;
    }

    @Override
    public boolean removeVertex(T vertex) {
        if (vertexs.keySet().contains(vertex)) {
            countVertex--;
            vertexs.remove(vertex);
            for (Set<Edge<T>> edgeList : vertexs.values()) {
                Iterator<Edge<T>> iterator = edgeList.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getTarget().equals(vertex)) {
                        iterator.remove();
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean addEdge(T source, T target) {
        return addEdge(source, target, 1.0);
    }

    @Override
    public boolean addEdge(T source, T target, double weight) {
        addVertex(source);
        addVertex(target);

        Edge<T> edge1 = new Edge<T>(source, target, weight);
        if (!vertexs.get(source).contains(edge1)) {
            vertexs.get(source).add(edge1);
        }

        Edge<T> edge2 = new Edge<T>(target, source, weight);
        if (!vertexs.get(target).contains(edge2)) {
            vertexs.get(target).add(edge2);
        }

        return true;
    }

    @Override
    public boolean addEdge(Edge<T> edge) {
        return addEdge(edge.getSource(),edge.getTarget(),edge.getWeight());
    }

    @Override
    public boolean removeEdge(T source, T target) {
        Iterator<Edge<T>> iterator = vertexs.get(source).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getTarget().equals(target)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeEdge(Edge<T> edge) {
        return removeEdge(edge.getSource(),edge.getTarget());
    }

    @Override
    public Set<Edge<T>> getEdges(T vertex) {
        return vertexs.get(vertex);
    }

    @Override
    public Set<Edge<T>> getEdges() {
        if (edges == null) {
            edges = new HashSet<Edge<T>>();
            for (Set<Edge<T>> edgeSet : vertexs.values()) {
                edges.addAll(edgeSet);
            }
        }
        return edges;
    }

    @Override
    public Set<T> getVertices() {
        return vertexs.keySet();
    }

    @Override
    public int getVertexCount() {
        return countVertex;
    }
}
