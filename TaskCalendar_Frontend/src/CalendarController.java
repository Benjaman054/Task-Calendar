import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.event.ActionEvent;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class CalendarController {
	
	private final List<Button>  buttons = new ArrayList<>();

	private String userName;

	
	private static final String[] MONTHS = {
		    "January", "February", "March", "April", "May", "June",
		    "July", "August", "September", "October", "November", "December"
		};

    @FXML
    private ComboBox<String> month_selector;

    @FXML
    private ComboBox<Integer> year_selector;
    
    @FXML
    private GridPane gridCalendar;
    
   
    
    @FXML
    private void initialize() {
    	for(int i = 1 ; i <= 6 ; i++) {
    		for(int j = 0 ; j <=6 ; j++) {
    			Button btn = (Button) gridCalendar.lookup("#" + String.format("day%d%d" , i , j));
    			buttons.add(btn);
    		}
    	}
    	
    	Calendar c = Calendar.getInstance();
    	
    	for(int y = 2000 ; y <= 2100 ; y++) {
    		year_selector.getItems().add(y);
    	}
    	month_selector.getItems().addAll(MONTHS);
    	year_selector.setValue(c.get(Calendar.YEAR));
    	month_selector.setValue(MONTHS[c.get(Calendar.MONTH)]);
    	c.set(Calendar.DAY_OF_MONTH, 1);
    	fillGrid(c.get(Calendar.DAY_OF_WEEK), c.getActualMaximum(Calendar.DAY_OF_MONTH));
    	year_selector.getSelectionModel()
    	.selectedItemProperty().addListener((obs, oldYear, newYear) ->{
    		if(newYear != null) {
    			c.set(Calendar.YEAR, newYear);
    			fillGrid(c.get(Calendar.DAY_OF_WEEK), c.getActualMaximum(Calendar.DAY_OF_MONTH));
    		}
    	});
    	
    	month_selector.getSelectionModel()
    	.selectedItemProperty().addListener((obs, oldMonth, newMonth)->{
    		if(newMonth != null) {
    			int i = getMonthindex(newMonth);
    			c.set(Calendar.MONTH, i);
    			fillGrid(c.get(Calendar.DAY_OF_WEEK), c.getActualMaximum(Calendar.DAY_OF_MONTH));

    		}
    	});
    	
    	
    }
    public void fillGrid(int start_day , int daysInMonth) {
    	int day_count = 1;
    	for(int b = 0 ; b < 42 ; b++) {
    		Button btn = buttons.get(b);
			if(b < start_day - 1 || b >= start_day - 1 + daysInMonth)
			{
				btn.setVisible(false);
			}
			else {
				btn.setVisible(true);
				buttons.get(b).setText(String.format("%d",day_count));
				day_count++;
			}
			
		}
    
    }
    
    
    
    
    @FXML
    void openTasksWindow(ActionEvent event) throws Exception {
    	String dayText = ((Button) event.getSource()).getText();
    	String month = (String) month_selector.getValue();
		String date = String.format("%d/%d/%d", Integer.valueOf(dayText) , getMonthindex(month) + 1  , year_selector.getValue());
		Calendar c = Calendar.getInstance();
		c.set(year_selector.getValue() , getMonthindex(month) , Integer.valueOf(dayText));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tasks.fxml"));
		Parent root = loader.load();
		TasksController tasksController = loader.getController();
		tasksController.setDate(c);
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setTitle("Tasks " + date);
		stage.setScene(scene);
		stage.show();
    }

	@FXML
	void saveButton(ActionEvent event) {

		ArrayList<TaskSender> jsonBody = new ArrayList<>();
		mapToJson(TasksController.tasksByDate , jsonBody);
		String finalJson = new Gson().toJson(jsonBody);
		String url = "http://localhost:8080/api/updateTasks/" + userName;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type" , "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(finalJson))
				.build();



			client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
					.thenAccept(response -> {
						if (response.statusCode() == 200) {
							Platform.runLater(() -> {
								AlertMessages.alertInformaition("Great Success", "Your data was saved" , "Saved!");
							});
						} else {
							Platform.runLater(() -> {
								AlertMessages.alertWarning("Server Error", "Your data was not saved");
							});
						}
					});

	}
    
    public int getMonthindex(String month) {
    	int i=0;
    	while(!month.equals(MONTHS[i])) i++;
    	return i;
    }

	public void setName(String name){
		this.userName = name;
	}

	public void mapToJson(Map<Calendar , MyPriorityQueue<String>> currMap , ArrayList<TaskSender> tasks){
		currMap.forEach((calendar , queue) ->{
			String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			Iterator<String> iterator = queue.iterator();
			while(iterator.hasNext()){
				String taskContent = iterator.next();
				tasks.add(new TaskSender(dateStr , taskContent , ((MyPriorityQueue.New_Iterator)iterator).getActualPriority()));

			}

		});

	}

}
