package algorithms.graph;

import structs.myGraph.Graph;
import structs.myGraph.components.Edge;

import java.util.HashMap;
import java.util.Map;

/**
 * алгоритм Форда-Беллмана
 * время работы O(V*E)
 *
 * @author Artem Konovalov
 */
public class FordBellman<T> {
    private Map<T,T>backtrace;
    public FordBellman(){
        backtrace=new HashMap<T, T>();
    }

     /*
     находит кратчашие пути от одной вершины до всех остальных
     @param source вершины путь от которой необходимо найти
     @return дерево кратчайших путей
    */
    public Map<T, Double> invoke(T source,Graph<T> graph) {
        Map<T, Double> distance = new HashMap<T, Double>();
        for (T v : graph.getVertices()) {
            distance.put(v, Double.MAX_VALUE);
        }
        distance.put(source, 0.0);

        backtrace.put(source,source);
        for (int i = 0; i < graph.getVertexCount() - 1; i++) {
            for (Edge<T> edge : graph.getEdges()) {
                if (distance.get(edge.getSource()) < Double.MAX_VALUE) {
                    if (distance.get(edge.getTarget()) > distance.get(edge.getSource()) + edge.getWeight()) {
                        distance.put(edge.getTarget(), distance.get(edge.getSource()) + edge.getWeight());
                        backtrace.put(edge.getTarget(),edge.getSource());
                    }
                }
            }
        }

        return distance;
    }

    /*
     @return возвращает дерево кратчащий путей
    */
    public Map<T,T>getBacktrace(){
        return backtrace;
    }
}
