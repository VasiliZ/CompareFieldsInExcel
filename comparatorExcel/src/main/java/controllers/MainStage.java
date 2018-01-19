package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainStage extends Application implements Actions, Initializable {

    private static int WIDTH_SCENCE = 503;
    private static int HEIGHT_SCENCE = 313;
    private Stage mMainStage;


    @FXML
    private Label mPathToFirstFileLabel;
    @FXML
    private Label mPathToSecondFileLabel;
    @FXML
    private Button mFirstButton;
    @FXML
    private Button mSecondButton;

    private ArrayList<File> mListFiles = new ArrayList<File>();

    public void start(final Stage primaryStage) throws Exception {
        Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\main_window_fxml.fxml"));
        primaryStage.setTitle("Compare files");
        Scene scene = new Scene(fxmlLoader, WIDTH_SCENCE, HEIGHT_SCENCE);
        primaryStage.setScene(scene);
        primaryStage.show();

        mMainStage = primaryStage;

    }

    @FXML
    public File getFile(ActionEvent pActionEvent) {


        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel files open only ", "*.xls", "*.xlsx");
        fileChooser.getExtensionFilters().add(filter);
        final File file = fileChooser.showOpenDialog(mMainStage);

        return file;
    }

    public void setPathFile(final File pFile) {

 
    }

    public void initialize(URL location, ResourceBundle resources) {
        mFirstButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                mListFiles.add(getFile(event));
            }
        });

        mSecondButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                mListFiles.add(getFile(event));
            }
        });
    }
}
