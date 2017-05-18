package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.StatusWeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Dávid on 2017.05.16..
 */
public class JsonReader {

    public static List<StatusWeatherData> readJson(Path p) {
        String read;
        JSONObject json = new JSONObject();
        try {
            File input = new File(p.toUri());
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }catch (RuntimeException re) {
            throw re;
        }
        //
        return new WeatherStatusResponse(json).getWeatherStatus();
    }
}
