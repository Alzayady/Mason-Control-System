import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Vector;
import java.util.function.UnaryOperator;


public class View  {
    private controller control;
    private Group root = new Group();
    private Text ans =new Text();
    private Text error =new Text();
    private TextArea details=new TextArea();

    Stage last =null;
    public View(Stage primaryStage, controller control ){
        this.control=control;
        Text text = new Text();

        text.setX(100);
        text.setY(100);
        text.setFont(Font.font("Abyssinica SIL",FontWeight.NORMAL,FontPosture.ITALIC,40));
        text.setText("Mason formula ");

        TextField numOfNodes=new TextField();

        numOfNodes.setPromptText("number of nodes");
        numOfNodes.setLayoutX(100);
        numOfNodes.setLayoutY(150);


        TextField source=new TextField();
        source.setPromptText("source");
        source.setLayoutX(100);
        source.setLayoutY(200);

        TextField sink=new TextField();
        sink.setPromptText("sink");
        sink.setLayoutX(100);
        sink.setLayoutY(250);




        Button button = new Button("Apply");
        button.setLayoutX(100);
        button.setLayoutY(730);
        button.setPrefWidth(150);
        button.setTextFill(Color.RED);

        TextArea textArea=new TextArea();
        textArea.setPromptText("Edges  \n                                 " +
                "                           From   To    Weight \n                        " +
                "            25       10      2");
        textArea.setLayoutX(100);
        textArea.setLayoutY(300);
        textArea.setPrefRowCount(25);
        textArea.setPrefColumnCount(20);

        Text edges=new Text("Edges");
        edges.setLayoutX(300);
        edges.setLayoutY(170);
        edges.setFont(Font.font ("Abyssinica SIL", 20));

        Text note = new Text("** Each row represnt edge \n  From   To    Weight \n " +
                "  25       10      2 ");
        note.setFont(Font.font ("Abyssinica SIL", 15));
        note.setFill(Color.CHOCOLATE);

        note.setX(370);
        note.setY(320);


        ans.setFont(Font.font ("Abyssinica SIL", 30));
        ans.setFill(Color.DEEPPINK);

        ans.setX(600);
        ans.setY(500);

        error.setFont(Font.font ("Abyssinica SIL", 17));
        error.setFill(Color.RED);

        error.setX(360);
        error.setY(700);


        details.setPromptText("");
        details.setLayoutX(500);
        details.setLayoutY(150);
        details.setPrefRowCount(30);
        details.setPrefColumnCount(12);
        details.setDisable(true);
       // details.setStyle(" -fx-font-family: Consolas; -fx-highlight-fill: #FF6D66; -fx-highlight-text-fill: #FF6D66; -fx-text-fill: #78092A; ");
        details.setFont(Font.font("Verdana", FontWeight.BOLD, 20));


        button.setOnAction(e -> {
            try {
                Integer num_of_nodes = Integer.parseInt(numOfNodes.getText().trim());
                Integer sink_num = Integer.parseInt(sink.getText().trim());
                Integer source_num = Integer.parseInt(source.getText().trim());
                String data = textArea.getText();
                try {
                    control.setGraph(num_of_nodes, source_num, sink_num, data);
                } catch (Exception e1) {
                    setErrorMessage("Error in Data");
                }

            } catch (Exception exception) {
                setErrorMessage("write numercal value in each field ");
            }
        });

        Scene scene = new Scene(root,1500,1000);
        root.getChildren().add(textArea);
        root.getChildren().add(text);
        root.getChildren().add(numOfNodes);
        root.getChildren().add(source);
        root.getChildren().add(sink);
        root.getChildren().add(ans);
        root.getChildren().add(error);
        root.getChildren().add(button);
        root.getChildren().add(details);
         primaryStage.setScene(scene);
        primaryStage.setTitle("Text Example");


        primaryStage.show();

    }


    public void setErrorMessage(String error_in_data) {
        System.out.println(error_in_data);
        error.setText(error_in_data);
    }

    public void setAns(Answer answer) {
        System.out.println(answer);
        details.setText(answer.toString());
//      ans.setText("Answer:   " + solve.toString());
    }


    public void drawGraph(SignalFlowGraph graph){

            resetDataMessage();

            Graph<String, String> g = new GraphEdgeList<>();

            int size=graph.getSize();
            for(Integer i=1;i<=size; i++){
                g.insertVertex(i.toString());
            }
            String shift="";
            Vector<Node>nodes=graph.getNodes();
            for(int i =0;i<size ;i ++){
                Vector<Integer>neighbors=nodes.get(i).get_neighbors();
                Vector<Double>weights=nodes.get(i).get_weights();
                for(int j = 0; j <neighbors.size() ; j++){
                    shift+=" ";
                    int from = i+1;
                    int to =neighbors.get(j)+1;
                    Double weight=weights.get(j);
                    g.insertEdge(Integer.toString(from), Integer.toString(to),shift+weight.toString());
                }
            }

            SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
            SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
            Scene scene = new Scene(graphView, 800, 768);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("signal flow graph ");
            stage.setScene(scene);
            stage.show();

            last=stage;
            graphView.init();



    }

    private void resetDataMessage() {
        setErrorMessage("");
    }
}
