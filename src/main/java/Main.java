import View.View;
import ViewModel.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

public class Main extends Application {
        private Model model;
        private View view;

        @Override
        public void start(Stage primaryStage) throws Exception {
            model = new Model();
            Controller controller = new Controller(model);
            model.addObserver(controller);
            primaryStage.setTitle("Search Engine!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/MyView.fxml").openStream());
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/EngineStyle.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(500);
            view = fxmlLoader.getController();
            view.setController(controller,primaryStage);
            controller.addObserver(view);
            primaryStage.show();
        }



    public static void main(String[] args) {
            launch(args); }
}
