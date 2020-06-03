import javafx.scene.layout.AnchorPane;

import java.util.Vector;

public class Model {
    private Vector<Double>gains_loops=new Vector<>();
    private Vector<Double>gains=new Vector<>();
    Vector<Vector<Integer>>loops;
    public Answer solve(SignalFlowGraph graph ) throws Exception {
        if(!graph.valid()){
            throw new Exception("graph isn't connected ");
        }
        Vector<Vector<Integer>>forward_paths=graph.get_forward_path();
        loops=graph.get_loops();
        Vector<Vector<Integer>>untouched_loops=graph.get_untouched_loops();

        Double ans=0.0;
        for(Vector<Integer> path : forward_paths){
            gains.add(graph.get_gain_of_path(path));
        }

        for(Vector<Integer> loop : loops) {
            gains_loops.add(graph.get_gain_of_path((loop)));
        }


        for (int i = 0 ; i <forward_paths.size() ; i++)
        {
            Vector<Integer> indexes_of_loops_out_of_path = new Vector<>();
            Vector<Vector<Integer>> untouched_loops_in_path = new Vector<>();
            /*
            get_loops out of this path
             */

            for (int j = 0; j < loops.size(); j++) {

                boolean add = true;
                for (int num : loops.get(j)) {
                    if (forward_paths.get(i).contains(num)) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    indexes_of_loops_out_of_path.add(j);
                }
            }


            /*
            get untouched loops out of this path
             */
            for (Vector<Integer> vec : untouched_loops) {
                boolean add = true;
                for (int index : vec) {
                    if (!indexes_of_loops_out_of_path.contains(index)) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    untouched_loops_in_path.add(vec);
                }
            }
            ans+=gains.get(i)*get_mason_delta(untouched_loops_in_path);
        }
        ans/=(get_mason_delta(untouched_loops));
        Answer answer=new Answer();
        answer.setValue(ans);
        for(int i = 0 ;i < forward_paths.size() ; i++){
            Vector<Integer >v =forward_paths.get(i);
            for(int j = 0; j < v.size() ; j++) {
                int p = v.get(j)+ 1;
                v.set(j,p);
            }
        }
        for(int i = 0 ;i < loops.size() ; i++){
            Vector<Integer >v =loops.get(i);
            for(int j = 0; j < v.size() ; j++) {
                int p = v.get(j)+ 1;
                v.set(j,p);
            }
        }
        answer.setForwardPaths(forward_paths);
        answer.setLoops(loops);
        return answer;

    }

    private Double get_mason_delta( Vector<Vector<Integer>> untouched_loops_in_path) {
        Double ans= 1.0 ;
        for(Vector<Integer> untouched : untouched_loops_in_path){
            Double ans_here=1.0;
            if(untouched.size()%2==1){
                ans_here*=-1;
            }
            for(int index : untouched){
                ans_here*=gains_loops.get(index);
            }
            ans+=ans_here;
        }
        return ans;
    }

}
