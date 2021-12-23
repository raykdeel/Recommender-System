package presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ricard Guixaró Trancho
 */

public class viewSignUpCtrl implements Initializable {
    private CtrlPresentation ctrlPresentation;

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword1;
    @FXML private PasswordField pfPassword2;
    @FXML private Button btnLogin;
    @FXML private Label errorLabel;
    @FXML private Pane paneUsername;
    @FXML private Pane panePassword1;
    @FXML private Pane panePassword2;
    @FXML private CheckBox adminCheck;

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
     * The back button has been clicked. The user is redirected to the logIn pane.
     * @param actionEvent
     * @throws IOException
     */
    public void btnBackClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        ctrlPresentation.changeStage("/presentation/viewLogIn.fxml");
    }

    /**
     * The logIn button has been clicked. The introduced data must fulfil some conditions though.
     * If those conditions are not accomplished, the system informs of the error.
     * @param actionEvent
     * @throws IOException
     */
    public void btnLoginClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        String username = tfUsername.getText();
        String password1 = pfPassword1.getText();
        String password2 = pfPassword2.getText();
        if (username.length() >= 3) {
            if (password1.length() >= 4) {
                if (password1.equals(password2)) {
                    if (ctrlPresentation.signUp(username, password1, adminCheck.isSelected())) {
                        if (ctrlPresentation.getImported()) {
                            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                            ctrlPresentation.changeStage("/presentation/viewMainMenu.fxml");
                        } else {
                            if (ctrlPresentation.isAdmin()) {
                                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                                ctrlPresentation.changeStage("/presentation/viewImport.fxml");
                            } else {
                                showError("The account has been created, yet no database has been imported. An administrator of the system has to load it first.");
                            }
                        }
                    } else {
                        showErrorLabel("Username not available", "username");
                    }
                } else {
                    showErrorLabel("Passwords are different", "password");
                }
            } else {
                showErrorLabel("Password must be at least 4 characters long", "password");
            }
        } else {
            showErrorLabel("Username must be at least 3 characters long", "username");
        }
    }

    /**
     * Shows a label with the given error and modifies some elements of the UI, to make the user see what's wrong
     * @param errorMessage
     * @param source
     */
    private void showErrorLabel(String errorMessage, String source) {
        btnLogin.setStyle("-fx-border-color: #ff0000");
        errorLabel.setText(errorMessage);
        if (source.equals("username")) {
            paneUsername.setStyle("-fx-border-color: #ff0000");
        } else {
            panePassword1.setStyle("-fx-border-color: #ff0000");
            panePassword2.setStyle("-fx-border-color: #ff0000");
        }
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
}
