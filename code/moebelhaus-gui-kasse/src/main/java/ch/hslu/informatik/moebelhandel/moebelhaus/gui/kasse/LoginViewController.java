package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.LoginService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * LoginViewController ist der Controller fuer das LoginView. Es zeigt den Login-Screen
 */
public class LoginViewController implements Initializable {
	
	private static Logger logger = LogManager.getLogger(LoginViewController.class);
	
	//Definition der Fehlermeldungen bei der Anmeldung.
	public static String LOGIN_ERROR_MESSAGE = "Benutzername oder Kennwort sind nicht korrekt!";
	public static String ACCESS_DENIED_MESSAGE = "Sie haben keine Berechtigung!";
	public static String SERVER_NOT_AVAILABLE = "Die Anmeldung ist aufgrund Serverproblemen nicht möglich";

	@FXML
	private Label lblError;
	
	@FXML
	private TextField txtBenutzername;
	
	@FXML
	private TextField txtKennwort;
	
	@FXML
	private Button btnAnmelden;
	
	public LoginViewController(){
		
		
	}
	

	@FXML
	private void anmelden(ActionEvent event) {
		
		lblError.setText("");
		
		LoginService loginService = null;
		
		try{
			loginService = Context.getInstance().getLoginService();
		} catch (Exception conException) {
			logger.error(conException.getMessage(), conException);
			lblError.setText(LoginViewController.SERVER_NOT_AVAILABLE);
			return;
		}
		
		try{
			
			Benutzer benutzer = loginService.login(txtBenutzername.getText(), txtKennwort.getText());
			
			if (benutzer != null){
				
				if (benutzer.getRolle() == RolleTyp.FILIALE_SACHBEARBEITER
						|| benutzer.getRolle() == RolleTyp.FILIALE_LEITER
						|| benutzer.getRolle() == RolleTyp.ADMINISTRATOR) {
					Context.getInstance().setBenutzer(benutzer);
					
					AnchorPane menuBar = FXMLLoader.load(getClass().getResource("/fxml/MenuBarView.fxml"));
					AnchorPane root  = FXMLLoader.load(getClass().getResource("/fxml/VerkaufenView.fxml"));
					Context.getInstance().getMainRoot().setTop(menuBar);
					Context.getInstance().getMainRoot().setCenter(root);
							
				} else {
					lblError.setText(LoginViewController.ACCESS_DENIED_MESSAGE);
				}
			} else {
					lblError.setText(LoginViewController.LOGIN_ERROR_MESSAGE);
					txtKennwort.setText("");
					txtBenutzername.setText("");
				}
			} catch (Exception e){
				throw new RuntimeException();
			}
		
		
		

	
		//lblError.setVisible(true);

		// Methode 'LoginService.login' aufrufen ...
	}

	/**
	 * Initialisiere das Hintergrundbild der App
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		txtBenutzername.requestFocus();
		Image img = new Image("buero_2_.png");
		BackgroundImage image = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Context.getInstance().getMainRoot().setBackground(new Background(image));
	}
}
