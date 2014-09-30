package algorithms.graph;

import structs.myGraph.Graph;
import structs.myGraph.components.Edge;

import java.util.*;

/**
 * Алгоритм Флойда-Уоршилла
 * Время работы O(n^3)
 *
 * @author : Artem Konovalov
 */
public class FloydWarshall<T> {
    private Graph<T> graph;
    private double[][] distance; //расстояния кратчайший путей
    private int[][] backtrace;   //индексы вершины,находящихся в середине кратчайшего пути
    private List<T> vertices;    //вершины графа

    public FloydWarshall(Graph<T> graph) {
        this.graph = graph;
        invoke();
    }

    /*
    возвращает длину кратчайшего пути между двумя вершинами
    @param sourceVertex вершина начала пути
    @param targetVertex вершина конца пути
    @return длина кратчайшего пути
     */
    public double getShortestPathDistance(T sourceVertex, T targetVertex) {
        return distance[vertices.indexOf(sourceVertex)][vertices.indexOf(targetVertex)];
    }

    /*
    возвращает дуги входящие в кратчаший путь
    @param sourceVertex вершина начала пути
    @param targetVertex вершина конца пути
    @return список дуг
    */
    public List<Edge<T>> getShortestPath(T sourceVertex, T targetVertex) {
        List<Edge<T>> edges = new ArrayList<Edge<T>>();
        recursionMethod(edges, vertices.indexOf(sourceVertex), vertices.indexOf(targetVertex));
        return edges;
    }

    private void recursionMethod(List<Edge<T>> edges, int sourceVertexIndex, int targetVertexIndex) {
        int middleVertexIndex = backtrace[sourceVertexIndex][targetVertexIndex];
        if (middleVertexIndex == -1) {
            Edge<T> edge = new Edge<T>(vertices.get(sourceVertexIndex), vertices.get(targetVertexIndex));
            if (graph.getEdges().contains(edge)) {
                edges.add(edge);
            }
        } else {
            recursionMethod(edges, sourceVertexIndex, middleVertexIndex);
            recursionMethod(edges, middleVertexIndex, targetVertexIndex);
        }
    }

    private void invoke() {
        vertices = new ArrayList<T>(graph.getVertices());
        distance = new double[graph.getVertexCount()][graph.getVertexCount()];

        //инициализация distance
        for (double[] aDistance : distance) {
            Arrays.fill(aDistance, Double.POSITIVE_INFINITY);
        }
        for (int i = 0; i < distance.length; i++) {
            distance[i][i] = 0.0;
        }
        for (Edge<T> edge : graph.getEdges()) {
            int sourceIndexVertex = vertices.indexOf(edge.getSource());
            int targetIndexVertex = vertices.indexOf(edge.getTarget());
            distance[sourceIndexVertex][targetIndexVertex] = edge.getWeight();
        }

        backtrace = new int[graph.getVertexCount()][graph.getVertexCount()];
        for (int[] bt : backtrace) {
            Arrays.fill(bt, -1);
        }

        //вычисление кратчайших путей
        for (int k = 0; k < distance.length; k++) {
            for (int i = 0; i < distance.length; i++) {
                for (int j = 0; j < distance.length; j++) {
                    double d = distance[i][k] + distance[k][j];
                    if (d < distance[i][j]) {
                        distance[i][j] = d;
                        backtrace[i][j] = k;
                    }
                }
            }
        }
    }
}
