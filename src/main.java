import javafx.application.Platform;
import javafx.stage.Stage;

public class main {
    public static void main(String[] args) {

        Platform.startup(() -> {
            Stage stage = new Stage();
            new controller(stage);
        });
    }
}
