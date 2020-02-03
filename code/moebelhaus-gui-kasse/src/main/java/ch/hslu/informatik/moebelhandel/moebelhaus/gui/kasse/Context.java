package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.PdfPrinter;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wrapper.RechnungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.pdfprinter.PdfPrinterImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiKasseService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiLoginService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

/**
 * Context haelt den ganzen Status der Applikation
 */
public class Context {

	private static Logger logger = LogManager.getLogger(Context.class);

	private static final String PROPERTY_FILE_NAME = "rmi_client.properties";
    private static final String POLICY_FILE_NAME = "rmi_client.policy";

	private Map<String, Object> contentMap = new HashMap<>();

	private static Context INSTANCE = new Context();

	private BorderPane mainRoot;
	
	private Benutzer benutzer;

	private RmiLoginService loginService;

	private RmiKasseService kasseService;

	private PdfPrinter pdfPrinter;
	
	private ObservableList<RechnungPositionWrapper> tabellenListe = FXCollections.observableArrayList();

	private Context() {
			
	}

	public static Context getInstance() {
		return INSTANCE;
	}

	public RmiLoginService getLoginService() {

		int portNr = 0;

        if (loginService == null) {

            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {

                setSecurityManager();

                Properties props = new Properties();

                if (is == null) {
                    throw new RuntimeException(
                            "Die Property-Datei \'" + PROPERTY_FILE_NAME + "\' konnte nicht gefunden werden!");
                } else {

                    props.load(is);

                    String ip = props.getProperty("rmi.server.ip");
                    String strPort = props.getProperty("rmi.registry.port");

                    try {
                        portNr = Integer.parseInt(strPort);
                        Registry reg = LocateRegistry.getRegistry(ip, portNr);

                        if (reg != null) {
                            String url = "rmi://" + ip + ":" + portNr + "/" + RmiLoginService.REMOTE_OBJECT_NAME;

                            loginService = (RmiLoginService) Naming.lookup(url);

                        } else {
                            String msg = "Die Reference auf RMI-Registry konnte auf " + ip + ":" + portNr
                                    + " nicht geholt werden!";
                            logger.error(msg);
                            throw new RuntimeException(msg);
                        }

                    } catch (NumberFormatException nfe) {
                        String msg = "Die Portnummer-Angabe \'" + strPort + "\' ist nicht korrekt";
                        logger.error(msg, nfe);
                        throw new RuntimeException(nfe);
                    }
                }
            } catch (Exception e) {
                String msg = "Fehler beim Holen des RmiLoginRO:";
                logger.error(msg, e);
                throw new RuntimeException(msg);
            }
        }

        return loginService;
	}
	
	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public RmiKasseService getKasseService() {

		int portNr = 0;

        if (kasseService == null) {

            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {

                setSecurityManager();

                Properties props = new Properties();

                if (is == null) {
                    throw new RuntimeException(
                            "Die Property-Datei \'" + PROPERTY_FILE_NAME + "\' konnte nicht gefunden werden!");
                } else {

                    props.load(is);

                    String ip = props.getProperty("rmi.server.ip");
                    String strPort = props.getProperty("rmi.registry.port");

                    try {
                        portNr = Integer.parseInt(strPort);
                        Registry reg = LocateRegistry.getRegistry(ip, portNr);

                        if (reg != null) {
                            String url = "rmi://" + ip + ":" + portNr + "/" + RmiKasseService.REMOTE_OBJECT_NAME;

                            kasseService = (RmiKasseService) Naming.lookup(url);

                        } else {
                            String msg = "Die Reference auf RMI-Registry konnte auf " + ip + ":" + portNr
                                    + " nicht geholt werden!";
                            logger.error(msg);
                            throw new RuntimeException(msg);
                        }

                    } catch (NumberFormatException nfe) {
                        String msg = "Die Portnummer-Angabe \'" + strPort + "\' ist nicht korrekt";
                        logger.error(msg, nfe);
                        throw new RuntimeException(nfe);
                    }
                }
            } catch (Exception e) {
                String msg = "Fehler beim Holen des RmiKasseRO:";
                logger.error(msg, e);
                throw new RuntimeException(msg);
            }
        }
        
        return kasseService;
	}

	/**
	 * Gibt den PDF-Printer zurueck, um PDF-Dateien zu erstellen
	 * @return der PDF Printer
	 */
	public PdfPrinter getPdfPrinter() {

		try {

			/* PdfPrinter-Klasse auslesen */
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));

			String className = props.getProperty("pdf.printer.class");

			if (pdfPrinter == null) {

				/* Klasse laden */
				Class<?> clazz = Class.forName(className);

				pdfPrinter = (PdfPrinter) clazz.newInstance();
				pdfPrinter = new PdfPrinterImpl();
			}

		} catch (IOException e) {
			String msg = "Fehler beim Auslesen des Namen der PdfPrinter-Klasse:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		} catch (ClassNotFoundException e) {
			String msg = "PdfPrinter-Klasse konnte nicht gefunden werden:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		} catch (Exception e) {
			String msg = "PdfPrinter-Klasse konnte nicht instantiiert werden:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		}

		return pdfPrinter;

	}

	public BorderPane getMainRoot() {
		return mainRoot;
	}

	public void setMainRoot(BorderPane mainRoot) {
		this.mainRoot = mainRoot;
	}

	public Map<String, Object> getContentMap() {
		return contentMap;
	}
	
	public ObservableList<RechnungPositionWrapper> getTabelleListe()
	{
		return tabellenListe;
	}
	
	/**
	 * Speichert ein Item, identifiziert ueber den itemCode, in der tabellenListe. Verwendet fuer items, die man verkaufen will
	 * @param itemCode der Item-Code des Items
	 * @return True, wenn man das item hinzugefuegt hat, False sonst (item darf nur einmal auftauchen)
	 */
	public boolean setTabellenItem(String itemCode)
	{
		try {
			List<Produkt> produkteAmLager = Context.getInstance().getKasseService()
					.findAlleAmLagerVerfuegbareProdukte();
			
			for(Produkt produkt : produkteAmLager) // durch Liste iterieren, jedes Item vergleichen mit Itemcode
			{
				if(produkt.getCode() == Long.valueOf(itemCode) ) // wen gefunden, Rechnungsposition erstellen
				{
					
					for(RechnungPositionWrapper rp : tabellenListe )
					{
						if(rp.getProduktCode() == Long.valueOf(itemCode))
						{
							return false;
						}
					}
					
					// neue Rechnungsposition erstellen und in der Tabelle hinzufuegen
					RechnungPosition rechnungsPosition = new RechnungPosition(produkt.getTyp(), 1);
					int nummer = tabellenListe.size() +1; // position in der Tabelle
					RechnungPositionWrapper wrapper = new RechnungPositionWrapper(nummer, rechnungsPosition, produkt.getCode());
					tabellenListe.add(wrapper);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Entfernt das item aus der tabellenListe, um eine Position zu loeschen
	 * @param item Das zu loeschende Item
	 */
	public void removeTabellenItem (RechnungPositionWrapper item){ 
		
		tabellenListe.remove(item);
		
	}
	
	/**
	 * Ganze tabellenListe loeschen
	 */
	public void clearTeabellenItems (){
		tabellenListe.clear();
	}
	
	/**
	 * Alle Positionen in der Tabelle aktualisieren (gebraucht, wenn man ein oder mehrere Eintrag(e) loescht)
	 */
	public void updateIndex(){
		int nummer = 1;
		for (RechnungPositionWrapper r : tabellenListe){
			r.setNummer(nummer++);
		}
	}
	
	private void setSecurityManager() throws IOException {

        /*
         * Hinweis: die rmi-policy File ist entweder im Verzeichnis 'src' oder
         * in 'resources', wenn man mit maven arbeitet
         */

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(POLICY_FILE_NAME);

        File tempFile = File
                .createTempFile(System.getProperty("user.home") + File.separator + "moebelhandel_rmi_policy", "tmp");

        FileOutputStream fos = new FileOutputStream(tempFile);

        /* Inhalt der policy-Datei in 'tempFile' kopieren */
        int n = 0;

        while ((n = is.read()) != -1) {
            fos.write(n);
        }

        is.close();
        fos.close();

        String pathToTempPolicyFile = tempFile.getAbsolutePath();

        tempFile.deleteOnExit();

        if (System.getSecurityManager() == null) {
            /* Policy-File muss im ROOT-Verzeichnis des Projekts sein! */
            System.setProperty("java.security.policy", pathToTempPolicyFile);
            System.setSecurityManager(new SecurityManager());
        }

    }
}
