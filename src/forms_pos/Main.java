/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms_pos;

////import static forms_pos.Main.ac.track;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.crypto.SecretKey;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//import org.apache.log4j.PropertyConfigurator;
import org.asoft.library.AL;
import org.asoft.library.AsoftEvent;
import org.asoft.library.AsoftFontData;
import org.asoft.library.AsoftGroupInfo;
import org.asoft.library.AsoftPersistentPreferences;
import org.asoft.library.AsoftSplashScreen;
import org.asoft.library.AsoftStatusBar;
import org.asoft.library.prefs.XMLLocalPreferences;
import org.asoft.library.prefs.XMLPreferencesData;
import org.asoft.library.translator.AsoftTranslatorPanel;

/**
 *
 * @author Pionir SU
 */
public final class Main implements ActionListener {
    
    public static Logger track = Logger.getLogger(Main.class);
    
    @Override
    public void actionPerformed(ActionEvent e) {

    String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        
        PropertyConfigurator.configure(log4jConfigFile);
        
//        track.debug("this is a debug log message");
//        track.info("this is a information log message");
//        track.warn("this is a warning log message");      
//            
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  public final static class ac {

    /** main signaler */
    public static AsoftEvent mainSignal = new AsoftEvent();
    /** Java help instanca */
//    public static HelpBroker hb = null;
    /** server socket instanca */
    public static ServerSocket serverSocket = null;
    /** globalna definicija promenljive koja definise debug status u aplikaciji */
    public static boolean debug = true;
    /** main instance da bi se onemogucio constructor za Main */
    public static Main main;
    /** URL Main message bundle */
    public static URL ASOFT_MESSAGES_BUNDLE;
    /** rachel path za Main message bundle */
    public static final String JAR_PATH_MESSAGE_BUNDLE =
        "class://org.asoft.Main/org/asoft/res/AsoftMessagesBundle"; // NOI18N
    /** rachel ime ASOFT_PGDUMP_JAR kompresovanog jara asoft distribucionih sema */
    public static final String ASOFT_PGDUMP_JAR =
        "class://org.asoft.Main/org/asoft/res/asoft-pgdump.jar"; // NOI18N
    /** inicijalni frame title */
    public static final String MAIN_TITLE = "Asoft - poslovni software A grupe"; // NOI18N
    /** nakon startovanja aLanguage sadrzi Locale oznaku jezika koji koristi Asoft */
    public static String aLanguage = "";  // NOI18N
    /** za 1.5 je "sh", a inace je "sr" */
    public static String languageConstant = "";  // NOI18N
    /** nakon startovanja aCountry sadrzi Locale oznaku zemlju koji koristi Asoft */
    public static String aCountry = ""; // NOI18N
    /** Default vrednost je "CS" */
    public static String countryConstant = ""; // NOI18N
    /** Locale koji je bio pre startovanja Asoft-a */
    public static Locale defaultLocale = Locale.getDefault();
    /** nakon startovanja aLocale sadrzi Locale koji koristi Asoft */
    public static Locale aLocale = Locale.getDefault();
    /** nakon startovanja dataLocale sadrzi sve Locale koji postoje u javi */
//    public static DataLocale dataLocale = new DataLocale();
    /** nakon startovanja availableLocales sadrzi sve Locale koje obezbedjuje Asoft */
    public static Set<String> availableLocales = new TreeSet<String>();
    /** ime OS-a */
    public static final String OS_NAME = System.getProperty("os.name"); // NOI18N
    /** ime korisnika iz OS-a koji je startovao Asoft */
    public static final String USER_NAME = System.getProperty("user.name"); // NOI18N
    /**
     * home dir korisnika koji je startovao Asoft:
     * Linux:   USER_HOME == env HOME
     * Windows: USER_HOME == env USERPROFILE
     */
    public static final String USER_HOME = System.getProperty("user.home"); // NOI18N
    /** user dir korisnika koji je startovao Asoft */
    public static final String USER_DIR = System.getProperty("user.dir"); // NOI18N
    /** Java class path */
    public static final String JAVA_CLASS_PATH = System.getProperty("java.class.path"); // NOI18N
    /** nativeview directory path */
    public static final String NATIVE_DIR; // NOI18N
    /** Java library path */
    public static final String JAVA_LIBRARY_PATH; // NOI18N

    /** Proces ID kao formatirani String*/
//    public static final String PID_FORMAT;
    public static String ROUTER_ADDRESS;
    public static String PROVIDER_NAME;
    public static String PROVIDER_INFO;
    public static String JDBC_VERSION;
    public static String SERVER_VERSION;
    static {
//      PID_FORMAT=AL.getPIDFormat();
      if (OS_NAME.toLowerCase().contains("windows")) {
        NATIVE_DIR = "c:\\Program Files\\asoft";
        JAVA_LIBRARY_PATH = NATIVE_DIR + ";";
      } else {
        NATIVE_DIR = USER_HOME + "/asoft/dist";
        JAVA_LIBRARY_PATH = NATIVE_DIR + ":";
      }
      System.setProperty("java.library.path", JAVA_LIBRARY_PATH + System.getProperty("java.library.path"));
      
    }
    
    

        
    /**
     * JavaWebStart specific
     */
//    static {
//      UnaryFunctor<DataRow,?> expImpl;
//    }

    /** Java version specification */
    public static final String JAVA_SPECIFICATION_VERSION =
        System.getProperty("java.specification.version"); // NOI18N
    public static final String JAVA_VERSION =
        System.getProperty("java.version");

    static {
      if (JAVA_SPECIFICATION_VERSION.equals("1.5")) {
        languageConstant = "sh";
        countryConstant = "CS";
      } else {
        languageConstant = "sr";
        countryConstant = "RS";
      }
      aLanguage = languageConstant;
      aCountry = countryConstant;
    }

    /** host name korisnika koji je startovao Asoft */
    public static String USER_HOST;
    /** host IP address korisnika koji je startovao Asoft */
    public static String USER_HOST_IP;
    /** polje u kome se cuva password administratora u slucaju kad se adminisatrator loguje */
    public static SecretKey key = null;
    /** Options i preferences
     * mapa u kojoj se cuvaju opsti options-i i preferences-i, a koji se dobijaju iz tabele config
     * prvi argument je ime properties-a a drugi njegova vrednost
     */
    public static XMLPreferencesData serverPrefs = new XMLPreferencesData();
    /** mapa u kojoj se cuvaju options-i i preferences-i vezani za korisnika a koji se dobijaju iz
     * tabele korisnik */
    public static XMLPreferencesData userPrefs = new XMLPreferencesData();

    static {
      try {
        InetAddress ia = InetAddress.getLocalHost();
        USER_HOST = ia.getHostName();
        USER_HOST_IP = ia.getHostAddress();
      } catch (Throwable t) {
        AL.logInfo("UPOZORENJE: Dogodila se greška prilikom odredjivanja IP adrese računara");
        AL.logInfo("            Ovo može imati za posledicu, ako je mašina predvidjena za server,");
        AL.logInfo("            da se ne formira folder sa distribucijom aplikacije");
        AL.logInfo("            Message:" + t.toString());
        USER_HOST = "localhost";  // NOI18N
        USER_HOST_IP = "127.0.0.1";  // NOI18N
      }
    }
    /** logger instanca za Asoft */
    public static Logger log = Logger.getLogger(Main.class);
    /** relative path za apache log4j u odnosu na USER_HOME */
    public static final String ASOFT_LOG_REL = "asoft/asoft.log";  // NOI18N
    /** path za apache log4j log file korisnika $USER_HOME/asoft/asoft.log */
    public static final String ASOFT_LOG = USER_HOME + "/" + ASOFT_LOG_REL;  // NOI18N           
    
    /** preferences name za korisnika koji je startovao Asoft
     *  u Linuxu to je $HOME/.java/.prefs/$USER_NAME
     *  u Windows-ima preferences su u registry file
     *    HKEY_CURRENT_USER\Software\JavaSoft\Prefs\$USER_NAME\asoft
     */
    public static final String ASOFT_PREFS = USER_NAME + "/asoft";  // NOI18N
    /** preferences instanca za Asoft */
//    public static XMLLocalPreferences  aPrefs = AsoftPersistentPreferences.localPref;
    /** rachel path za Main resurse (icons, xml, itd) */
    public static final String JAR_PATH = "class://org.asoft.Main/org/asoft/res/";  // NOI18N
    /** rachel path za Main potpisani jar (META-INF/asoft.dsa) */
    public static final String PUBLIC_KEY_PATH =
        "class://org.asoft.Main/org/asoft/res/asoft-special.png"; // NOI18N
    /** ime DB connection pool-a */
    public static final String ASOFT_CONNECTION_POOL_NAME = "postgresql"; // NOI18N
    /** ime DB connection apache dbcp pool-a */
    public static final String ASOFT_CONNECTION_DBCP_POOL_NAME =
        "jdbc:apache:commons:dbcp:" + ASOFT_CONNECTION_POOL_NAME; // NOI18N
    /** skracenica za server (jedna rec) string sa kojim se startuje Asoft aplikacija
     *  za normalne korisnike, formira se iz xml file koji se distribuira sa javaws
     */
    public static String serverName = "default";
    /** DB driver connection string sa kojim se startuje Asoft aplikacija
     *  za normalne korisnike, formira se iz xml file koji se distribuira sa javaws
     */
    public static String dbConnStr = null;
    /** DB driver connection string sa kojim se startuje Asoft aplikacija
     *  za DB administratora, formira se iz xml file koji se distribuira sa javaws
     */
    public static String dbConnStrAdmin = null;
    /** default connection */
    public static Connection conn = null;
    /** oznaka korisnika da li je developer */
    public static boolean testAsoft = false;
    /** XML naziv firme */
    public static String firma = "Asoft-Pionir";  // NOI18N
    /** XML Asoft ID firme */
    public static String firmaid = "1234567890";  // NOI18N
    /** XML JNLP firme na Asoft Web */
    public static String firmajnlp = "";  // NOI18N
    /** XML skracenii naziv firme */
    public static String firmaKraciNaziv = "Asoft"; // NOI18N
    /** XML identifikacija da li je dozvoljena upotreba vise jezika u aplikaciji */
    public static boolean dozvoljenoViseJezika = false;
    /** XML locale firme */
    public static String xmlLocale = "";  // NOI18N
    /**  ime grupe koja predstavlje u bazi polje opis u tabeli adefault_posloviadministratora.grupa */
    public static String defaultGrupa = "Default - " + ac.firmaKraciNaziv; // NOI18N
    /** Main panel */
//    public static MainPanel mainPanel;
    /** Main frame */
    public static JFrame frame;
    /** Main split panel */
    public static JSplitPane splitPanel;
    /** Main thread za Asoft socket */
//    public static myWorkerThread mwt;
    /** Asoft pocetna grupa */
    public static AsoftGroupInfo defaultgroup = null;
    /** Trenutna Asoft grupa sa kojom se radi - koristiti je samo u slucaju zadavanja novog taska */
    public static AsoftGroupInfo tekucagrupa = null;
    /** spisak svih otvorenig grupa u aplikaciji */
    public static Hashtable<JScrollPane, AsoftGroupInfo> groups =
        new Hashtable<JScrollPane, AsoftGroupInfo>(0);
    /** Asoft status bar */
    public static AsoftStatusBar statusBar;
    /** MouseListener levog tab panela */
//    public static LeftMouseListener mouseLeftListener = new LeftMouseListener();
    /** MouseListener desnog tab panela */
//    public static MouseClickedListener mouseListener = new MouseClickedListener();
    /** Zavrsetak rada sa serverom */
//    public static ShuttingDownSignaler sds = new ShuttingDownSignaler();
    /** Popup window koji pokazuje da se puni klasa */
    public static AsoftSplashScreen assClassLoad;
    /** PopupMenuListener za tabove */
//    public static PopupMenuListener pml;
    /** Fontovi po izboru korisnika */
    public static Map<String, AsoftFontData> uiFonts;
    /** Ako je true, onda je postupak zatvaranja svih gornjih tabova u toku */
    public static boolean closeAllTabsInProgress;
    /** Virtuelna velicina dekstop-a (union svih video devices - screens) */
    public static Rectangle virtualBounds = new Rectangle();
    /** Virtuelna velicina dekstop-a preferred velicina */
    public static Rectangle preferredVirtualBounds = new Rectangle();
    /**Google translator instance */
    public static AsoftTranslatorPanel translator;
    /**Ako je javafx runtime instaliran */
    public static boolean javafx = false;
    /**Argumenti zadati prilikom poziva Asoft-a - dodao Vladimir Koković 13.09.2013 */
    public static String[] args;

    private ac() {
    }
        
  }

  static class LocaleDataModel extends AbstractTableModel {
    protected Object[][] locales;
    protected String[] columnNames = new String[]{
      "Država", "Jezik", "Oznaka"  // NOI18N
    };
    protected Class[] columnClasses = new Class[]{
      String.class, String.class, String.class
    };

    public LocaleDataModel(Set<String> loc) {
      int i = 0, len = loc.size();
      locales = new Object[len][];
//      for (String s : loc) {
//        locales[i++] = new Object[] {
//          s.substring(0, s.length() - 4), ac.dataLocale.getLanguage(s), ac.dataLocale.getCountry(s)
//        };
//      }
    }

        @Override
        public int getRowCount() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getColumnCount() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    
}

}
