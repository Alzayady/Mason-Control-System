import javafx.stage.Stage;

public class controller {
    private Stage stage ;
    private View view;
    public controller(Stage stage){
        stage.setHeight(800);
        stage.setWidth(900);
        this.stage=stage;
        view=new View(stage, this);
    }
    void setGraph(int size , int source , int sink , String data) throws Exception {
        String elements[] = data.split(("\\s+"));
        if (elements.length%3!=0){
            view.setErrorMessage("Error in Data");
            return;
        }
        SignalFlowGraph graph=new SignalFlowGraph(source,sink,size);
        try{
            for(int i =0 ;i < elements.length ; i+=3){
                int from = Integer.parseInt(elements[i]);
                int to =Integer.parseInt(elements[i+1]);
                Double weight=Double.parseDouble(elements[i+2]);
                graph.add_edge(from,to,weight);
            }

        }catch (Exception e ){
            view.setErrorMessage("error in data");
            return;
        }
        try{
            Model model=new Model();
            view.setAns(model.solve(graph));
            view.drawGraph(graph);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            view.setErrorMessage("error in calc");
        }
    }
}
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
2 3 1
3 2 1
2 4 1
2 5 1
3 4 1
4 3 1
4 4 1
4 5 1

 */
