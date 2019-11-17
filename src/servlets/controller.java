package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Created by cyril rocca Gr 2227 INPRES .
 */
@WebServlet(name = "Controller", urlPatterns = {"/servlets/Controller"})
public class controller extends HttpServlet implements HttpSessionListener {

    private String _host;
    private int _port;
    private String _separator;
    private String _endOfLine;
    private Socket _connexion = null;
    private PrintWriter _writer = null;
    private BufferedInputStream _reader = null;
    private ServletContext sc;
    private String typeLogin;
    private ArrayList<String> _arrayOfArg = new ArrayList<>();


    @Override
    public void init() throws ServletException {
        super.init();
        sc = getServletContext();
        sc.log("Start controller");
        System.out.println("Start controller");
        readPropertyFile();
        connectToServer();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("Do Post ");
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("Do Get ");
        process(req, resp);
    }


    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext sc = getServletContext();
        String action = req.getParameter("action");
        sc.log("-- valeur de action = " + action);

        if(action == null) {
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
        } else {
            switch (action) {
                case "LOGIN_VERIFY":
                    System.out.println("Dans LOGIN_VERIFY");
                    sc.log("LOGIN_VERIFY");

                    // 1. Recup num login entre :
                    int num_client = Integer.parseInt(req.getParameter("numcli"));
                    System.out.println("Num recupere de page login: " + num_client);

                    // 2. Definir type login "old_client"
                    typeLogin = "OLD_CLIENT";

                    // 3. Envoyer requete au serveur_compagnie pour verif num client
                    _arrayOfArg.clear();
                    _arrayOfArg.add(String.valueOf(num_client));
                    String requestLV = MakeRequest("LOGIN_VERIFY", _arrayOfArg );
                    String responseLV = SendRequest(requestLV);
                    System.out.println("Reponse serveur : *" + responseLV + "*");
                    AnalyseReponse("LOGIN_VERIFY",responseLV, req, resp);

                    break;
                case "LOGIN_NEW":
                    System.out.println("Dans LOGIN_NEW");

                    // 1. Definir type login "new_client"
                    typeLogin = "NEW_CLIENT";

                    // 2. Redirect sur formulaire :
                    redirectSurPage("formulaire_login.jsp", req, resp);



                    break;
                case "LOGIN_NEW_FORM" :
                    System.out.println("Dans LOGIN_NEW_FORM");
                    String nom_client = req.getParameter("nomcli");
                    String prenom_client = req.getParameter("prenomcli");
                    String adr_client = req.getParameter("adrcli");
                    String email_client = req.getParameter("emailcli");
                    System.out.println("Info recupere de formulaire login: " + nom_client + ", " + prenom_client + ", " + adr_client + ", " + email_client);

                    // traiter informations, recuperer numclient du serveur
                    _arrayOfArg.clear();
                    _arrayOfArg.add(nom_client);
                    _arrayOfArg.add(prenom_client);
                    _arrayOfArg.add(adr_client);
                    _arrayOfArg.add(email_client);
                    String requestLNF = MakeRequest("LOGIN_GENERATE", _arrayOfArg );
                    System.out.println("Requete : " + requestLNF);
                    String responseLNF = SendRequest(requestLNF);
                    System.out.println("Reponse serveur : *" + responseLNF + "*");
                    AnalyseReponse("LOGIN_GENERATE", responseLNF, req, resp);

                    break;
                case "ACHATS" :
                    System.out.println("Dans ACHATS");

                    // 1. Rediriger sur page achats
                    redirectSurPage("achats.jsp", req, resp);


                    break;
                case "ACHATS_DATE_CH" :
                    System.out.println("Dans ACHATS_DATE_CH");

                    String datetrnf = req.getParameter("datetr");
                    System.out.println("Date choisie par le client : " + datetrnf);

                    // transformer date en format bd :
                    String datetr = changeFormatStringDate(datetrnf);
                    System.out.println("Date format correct : " + datetr);

                    // TODO : envoyer requete au serveur : chercher les traversees pour date donnee


                    break;
                case "ACHATS_RET_MENU" :
                    redirectSurPage("achats.jsp", req, resp);

                    break;
                case "PROMO" :
                    System.out.println("Dans PROMO");


                    break;
                case "TERMINER" :
                    System.out.println("Dans TERMINER");

                    // 1. Old client - terminer session, retour page fin_session->login
                    redirectSurPage("fin_session.jsp", req, resp);

                    break;
//                case "TERMINER_FORM" :
//                    System.out.println("Dans TERMINER_FORM");
//
//                    // 1. Terminer session, retour page login
//                    RequestDispatcher rdTF = sc.getRequestDispatcher("/fin_session.jsp");
//                    sc.log("-- Tentative de redirection sur fin_session.jsp");
//                    rdTF.forward(req, resp);
//
//                    break;
                default:
                    System.out.println("Requete inconnue");
                    break;
            }
        }
    }


    private void AnalyseReponse(String cmd, String response, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String reponse_commande = "";

        String tokfull = response.replaceAll(".$", "");
        String[] tok = tokfull.split(_separator);
        String rep = tok[0];
        System.out.println(rep);

        switch(cmd)
        {
            case "LOGIN_VERIFY":
                if(rep.equals("ACK")) {

                    // aller dans menu
                    // TODO : passer numClient à la page menu
                    System.out.println("Client trouve");

                    redirectSurPage("menu.jsp", req, resp);

                } else {

                    // num client existe pas
                    // TODO : dire au client que numClient n'exste pas
                    System.out.println("Ce numero de client n'existe pas");

                    redirectSurPage("login.jsp", req, resp);
                }

                break;
            case "LOGIN_GENERATE" :
                if(rep.equals("ACK"))
                {
                    // recuperer numclient du serveur
                    String numCliGen = tok[1];

                    // rediriger sur menu
                    // TODO : passer numCliGen à la page menu

                    redirectSurPage("menu.jsp", req, resp);
                }

                break;
            case "A" :


                break;
            case "B" :


                break;
            default:
                response = "Commande inconnue";
                break;
        }
    }

        @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }


    private void connectToServer(){

        try {
            _connexion = new Socket(_host, _port);
            //_separator = ";";
            //_endOfLine = "#";
            sc.log("Connected to server");
            System.out.println("Connected to server");

            _writer = new PrintWriter(_connexion.getOutputStream(), true);
            _reader = new BufferedInputStream(_connexion.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String SendRequest(String request) throws IOException {

        _writer.write(request);
        _writer.flush();

        System.out.println("Commande [" + request + "] envoyée au serveur");

        //On attend la réponse
        String response = read();
        System.out.println("\t * " + response + " : Réponse reçue " + response);

        return response;
    }

    private String MakeRequest(String cmd, ArrayList<String> arrayOfArg) {
        String request = cmd;

        for(String str : arrayOfArg) {
            request += _separator + str;
        }

        request += _endOfLine;


        return request;
    }

    //Méthode pour lire les réponses du serveur
    private String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = _reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    public void readPropertyFile(){

        //Lecture PROPERTY FILE
        Properties _propFile = new Properties();
        InputStream _InStream = null;
        try
        {
            System.getProperty("user.dir");
            // recup user.dir
            _InStream = new FileInputStream("C:\\Users\\stasy\\Desktop\\RTI\\Labo\\Evaluation3\\Web_CheckIn\\resources\\config.properties");
            _propFile.load(_InStream);
            _port = Integer.parseInt(_propFile.getProperty("PORT"));
            _host = _propFile.getProperty("HOST");
            _separator = _propFile.getProperty("SEPARATOR");
            _endOfLine = _propFile.getProperty("ENDOFLINE");

            _InStream.close();

        } catch (IOException e) {
            System.err.println("Error Reading Properties Files [" + e + "]");
        }
    }

    private String changeFormatStringDate(String old_date) //2000-12-31
    {
        //String dateR = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String anneeS = old_date.substring(0, 4);
        String moisS = old_date.substring(5, 7);
        String jourS = old_date.substring(8, 10);
        String new_date = jourS + "/" + moisS + "/" + anneeS;

        return new_date;
    }

    private void redirectSurPage(String filename, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestDispatcher rd = sc.getRequestDispatcher("/" + filename);
        sc.log("-- Tentative de redirection sur " + filename);
        System.out.println("-- Tentative de redirection sur " + filename);
        rd.forward(req, resp);
    }


}
