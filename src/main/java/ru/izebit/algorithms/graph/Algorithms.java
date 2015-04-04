package ru.izebit.algorithms.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author : Artem Konovalov
 */
public class Algorithms {
    /**
     * строит путь до указанной вершины по дереву кратчайших путей
     *
     * @param target           вершина до которой строится путь
     * @param shortestPathTree дерево кратчайших путей
     * @return список вершин,входящих в кратчайший путь, в соответствующем порядке
     */
    public static <T> List<T> getPath(T target, Map<T, T> shortestPathTree) {
        List<T> path = new ArrayList<T>();
        return recursionMethod(target, shortestPathTree, path);
    }

    private static <T> List<T> recursionMethod(T target, Map<T, T> shortestPathTree, List<T> path) {
        path.add(target);
        if (target.equals(shortestPathTree.get(target))) {
            Collections.reverse(path);
            return path;
        } else {
            return recursionMethod(shortestPathTree.get(target), shortestPathTree, path);
        }
    }
}
