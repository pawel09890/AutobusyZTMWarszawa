package pawel.pawlik;
import com.google.gson.*;
import javafx.application.Application;
import okhttp3.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

        private static final String API_KEY = "36c8d1fc-0ed8-4645-86ad-302b9c47bd60";
        private static final String URL = "https://api.um.warszawa.pl/api/action/busestrams_get";

        public static void main(String[] args) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
            urlBuilder.addQueryParameter("resource_id", "f2e5503e927d-4ad3-9500-4ab9e55deb59");
            urlBuilder.addQueryParameter("type", "1");
            urlBuilder.addQueryParameter("apikey", API_KEY);

            Request request = new Request.Builder().url(urlBuilder.build()).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Tresc bledu: " + response);
                }

                String responseBody = response.body().string();
                JsonElement root = JsonParser.parseString(responseBody);
                JsonArray results = root.getAsJsonObject().getAsJsonArray("result");

                System.out.println("Biezacy stan autobusow w Warszawie:");
                for (JsonElement el : results) {
                    JsonObject bus = el.getAsJsonObject();
                    String line = bus.get("Lines").getAsString();
                    String vehicle = bus.get("VehicleNumber").getAsString();
                    String lat = bus.get("Lat").getAsString();
                    String lon = bus.get("Lon").getAsString();
                    String time = bus.get("Time").getAsString();

                    System.out.printf("Linia %s | Nr pojazdu: %s | [%s, %s] | Czas: %s\n",
                            line, vehicle, lat, lon, time);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            launch(args);


        }
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/map.html").toExternalForm());

        primaryStage.setTitle("Mapa Autobusów ZTM Warszawa");
        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.show();
    }



}