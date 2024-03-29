package gui;

import controller.Controller;
import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.ProgramState;
import model.adt.MyDictionary;
import model.statements.IStatement;
import repository.IRepository;
import repository.Repository;

public class SelectProgramController {

    MainController selectedProgramController;

    @FXML
    ListView<IStatement> allPrograms;
    @FXML
    ObservableList<IStatement> allProgramsModel = FXCollections.observableArrayList();

    public void setController(MainController selectedProgramController) {
        this.selectedProgramController = selectedProgramController;
        allProgramsModel.setAll(BuildPrograms.build());
        allPrograms.setItems(allProgramsModel);

    }


    public void handleSelectStmt() {
        IStatement selectedStmt = allPrograms.getSelectionModel().getSelectedItem();

        try {
            selectedStmt.typecheck(new MyDictionary<>());
            ProgramState prgState = new ProgramState(selectedStmt);
            IRepository repo = new Repository(prgState, "logfile.txt");
            Controller controller = new Controller(repo);
            controller.startExecutor();
            selectedProgramController.setController(controller);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error (type checker)! Invalid program");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }


}




