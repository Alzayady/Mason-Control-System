
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;

public class SignalFlowGraph {


     private Set<Integer> visited=new HashSet<>();
     private Integer sink;
     private int source;
     private Vector<Vector<Integer>>loops=new Vector<>();
     private boolean find_loops=false;
     private Vector<Vector<Integer>> forward_paths;
     private Vector<Node>nodes;
     private int size ;

    public Vector<Node> getNodes() {
        return nodes;
    }

    public int getSize() {
        return size;
    }

    public SignalFlowGraph(int source , int sink, int size)  {
         this.sink=sink-1;
         this.size=size;
         this.source=source-1;
         nodes=new Vector<>();
         for(int i =0 ; i < size ; i++){
             nodes.add(new Node());
         }
     }
     void add_edge(int from, int to , Double weight){
         from--;
         to--;
         nodes.get(from).insert_neighbor(to,weight);
     }


     public Vector<Vector<Integer>> get_forward_path() {
         visited.clear();
          forward_paths= dfs(source);
         for(Vector  v :forward_paths){
             reverse(v);
         }
         return forward_paths;
    }

     private Vector<Vector<Integer>>dfs(int node ){
        Vector<Vector<Integer>>ans=new Vector<>();
        if (node==sink){
            Vector<Integer>temp=new Vector<>();
            temp.add(node);
            ans.add(temp);
            return ans;
        }
        visited.add(node);
        for (int  neighbor: nodes.get(node).get_neighbors()){
            if (visited.contains(neighbor))continue;
            Vector<Vector<Integer>> next_step=dfs(neighbor);
            for (Vector<Integer>next_vec:next_step) {
                next_vec.add(node);
                ans.add(next_vec);
            }
        }
        visited.remove(node);
        return ans;
    }


    public Vector<Vector<Integer>> get_loops() {
          visited.clear();
          loops.clear();
          dfs_loops(source);
          for(Vector  v :loops){
            reverse(v);
          }
          find_loops=true;
          remove_dublicates();
          return loops;
    }


    private void remove_dublicates() {
        Vector<Vector<Integer>>new_loops=new Vector<>();
        Vector<Vector<Integer>>sorted_new_loops=new Vector<>();

        for(Vector<Integer> vec: loops)
        {
            Vector<Integer>temp= (Vector<Integer>) vec.clone();
            temp.remove(temp.size()-1);
            sort(temp);
            boolean add = true;
            for(Vector<Integer>prev:sorted_new_loops)
            {
                if(prev.equals(temp))
                {
                    add=false;
                    break;
                }
            }
            if(add)
            {
                new_loops.add(vec);
                sorted_new_loops.add(temp);
            }
        }
        loops=new_loops;
    }



    private Vector<Vector<Integer>> dfs_loops(int node) {
         Vector<Vector<Integer>>ans=new Vector<>();
         if(visited.contains(node)){
             Vector<Integer>temp=new Vector<>();
             temp.add(node);
             ans.add(temp);
             return ans;
         }
         visited.add(node);
        for (int  neighbor: nodes.get(node).get_neighbors()){
            Vector<Vector<Integer>> next_step=dfs_loops(neighbor);
            for(Vector<Integer>next:next_step) {
                next.add(node);
                if(next.get(0)==node){
                    // loop is finised and store it
                    loops.add(next);
                }else{
                    // store this part of loop in ans and then return it to prev step
                    ans.add(next);
                }
            }
        }

        visited.remove(node);
        return ans;
    }
    public Vector<Vector<Integer>> get_untouched_loops( ){
      if(find_loops){
          return get_untouched_loops(loops);
      }
      find_loops=true;
      return get_untouched_loops(this.get_loops());
    }


    public Vector<Vector<Integer>> get_untouched_loops(Vector<Vector<Integer>>inner_loops){
        Vector<Vector<Integer>>current_level=new Vector<>();
       Vector<Vector<Integer>>ans=new Vector<>();

       for(int i = 0 ;  i < inner_loops.size() ; i ++){
           Vector<Integer>temp=new Vector<>();
           temp.add(i);
           ans.add(temp);
           current_level.add(temp);
       }

       while (current_level.size()>0){
           Vector<Vector<Integer>>next_level=new Vector<>();
           for(int i =0 ; i <inner_loops.size() ; i++){
               for(int j = 0 ; j<current_level.size() ; j ++){
                   if(untouch(inner_loops.get(i),current_level.get(j),inner_loops)){
                       Vector<Integer>untouched_loops= (Vector<Integer>) current_level.get(j).clone();
                       untouched_loops.add(i);
                       sort(untouched_loops);
                       if(!next_level.contains(untouched_loops)){
                           next_level.add(untouched_loops);
                           ans.add(untouched_loops);
                       }
                   }
               }
           }
           current_level=next_level;
       }
      return ans;

    }

    private boolean untouch(Vector<Integer> one_loop, Vector<Integer> prev_level, Vector<Vector<Integer>> inner_loops) {
           for(int num : prev_level){
               if(touch(one_loop,inner_loops.get(num))){
                   return false;
               }
           }
           return true;
    }

    private boolean touch(Vector<Integer> one_loop, Vector<Integer> sec_loop) {
        for(int num : one_loop){
            if(sec_loop.contains(num)){
                return true;
            }
        }
        return false;
    }

    public Double get_gain_of_path(Vector<Integer> path) throws Exception {
        Double ans=1.0;
        for(int i= 0 ; i<path.size()-1 ; i++){
            ans*=get_gain_of_edge(path.get(i),path.get(i+1));
        }
        return ans;
    }

    private Double get_gain_of_edge(Integer from, Integer to) throws Exception {
        Vector<Integer>neighbor=nodes.get(from).get_neighbors();
        Vector<Double>weights=nodes.get(from).get_weights();
        int size=neighbor.size();
        for(int i =0 ; i< size ; i++){
            if(neighbor.get(i).equals(to))
                return weights.get(i);
        }
        throw new Exception(from + " isn't connected with " +to);
    }

    public boolean valid() {
        visited.clear();
        dfs_valid(source);
        return visited.size()==size;
    }
    void dfs_valid(int node){
        visited.add(node);
        for (int  neighbor: nodes.get(node).get_neighbors()){
            if (visited.contains(neighbor))continue;
            dfs_valid(neighbor);
        }
    }

}
