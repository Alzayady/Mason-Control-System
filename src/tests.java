import org.junit.Test;

import java.util.Vector;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;
import static org.junit.Assert.*;

public class tests {

    Vector<Node> get_node_of_graph3() {
        Vector<Node> nodes = new Vector<>();
        Vector<Integer> neighbor = new Vector<>();
        Vector<Double> weights = new Vector<>();

        neighbor.add(1);
        weights.add(1d);
        nodes.add(new Node(neighbor, weights));

        neighbor = new Vector<>();
        weights = new Vector<>();

        neighbor.add(2);
        neighbor.add(5);
        weights.add(5d);
        weights.add(10d);

        nodes.add(new Node(neighbor, weights));
        neighbor = new Vector<>();
        weights = new Vector<>();

        neighbor.add(3);
        weights.add(10d);


        nodes.add(new Node(neighbor, weights));
        neighbor = new Vector<>();
        weights = new Vector<>();

        neighbor.add(4);
        weights.add(2d);

        neighbor.add(2);
        weights.add(-1d);

        nodes.add(new Node(neighbor, weights));
        neighbor = new Vector<>();
        weights = new Vector<>();

        neighbor.add(6);
        weights.add(1d);

        neighbor.add(3);
        weights.add(-2d);

        neighbor.add(1);
        weights.add(-1d);

        nodes.add(new Node(neighbor, weights));
        neighbor = new Vector<>();
        weights = new Vector<>();

        neighbor.add(5);
        weights.add(-1d);
        neighbor.add(4);
        weights.add(2d);
        nodes.add(new Node(neighbor, weights));
        neighbor = new Vector<>();
        weights = new Vector<>();
        nodes.add(new Node(neighbor, weights));


        return nodes;
    }

    @Test
    public void test_loops3() {
        try {
            SignalFlowGraph graph = new SignalFlowGraph(1, 7, 1);
            Vector<Vector<Integer>> loops = graph.get_loops();
            int i = 0;
            for (Vector<Integer> vec : loops) {
                System.out.println("loop: " + i + " is : ");
                for (int num : vec) {
                    System.out.print(num + 1 + " ");
                }
                i++;
                System.out.println();
            }

        } catch (Exception e) {
            fail();
        }
    }

    //    public void un_touched_loops_3()
//    {
//        test_loops3();
//        System.out.println("==================");;
//        try {
//            Graph graph= new Graph(get_node_of_graph3());
//            Vector<Vector<Integer>> un=graph.get_untouched_loops(0);
//            for(Vector<Integer>vec:un ){
//                System.out.println("untouched ");
//                for(int num:vec){
//                    System.out.print(num+" ");
//                }
//                System.out.println();
//            }
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//
//    @Test
//    public void test_gain() throws Exception {
//        Graph graph=new Graph(get_node_of_graph3());
//        Vector<Vector<Integer>>forward_paths=graph.get_forward_path(0);
//        for(Vector<Integer> path : forward_paths){
//            System.out.println(graph.get_gain_of_path(path));
//        }
//    }
//
    @Test
    public void test_mason1() throws Exception {
        test_loops3();
        SignalFlowGraph graph = new SignalFlowGraph(1, 7, 7);
        Answer ans = new Model().solve(graph);
        Double ac = 14d / 15;
        System.out.println(ans);
        assertEquals(ans.value, ac);
        System.out.println(ans);
        ;
    }

    @Test
    public void test_masson() throws Exception {
        SignalFlowGraph graph = new SignalFlowGraph(1, 6, 6);
        graph.add_edge(1, 2, 1d);
        graph.add_edge(2, 5, 3d);
        graph.add_edge(2, 3, 5d);
        graph.add_edge(3, 2, -7d);
        graph.add_edge(3, 4, 11d);
        graph.add_edge(4, 5, 23d);
        graph.add_edge(5, 4, -13d);
        graph.add_edge(5, 2, -17d);
        graph.add_edge(5, 6, 19d);

        Answer ans = new Model().solve(graph);
        double delta = 1 - ((-35.0) + (-21505.0) + (-51.0) + (-299.0)) + (10465.0);
        double m1 = 24035;
        double m2 = 57;
        System.out.println(ans);
        assertEquals((m1 + m2) / delta, ans.value, 0.00000000001);

    }
}


///*
//1 2 1
//2 3 5
//3 4 10
//4 5 2
//5 6 1
//2 7 10
//7 7 -1
//7 5 2
//5 2 -1
//5 4 -2
//4 3 -1
// */
//    }
//
/*
1 2 1
2 3 5
2 6 10
3 4 10
4 5 2
4 3 -1
5 4 -2
5 7 1
5 2 -1
6 5 2
6 6 -1

 */

/*
1 2 1
2  5 3
2 3 5
3  2 -7
3  4  11
 4 5  23
5  4  -13
5 2 -17
5 6 19
 */