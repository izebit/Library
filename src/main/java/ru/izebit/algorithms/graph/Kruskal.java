package ru.izebit.algorithms.graph;


import ru.izebit.structs.graph.NotDirectedGraph;
import ru.izebit.structs.graph.Graph;
import ru.izebit.structs.graph.components.Edge;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//TODO можно улучшить используя непересекающиеся множества (WTF?)

/**
 * Алгоритм Крускала. Задача MST
 * Время работы О(V*V+V*log(E))
 *
 * @author Artem Konovalov
 */
public class Kruskal {

    /**
     * @param граф, остновное дерево которго будет строиться
     * @return оставное дерево
     */
    public static <T> Graph<T> invoke(Graph<T> graph) {
        Graph<T> mst = new NotDirectedGraph<T>(graph.getVertexCount());

        PriorityQueue<Edge<T>> edgeQueue;
        edgeQueue = new PriorityQueue<Edge<T>>(graph.getVertexCount() * graph.getVertexCount());
        edgeQueue.addAll(graph.getEdges());

        Map<T, Integer> groupNumber = new HashMap<T, Integer>(graph.getVertexCount());
        int i = 0;
        for (T vertex : graph.getVertices()) {
            groupNumber.put(vertex, i++);
        }

        while (edgeQueue.size() > 0) {
            Edge<T> edge = edgeQueue.poll();
            if (!groupNumber.get(edge.getSource()).equals(groupNumber.get(edge.getTarget()))) {
                System.out.println(edge);
                mst.addEdge(edge);
                Integer oldGroupNumber = groupNumber.get(edge.getSource());
                Integer newGroupNumber = groupNumber.get(edge.getTarget());
                for (T vertex : groupNumber.keySet()) {
                    if (groupNumber.get(vertex).equals(oldGroupNumber)) {
                        groupNumber.put(vertex, newGroupNumber);
                    }
                }
            }
        }

        return mst;
    }
}
