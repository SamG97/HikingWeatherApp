package uk.ac.cam.group7.interaction_design.hiking_app.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uk.ac.cam.group7.interaction_design.hiking_app.backend.ForecastContainer;
import uk.ac.cam.group7.interaction_design.hiking_app.backend.Location;

/**
 * Handles generating the options menu and passes resulting scene back to MainMenu to be displayed
 *
 * @author Sam Gooch
 */
public class OptionsMenu {

    private ForecastContainer forecasts = ForecastContainer.getReference();
    private Location location;
    private MainMenu main;
    private boolean isFavourite;

    /**
     * Constructor for OptionsMenu
     *
     * @param location Location the menu is for
     * @param main     Reference back to allow the menu to be drawn
     */
    protected OptionsMenu(Location location, MainMenu main) {
        this.location = location;
        this.main = main;
        this.isFavourite = location.isFavourite();
    }

    /**
     * Generates the options menu for the location
     *
     * @return The options menu scene
     */
    Scene generateOptionsMenu() {
        BorderPane border = new BorderPane();

        VBox container = new VBox();

        HBox topBar = new HBox();
        TextField locationName = new TextField();
        locationName.setPromptText("Location Name");
        locationName.setText(location.getName());
        Button home = new Button();
        home.setText("<");
        ToggleButton favourite = new ToggleButton();
        favourite.setSelected(location.isFavourite());
        topBar.getChildren().addAll(locationName, home, favourite);

        HBox.setHgrow(locationName, Priority.ALWAYS);
        locationName.setPrefWidth(500);
        HBox.setHgrow(home, Priority.ALWAYS);
        home.setPrefWidth(100);
        HBox.setHgrow(favourite, Priority.ALWAYS);
        favourite.setPrefWidth(65);

        HBox coordinatesBar = new HBox();
        Label latitude = new Label("Latitude: " + location.getLatitude());
        latitude.setPrefWidth(500);
        latitude.setAlignment(Pos.CENTER);
        latitude.setStyle("-fx-font-size: 3em;");
        Label longitude = new Label("Longitude: " + location.getLongitude());
        longitude.setPrefWidth(500);
        longitude.setAlignment(Pos.CENTER);
        longitude.setStyle("-fx-font-size: 3em;");
        coordinatesBar.getChildren().addAll(latitude, longitude);
        coordinatesBar.setAlignment(Pos.CENTER);

        HBox.setHgrow(latitude, Priority.ALWAYS);
        HBox.setHgrow(longitude, Priority.ALWAYS);

        Button delete = new Button();
        delete.setText("Delete location");
        delete.getStyleClass().add("warning-button");
        delete.setPrefWidth(700);

        VBox deletePrompt = new VBox();

        Label confirmPrompt1 = new Label();
        Label confirmPrompt2 = new Label();
        Label confirmPrompt3 = new Label();
        confirmPrompt1.setText("This will permanently delete");
        confirmPrompt2.setText("this location, are you sure you");
        confirmPrompt3.setText("want to continue?");
        confirmPrompt1.getStyleClass().set(0, "warning-text");
        confirmPrompt2.getStyleClass().set(0, "warning-text");
        confirmPrompt3.getStyleClass().set(0, "warning-text");

        HBox confirmContainer = new HBox();
        Button yes = new Button();
        yes.setText("I'm sure");
        yes.setPrefWidth(500);
        yes.getStyleClass().add("warning-button");
        Button no = new Button();
        no.setText("Don't delete");
        no.setPrefWidth(500);
        no.getStyleClass().add("warning-button");
        confirmContainer.getChildren().addAll(yes, no);

        HBox.setHgrow(yes, Priority.ALWAYS);
        HBox.setHgrow(no, Priority.ALWAYS);

        deletePrompt.getChildren().addAll(confirmPrompt1, confirmPrompt2, confirmPrompt3, confirmContainer);
        deletePrompt.setAlignment(Pos.CENTER);
        deletePrompt.setVisible(false);

        Button undo = new Button();
        undo.setText("Undo Changes");
        undo.setPrefWidth(700);

        container.getChildren().addAll(topBar, coordinatesBar, delete, deletePrompt);

        border.setCenter(container);
        border.setBottom(undo);

        home.setOnAction(event -> returnHome(locationName.getText()));
        favourite.setOnAction(event -> toggleFavourite());
        delete.setOnAction(event -> deletePrompt(deletePrompt));
        yes.setOnAction(event -> deleteLocation());
        no.setOnAction(event -> cancelDelete(deletePrompt));
        undo.setOnAction(event -> undoChanges(locationName, favourite));

        return new Scene(border);
    }

    /**
     * Toggles if a location is a favourite
     */
    private void toggleFavourite() {
        if (location.isFavourite()) {
            forecasts.removeFavourite(location);
        } else {
            forecasts.makeFavourite(location);
        }
    }

    /**
     * Brings up the delete confirmation prompt
     *
     * @param confirmLayout Reference to the confirmation prompt to display
     */
    private void deletePrompt(VBox confirmLayout) {
        confirmLayout.setVisible(true);
    }

    /**
     * Deletes a location
     */
    private void deleteLocation() {
        forecasts.removeLocation(location);
        main.returnHome();
    }

    /**
     * Removes the delete confirmation prompt if the user decides not to delete the location
     *
     * @param confirmLayout Reference to the confirmation prompt to display
     */
    private void cancelDelete(VBox confirmLayout) {
        confirmLayout.setVisible(false);
    }

    /**
     * Returns to the main menu screen and saves changes
     *
     * @param name New name of location to update
     */
    private void returnHome(String name) {
        if (!name.equals(location.getName())) {
            forecasts.renameLocation(location, name);
        }
        main.returnHome();
    }

    /**
     * Reverts changes
     *
     * @param locationName TextField containing location name to reset value of
     * @param favourite    Favourite button
     */
    private void undoChanges(TextField locationName, ToggleButton favourite) {
        locationName.setText(location.getName());
        if (!favourite.isSelected() == isFavourite) {
            toggleFavourite();
            favourite.setSelected(location.isFavourite());
        }
    }

}
