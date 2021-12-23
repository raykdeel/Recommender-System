package presentation;

import domain.classes.Item;
import utils.Pair;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Ricard Guixaró Trancho
 */

public class viewItemsCtrl implements Initializable {
    private CtrlPresentation ctrlPresentation;
    private HashMap<String, Item> itemsMap;
    private String selectedItemId;
    private HashMap<String, Pair<String, Integer>> dbConfig;

    @FXML private Button btnBack;
    @FXML private Label selectedItemName;
    @FXML private Label selectedItemAverage;
    @FXML private Label selectedItemTotal;
    @FXML private Label tabTitle;
    @FXML private TextField tfSearch;
    @FXML private ImageView selectedItemImage;
    @FXML private ImageView icon;
    @FXML private ImageView btnBackImg;
    @FXML private ImageView searchImg;
    @FXML private Rating selectedRating;
    @FXML private ListView<CustomCell> llista;
    @FXML private VBox selectedItemCard;
    @FXML private VBox vBoxLeft;
    @FXML private HBox top;
    @FXML private HBox topIcon;
    @FXML private HBox boxSearch;
    @FXML private VBox vBoxCenter;

    /**
     * Method called when class is initialized
     * Sets the user interface according to the user preferences and the database configuration
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrlPresentation = CtrlPresentation.getInstance();
        ArrayList<String> userData = ctrlPresentation.getUser();
        dbConfig = ctrlPresentation.getDBConfig();
        tabTitle.setText(dbConfig.get("type").first);
        if (userData.get(4).equals("dark")) darkTheme();
        itemsMap = ctrlPresentation.getItems();
        showListView("");

        llista.setOnMouseClicked(mouseEvent -> {
            CustomCell selectedItem = llista.getSelectionModel().getSelectedItem();
            setSelectedItemData(selectedItem);
        });

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {showListView(newValue);});
    }

    /**
     * Applies the dark theme of the application to some elements of the interface
     */
    private void darkTheme() {
        vBoxLeft.getStyleClass().setAll("leftright-dark");
        top.getStyleClass().setAll("leftright-dark", "aux-label-dark");
        topIcon.getStyleClass().setAll("leftright-dark");
        boxSearch.getStyleClass().setAll("button-box-dark","shadow", "aux-label-dark");
        vBoxCenter.getStyleClass().setAll("center-dark", "aux-label-dark");
        searchImg.setImage(new Image(getClass().getResourceAsStream("/resources/search.png")));
        icon.setImage(new Image(getClass().getResourceAsStream("/resources/recommenderLogo_light.png")));
        btnBackImg.setImage(new Image(getClass().getResourceAsStream("/resources/back.png")));
        btnBack.setTextFill(Color.WHITE);
        llista.getStyleClass().setAll("listview");

    }

    /**
     * The rate button has been clicked
     */
    public void btnRateClicked() {
        double rate = 2*selectedRating.getRating();
        String itemId = selectedItemId;
        ArrayList<String> rating = new ArrayList<>();
        rating.add(itemId);
        rating.add(String.valueOf(rate));
        ctrlPresentation.createRating(rating);
        showStats(itemId);
    }

    /**
     * Shows the statistics for the given item
     * @param itemId
     */
    private void showStats(String itemId) {
        ArrayList<String> stats = itemsMap.get(itemId).getStats().discretize();
        String temp1 = stats.get(0);
        String temp2 = stats.get(1);
        if (temp1.equals("0")) temp1 = "-";
        else {
            BigDecimal decimal = new BigDecimal(temp1);
            decimal = decimal.setScale(2, RoundingMode.HALF_UP);
            temp1 = decimal.toString();
        }
        selectedItemAverage.setText("Average rating " + temp1);
        selectedItemTotal.setText("Total ratings " + temp2);
    }

    /**
     * Updates the list according to the search
     * @param value
     */
    private void showListView(String value) {
        ArrayList<CustomCell> temp = new ArrayList<>();
        ArrayList<String> itemAttributes = ctrlPresentation.getAttributes();
        temp.add(new CustomCell("", itemAttributes));
        for (Item item : itemsMap.values()) {
            String id = item.getItemId();
            String name = item.getValues().get(dbConfig.get("name").second).getValue();
            if (value.length() == 0 || id.startsWith(value) || name.toLowerCase().startsWith(value.toLowerCase())) {
                temp.add(new CustomCell(id, item.discretize()));
            }
        }
        llista.setItems(FXCollections.observableList(temp));
    }

    /**
     * Sets the data for the selected item from the list
     * @param selectedItem
     */
    private void setSelectedItemData(CustomCell selectedItem) {
        selectedItemId = selectedItem.id;
        showStats(selectedItemId);
        selectedItemName.setText(selectedItem.values.get(dbConfig.get("name").second));
        if (dbConfig.get("image") != null) {
            selectedItemImage.setImage(new Image(selectedItem.values.get(dbConfig.get("image").second)));
        } else {
            selectedItemImage.setImage(new Image(getClass().getResource("/resources/noImage.png").toExternalForm()));
        }
        selectedItemCard.setVisible(true);
    }

    /**
     * The back button has been cliked
     * @param actionEvent
     */
    public void btnBackClicked(javafx.event.ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        try {
            ctrlPresentation.changeStage("/presentation/viewMainMenu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CustomCell extends HBox {
        private GridPane grid = new GridPane();
        private String id;
        private ArrayList<String> values;

        CustomCell(String id, ArrayList<String> values) {
            super();
            this.id = id;
            this.values = values;
            grid.setHgap(30);
            grid.setVgap(10);
            int idx = 0;
            Label label;
            if (id.length() == 0) { //header id
                label = new Label("id");
                label.setMaxWidth(150);
                label.setMinWidth(150);
                label.setAlignment(Pos.CENTER_LEFT);
                grid.add(label, idx, 0);
                ++idx;
            }
            for (String value : values) {
                if (!value.equals("id")) {
                    label = new Label(value);
                    label.setMaxWidth(150);
                    label.setMinWidth(150);
                    label.setAlignment(Pos.CENTER_LEFT);
                    grid.add(label, idx, 0);
                    ++idx;
                }
            }
            this.getChildren().add(grid);
        }
    }
}
