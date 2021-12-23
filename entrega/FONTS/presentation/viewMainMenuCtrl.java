package presentation;

import domain.classes.Item;
import utils.Pair;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Ricard Guixaró Trancho
 */

public class viewMainMenuCtrl implements Initializable {
    private CtrlPresentation ctrlPresentation;
    private String actualUser;
    private Boolean changed, imported, hasRecommendation;
    private ArrayList<Item> recommendedItems;
    private HashMap<String, Item> itemsMap;
    private ArrayList<String> userData;
    private HashMap<String, Pair<String, Integer>> dbConfig;

    @FXML private Button btnSettings;
    @FXML private Button btnSettingsClose;
    @FXML private Button btnLogout;
    @FXML private Button btnCollaborative;
    @FXML private Button btnContentBased;
    @FXML private Button btnHybrid;
    @FXML private Button btnConfigSetUsername;
    @FXML private Button btnConfigSetPassword;
    @FXML private Button btnSaveAll;

    @FXML private Label labelConfigSetUsername;
    @FXML private Label labelConfigSetPassword;
    @FXML private Label labelQuality;
    @FXML private Label labelItems;
    @FXML private Label tabSubtitle;
    @FXML private TextField tfNewUsername;
    @FXML private PasswordField pfNewPwd1;
    @FXML private PasswordField pfNewPwd2;
    @FXML private ImageView icon;
    @FXML private ImageView btnLogoutImg;
    @FXML private HBox top;
    @FXML private HBox topIcon;
    @FXML private VBox vBoxCenter;
    @FXML private VBox vBoxRight;
    @FXML private VBox vBoxLeft;
    @FXML private HBox hboxRecommendations;
    @FXML private HBox hboxItems;
    @FXML private HBox hboxData;
    @FXML private ToggleSwitch switchMode;
    @FXML private AnchorPane main;
    @FXML private AnchorPane slider;

    @FXML private GridPane itemContainer;
    @FXML private ScrollPane scrollpaneRecommended;

    /**
     * Method called when class is initialized.
     * Initialized the data structures and collects the necessary data to update the
     * UI according to the user preferences.
     * Implements multiple listeners.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrlPresentation = CtrlPresentation.getInstance();
        imported = ctrlPresentation.getImported();
        recommendedItems = new ArrayList<>();
        hasRecommendation = false;
        changed = false;
        getData();
        userData = ctrlPresentation.getUser();
        actualUser = userData.get(1);
        dbConfig = ctrlPresentation.getDBConfig();
        labelItems.setText("Rate "+dbConfig.get("type").first);
        tabSubtitle.setText("Recommended " + dbConfig.get("type").first+ " for you");
        if (userData.get(4).equals("dark")) {
            switchMode.setSelected(true);
            updateTheme("dark");
        }
        if (userData.get(3).equals("true")) {
            showAdministrativeView();
        }

        slider.setTranslateX(+224);
        main.setOnMouseClicked(mouseEvent -> {
            if (!mouseEvent.getTarget().equals(slider)) {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(.2));
                slide.setNode(slider);

                slide.setToX(+224);
                slide.play();
                labelConfigSetUsername.setVisible(false);
                labelConfigSetPassword.setVisible(false);
                slider.setTranslateX(0);
                slide.setOnFinished((ActionEvent e) -> {
                    btnSettings.setVisible(true);
                    btnSettingsClose.setVisible(false);
                });
            }
        });

        hboxItems.setOnMouseClicked(mouseEvent -> {
            if (imported) {
                ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
                try {
                    ctrlPresentation.changeStage("/presentation/viewItems.fxml");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showError("Items must be imported first!");
            }

        });

        hboxData.setOnMouseClicked(mouseEvent -> {
            ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
            try {
                ctrlPresentation.changeStage("/presentation/viewImport.fxml");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnSettings.setOnMouseClicked(mouseEvent -> {
            slider.setVisible(true);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(.2));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-224);
            slide.setOnFinished((ActionEvent e)-> {
                btnSettings.setVisible(false);
                btnSettingsClose.setVisible(true);
            });
        });
        btnSettingsClose.setOnMouseClicked(mouseEvent -> {
            slider.setVisible(true);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(.2));
            slide.setNode(slider);

            slide.setToX(+224);
            slide.play();
            labelConfigSetUsername.setVisible(false);
            labelConfigSetPassword.setVisible(false);

            slider.setTranslateX(0);
            slide.setOnFinished((ActionEvent e)-> {
                btnSettings.setVisible(true);
                btnSettingsClose.setVisible(false);
            });
        });
    }

    /**
     * Shows the import data button if the user is administrator of the system
     */
    private void showAdministrativeView() {
        hboxData.setVisible(true);
    }

    /**
     * Collects the data from the domainController.
     */
    private void getData() {
        itemsMap = ctrlPresentation.getItems();
        userData = ctrlPresentation.getUser();
    }

    /**
     * Updates the UI according to the selected application theme.
     * @param theme
     */
    private void updateTheme(String theme) {
        if (theme.equals("dark")) {
            btnLogout.setTextFill(Color.WHITE);
            btnConfigSetUsername.setTextFill(Color.WHITE);
            btnConfigSetPassword.setTextFill(Color.WHITE);
            btnSaveAll.setTextFill(Color.WHITE);
            icon.setImage(new Image(getClass().getResourceAsStream("/resources/recommenderLogo_light.png")));
            btnLogoutImg.setImage(new Image(getClass().getResourceAsStream("/resources/back.png")));
        } else {
            btnLogout.setTextFill(Color.BLACK);
            btnConfigSetUsername.setTextFill(Color.BLACK);
            btnConfigSetPassword.setTextFill(Color.BLACK);
            btnSaveAll.setTextFill(Color.BLACK);
            icon.setImage(new Image(getClass().getResourceAsStream("/resources/recommenderLogo_dark.png")));
            btnLogoutImg.setImage(new Image(getClass().getResourceAsStream("/resources/back_black.png")));
        }
        top.getStyleClass().setAll("leftright-"+ theme, "aux-label-"+theme);
        topIcon.getStyleClass().setAll("leftright-"+ theme);
        hboxRecommendations.getStyleClass().setAll("pages-"+ theme);
        hboxItems.getStyleClass().setAll("pages-"+ theme);
        hboxData.getStyleClass().setAll("pages-"+ theme);
        scrollpaneRecommended.getStyleClass().setAll("aux-label-light", "center-"+theme);
        vBoxCenter.getStyleClass().setAll("center-"+theme, "aux-label-"+theme);
        vBoxLeft.getStyleClass().setAll("leftright-"+theme);
        vBoxRight.getStyleClass().setAll("center-"+theme);
        slider.getStyleClass().setAll("leftright-"+theme, "config-"+theme);
        btnSettings.getStyleClass().setAll("shadow", "btn-settings-"+theme);
        btnSettingsClose.getStyleClass().setAll("shadow", "btn-settings-"+theme);
        btnCollaborative.getStyleClass().setAll("shadow", "buttonAlgorithm-"+ theme, "buttonCollaborativeHover");
        btnCollaborative.setAlignment(Pos.CENTER);
        btnContentBased.getStyleClass().setAll("shadow", "buttonAlgorithm-"+ theme, "buttonContentBasedHover");
        btnContentBased.setAlignment(Pos.CENTER);
        btnHybrid.getStyleClass().setAll("shadow", "buttonAlgorithm-"+ theme, "buttonHybridHover");
        btnHybrid.setAlignment(Pos.CENTER);
        btnConfigSetUsername.getStyleClass().setAll("button-config", "button-save-"+theme);
        btnConfigSetUsername.setAlignment(Pos.CENTER);
        btnConfigSetPassword.getStyleClass().setAll("button-config", "button-save-"+theme);
        btnConfigSetPassword.setAlignment(Pos.CENTER);
        btnSaveAll.getStyleClass().setAll("shadow", "button-config", "button-save2-"+theme);

        if (changed) {
            userData.set(4, theme);
            ctrlPresentation.changeTheme(theme);
            changed = false;
        }
    }

    /**
     * Called when the switch element of the UI is fired.
     * Calls the function to update the UI.
     */
    public void switchMode() {
        changed = true;
        if (!switchMode.isSelected()) {
            updateTheme("dark");
        } else {
            updateTheme("light");
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

    /**
     * The recommendation button has been clicked. Checks the source of the event and asks the domain
     * controller for the selected algorithm.
     * @param actionEvent
     */
    public void recommendationClicked(javafx.event.ActionEvent actionEvent) {
        if (hasRecommendation) {
            itemContainer.getChildren().clear();
        }
        TreeMap<Double, String> predictedRecommendation;
        if (actionEvent.getSource().equals(btnCollaborative)) {
            predictedRecommendation = ctrlPresentation.getRecommendation("collaborative");
            if (predictedRecommendation.size() == 0) showError("First you must rate some items!");
        } else if (actionEvent.getSource().equals(btnContentBased)) {
            predictedRecommendation = ctrlPresentation.getRecommendation("contentBased");
            if (predictedRecommendation.size() == 0) showError("First you must rate some items!");
        } else {
            predictedRecommendation = ctrlPresentation.getRecommendation("hybrid");
            if (predictedRecommendation.size() == 0) showError("First you must rate some items!");
        }
        recommendedItems = new ArrayList<>();
        hasRecommendation = true;
        for (String id : predictedRecommendation.values()) {
            recommendedItems.add(itemsMap.get(id));
        }
        int col = 0;
        int row = 1;
        for (Item item : recommendedItems) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("item.fxml"));
            try {
                VBox itemCard = loader.load();
                ItemController itemController = loader.getController();
                itemController.setData(item, dbConfig);
                if (col == 3) {
                    col = 0;
                    ++row;
                }
                itemContainer.add(itemCard,col++, row);
                GridPane.setMargin(itemCard, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The quality button has been clicked. Asks the domainController to calculate the quality
     * of the predicted recommendation. ONLY works for those users of known and unknown csv files
     */
    public void btnQualityClicked() {
        if (hasRecommendation) {
            Double quality = ctrlPresentation.calculateQuality();
            labelQuality.setText(quality.toString());
        } else showError("You must ask for a recomendation first!");
    }

    /**
     * The logOut button has been clicked. The user is redirected to the logIn page.
     * @param actionEvent
     * @throws IOException
     */
    public void btnLogoutClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        ctrlPresentation.logOut();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        ctrlPresentation.changeStage("/presentation/viewLogIn.fxml");
    }

    /**
     * The delete button has been clicked.
     * A confirmation is asked and if the user still wants to go on, the system proceeds
     * by logging out the user and erasing all its data.
     * @param actionEvent
     * @throws IOException
     */
    public void btnDeleteAccountClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you really want to delete your account?");
        alert.setContentText("This will erase ALL your data");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ctrlPresentation.deleteUser();
            ctrlPresentation.logOut();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            ctrlPresentation.changeStage("/presentation/viewLogIn.fxml");
        } else {
            alert.close();
        }
    }

    /**
     * The set username button has been clicked. The system updates the username of the user.
     */
    public void btnConfigSetUsernameClicked() {
        String newUsername = tfNewUsername.getText();
        if (newUsername != actualUser) {
            if (newUsername.length() != 0) {
                boolean res = ctrlPresentation.changeUsername(newUsername, actualUser);
                if (!res) {
                    showError("Username already used");
                }
                else {
                    labelConfigSetUsername.setVisible(true);
                }
            } else {
                showError("Username must be at least 3 characters long");
            }
        } else {
            showError("You already have this username");
        }
    }

    /**
     * The set password button has been clicked. The system updates the password of the user.
     */
    public void btnConfigSetPasswordClicked() {
        String newPwd1 = pfNewPwd1.getText();
        String newPwd2 = pfNewPwd2.getText();
        String actualPwd = userData.get(2);
        if (newPwd1.length() >= 4 && newPwd2.length() >= 4) {
            if (newPwd1.equals(newPwd2)) {
                if (!actualPwd.equals(newPwd1)) {
                    ctrlPresentation.changePassword(newPwd1, actualUser);
                    labelConfigSetPassword.setVisible(true);
                } else {
                    showError("You already have this password");
                }
            } else {
                showError("Passwords are different");
            }
        } else {
            showError("Password must be at least 4 characters long");
        }
    }

    /**
     * The system asks the domain controller to save all changes done.
     */
    public void btnSaveAllClicked() {
        ctrlPresentation.saveAll();
    }
}
