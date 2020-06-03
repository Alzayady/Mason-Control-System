import java.util.Vector;

public class Answer {
    Double value;
    Vector<Vector<Integer>>forwardPaths;
    Vector<Vector<Integer>>loops;

    public void setValue(Double value) {
        this.value = value;
    }


    public void setForwardPaths(Vector<Vector<Integer>> forwardPaths) {
        this.forwardPaths = forwardPaths;
    }

    public void setLoops(Vector<Vector<Integer>> loops) {
        this.loops = loops;
    }
    @Override
    public String toString(){
        String ans="Answer\n";
        ans+=value.toString();
        ans+="\n";
        ans+="Forward Paths\n";
        for(Vector<Integer> vec:forwardPaths){
            ans+=vec;
            ans+="\n";
        }
        ans+="\n \n \n ";
        ans+="Loos\n";


        for(Vector<Integer> vec:loops){
            ans+=vec;
            ans+="\n";
        }
        return ans;
    }
}
