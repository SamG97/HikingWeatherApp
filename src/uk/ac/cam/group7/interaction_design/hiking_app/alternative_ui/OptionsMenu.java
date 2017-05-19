package uk.ac.cam.group7.interaction_design.hiking_app.alternative_ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastContainer;
import uk.ac.cam.group7.interaction_design.hiking_app.Location;

public class OptionsMenu {

    private ForecastContainer forecasts = ForecastContainer.getReference();
    private Location location;
    private MainMenu main;
    private boolean isFavourite;

    protected OptionsMenu(Location location, MainMenu main) {
        this.location = location;
        this.main = main;
        this.isFavourite = location.isFavourite();
    }

    protected Scene generateOptionsMenu() {
        VBox container = new VBox();

        HBox topBar = new HBox();
        TextField locationName = new TextField();
        locationName.setPromptText("Location Name");
        locationName.setText(location.getName());
        Button home = new Button();
        home.setText("<");
        Button favourite = new Button();
        if (location.isFavourite()) {
            favourite.setText("UnF");
        } else {
            favourite.setText("Fav");
        }
        topBar.getChildren().addAll(locationName, home, favourite);

        topBar.setHgrow(locationName, Priority.ALWAYS);
        topBar.setHgrow(home, Priority.ALWAYS);
        topBar.setHgrow(favourite, Priority.ALWAYS);

        HBox coordinatesBar = new HBox();
        Label latitude = new Label("Latitude: " + location.getLatitude());
        latitude.setPrefWidth(500);
        Label longitude = new Label("Longitude: " + location.getLongitude());
        longitude.setPrefWidth(500);
        coordinatesBar.getChildren().addAll(latitude, longitude);

        coordinatesBar.setHgrow(latitude, Priority.ALWAYS);
        coordinatesBar.setHgrow(longitude, Priority.ALWAYS);

        Button delete = new Button();
        delete.setText("Delete location");
        delete.setPrefWidth(700);

        VBox deletePrompt = new VBox();

        Label confirmPrompt = new Label();
        confirmPrompt.setText("This will permanently delete this location, are you sure you want to delete?");

        HBox confirmContainer = new HBox();
        Button yes = new Button();
        yes.setText("I'm sure");
        yes.setPrefWidth(500);
        Button no = new Button();
        no.setText("Don't delete");
        no.setPrefWidth(500);
        confirmContainer.getChildren().addAll(yes, no);

        confirmContainer.setHgrow(yes, Priority.ALWAYS);
        confirmContainer.setHgrow(no, Priority.ALWAYS);

        deletePrompt.getChildren().addAll(confirmPrompt, confirmContainer);
        deletePrompt.setVisible(false);

        Button cancel = new Button();
        cancel.setText("Cancel without saving");
        cancel.setPrefWidth(700);

        container.getChildren().addAll(topBar, coordinatesBar, delete, deletePrompt, cancel);

        home.setOnAction(event -> returnHome(locationName.getText()));
        favourite.setOnAction(event -> toggleFavourite(favourite));
        delete.setOnAction(event -> deletePrompt(deletePrompt));
        yes.setOnAction(event -> deleteLocation());
        no.setOnAction(event -> cancelDelete(deletePrompt));
        cancel.setOnAction(event -> exitNoSave());

        return new Scene(container);
    }

    private void toggleFavourite(Button favourite) {
        if (location.isFavourite()) {
            favourite.setText("Fav");
            forecasts.removeFavourite(location);
        } else {
            favourite.setText("UnF");
            forecasts.makeFavourite(location);
        }
    }

    private void deletePrompt(VBox confirmLayout) {
        confirmLayout.setVisible(true);
    }

    private void deleteLocation() {
        forecasts.removeLocation(location);
        main.returnHome();
    }

    private void cancelDelete(VBox confirmLayout) {
        confirmLayout.setVisible(false);
    }

    private void returnHome(String name) {
        if (!name.equals(location.getName())) {
            forecasts.renameLocation(location, name);
        }
        main.returnHome();
    }

    private void exitNoSave() {
        if (!isFavourite == location.isFavourite()) {
            if (isFavourite) {
                forecasts.makeFavourite(location);
            } else {
                forecasts.removeFavourite(location);
            }
        }
        main.returnHome();
    }

}
