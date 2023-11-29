package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import jdk.nashorn.internal.runtime.options.Option;
import model.DeathWishMainModel;
import to.Soul;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

public class DeathWishMainFormController {
    public AnchorPane paneMain;
    public TextField tfFName;
    public TextField tfSName;
    public DatePicker dpDOB;
    public Button btnShow;
    public TextField tfDeathDate;
    public DeathWishMainModel deathWishMainModel=new DeathWishMainModel();

    public void fNameOnAction(ActionEvent actionEvent) {
        tfSName.requestFocus();
    }

    public void showOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Do You Really Wanna know....");
        alert.setTitle("Confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            String sId= deathWishMainModel.generateNewId();
            String fName=tfFName.getText();
            String sName=tfSName.getText();
            Date dob = Date.valueOf(dpDOB.getValue());

            Soul soul= new Soul(sId,fName,sName,dob);
            boolean isAlreadyExists = deathWishMainModel.checkAvailability(soul);
            if (isAlreadyExists){
               String dd= deathWishMainModel.getDeathDate(soul);
               tfDeathDate.setText(dd);
            }else{
                boolean isAdded=deathWishMainModel.saveSoul(soul);
                if (isAdded){
                    String dd= deathWishMainModel.getDeathDate(soul);
                    tfDeathDate.setText(dd);
                }
            }
        }else {
            tfDeathDate.setText("Ok pussy see ya later");
        }
    }
}
