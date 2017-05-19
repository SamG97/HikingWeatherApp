package uk.ac.cam.group7.interaction_design.hiking_app.alternative_ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import org.bitpipeline.lib.owm.StatusWeatherData;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastContainer;
import uk.ac.cam.group7.interaction_design.hiking_app.ForecastFormatting;
import uk.ac.cam.group7.interaction_design.hiking_app.Location;

import java.time.DayOfWeek;
import java.util.*;

public class ForecastDisplay {

    private static ForecastContainer forecasts = ForecastContainer.getReference();

    private MainMenu main;
    private Location location;
    private Map<DayOfWeek, List<StatusWeatherData>> dailyForecasts;
    private DayOfWeek currentDay;
    private List<StatusWeatherData> currentForecastToDisplay;

    protected ForecastDisplay(Location location, MainMenu main) {
        this.location = location;
        this.dailyForecasts = ForecastFormatting.getDailyForecasts(forecasts.getForecast(location));
        this.main = main;
        Calendar calendar = Calendar.getInstance();
        currentDay = ForecastFormatting.getToady();
        currentForecastToDisplay = dailyForecasts.get(currentDay);
    }

    protected Scene getWeatherDisplay() {
        VBox display = new VBox();

        HBox titleBar = new HBox();
        Label locationName = new Label();
        locationName.setText(location.getName());
        Button home = new Button();
        home.setText("<");
        Button favourite = new Button();
        if (location.isFavourite()) {
            favourite.setText("UnF");
        } else {
            favourite.setText("Fav");
        }
        titleBar.getChildren().addAll(locationName, home, favourite);
        titleBar.setHgrow(locationName, Priority.ALWAYS);
        locationName.setPrefWidth(600);
        titleBar.setHgrow(home, Priority.ALWAYS);
        home.setPrefWidth(50);
        titleBar.setHgrow(favourite, Priority.ALWAYS);
        favourite.setPrefWidth(50);

        GridPane forecasts = generateForecastScreen();
        ScrollPane scroll = new ScrollPane(forecasts);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        HBox days = getDayMenu();

        display.getChildren().addAll(titleBar, scroll, days);

        home.setOnAction(event -> returnHome());
        favourite.setOnAction(event -> toggleFavourite(favourite));

        return new Scene(display);
    }

    private GridPane generateForecastScreen() {
        GridPane display = new GridPane();

        Label timeHeader = new Label();
        timeHeader.setText("Time");
        display.add(timeHeader, 0, 0);
        Label temperatureHeader = new Label();
        temperatureHeader.setText("Temperature / C");
        display.add(temperatureHeader, 1, 0);
        Label typeHeader = new Label();
        typeHeader.setText("Conditions");
        display.add(typeHeader, 2, 0);
        Label precipitationHeader = new Label();
        precipitationHeader.setText("Precipitation / mm");
        display.add(precipitationHeader, 3, 0);
        Label windHeader = new Label();
        windHeader.setText("Wind / mph");
        display.add(windHeader, 4, 0);
        Label humidityHeader = new Label();
        humidityHeader.setText("Humidity / %");
        display.add(humidityHeader, 5, 0);

        int row = 1;
        for (StatusWeatherData weather : currentForecastToDisplay) {
            Label time = new Label();
            time.setText(ForecastFormatting.getTimeLabel(weather.getDateTime()));
            display.add(time, 0, row);
            Label temperature = new Label();
            temperature.setText(Integer.toString(ForecastFormatting.normaliseTemperature(weather.getTemp())));
            display.add(temperature, 1, row);
            Label type = new Label();
            type.setText(weather.getWeatherConditions().get(0).getDescription());
            display.add(type, 2, row);
            Label precipitation = new Label();
            if (weather.getRain() == Integer.MIN_VALUE) {
                precipitation.setText("0");
            } else {
                precipitation.setText(Integer.toString(weather.getRain()));
            }
            display.add(precipitation, 3, row);
            Label wind = new Label();
            wind.setText(Double.toString(ForecastFormatting.convertWindSpeed(weather.getWindSpeed())));
            display.add(wind, 4, row);
            Label humidity = new Label();
            humidity.setText(Float.toString(weather.getHumidity()));
            display.add(humidity, 5, row);
            row++;
        }
        ColumnConstraints timeColumn = new ColumnConstraints(50,50,50);
        timeColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints temperatureColumn = new ColumnConstraints(100,100,100);
        temperatureColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints typeColumn = new ColumnConstraints(50,50,50);
        typeColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints precipitationColumn = new ColumnConstraints(100,100,100);
        precipitationColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints windColumn = new ColumnConstraints(100,100,100);
        windColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints humidityColumn = new ColumnConstraints(100,100,100);
        humidityColumn.setHgrow(Priority.ALWAYS);
        display.getColumnConstraints().addAll(timeColumn, temperatureColumn, typeColumn, precipitationColumn,
                windColumn, humidityColumn);
        return display;
    }

    public HBox getDayMenu() {
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
        List<DayOfWeek> sortedDays = rightList;
        sortedDays.addAll(leftList);

        HBox days = new HBox();
        for (DayOfWeek day : sortedDays) {
            if (day == currentDay) {
                continue;
            }
            Button dayButton = new Button();
            dayButton.setText(day.toString());
            dayButton.setPrefWidth(200);
            dayButton.setOnAction(event -> changeDay(day));
            days.getChildren().add(dayButton);
        }

        for (Node node : days.getChildren()) {
            days.setHgrow(node, Priority.ALWAYS);
        }

        return days;
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

    private void changeDay(DayOfWeek day) {
        currentDay = day;
        currentForecastToDisplay = dailyForecasts.get(day);
        main.drawScreen(getWeatherDisplay());
    }

    private void returnHome() {
        main.returnHome();
    }

}
