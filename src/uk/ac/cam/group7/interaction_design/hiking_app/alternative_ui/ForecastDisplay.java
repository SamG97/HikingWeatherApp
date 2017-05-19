package uk.ac.cam.group7.interaction_design.hiking_app.alternative_ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.bitpipeline.lib.owm.StatusWeatherData;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastContainer;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastFormatting;
import uk.ac.cam.group7.interaction_design.hiking_app.Location;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Handles generating the forecast display and passes resulting scene back to MainMenu to be displayed
 *
 * @author Sam Gooch
 */
public class ForecastDisplay {

    private static ForecastContainer forecasts = ForecastContainer.getReference();

    private MainMenu main;
    private Location location;
    private Map<DayOfWeek, List<StatusWeatherData>> dailyForecasts;
    private DayOfWeek currentDay;
    private List<StatusWeatherData> currentForecastToDisplay;

    /**
     * Constructor for ForecastDisplay
     *
     * @param location The location to display the forecast for
     * @param main     Reference back to allow the menu to be drawn
     */
    protected ForecastDisplay(Location location, MainMenu main) {
        this.location = location;
        this.dailyForecasts = ForecastFormatting.getDailyForecasts(forecasts.getForecast(location));
        this.main = main;
        if (dailyForecasts == null) {
            currentDay = null;
            currentForecastToDisplay = null;
        } else {
            currentDay = ForecastFormatting.getToady();
            currentForecastToDisplay = dailyForecasts.get(currentDay);
            if (currentForecastToDisplay == null) {
                int tomorrow = currentDay.getValue() + 1;
                if (tomorrow > 8) {
                    tomorrow -= 7;
                }
                currentDay = DayOfWeek.of(tomorrow);
                currentForecastToDisplay = dailyForecasts.get(currentDay);
            }
        }
    }

    /**
     * Generates the forecast display screen for the location
     *
     * @return The forecast display screen
     */
    protected Scene getWeatherDisplay() {
        VBox display = new VBox();

        HBox titleBar = new HBox();
        Label locationName = new Label();
        locationName.setText(ForecastFormatting.dayAlias(currentDay) + " - " + location.getName());
        locationName.setAlignment(Pos.CENTER_LEFT);
        Button home = new Button();
        home.setText("<");
        ToggleButton favourite = new ToggleButton();
        favourite.setSelected(location.isFavourite());
        titleBar.getChildren().addAll(locationName, home, favourite);
        titleBar.setHgrow(locationName, Priority.ALWAYS);
        locationName.setPrefWidth(500);
        titleBar.setHgrow(home, Priority.ALWAYS);
        home.setPrefWidth(100);
        titleBar.setHgrow(favourite, Priority.ALWAYS);
        favourite.setPrefWidth(65);
        favourite.setAlignment(Pos.TOP_RIGHT);

        if (currentForecastToDisplay == null) {
            Label noData = new Label();
            noData.setText("There is no saved data for this location and we cannot access the web server!");
            noData.setAlignment(Pos.CENTER);
            noData.setWrapText(true);
            Label helpMessage = new Label();
            helpMessage.setText("Please connect to the internet to view data for this location");
            helpMessage.setAlignment(Pos.CENTER);
            helpMessage.setWrapText(true);
            display.getChildren().addAll(titleBar, noData, helpMessage);
        } else {
            GridPane forecasts = generateForecastScreen();
            ScrollPane scroll = new ScrollPane(forecasts);
            scroll.setFitToHeight(true);
            scroll.setFitToWidth(true);
            scroll.setPrefViewportHeight(800);

            HBox days = getDayMenu();

            display.getChildren().addAll(titleBar, scroll, days);
        }

        home.setOnAction(event -> returnHome());
        favourite.setOnAction(event -> toggleFavourite());

        return new Scene(display);
    }

    /**
     * Generates the grid of forecast data
     *
     * @return Formatted forecast data
     */
    private GridPane generateForecastScreen() {
        GridPane display = new GridPane();

        Label timeHeader = new Label();
        timeHeader.setText("Time");
        timeHeader.getStyleClass().set(0, "label-small");
        display.add(timeHeader, 0, 0);
        Label temperatureHeader = new Label();
        temperatureHeader.getStyleClass().set(0, "label-small");
        temperatureHeader.setText("Temp");
        display.add(temperatureHeader, 1, 0);
        Label precipitationHeader = new Label();
        precipitationHeader.getStyleClass().set(0, "label-small");
        precipitationHeader.setText("Rain");
        display.add(precipitationHeader, 3, 0);
        Label windHeader = new Label();
        windHeader.getStyleClass().set(0, "label-small");
        windHeader.setText("Wind");
        display.add(windHeader, 4, 0);
        display.setColumnSpan(windHeader, 2);
        Label humidityHeader = new Label();
        humidityHeader.getStyleClass().set(0, "label-small");
        humidityHeader.setText("Humidity");
        display.add(humidityHeader, 6, 0);

        int row = 1;
        for (StatusWeatherData weather : currentForecastToDisplay) {
            Label time = new Label();
            time.setText(ForecastFormatting.getTimeLabel(weather.getDateTime()));
            display.add(time, 0, row);
            Label temperature = new Label();
            temperature.setText(ForecastFormatting.normaliseTemperature(weather.getTemp()) + "\u00b0" + "C");
            display.add(temperature, 1, row);
            ImageView type = WeatherIconConverter.getIconImage(weather.getWeatherConditions(), weather.getDateTime());
            display.add(type, 2, row);
            Label precipitation = new Label();
            if (weather.getRain() == Integer.MIN_VALUE) {
                precipitation.setText("0 mm");
            } else {
                precipitation.setText(weather.getRain() + " mm");
            }
            display.add(precipitation, 3, row);

            VBox windData = new VBox();
            Label wind = new Label();
            wind.setText(Double.toString(ForecastFormatting.convertWindSpeed(weather.getWindSpeed())));
            wind.getStyleClass().set(0, "label-small");
            Label mph = new Label("mph");
            mph.getStyleClass().set(0, "label-small");
            windData.getChildren().addAll(wind, mph);
            windData.setAlignment(Pos.CENTER);
            display.add(windData, 4, row);

            HBox windDirectionContainer = new HBox();
            ImageView windDirection = WeatherIconConverter.getWindDirection(weather.getWindDeg());
            windDirectionContainer.getChildren().addAll(windDirection);
            windDirectionContainer.setAlignment(Pos.CENTER);
            display.add(windDirectionContainer, 5, row);

            Label humidity = new Label();
            humidity.setText(weather.getHumidity() + "%");
            display.add(humidity, 6, row);
            row++;
        }
        ColumnConstraints timeColumn = new ColumnConstraints(150, 150, 150);
        timeColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints temperatureColumn = new ColumnConstraints(120, 120, 120);
        temperatureColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints typeColumn = new ColumnConstraints(150, 150, 150);
        typeColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints precipitationColumn = new ColumnConstraints(140, 140, 140);
        precipitationColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints windColumn = new ColumnConstraints(80, 80, 80);
        windColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints windDirectionColumn = new ColumnConstraints(100, 100, 100);
        windDirectionColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints humidityColumn = new ColumnConstraints(180, 180, 180);
        humidityColumn.setHgrow(Priority.ALWAYS);
        display.getColumnConstraints().addAll(timeColumn, temperatureColumn, typeColumn, precipitationColumn,
                windColumn, windDirectionColumn, humidityColumn);
        return display;
    }

    /**
     * Gets day select menu at bottom of the the screen
     *
     * @return Day select menu
     */
    private HBox getDayMenu() {
        Set<DayOfWeek> allDays = dailyForecasts.keySet();
        boolean breakFound = false;
        List<DayOfWeek> leftList = new LinkedList<>();
        List<DayOfWeek> rightList = new LinkedList<>();
        for (int i = 1; i <= 7; i++) {
            if (allDays.contains(DayOfWeek.of(i))) {
                if (breakFound) {
                    rightList.add(DayOfWeek.of(i));
                } else {
                    leftList.add(DayOfWeek.of(i));
                }
            } else {
                breakFound = true;
            }
        }
        rightList.addAll(leftList);

        HBox days = new HBox();
        for (DayOfWeek day : rightList) {
            if (day == currentDay) {
                continue;
            }
            Button dayButton = new Button();
            dayButton.setText(ForecastFormatting.dayAlias(day));
            dayButton.setPrefWidth(200);
            dayButton.setOnAction(event -> changeDay(day));
            days.getChildren().add(dayButton);
        }

        for (Node node : days.getChildren()) {
            days.setHgrow(node, Priority.ALWAYS);
        }

        return days;
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
     * Changes the day to be displayed
     *
     * @param day The day to change to
     */
    private void changeDay(DayOfWeek day) {
        currentDay = day;
        currentForecastToDisplay = dailyForecasts.get(day);
        main.drawScreen(getWeatherDisplay());
    }

    /**
     * Returns to the main menu
     */
    private void returnHome() {
        main.returnHome();
    }

}
