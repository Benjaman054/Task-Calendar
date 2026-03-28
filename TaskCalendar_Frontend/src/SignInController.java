import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SignInController {

    @FXML
    private PasswordField passTextField;

    @FXML
    private TextField userTextField;

    @FXML
    void signInButton(ActionEvent event) {
        String name = userTextField.getText();
        String pass = passTextField.getText();
        if(name.isEmpty() || pass.isEmpty()){
            AlertMessages.alertWarning("Empty Cell" , "Fill the cells please");
            return;
        }
        String json = String.format("{\"userName\":\"%s\", \"password\":\"%s\"}",name,pass);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/login"))
                .header("Content-type" , "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response ->{
                    int statusCode = response.statusCode();

                    Platform.runLater(()->{
                        if(statusCode == 200){
                            TasksController.tasksByDate.clear();
                            String jsonResponse = response.body();
                            if(!jsonResponse.equals("{}")){
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ArrayList<TaskSender>>(){}.getType();
                                ArrayList<TaskSender> tasks = gson.fromJson(jsonResponse, listType);

                                if(tasks != null){

                                    for(TaskSender task : tasks ){
                                        Calendar c = parseStringToCalendar(task.getDate());
                                        if(!TasksController.tasksByDate.containsKey(c)){
                                            TasksController.tasksByDate.put(c , new MyPriorityQueue<>(5));
                                        }
                                        TasksController.tasksByDate.get(c).add(task.getTask() , task.getPriority());
                                    }
                                }
                            }




                            try{
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
                                Parent root = loader.load();
                                CalendarController calendar = loader.getController();
                                calendar.setName(name);
                                Stage new_stage = new Stage();
                                Scene scene = new Scene(root);
                                new_stage.setTitle("My Calendar");
                                new_stage.setScene(scene);
                                new_stage.show();
                                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                currentStage.close();
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }

                        }
                        else if(statusCode == 401){
                            AlertMessages.alertWarning("Error" , "Either wrong password or wrong user name");

                        }
                        else{
                            AlertMessages.alertWarning("Error" , "Server Error");
                        }
                    });
                });

    }

    @FXML
    void registerBotton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
            Parent root = loader.load();
            Stage new_stage = new Stage();
            Scene scene = new Scene(root);
            new_stage.setTitle("Register");
            new_stage.setScene(scene);
            new_stage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public Calendar parseStringToCalendar(String data){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(data);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY , 0);
            cal.set(Calendar.MINUTE , 0);
            cal.set(Calendar.SECOND , 0);
            cal.set(Calendar.MILLISECOND , 0);
            return cal;

        }
        catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }



}
