import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;


public class TasksController {
	
	private static final int MAX_PRIORITY = 5;
	private Calendar date;
	
	public static final Map<Calendar , MyPriorityQueue<String>> tasksByDate = new HashMap<>();
	
    @FXML
    private Button addButton;

    @FXML
    private TextField inputTask;

    @FXML
    private ListView<String> listvVeiwTask;

    @FXML
    private Spinner<Integer> prioritySpinner;

    @FXML
    private Button removeButton;
    
    @FXML
    private void initialize() {
    	for(int p = 1 ; p < 6 ; p++) {
    		prioritySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
    	}
    }
    
    public void setDate(Calendar date) {
    	this.date = (Calendar) date.clone();
    	this.date.set(Calendar.HOUR_OF_DAY, 0);
        this.date.set(Calendar.MINUTE, 0);
        this.date.set(Calendar.SECOND, 0);
        this.date.set(Calendar.MILLISECOND, 0);
    	loadTasks();
    }
    
    public void loadTasks() {
    	listvVeiwTask.getItems().clear();
    	if(tasksByDate.containsKey(this.date)) {
    		for(String s:tasksByDate.get(this.date)) {
    			listvVeiwTask.getItems().add(s);
    		}
    		 
    	}
    	
    }
    
    @FXML
    public void addTask(ActionEvent event) {
    	String taskText = "";
    	taskText = inputTask.getText();
    	if(taskText=="") {
			AlertMessages.alertWarning("EMPTY TASK", "You have to write something!");
			return;
		}
    	if(tasksByDate.containsKey(this.date)) {
    		tasksByDate.get(this.date).add(String.format("%s , Priority: %d" , taskText,prioritySpinner.getValue()) , prioritySpinner.getValue() );
    	}
    	else {
    		MyPriorityQueue<String> newQueue = new MyPriorityQueue<>(MAX_PRIORITY);
    		newQueue.add(String.format("%s , Priority: %d" , taskText,prioritySpinner.getValue()), prioritySpinner.getValue());
    		tasksByDate.put(this.date, newQueue);
    	}
    	inputTask.clear();
    	loadTasks();
    	
    }
    
    @FXML
    public void removeTask(ActionEvent event) {
    	if(!tasksByDate.containsKey(this.date) || tasksByDate.get(this.date).size() == 0){
    		AlertMessages.alertWarning("NO TASKS", "There are no tasks to remove");
    	}
    	else {
    		String task = listvVeiwTask.getSelectionModel().getSelectedItem();
    		if(task == null) {
    			AlertMessages.alertWarning("NO TASK WAS SELECTED", "Select the task you wish to remove");
    		}
    		else {
    			tasksByDate.get(this.date).remove(task);
    			loadTasks();
    		}
    	}
    }
    

}
