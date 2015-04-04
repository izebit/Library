package ru.izebit.algorithms.graph;

import ru.izebit.structs.graph.Graph;
import ru.izebit.structs.graph.components.Edge;

import java.util.HashMap;
import java.util.Map;

/**
 * Алгоритм Дейкстры. задача SSSP
 * Время работы O(V*E)
 *
 * @author : Artem Konovalov
 */
public class Dijkstra<T> {
    private Map<T, T> backtrace;

    public Dijkstra() {
        backtrace = new HashMap<T, T>();
    }

    /**
     * @param source вершина с которой начинается обход
     * @param graph  граф который будет обходиться
     * @return длину кратчайшего пути до каждой вершины из source
     */
    public Map<T, Double> invoke(T source, Graph<T> graph) {
        Map<T, Double> distance = new HashMap<T, Double>(graph.getVertexCount());
        Map<T, Boolean> used = new HashMap<T, Boolean>(distance.size());
        for (T vertex : graph.getVertices()) {
            distance.put(vertex, Double.POSITIVE_INFINITY);
            used.put(vertex, Boolean.FALSE);
        }

        distance.put(source, 0.0);
        backtrace.put(source, source);
        for (int i = 0; i < graph.getVertexCount(); i++) {
            Double minDistance = Double.POSITIVE_INFINITY;
            T vertex = null;
            for (T v : distance.keySet()) {
                if (!used.get(v) && distance.get(v) < minDistance) {
                    vertex = v;
                    minDistance = distance.get(v);
                }
            }
            if (vertex == null) break;

            used.put(vertex, Boolean.TRUE);
            for (Edge<T> edge : graph.getEdges(vertex)) {
                if (distance.get(vertex) + edge.getWeight() < distance.get(edge.getTarget())) {
                    distance.put(edge.getTarget(), distance.get(vertex) + edge.getWeight());
                    backtrace.put(edge.getTarget(), edge.getSource());
                }
            }
        }

        return distance;
    }

    /**
     * @return возвращает дерево кратчащий путей
     */
    public Map<T, T> getBacktrace() {
        return backtrace;
    }
}
