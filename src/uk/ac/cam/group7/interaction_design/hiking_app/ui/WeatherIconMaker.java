package uk.ac.cam.group7.interaction_design.hiking_app.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bitpipeline.lib.owm.WeatherData.WeatherCondition;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

class WeatherIconMaker {

    /**
     * Get a weather condition icon to display from a list of conditions
     *
     * @param allConditions List of conditions in a forecast
     * @param timeStamp     Timestamp of forecast
     * @return Icon to display
     */
    static ImageView getIconImage(List<WeatherCondition> allConditions, long timeStamp) {
        List<WeatherCondition.ConditionCode> allConditionCodes = new LinkedList<>();
        for (WeatherCondition condition : allConditions) {
            allConditionCodes.add(condition.getCode());
        }

        boolean isDay = isDay(timeStamp);

        String iconCode;
        if (allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM_WITH_DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM_WITH_HEAVY_DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM_WITH_HEAVY_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM_WITH_LIGHT_DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM_WITH_LIGHT_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.THUNDERSTORM_WITH_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.LIGHT_THUNDERSTORM) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.HEAVY_THUNDERSTORM) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.RAGGED_THUNDERSTORM)) {
            iconCode = "00";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.HAIL)) {
            iconCode = "18";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.HEAVY_SNOW)) {
            iconCode = "16";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.SNOW)) {
            iconCode = "14";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.LIGHT_SNOW)) {
            iconCode = "13";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.SHOWER_SNOW)) {
            if (isDay) {
                iconCode = "41";
            } else {
                iconCode = "46";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.SLEET)) {
            iconCode = "05";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.FREEZING_RAIN)) {
            iconCode = "08";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.HEAVY_INTENSITY_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.EXTREME_RAIN)) {
            iconCode = "01";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.MODERATE_RAIN)) {
            iconCode = "12";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.LIGHT_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.LIGHT_INTENSITY_DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.HEAVY_INTENSITY_DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.LIGHT_INTENSITY_DRIZZLE_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.DRIZZLE_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.HEAVY_INTENSITY_DRIZZLE_RAIN)) {
            iconCode = "09";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.SHOWER_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.SHOWER_DRIZZLE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.LIGHT_INTENSITY_SHOWER_RAIN) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.HEAVY_INTENSITY_SHOWER_RAIN)) {
            if (isDay) {
                iconCode = "39";
            } else {
                iconCode = "45";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.FOG)) {
            iconCode = "20";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.MIST) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.SMOKE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.HAZE) ||
                allConditionCodes.contains(WeatherCondition.ConditionCode.SAND_OR_DUST_WHIRLS)) {
            if (isDay) {
                iconCode = "19";
            } else {
                iconCode = "21";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.OVERCAST_CLOUDS)) {
            iconCode = "26";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.BROKEN_CLOUDS)) {
            if (isDay) {
                iconCode = "28";
            } else {
                iconCode = "27";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.SCATTERED_CLOUDS)) {
            if (isDay) {
                iconCode = "30";
            } else {
                iconCode = "29";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.FEW_CLOUDS)) {
            if (isDay) {
                iconCode = "34";
            } else {
                iconCode = "33";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.SKY_IS_CLEAR)) {
            if (isDay) {
                iconCode = "32";
            } else {
                iconCode = "31";
            }
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.HOT)) {
            iconCode = "36";
        } else if (allConditionCodes.contains(WeatherCondition.ConditionCode.WINDY)) {
            iconCode = "23";
        } else {
            iconCode = "na";
        }

        try {
            FileInputStream storedImage = new FileInputStream("src/img/weather_icons/" + iconCode + ".png");
            Image icon = new Image(storedImage);
            return new ImageView(icon);
        } catch (IOException e) {
            throw new RuntimeException("File system improperly configured");
        }
    }

    /**
     * Get weather warning icon
     *
     * @param severity Severity of the weather warning
     * @return Weather warning icon
     */
    static ImageView getWarningIcon(int severity) {
        try {
            FileInputStream storedImage = new FileInputStream("src/img/Warning" + severity + ".png");
            Image icon = new Image(storedImage);
            return new ImageView(icon);
        } catch (IOException e) {
            throw new RuntimeException("File system improperly configured");
        }
    }

    /**
     * Get wind direction icon
     *
     * @param direction Wind direction (degrees CW from North)
     * @return Wind direction icon
     */
    static ImageView getWindDirection(int direction) {
        try {
            FileInputStream baseArrow = new FileInputStream("src/img/WindDirection.png");
            Image icon = new Image(baseArrow);
            ImageView iconView = new ImageView(icon);
            iconView.setFitWidth(50);
            iconView.setRotate(direction);
            return iconView;
        } catch (IOException e) {
            throw new RuntimeException("File system improperly configured");
        }
    }

    /**
     * Returns if time stamp corresponds to day or night time
     *
     * @param timeStamp Time stamp
     * @return true if day; false if night
     */
    private static boolean isDay(long timeStamp) {
        long dayTime = timeStamp % 86400;
        return !(dayTime < 28800 || dayTime > 72000);
    }

}
