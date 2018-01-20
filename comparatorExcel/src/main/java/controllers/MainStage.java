package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ModelMainStage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class MainStage extends Application implements Actions, Initializable {

    private static int WIDTH_SCENE = 503;
    private static int HEIGHT_SCENE = 313;
    private Stage mMainStage;


    @FXML
    private Label mPathToFirstFileLabel;
    @FXML
    private Label mPathToSecondFileLabel;
    @FXML
    private Button mFirstButton;
    @FXML
    private Button mSecondButton;
    @FXML
    private ComboBox<String> mFieldsFistFile;
    @FXML
    private ComboBox<String> mFieldsSecondFile;
    @FXML
    private Button mCompareFiles;


    private ArrayList<File> mListFiles = new ArrayList<>();
    private ObservableList<String> arrayNameFieldsFirstFile = FXCollections.observableArrayList();
    private ObservableList<String> arrayNameFieldsSecondFile = FXCollections.observableArrayList();
    private List<List<String>> mDataFirstFile = new ArrayList<>();
    private List<List<String>> mDataSecondFile = new ArrayList<>();

    public void start(final Stage primaryStage) throws Exception {
        ModelMainStage modelMainStage = new ModelMainStage();
        Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\main_window_fxml.fxml"));
        primaryStage.setTitle("Compare files");
        Scene scene = new Scene(fxmlLoader, WIDTH_SCENE, HEIGHT_SCENE);
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

    public void getNameField(final File pFile, final ObservableList observableList) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream inputStream = new FileInputStream(pFile.getAbsolutePath());
                    HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    Row row = sheet.getRow(0);

                    for (int i = 0; i < row.getLastCellNum(); i++) {
                        observableList.add(String.valueOf(row.getCell(i)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Platform.runLater(runnable);
    }

    public void getDataFromFile(final File pFile, final List<List<String>> pListForData){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    FileInputStream fileInputStream = new FileInputStream(pFile.getAbsolutePath());
                    HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
                    HSSFSheet sheet = workbook.getSheetAt(0);

                    for (int i = 1; i< sheet.getLastRowNum(); i++){
                        Row currentRow = sheet.getRow(i);

                        Iterator<Cell> cellIterator = currentRow.iterator();
                        List<String> dataRow = new ArrayList<>();
                        while (cellIterator.hasNext()){

                            Cell currentCell = cellIterator.next();
                            dataRow.add(String.valueOf(currentCell));
                        }
                        pListForData.add(dataRow);
                    }
                }catch (Exception e) {
                    e.fillInStackTrace();
                }finally {
                    System.out.println(pListForData.size());
                }
            }
        };
       Platform.runLater(runnable);

    }

    public void initialize(URL location, ResourceBundle resources) {
        mFirstButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                File firstFile = getFile(event);
                mPathToFirstFileLabel.setText(firstFile.getAbsolutePath());
                getNameField(firstFile, arrayNameFieldsFirstFile);
                mListFiles.add(firstFile);
            }
        });

        mSecondButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                File secondFile = getFile(event);
                mPathToSecondFileLabel.setText(secondFile.getAbsolutePath());
                getNameField(secondFile, arrayNameFieldsSecondFile);
                mListFiles.add(secondFile);
            }
        });

        mFieldsFistFile.setItems(arrayNameFieldsFirstFile);
        mFieldsSecondFile.setItems(arrayNameFieldsSecondFile);

        mCompareFiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getDataFromFile(mListFiles.get(0), mDataFirstFile);
                getDataFromFile(mListFiles.get(1), mDataSecondFile);
            }
        });
    }
}
