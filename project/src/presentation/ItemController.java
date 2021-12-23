package presentation;

import domain.classes.Item;
import utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * @author Miguel Garcia Soler
 */

public class ItemController {
    @FXML private ImageView itemImage;
    @FXML private Label itemName;
    @FXML private ImageView itemRating;

    public void setData(Item item, HashMap<String, Pair<String, Integer>> dbConfig) {
        double average = item.getStats().getAverageRating();
        if (average == 5) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/5stars.png")));
        }
        else if (average >= 4.5) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/4,5stars.png")));
        }
        else if (average >= 4) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/4stars.png")));
        }
        else if (average >= 3.5) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/3,5stars.png")));
        }
        else if (average >= 3) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/3stars.png")));
        }
        else if (average >= 2.5) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/2,5stars.png")));
        }
        else if (average >= 2) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/2,5stars.png")));
        }
        else if (average >= 1.5) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/1,5stars.png")));
        }
        else if (average >= 1) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/1stars.png")));
        }
        else if (average >= 0.5) {
            itemRating.setImage(new Image(getClass().getResourceAsStream("/resources/0,5stars.png")));
        }
        itemName.setText(item.getValues().get(dbConfig.get("name").second).getValue());
        itemImage.setImage(new Image(item.getValues().get(dbConfig.get("image").second).getValue()));
    }
}
