package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class KasseGUI extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane mainRoot = new BorderPane();

		mainRoot.setMinWidth(1080);
		mainRoot.setMinHeight(700);

		mainRoot.setPrefWidth(800);
		mainRoot.setPrefHeight(600);

		mainRoot.setMaxWidth(800);
		mainRoot.setMaxHeight(600);

		/* mainRoot 'exportieren' zu Context */
		Context.getInstance().setMainRoot(mainRoot);

		Scene scene = new Scene(mainRoot);
		
		AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
		mainRoot.setCenter(root);

		scene.getStylesheets().add("/styles/Styles.css");

		stage.setTitle("Möbelhaus - Kasse");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
