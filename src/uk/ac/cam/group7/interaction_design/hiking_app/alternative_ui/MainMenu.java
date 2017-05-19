package uk.ac.cam.group7.interaction_design.hiking_app.alternative_ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastContainer;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastFormatting;
import uk.ac.cam.group7.interaction_design.hiking_app.Location;

import java.util.LinkedList;
import java.util.List;

public class MainMenu extends Application {

    private Stage window;

    private static ForecastContainer forecasts = ForecastContainer.getReference();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Weather App");

        drawScreen(generateMainMenu());
    }

    protected void returnHome() {
        drawScreen(generateMainMenu());
    }

    protected void drawScreen(Scene scene) {
        window.setScene(scene);
        window.setHeight(900);
        window.setWidth(700);
        window.show();
    }

    private Scene generateMainMenu() {
        VBox listContainer = new VBox();

        HBox searchBar = new HBox(8);
        TextField latitude = new TextField();
        latitude.setPromptText("Latitude");
        TextField longitude = new TextField();
        longitude.setPromptText("Longitude");
        Button confirm = new Button("Search");

        searchBar.getChildren().addAll(latitude, longitude, confirm);
        searchBar.setHgrow(latitude, Priority.ALWAYS);
        searchBar.setHgrow(longitude, Priority.ALWAYS);
        searchBar.setHgrow(confirm, Priority.ALWAYS);

        Label invalidCoordinates = new Label();
        invalidCoordinates.setVisible(false);

        GridPane locationList = generateLocationList();
        ScrollPane scroll = new ScrollPane(locationList);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        listContainer.getChildren().addAll(searchBar, invalidCoordinates, scroll);
        listContainer.setAlignment(Pos.TOP_CENTER);

        confirm.setOnAction(event -> searchForLocation(latitude, longitude, invalidCoordinates));

        return new Scene(listContainer);
    }

    private void searchForLocation(TextField latitude, TextField longitude, Label errorOutput) {
        if (latitude.getLength() == 0) {
            errorOutput.setText("Please enter a latitude");
            errorOutput.setVisible(true);
            return;
        }
        if (longitude.getLength() == 0){
            errorOutput.setText("Please enter a longitude");
            errorOutput.setVisible(true);
            return;
        }
        float lat;
        float lon;
        try {
            lat = Float.parseFloat(latitude.getText());
        } catch (NumberFormatException e) {
            errorOutput.setText("Please enter a numerical value for latitude");
            errorOutput.setVisible(true);
            return;
        }
        try{
            lon = Float.parseFloat(longitude.getText());
        } catch (NumberFormatException e) {
            errorOutput.setText("Please enter a numerical value for longitude");
            errorOutput.setVisible(true);
            return;
        }
        if (Math.abs(lat) <= 90 && Math.abs(lon) <= 180) {
            errorOutput.setVisible(false);
            Location location = new Location(lat, lon);
            forecasts.addNewLocation(location);
            drawScreen(generateMainMenu());
        } else {
            if (Math.abs(lat) > 90) {
                errorOutput.setText("Latitude must be within -90 to 90");
            } else {
                errorOutput.setText("Longitude must be within -180 to 180");
            }
            errorOutput.setVisible(true);
        }
    }

    private GridPane generateLocationList() {
        GridPane display = new GridPane();
        int row = 0;
        List<Location> allLocations = new LinkedList<>(forecasts.getFavourites());
        allLocations.addAll(forecasts.getRecent());
        for (Location location : allLocations) {
            Button options = new Button("!");
            options.setOnAction(event -> makeOptionsMenu(location));
            display.add(options, 0, row);
            Button name = new Button(location.getName());
            name.setOnAction(event -> makeForecastDisplay(location));
            display.add(name, 1, row);
            Label temperature = new Label(Integer.toString(ForecastFormatting.normaliseTemperature(
                    forecasts.getForecast(location).get(0).getTemp())));
            temperature.setTextAlignment(TextAlignment.RIGHT);
            display.setConstraints(temperature, 2, row, 1, 1, HPos.RIGHT, VPos.CENTER);
            display.add(temperature, 2, row);
            Label weather = new Label(forecasts.getForecast(location).get(0).getWeatherConditions().get(0)
                    .getDescription());
            weather.setTextAlignment(TextAlignment.RIGHT);
            display.setConstraints(weather, 3, row, 1, 1, HPos.RIGHT, VPos.CENTER);
            display.add(weather, 3, row);
            row++;
        }
        ColumnConstraints optionsColumn = new ColumnConstraints(25,25,25);
        optionsColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints nameColumn = new ColumnConstraints(20,600,700);
        nameColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints temperatureColumn = new ColumnConstraints(10,30,500);
        temperatureColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints conditionsColumn = new ColumnConstraints(10,50,500);
        conditionsColumn.setHgrow(Priority.ALWAYS);
        display.getColumnConstraints().addAll(optionsColumn, nameColumn, temperatureColumn, conditionsColumn);
        return display;
    }

    private void makeOptionsMenu(Location location) {
        OptionsMenu menu = new OptionsMenu(location, this);
        drawScreen(menu.generateOptionsMenu());
    }

    private void makeForecastDisplay(Location location) {
        ForecastDisplay display = new ForecastDisplay(location, this);
        drawScreen(display.getWeatherDisplay());
    }

}
