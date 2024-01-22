package com.example.toylanguagegui;

import exceptions.InterpreterException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ADTs.MyDictionary;
import model.statements.IStatement;
import view.Examples;

import java.io.IOException;

public class ChooseProgramController {
    @FXML
    private ListView<IStatement> listOfPrograms;
    @FXML
    private Label chooseProgramText;

    private RunProgramController runProgramController;

    @FXML
    public void initialize() {
        listOfPrograms.setItems(this.getStatements());

        // get second window
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RunProgramStyle.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load(), 950, 600);
            this.runProgramController = fxmlLoader.getController();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        listOfPrograms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStatement>() {
            @Override
            public void changed(ObservableValue<? extends IStatement> observableValue, IStatement oldStatement, IStatement newStatement) {
                String filename = "log" + (listOfPrograms.getSelectionModel().getSelectedIndex()+1)+ ".txt";
                runProgramController.setStatement(newStatement, filename);
            }
        });
    }

    private ObservableList<IStatement> getStatements(){
        ObservableList<IStatement> exampleList = FXCollections.observableArrayList();
        IStatement[] examples = new Examples().exampleList();
        for(IStatement example: examples){
            try{
                example.typecheck(new MyDictionary<>());
                exampleList.add(example);
            } catch (InterpreterException exception) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Program that did not pass:\n" + example.toString() + "\n due to:\n" + exception.getMessage());
                error.showAndWait();
            }
        }
        return exampleList;
    }
}