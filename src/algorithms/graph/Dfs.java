package algorithms.graph;

import structs.myGraph.Graph;
import structs.myGraph.components.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Алгоритм обхода графа в глубину
 * Время работы O(E)
 */

public class Dfs {
    /*
      осуществляет обход графа
      @param source вершина с которой начинается обход
      @param graph граф который будет обходиться
      @return дерево обхода
    */
    public static <T> Map<T, T> invoke(T source, Graph<T> graph) {
        Set<T> used = new HashSet<T>();
        Map<T, T> backtrace = new HashMap<T, T>();
        for (T vertex : graph.getVertices()) {
            backtrace.put(vertex, null);
        }
        backtrace.put(source, source);
        used.add(source);
        return discovered(source, graph, backtrace, used);
    }

    private static <T> Map<T, T> discovered(T source, Graph<T> graph, Map<T, T> parent, Set<T> used) {
        for (Edge<T> edge : graph.getEdges(source)) {
            if (!used.contains(edge.getTarget())) {
                parent.put(edge.getTarget(), source);
                used.add(edge.getTarget());
                parent = discovered(edge.getTarget(), graph, parent, used);
            }
        }
        return parent;
    }
}