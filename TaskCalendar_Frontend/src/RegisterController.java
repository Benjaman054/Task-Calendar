import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterController {

    @FXML
    private PasswordField passTextField2;


    @FXML
    private PasswordField passTextField1;

    @FXML
    private TextField userTextField;

    @FXML
    void registerBotton(ActionEvent event) {
        String name = userTextField.getText();
        String pass = passTextField1.getText();
        String pass_re = passTextField2.getText();
        if(name.isEmpty() || pass.isEmpty() || pass_re.isEmpty()){
            AlertMessages.alertWarning("Empty Cell" , "Fill the cells please");
            return;
        }
        if(!pass.equals(pass_re)){
            AlertMessages.alertWarning("Error" , "The passwords not the same");
            passTextField1.clear();
            passTextField2.clear();
        }
        else{
            String json = String.format("{\"userName\":\"%s\", \"password\":\"%s\"}",name,pass);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/register"))
                    .header("Content-type" , "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        int statusCode = response.statusCode();

                        Platform.runLater(() -> {
                            if (statusCode == 200) {
                                TasksController.tasksByDate.clear();
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/signIn.fxml"));
                                    Parent root = loader.load();
                                    Stage new_stage = new Stage();
                                    Scene scene = new Scene(root);
                                    new_stage.setTitle("Sign in");
                                    new_stage.setScene(scene);
                                    new_stage.show();
                                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AlertMessages.alertWarning("Error", "Server Error");
                            }
                        });
                    });
        }


    }

}
