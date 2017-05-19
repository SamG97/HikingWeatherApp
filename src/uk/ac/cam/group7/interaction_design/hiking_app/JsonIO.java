package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.StatusWeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Reads and writes JSON files to disk
 *
 * @author Dávid, Sam Gooch
 */
public class JsonIO {

    /**
     * Reads a JSON file in from disk
     *
     * @param path The path to the file on disk
     * @return List<StatusWeatherData> The forecast stored in the file
     */
    protected static List<StatusWeatherData> readJson(Path path) {
        String read;
        JSONObject json = new JSONObject();
        try {
            File input = new File(path.toUri());
            InputStream fileIn = new FileInputStream(input);
            Reader isReader = new BufferedReader(new InputStreamReader(fileIn));
            int fileSize = (int) input.length();
            if (fileSize < 0)
                fileSize = 8 * 1024;
            StringWriter strWriter = new StringWriter(fileSize);
            char[] buffer = new char[8 * 1024];
            int n = 0;
            while ((n = isReader.read(buffer)) != -1) {
                strWriter.write(buffer, 0, n);
            }
            read = strWriter.toString();
            fileIn.close();
            json = new JSONObject(read);
        } catch (IOException | JSONException e) {
            System.out.println(e.getMessage());
        }

        return new WeatherStatusResponse(json).getWeatherStatus();
    }

    /**
     * Writes a JSON file to disk
     *
     * @param path The path to the file on disk to write to
     * @param data The JSON file to save
     */
    protected static void saveJson(Path path, JSONObject data) {
        ObjectOutputStream outputStream;
        try {
            BufferedWriter w = Files.newBufferedWriter(path);
            w.write(data.toString());
            w.flush();
            w.close();
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
}
