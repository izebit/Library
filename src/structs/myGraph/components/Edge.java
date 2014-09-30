package structs.myGraph.components;

/**
 * Created with IntelliJ IDEA.
 * Date: 03.10.12
 * Time: 18:58
 *
 * @author Artem Konovalov
 */
public class Edge<T> implements Comparable<Edge<T>>{
    private T source;
    private T target;
    private double weight;

    public Edge(T source, T target) {
        this(source, target, 1.0);
    }

    public Edge(T source, T target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }


    public T getTarget() {
        return target;
    }

    public T getSource() {
        return source;
    }

    public double getWeight() {
        return weight;
    }

    public boolean equals(Object o) {
        Edge<T> edge = (Edge<T>) o;
        return (source.equals(edge.source)) && (target.equals(edge.target));
    }

    public int hashCode() {
        return (source+" "+target).hashCode();
    }

    @Override
    public int compareTo(Edge<T> o) {
        return (int)(weight-o.weight);
    }

    public String toString(){
        return source+"--"+target;
    }
}
