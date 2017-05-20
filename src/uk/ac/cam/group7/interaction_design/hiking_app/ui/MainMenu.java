package uk.ac.cam.group7.interaction_design.hiking_app.ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uk.ac.cam.group7.interaction_design.hiking_app.backend.ForecastContainer;
import uk.ac.cam.group7.interaction_design.hiking_app.backend.ForecastFormatting;
import uk.ac.cam.group7.interaction_design.hiking_app.backend.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Main menu class for the 'app'
 * Handles building and displaying the window and then filling it with the main menu
 *
 * @author Sam Gooch
 */
public class MainMenu extends Application {

    private static ForecastContainer forecasts = ForecastContainer.getReference();
    private Stage window;

    /**
     * Main function for the 'app'; starts everything
     *
     * @param args Arguments passed from command line
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Default starting stage for the window
     *
     * @param primaryStage Reference to the primary stage
     * @throws Exception Terrible practice but has to inherit this from Application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Weather App");

        drawScreen(generateMainMenu());
    }

    /**
     * Allows other scenes to easily return to the home menu
     */
    void returnHome() {
        drawScreen(generateMainMenu());
    }

    /**
     * Draws a scene to the main window
     *
     * @param scene Scene to draw
     */
    void drawScreen(Scene scene) {
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.setHeight(900);
        window.setWidth(700);
        window.show();
    }

    /**
     * Generates the main menu scene
     *
     * @return Main menu scene
     */
    private Scene generateMainMenu() {
        VBox listContainer = new VBox();

        HBox searchBar = new HBox(8);
        TextField latitude = new TextField();
        latitude.setPromptText("Latitude");
        TextField longitude = new TextField();
        longitude.setPromptText("Longitude");
        Button confirm = new Button("Go");
        latitude.setPrefWidth(300);
        longitude.setPrefWidth(300);
        confirm.setPrefWidth(100);

        searchBar.getChildren().addAll(latitude, longitude, confirm);
        HBox.setHgrow(latitude, Priority.ALWAYS);
        HBox.setHgrow(longitude, Priority.ALWAYS);
        HBox.setHgrow(confirm, Priority.ALWAYS);

        Label invalidCoordinates = new Label();
        invalidCoordinates.setStyle("-fx-font-size: 3em;");
        invalidCoordinates.setVisible(false);

        VBox locations = new VBox();
        if (forecasts.getFavourites().size() > 0) {
            Label favourites = new Label("Favourites");
            favourites.getStyleClass().set(0, "label-small");
            GridPane favouriteLocations = generateLocationList(new LinkedList<>(forecasts.getFavourites()));
            locations.getChildren().addAll(favourites, favouriteLocations);
        }
        if (forecasts.getRecent().size() > 0) {
            Label recent = new Label("Recent Locations");
            recent.getStyleClass().set(0, "label-small");
            GridPane recentLocations = generateLocationList(new LinkedList<>(forecasts.getRecent()));
            locations.getChildren().addAll(recent, recentLocations);
        }
        if (forecasts.getFavourites().size() == 0 && forecasts.getRecent().size() == 0) {
            VBox instructionContainer = new VBox();
            Label startInstructions1 = new Label();
            startInstructions1.setText("You don't have any saved locations yet!");
            startInstructions1.getStyleClass().set(0, "label-small");
            startInstructions1.setWrapText(true);
            Label startInstructions2 = new Label();
            startInstructions2.setText("Enter some co-ordinates to get started!");
            startInstructions2.getStyleClass().set(0, "label-small");
            startInstructions2.setWrapText(true);
            instructionContainer.getChildren().addAll(startInstructions1, startInstructions2);
            instructionContainer.setAlignment(Pos.CENTER);
            listContainer.getChildren().addAll(searchBar, invalidCoordinates, instructionContainer);
        } else {
            ScrollPane scroll = new ScrollPane(locations);
            scroll.setFitToHeight(true);
            scroll.setFitToWidth(true);

            listContainer.getChildren().addAll(searchBar, invalidCoordinates, scroll);
        }
        listContainer.setAlignment(Pos.TOP_CENTER);

        confirm.setOnAction(event -> searchForLocation(latitude, longitude, invalidCoordinates));

        return new Scene(listContainer);
    }

    /**
     * Issues a request to get weather data for a location
     *
     * @param latitude    TextField for latitude of the location
     * @param longitude   TextField for longitude of the location
     * @param errorOutput Label to update if co-ordinates are not valid
     */
    private void searchForLocation(TextField latitude, TextField longitude, Label errorOutput) {
        if (latitude.getLength() == 0) {
            errorOutput.setText("Please enter a latitude");
            errorOutput.setVisible(true);
            return;
        }
        if (longitude.getLength() == 0) {
            errorOutput.setText("Please enter a longitude");
            errorOutput.setVisible(true);
            return;
        }
        float lat;
        float lon;
        try {
            lat = Float.parseFloat(latitude.getText());
        } catch (NumberFormatException e) {
            errorOutput.setText("Please enter latitude using digits");
            errorOutput.setVisible(true);
            return;
        }
        try {
            lon = Float.parseFloat(longitude.getText());
        } catch (NumberFormatException e) {
            errorOutput.setText("Please enter longitude using digits");
            errorOutput.setVisible(true);
            return;
        }
        if (Math.abs(lat) <= 90 && Math.abs(lon) <= 180) {
            errorOutput.setVisible(false);
            Location location = new Location(lat, lon);
            forecasts.addNewLocation(location);
            makeForecastDisplay(location);
        } else {
            if (Math.abs(lat) > 90) {
                errorOutput.setText("Latitude must be within -90 to 90");
            } else {
                errorOutput.setText("Longitude must be within -180 to 180");
            }
            errorOutput.setVisible(true);
        }
    }

    /**
     * Generates list of locations
     *
     * @param locationList List of locations to build
     * @return Formatted list of locations
     */
    private GridPane generateLocationList(List<Location> locationList) {
        GridPane display = new GridPane();
        int row = 0;
        for (Location location : locationList) {
            Button options = new Button();
            options.getStyleClass().add("options");
            options.setPrefWidth(65);
            options.setOnAction(event -> makeOptionsMenu(location));
            display.add(options, 0, row);
            Button name = new Button(location.getName());
            name.setOnAction(event -> makeForecastDisplay(location));
            display.add(name, 1, row);
            if (forecasts.getForecast(location) == null) {
                row++;
                continue;
            }
            if (!(location.getTopWarning() == null)) {
                ImageView warningIcon = WeatherIconMaker.getWarningIcon(location.getWarningIconCode());
                warningIcon.setFitWidth(50);
                warningIcon.setFitHeight(50);
                GridPane.setConstraints(warningIcon, 2, row, 1, 1, HPos.RIGHT, VPos.CENTER);
                display.add(warningIcon, 2, row);
            }
            Label temperature = new Label(ForecastFormatting.normaliseTemperature(
                    forecasts.getForecast(location).get(0).getTemp()) + "\u00b0" + "C");
            temperature.setTextAlignment(TextAlignment.RIGHT);
            GridPane.setConstraints(temperature, 3, row, 1, 1, HPos.RIGHT, VPos.CENTER);
            display.add(temperature, 3, row);
            ImageView type = WeatherIconMaker.getIconImage(
                    forecasts.getForecast(location).get(0).getWeatherConditions(),
                    forecasts.getForecast(location).get(0).getDateTime());
            GridPane.setConstraints(type, 4, row, 1, 1, HPos.RIGHT, VPos.CENTER);
            display.add(type, 4, row);
            row++;
        }
        ColumnConstraints optionsColumn = new ColumnConstraints(65, 65, 65);
        optionsColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints nameColumn = new ColumnConstraints(20, 600, 700);
        nameColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints warningColumn = new ColumnConstraints(65, 65, 65);
        warningColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints temperatureColumn = new ColumnConstraints(120, 120, 120);
        temperatureColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints conditionsColumn = new ColumnConstraints(150, 150, 150);
        conditionsColumn.setHgrow(Priority.ALWAYS);
        display.getColumnConstraints().addAll(optionsColumn, nameColumn, warningColumn, temperatureColumn, conditionsColumn);
        return display;
    }

    /**
     * Calls for an option menu to be generated and displayed
     *
     * @param location Location to generate options menu for
     */
    private void makeOptionsMenu(Location location) {
        OptionsMenu menu = new OptionsMenu(location, this);
        drawScreen(menu.generateOptionsMenu());
    }

    /**
     * Calls for the forecast screen to be generated and displayed
     *
     * @param location Location to generate forecast screen for
     */
    private void makeForecastDisplay(Location location) {
        ForecastDisplay display = new ForecastDisplay(location, this);
        drawScreen(display.getWeatherDisplay());
    }

}
