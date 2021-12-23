package presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ricard Guixaró Trancho
 */

public class viewLogInCtrl implements Initializable {
    private CtrlPresentation ctrlPresentation;

    @FXML private TextField userTxt;
    @FXML private TextField pwdTxt;
    @FXML private Label errorLabel;
    @FXML private Button btnLogIn;
    @FXML private Pane paneUsername;
    @FXML private Pane panePassword;

    /**
     * Method called when class is initialized
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrlPresentation = CtrlPresentation.getInstance();
    }

    /**
     * The logIn button has been clicked. The introduced data must fulfil some conditions though.
     * If those conditions are not accomplished, the system informs of the error.
     * @param actionEvent
     * @throws IOException
     */
    public void logInClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        if(userTxt.getText().length() !=0 && pwdTxt.getText().length() != 0) {
            if(ctrlPresentation.logIn(userTxt.getText(), pwdTxt.getText())) {
                if (ctrlPresentation.getImported()) {
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    ctrlPresentation.changeStage("/presentation/viewMainMenu.fxml");
                } else {
                    if (ctrlPresentation.isAdmin()) {
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                        ctrlPresentation.changeStage("/presentation/viewImport.fxml");
                    } else {
                        showError("Database not imported. An administrator of the system has to load it first.");
                    }
                }
            } else {
                showErrorLabel("Username or password incorrect");
            }
        } else {
            showErrorLabel("Credentials can't be empty!");
        }
    }

    /**
     * Shows a label with the given error and modifies some elements of the UI, to make the user see what's wrong
     * @param error
     */
    public void showErrorLabel(String error) {
        btnLogIn.setStyle("-fx-border-color: #ff0000");
        paneUsername.setStyle("-fx-border-color: #ff0000");
        panePassword.setStyle("-fx-border-color: #ff0000");
        errorLabel.setText(error);
    }

    /**
     * PopUps an error into the screen.
     * @param errorMessage
     */
    private void showError(String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(errorMessage);
        errorAlert.show();
    }

    /**
     * The signUp button has been clicked. The user is redirected to the SignUp page.
     * @param actionEvent
     * @throws IOException
     */
    public void signUpClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        ctrlPresentation.changeStage("/presentation/viewSignUp.fxml");
    }
}
