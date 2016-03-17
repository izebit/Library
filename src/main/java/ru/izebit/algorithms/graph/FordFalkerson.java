package ru.izebit.algorithms.graph;

import ru.izebit.structs.graph.Graph;
import ru.izebit.structs.graph.components.Edge;

import java.util.*;

/**
 * Time: 13:23
 * Date: 06.10.12
 * <p>
 * алгоритм Форда-Фалкерсона решающий задачу о максимальном потоке
 *
 * @author Artem Konovalov
 */
public class FordFalkerson<T> {
    private Graph<T> graph;
    private int[][] capacity;              //пропускная способность
    private int[][] flow;                  //размер потока
    private Map<T, Integer> vertices;  //вершины графа

    private int maxFlow;                   //максимальный поток который возможно протолкнуть для текущего пути

    public FordFalkerson(Graph<T> graph) {
        this.graph = graph;
        vertices = new HashMap<>();
        int index = 0;
        for (T v : graph.getVertices())
            vertices.put(v, index++);


        capacity = new int[graph.getVertexCount()][graph.getVertexCount()];

        //инициализация capacity
        for (int i = 0; i < capacity.length; i++) {
            capacity[i][i] = 0;
        }
        for (Edge<T> edge : graph.getEdges()) {
            int sourceIndexVertex = vertices.get(edge.getSource());
            int targetIndexVertex = vertices.get(edge.getTarget());
            capacity[sourceIndexVertex][targetIndexVertex] = (int) edge.getWeight();
        }

        flow = new int[graph.getVertexCount()][graph.getVertexCount()];
    }

    /**
     * вычисляет максмальный поток для двух вершин
     *
     * @param source вершина начала потока
     * @param target вершина конца потока
     * @return ребра через которые "проталкивается поток"
     */
    public Set<Edge<T>> getMaxFlow(T source, T target) {
        List<Edge<T>> path;
        while ((path = getPath(source, target)) != null) {
            for (Edge<T> edge : path) {
                int indexSourceVertex = vertices.get(edge.getSource());
                int indexTargetVertex = vertices.get(edge.getTarget());
                capacity[indexSourceVertex][indexTargetVertex] -= maxFlow;
                flow[indexSourceVertex][indexTargetVertex] += maxFlow;
                flow[indexTargetVertex][indexSourceVertex] = -flow[indexSourceVertex][indexTargetVertex];
                if (capacity[indexSourceVertex][indexTargetVertex] == 0) {
                    graph.removeEdge(edge);
                }
            }
        }

        Set<Edge<T>> maxFlow = new HashSet<Edge<T>>();
        for (int i = 0; i < flow.length; i++) {
            for (int j = 0; j < flow.length; j++) {
                if (flow[i][j] > 0) {
                    T sourceVertex = null;
                    for (Map.Entry<T, Integer> m : vertices.entrySet()) {
                        if (m.getValue().equals(i)) {
                            sourceVertex = m.getKey();
                        }
                    }
                    T targetVertex = null;
                    for (Map.Entry<T, Integer> m : vertices.entrySet()) {
                        if (m.getValue().equals(j)) {
                            targetVertex = m.getKey();
                        }
                    }
                    Edge<T> edge = new Edge<T>(sourceVertex, targetVertex, flow[i][j]);
                    maxFlow.add(edge);
                }
            }
        }
        return maxFlow;
    }


    /**
     * функция находящая путь между 2 вершинами
     *
     * @param source вершина из которой нужно найти путь
     * @param target вершина до которой нужно найти путь
     * @return список ребер входящих в путь
     */
    private List<Edge<T>> getPath(T source, T target) {
        Set<T> used = new HashSet<T>();
        List<Edge<T>> backtrace = new ArrayList<Edge<T>>();

        used.add(source);

        //вызывается модифицированный dfs
        if (recursionMethod(source, target, backtrace, used, Integer.MAX_VALUE)) {
            return backtrace;
        } else {
            return null;
        }
    }

    /**
     * модифицированный dfs для нахождения пути от одной вершины до другой
     *
     * @param source    вершина от которой находится путь
     * @param target    вершина до которой находится путь
     * @param backtrace список ребер входящий в путь
     * @param used      содержит ранее посещенные вершины
     * @param maxFlow   максимальный поток для данного пути
     * @return true если путь существует, иначе false
     */
    private boolean recursionMethod(T source, T target, List<Edge<T>> backtrace, Set<T> used, int maxFlow) {
        if (source.equals(target)) {
            this.maxFlow = maxFlow;
            return true;
        }

        for (Edge<T> edge : graph.getEdges(source)) {
            if (!used.contains(edge.getTarget())) {
                used.add(edge.getTarget());
                backtrace.add(edge);
                int i = vertices.get(edge.getSource());
                int j = vertices.get(edge.getTarget());
                boolean res = recursionMethod(edge.getTarget(), target, backtrace, used, maxFlow > capacity[i][j] ? capacity[i][j] : maxFlow);
                if (res) {
                    return true;
                } else {
                    used.remove(edge.getTarget());
                    backtrace.remove(backtrace.size() - 1);
                }
            }
        }

        return false;
    }
}
