import java.util.Vector;

public class Node {

    private Vector<Integer> neighbors=new Vector<>();
    private Vector<Double>weights=new Vector<>();

    public Node(){

    }
    public Node(Vector<Integer> neighbors, Vector<Double> weights) {
        this.neighbors = neighbors;
        this.weights = weights;
    }

    public void insert_neighbor(int node, Double weight){
        neighbors.add(node);
        weights.add(weight);
    }
    public Vector<Integer> get_neighbors(){
        return neighbors;
    }
    public Vector<Double> get_weights(){
        return weights;
    }

}
