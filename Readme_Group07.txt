The 'app' can be run by running the HikingWeatherApp.jar file, either by double clicking it within Explorer or by
running it via a command line or terminal in the HikingWeatherApp folder with 'java -jar HikingWeatherApp.jar'.

The application has only been tested in a Windows environment but it should work with any other operating system.

The .jar file has several dependencies on files within the src folder for graphical elements so should not be moved
independently.

It should be noted that all yellow, amber and red weather warnings are implemented using a random number generator for
demonstrative purposes rather than real data so are likely to produce nonsensical warnings (e.g. red warning of rain
when the forecast is for sun) so should be ignored expect for gaining an idea of the appearance of them in the final
version.

The following external libraries are used:
Apache Commons Logging (commons-logging-1.2.jar)
Apache HttpClient (httpclient-4.5.2.jar)
Apache HttpCore (httpcore-4.4.6.jar)
JSON In Java (json-20090211.jar)

A modified version of the OpenWeatherMap JSON API client (org.bitpipeline.lib.owm)
Almost all of the code in this package was not written by our group and can be assumed to be an external library

The following internal libraries are used:
Various java.io and java.nio
Various java.util
java.time.DayOfWeek
Various javafx for the UI (used programmatically rather than a through an IDE)

All code within the uk.ac.cam.group7.interaction_design.hiking_app.backend and ui packages was written by the team fresh
for this project.

The weather conditions icons are used under the Creative Commons Attribution-ShareAlike license.
They can be found at http://merlinthered.deviantart.com/art/plain-weather-icons-157162192.
All other images and styling were designed by the team.