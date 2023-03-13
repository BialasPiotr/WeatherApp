package com.example.weather;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherApp extends Application {
    private Label cityLabel, tempLabel, descLabel;
    private TextField cityField;
    private Button searchButton;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        cityLabel = new Label("City:");
        GridPane.setConstraints(cityLabel, 0, 0);
        gridPane.getChildren().add(cityLabel);

        cityField = new TextField();
        cityField.setPrefWidth(200);
        GridPane.setConstraints(cityField, 1, 0);
        gridPane.getChildren().add(cityField);

        searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 2, 0);
        gridPane.getChildren().add(searchButton);

        tempLabel = new Label();
        GridPane.setConstraints(tempLabel, 0, 1);
        gridPane.getChildren().add(tempLabel);

        descLabel = new Label();
        GridPane.setConstraints(descLabel, 0, 2);
        gridPane.getChildren().add(descLabel);

        VBox vbox = new VBox(gridPane);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setTitle("Weather App");
        primaryStage.setScene(scene);
        primaryStage.show();

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String city = URLEncoder.encode(cityField.getText(), "UTF-8");
                    String apiKey = "";
                    String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    JSONObject json = new JSONObject(response.toString());
                    JSONObject main = json.getJSONObject("main");
                    double temp = main.getDouble("temp") - 273.15;
                    String desc = json.getJSONArray("weather").getJSONObject(0).getString("description");
                    tempLabel.setText("Temperature: " + String.format("%.2f", temp) + " °C");
                    descLabel.setText("Description: " + desc);
                    if (desc.contains("clear")) {
                        Image weatherImage = new Image(getClass().getResource("/images/sunny.png").toExternalForm());
                        ImageView imageView = new ImageView(weatherImage);
                        GridPane.setConstraints(imageView, 2, 1);
                        gridPane.getChildren().add(imageView);
                    } else if (desc.contains("cloud")) {
                        Image weatherImage = new Image(getClass().getResource("/images/cloudy.png").toExternalForm());
                        ImageView imageView = new ImageView(weatherImage);
                        GridPane.setConstraints(imageView, 2, 1);
                        gridPane.getChildren().add(imageView);
                    } else if (desc.contains("rain")) {
                        Image weatherImage = new Image(getClass().getResource("/images/rain.png").toExternalForm());
                        ImageView imageView = new ImageView(weatherImage);
                        GridPane.setConstraints(imageView, 2, 1);
                        gridPane.getChildren().add(imageView);
                    } else if (desc.contains("snow")) {
                        Image weatherImage = new Image(getClass().getResource("/images/snow.png").toExternalForm());
                        ImageView imageView = new ImageView(weatherImage);
                        GridPane.setConstraints(imageView, 2, 1);
                        gridPane.getChildren().add(imageView);
                    } else if (desc.contains("storm")) {
                        Image weatherImage = new Image(getClass().getResource("/images/storm.png").toExternalForm());
                        ImageView imageView = new ImageView(weatherImage);
                        GridPane.setConstraints(imageView, 2, 1);
                        gridPane.getChildren().add(imageView);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
