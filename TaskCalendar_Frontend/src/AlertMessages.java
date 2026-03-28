import javafx.scene.control.Alert;

public class AlertMessages {
    public static void alertWarning(String title , String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }


    public static void alertInformaition(String title , String content , String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}
