package ru.izebit.algorithms.graph;

import ru.izebit.structs.graph.NotDirectedGraph;
import ru.izebit.structs.graph.Graph;
import ru.izebit.structs.graph.components.Edge;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Алгоритм Прима. Построение минимального остовного дерева. Задача MST
 * Время работы в худшем случае О(V*E), хотя работать должно быстрее
 *
 * @author Artem Konovalov
 */

public class Prim {
    /**
     * @param graph - остновное дерево которого будет строиться
     * @return оставное дерево
     */
    public static <T> Graph<T> invoke(Graph<T> graph) {
        Graph<T> mst = new NotDirectedGraph<T>(graph.getVertexCount());

        T firstVertex = graph.getVertices().iterator().next();
        Set<T> vertices = new HashSet<T>(graph.getVertexCount());
        vertices.add(firstVertex);

        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>(graph.getVertexCount() * graph.getVertexCount());
        queue.addAll(graph.getEdges(firstVertex));

        while (mst.getVertexCount() != graph.getVertexCount()) {
            Edge<T> edge = queue.poll();
            while (vertices.contains(edge.getTarget())) {
                edge = queue.poll();
            }
            vertices.add(edge.getTarget());
            queue.addAll(graph.getEdges(edge.getTarget()));
            mst.addEdge(edge);
        }

        return mst;
    }
}
