package pawel.pawlik;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class Map {

    public void showMap(Stage stage) {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/map.html").toExternalForm());

        Scene scene = new Scene(webView, 800, 600);
        stage.setTitle("Mapa ZTM – Warszawa");
        stage.setScene(scene);
        stage.show();
    }

}
