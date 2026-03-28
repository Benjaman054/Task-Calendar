import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("./signIn.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Calendar");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}

}
