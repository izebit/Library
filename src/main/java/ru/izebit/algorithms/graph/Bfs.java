package ru.izebit.algorithms.graph;

import ru.izebit.structs.graph.Graph;
import ru.izebit.structs.graph.components.Edge;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Алгоритм обхода графа в ширину
 * Время работы O(E)
 */

public class Bfs {
    /**
     * осуществляет обход графа
     *
     * @param source вершина с которой начинается обход
     * @param graph  граф который будет обходиться
     * @return дерево обхода
     */

    public static <T> Map<T, T> invoke(T source, Graph<T> graph) {
        Map<T, T> backtrace = new HashMap<T, T>(graph.getVertexCount());
        Set<T> used = new HashSet<T>(graph.getVertexCount());
        for (T vertex : graph.getVertices()) {
            backtrace.put(vertex, null);
        }
        backtrace.put(source, source);
        used.add(source);

        Queue<T> queue = new ArrayBlockingQueue<T>(graph.getVertexCount());
        queue.add(source);
        while (queue.size() > 0) {
            T vertex = queue.poll();
            for (Edge<T> edge : graph.getEdges(vertex)) {
                if (!used.contains(edge.getTarget())) {
                    used.add(edge.getTarget());
                    backtrace.put(edge.getTarget(), vertex);
                    queue.add(edge.getTarget());
                }
            }
        }
        return backtrace;
    }
}
