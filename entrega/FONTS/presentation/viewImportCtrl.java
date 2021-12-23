package presentation;

import domain.classes.Item;
import utils.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Ricard Guixaró Trancho
 */

public class viewImportCtrl implements Initializable {
    private CtrlPresentation ctrlPresentation;
    private boolean imported;
    private ArrayList<String> itemAttributes;
    private HashMap<String, Pair<String,Integer>> config;

    @FXML private ComboBox comboBoxName;
    @FXML private TextField tfItemType;

    /**
     * Method called when class is initialized
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        config = new HashMap<>();
        ctrlPresentation = CtrlPresentation.getInstance();
        imported = ctrlPresentation.getImported();

    }

    /**
     * The import button has been clicked. The function checks if the selected directory contains all necessary files.
     * If not, popup's an error.
     */
    public void btnImportClicked() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (ctrlPresentation.jsonExists()) {
            ctrlPresentation.deleteData();
        }
        try {
            String path = selectedDirectory.getPath();
            ctrlPresentation.importData(path);
            imported = true;
            itemAttributes = ctrlPresentation.getAttributes();
            HashMap<String, Item> itemsMap = ctrlPresentation.getItems();
            String imageAttribute = findImageAttribute(itemsMap);
            config.put("image", new Pair<>(imageAttribute, itemAttributes.indexOf(imageAttribute)));
            config.put("path", new Pair<>(path, -1));
            initializeComboBoxes();
        } catch (Exception e) {
            showError("Invalid database");
        }
    }

    /**
     * The back button has been clicked. If the database has not been imported, an error appears. Else returns to the previous stage.
     * @param actionEvent
     */
    public void btnBackClicked(javafx.event.ActionEvent actionEvent) {
        if (!imported) {
            showError("No database has been imported");
        } else {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            try {
                ctrlPresentation.changeStage("/presentation/viewMainMenu.fxml");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The done button has been clicked, and certain parameters have to be checked.
     * @param actionEvent
     */
    public void btnDoneClicked(javafx.event.ActionEvent actionEvent) {
        if (imported) {
            String itemType = tfItemType.getText();
            String name = null;
            try {
                name = comboBoxName.getSelectionModel().getSelectedItem().toString();
            } catch (Exception e) {
                showError("The name of the items has not been set!");
            }
            if (itemType != null && itemType.length() != 0) {
                config.put("type", new Pair<>(itemType, -1));
                if (name != null && name.length() != 0) {
                    config.put("name", new Pair<>(name, itemAttributes.indexOf(name)));
                    ctrlPresentation.setDBConfig(config);
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                    try {
                        ctrlPresentation.changeStage("/presentation/viewMainMenu.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showError("The name of the items has not been set!");
                }
            } else {
                showError("The type of the items has not been set!");
            }
        } else {
            showError("Items must be imported first!");
        }
    }

    /**
     * After the database has been loaded, this function tries to find an attribute that contains the images of the item
     * @param itemsMap
     * @return
     */
    private String findImageAttribute(HashMap<String, Item> itemsMap) {
        if (itemsMap != null && itemsMap.size() != 0) {
            for (Map.Entry<String, Item> item : itemsMap.entrySet()) {
                for (String attrb : itemAttributes) {
                    String value = item.getValue().getValues().get(itemAttributes.indexOf(attrb)).getValue();
                    if (value.contains(".png") || value.contains(".jpg") || value.contains(".jpe") || value.contains(".bmp")) {
                        return attrb;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Initializes the attributes comboBox with the extracted attributes from the database.
     */
    private void initializeComboBoxes() {
        ObservableList<String> attrb = FXCollections.observableArrayList(itemAttributes);
        comboBoxName.setItems(attrb);

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
