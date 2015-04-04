package ru.izebit.algorithms.graph;

import ru.izebit.structs.graph.NotDirectedGraph;
import ru.izebit.structs.graph.components.Edge;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 18.10.12
 * Time: 22:55
 *
 * @author Artem Konovalov
 */

//TODO необходимо сделать нормальные интерфейсы для графа и дописать реализацию чтобы она работала и с ориентированными графами

public class EulerPath {
    public static <T> List<Edge<T>> find(NotDirectedGraph<T> graph) {
        Map<T, List<T>> vertices = new HashMap<T, List<T>>(graph.getVertexCount());
        //проверка на критерий существования эйлерового пути
        T startVertex = graph.getVertices().iterator().next();
        int countVertex = 0; //колличество вершин с нечетными степенями
        for (T vertex : graph.getVertices()) {
            if (graph.getEdges(vertex).size() % 2 == 1) {
                countVertex++;
                startVertex = vertex;
            }
            List<T> list = new ArrayList<T>(graph.getEdges(vertex).size());
            for (Edge<T> edge : graph.getEdges(vertex)) {
                list.add(edge.getTarget());
            }
            vertices.put(vertex, list);
        }
        if (countVertex != 0 && countVertex != 2) {
            return null;
        }

        List<Edge<T>> path = new ArrayList<Edge<T>>(graph.getVertexCount());

        Stack<T> stack = new Stack<T>();
        stack.push(startVertex);
        while (stack.size() != 0) {
            T vertex = stack.peek();
            List<T> subList = vertices.get(vertex);
            if (subList.size() == 0) {
                stack.pop();
                if (stack.size() != 0) {
                    for (Edge<T> edge : graph.getEdges(stack.peek())) {
                        if (edge.getTarget().equals(vertex)) {
                            path.add(edge);
                            break;
                        }
                    }
                }
            } else {
                T nextVertex = subList.get(subList.size() - 1);
                subList.remove(subList.size() - 1);
                vertices.get(nextVertex).remove(vertex);
                stack.push(nextVertex);
            }
        }

        for (T vertex : vertices.keySet()) {
            if (vertices.get(vertex).size() != 0) {
                return null;
            }
        }

        return path;
    }
}
