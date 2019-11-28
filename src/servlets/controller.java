package servlets;

import DataClass.Panier;
import DataClass.Traversees;

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

import static java.lang.String.valueOf;

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
        System.out.println("* * * * * Dans Destroy() * * * * *");
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        if(session.isNew()){
            System.out.println("/!\\ Nouvelle session creee");
        }
        else {
            System.out.println("/!\\ Connecte a la session ouverte");

        }
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
                    _arrayOfArg.add(valueOf(num_client));
                    String requestLV = MakeRequest("LOGIN_VERIFY", _arrayOfArg, false);
                    String responseLV = SendRequest(requestLV);
                    System.out.println("Reponse serveur : *" + responseLV + "*");
                    AnalyseReponse("LOGIN_VERIFY",responseLV, req, resp, session);


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
                    String requestLNF = MakeRequest("LOGIN_GENERATE", _arrayOfArg, false);
                    System.out.println("Requete : " + requestLNF);
                    String responseLNF = SendRequest(requestLNF);
                    System.out.println("Reponse serveur : *" + responseLNF + "*");
                    AnalyseReponse("LOGIN_GENERATE", responseLNF, req, resp, session);


                    break;
                case "MENU_ERR_LOGIN" :

                    // Redigiger sur la page login de base, terminer session :
                    System.out.println("/!\\ Suppression de la session en cours... ");
                    session.invalidate();
                    redirectSurPage("login.jsp", req, resp);


                    break;
                case "ACHATS" :
                    System.out.println("Dans ACHATS");

                    // Rediriger sur page achats
                    redirectSurPage("achats.jsp", req, resp);


                    break;
                case "ACHATS_DATE_CH" :
                    System.out.println("Dans ACHATS_DATE_CH");

                    String datetrnf = req.getParameter("datetr");
                    System.out.println("Date choisie par le client : " + datetrnf);

                    // Transformer date en format bd :
                    String datetr = changeFormatStringDate(datetrnf);
                    System.out.println("Date format correct : " + datetr);

                    // Envoyer requete au serveur : chercher les traversees pour date donnee
                    _arrayOfArg.clear();
                    _arrayOfArg.add(datetr);
                    String requestADC = MakeRequest("ACHATS_LISTE_TRAV_RECH", _arrayOfArg, true);
                    System.out.println("Requete : " + requestADC);
                    String responseADC = SendRequest(requestADC);
                    System.out.println("Reponse serveur : *" + responseADC + "*");
                    AnalyseReponse("ACHATS_LISTE_TRAV_RECH", responseADC, req, resp, session);


                    break;
                case "ACHATS_RET_MENU" : case "PROMO_RET_MENU" :
                    System.out.println("Dans ACHATS_RET_MENU");

                    // Rediriger sur page menu
                    redirectSurPage("menu.jsp", req, resp);


                    break;

                case "CHECKOUT_RET_PANIER" :
                    System.out.println("Dans CHECKOUT_RET_PANIER");

                    redirectSurPage("panier.jsp", req, resp);


                    break;
                case "CHECKOUT" :
                    System.out.println("Dans CHECKOUT");

                    redirectSurPage("checkout.jsp", req, resp);

                    break;
                case "CHECKOUT_CONFIRM" :

                    System.out.println("Dans CHECKOUT_CONFIRM");

                    // TODO : compter nb res, compter prix total
                    int nbRes = 0;
                    int prixTotal = 777;

                    // Redirect sur page confirmation achats
                    session.setAttribute("nbRes", nbRes);
                    session.setAttribute("prixTotal", prixTotal);
                    redirectSurPage("checkout_confirm.jsp", req, resp);

                    break;


                case "CHECKOUT_MERCI" :
                    System.out.println("Dans CHECKOUT_MERCI");

                    // TODO : envoyer requete d'achat au serveur


                    // Redirect sur page confirmation achats
//                    session.setAttribute("nbRes", nbRes);
//                    session.setAttribute("prixTotal", prixTotal);
                    redirectSurPage("checkout_merci.jsp", req, resp);
                    break;

                case "PROMO_INIT" :
                    System.out.println("Dans PROMO_INIT");

                    // trouver date aj
                    String datetrp = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    System.out.println("Date format correct : " + datetrp);

                    // Envoyer requete au serveur : chercher les traversees pour date donnee
                    _arrayOfArg.clear();
                    _arrayOfArg.add(datetrp);
                    String requestPI = MakeRequest("PROMO_LISTE_TRAV_RECH", _arrayOfArg, true);
                    System.out.println("Requete : " + requestPI);
                    String responsePI = SendRequest(requestPI);
                    System.out.println("Reponse serveur : *" + responsePI + "*");

                    // redirect sur page promo
                    AnalyseReponse("PROMO_LISTE_TRAV_RECH", responsePI, req, resp, session);


                    break;
                case "PROMO" :
                    System.out.println("Dans PROMO");


                    break;
                case "TERMINER" :
                    System.out.println("Dans TERMINER");

                    // 1. Old client - terminer session, retour page fin_session->login
                    System.out.println("/!\\ Suppression de la session en cours... ");
                    session.invalidate();
                    System.out.println("succes.");
                    redirectSurPage("html_finsession.jsp", req, resp);


                    break;
                case "ADD_PANIER" :
                    System.out.println("Dans ADD_PANIER");


                    System.out.println("Num CLient : " + (String)session.getAttribute("numCli"));

                    // Envoyer requete au serveur : chercher les traversees pour date donnee
                    _arrayOfArg.clear();
                    _arrayOfArg.add((String)session.getAttribute("numCl"));
                    _arrayOfArg.add(req.getParameter("traverseesId"));
                    _arrayOfArg.add(req.getParameter("prix"));
                    requestADC = MakeRequest("ADD_PANIER", _arrayOfArg, true);
                    System.out.println("Requete : " + requestADC);
                    responseADC = SendRequest(requestADC);
                    System.out.println("Reponse serveur : *" + responseADC + "*");
                    AnalyseReponse("ADD_PANIER", responseADC, req, resp, session);


                    break;

                case "PANIER":
                    System.out.println("Dans PANIER");

                    System.out.println("Num CLient : " + (String)session.getAttribute("numCli"));

                    // Envoyer requete au serveur : chercher les traversees pour date donnee
                    _arrayOfArg.clear();
                    _arrayOfArg.add((String)session.getAttribute("numCl"));
                    requestADC = MakeRequest("PANIER", _arrayOfArg, true);
                    System.out.println("Requete : " + requestADC);
                    responseADC = SendRequest(requestADC);
                    System.out.println("Reponse serveur : *" + responseADC + "*");
                    AnalyseReponse("PANIER", responseADC, req, resp, session);

                    break;

                case "PANIER_RETOUR" :
                    System.out.println("Dans PANIER_RETOUR");


                    // Redirect sur page prec
                    session.setAttribute("ajoutPanier","NAVIRE_IS_FULL");
                    String page_prec = req.getParameter("page_prec");
                    redirectSurPage(page_prec, req, resp);


                    break;

                case "REMOVE_FROM_PANIER":

                    System.out.println("Dans REMOVE_FROM_PANIER");


                    System.out.println("ID PANIER : " + (String)session.getAttribute("panierId"));

                    // Envoyer requete au serveur : chercher les traversees pour date donnee
                    _arrayOfArg.clear();
                    _arrayOfArg.add(req.getParameter("panierId"));
                    _arrayOfArg.add(req.getParameter("numCli"));
                    requestADC = MakeRequest("REMOVE_FROM_PANIER", _arrayOfArg, true);
                    System.out.println("Requete : " + requestADC);
                    responseADC = SendRequest(requestADC);
                    System.out.println("Reponse serveur : *" + responseADC + "*");
                    AnalyseReponse("PANIER", responseADC, req, resp, session);


                    break;

                case "PAYEMENT" :

                    break;
//                case "TERMINER_FORM" :
//                    System.out.println("Dans TERMINER_FORM");
//
//                    // 1. Terminer session, retour page login
//                    RequestDispatcher rdTF = sc.getRequestDispatcher("/html_finsession.jsp");
//                    sc.log("-- Tentative de redirection sur html_finsession.jsp");
//                    rdTF.forward(req, resp);
//
//                    break;
                default:
                    System.out.println("Requete inconnue");
                    break;
            }
        }
    }


    private void AnalyseReponse(String cmd, String response, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException{
        String reponse_commande = "";

        String tokfull = response.replaceAll(".$", "");
        String[] tok = tokfull.split(_separator);
        String rep = tok[0];
        System.out.println(rep);

        switch(cmd)
        {
            case "LOGIN_VERIFY":
                if(rep.equals("ACK")) {
                    System.out.println("Client trouve");

                    //int NumClient = Integer.parseInt(req.getParameter("numcli"));
                    String NumClient = req.getParameter("numcli");

                    // 1. Sauvegarder numclient dans la sssion
                    session.setAttribute("numCl", NumClient);

                    // 2. Redirect sur menu
                    redirectSurPage("menu.jsp", req, resp);


                } else {
                    System.out.println("Ce numero de client n'existe pas");

                    // Output
                    session.setAttribute("action", "LOGIN_FAIL");

                    redirectSurPage("login.jsp", req, resp);
                }

                break;
            case "LOGIN_GENERATE" :
                if(rep.equals("ACK"))
                {
                    // recuperer numclient du serveur
                    String numCliGen = tok[1];

                    // 1. Sauvegarder numclient dans la session
                    session.setAttribute("numCl", numCliGen);

                    // 2. Redirect sur menu
                    redirectSurPage("menu.jsp", req, resp);

                }

                break;
            case "ACHATS_LISTE_TRAV_RECH" :
                if(rep.equals("ACK"))
                {

                    String tokenn = tokfull.replaceAll("ACK", "");
                    String token = tokenn.replaceAll("#", "");
                    String[] tokens = token.split("\\|");


                    // 1. boucle : creer liste idtraversees, horaires, destinations
                    ArrayList<Traversees> listTravALTR = new ArrayList<>();


                    System.out.println("Liste de traversees pour la date :");
                    for (String str : tokens)
                    {

                        String[] oneTravSplit = str.split(";");
                        Traversees trav = new Traversees();
                        trav.set_idTraversees(oneTravSplit[1]);
                        trav.set_heureDep(oneTravSplit[2]);
                        trav.set_destination(oneTravSplit[3]);
                        trav.set_prix(oneTravSplit[4]);

                        listTravALTR.add(trav);

                        //System.out.println(tok[i_tr] + " - " + tok[i_hor] + " - " + tok[i_des] + " - " + tok[i_pr]);
                    }

                    // 2. Sauvegarder liste dans objet session
                    session.setAttribute("listTrav", listTravALTR);

                    // 3. Redirect sur achats
                    session.setAttribute("action","ACHATS_LISTE_TRAV_TROUV");
                    redirectSurPage("achats.jsp", req, resp);

                }
                else {
                    System.out.println("Pas de traversees trouvees");
                    // Redirect sur page prec
                    session.setAttribute("action","ACHATS_LISTE_TRAV_VIDE");
                    redirectSurPage("achats.jsp", req, resp);
                }

                break;
            case "ADD_PANIER":
                if(rep.equals("ACK"))
                {

                    System.out.println("Ajout panier : OK");

                    session.setAttribute("ajoutPanier","ACK");
                    String page_prec = req.getParameter("page_prec");
                    redirectSurPage(page_prec, req, resp);

                } else {

                    System.out.println("Ajout panier : Fail");

                    // Redirect sur page prec
                    session.setAttribute("ajoutPanier","NAVIRE_IS_FULL");
                    String page_prec = req.getParameter("page_prec");
                    redirectSurPage(page_prec, req, resp);
                }
                break;
            case "PANIER" :

                if(rep.equals("ACK")) {
                    String tokenP = tokfull.replaceAll("ACK", "");
                    String token = tokenP.replaceAll("#", "");
                    String[] tokens = token.split("\\|");

                    // 1. boucle : creer liste idtraversees, horaires, destinations
                    ArrayList<Panier> list_Panier = new ArrayList<>();

                    System.out.println("Liste d'articles dans le panier :");
                    for (String str : tokens)
                    {

                        String[] onePanierSplit = str.split(";");
                        Panier panier = new Panier();
                        panier.set_id_panier(onePanierSplit[1]);
                        panier.set_traversee_id(onePanierSplit[2]);
                        panier.set_client_id(onePanierSplit[3]);
                        panier.set_prix(onePanierSplit[4]);

                        list_Panier.add(panier);

                    }

                    // 2. Sauvegarder liste dans objet session
                    session.setAttribute("list_Panier", list_Panier);

                    session.setAttribute("action", "GET_PANIER_OK");

                    // 2. Redirect sur menu
                    System.out.println("---Valeur page_prec=" + req.getParameter("page_prec"));

                    session.setAttribute("page_prec", req.getParameter("page_prec"));
                    redirectSurPage("panier.jsp", req, resp);


                } else {
                    System.out.println("Erreur fetching panier");

                    // Output
                    session.setAttribute("action", "GET_PANIER_FAIL");

                    redirectSurPage("error.jsp", req, resp);
                }


                break;



            case "REMOVE_FROM_PANIER":

                if(rep.equals("ACK")) {
                    String tokenn = tokfull.replaceAll("ACK", "");
                    String token = tokenn.replaceAll("#", "");
                    String[] tokens = token.split("\\|");


                    // 1. boucle : creer liste idtraversees, horaires, destinations
                    ArrayList<Panier> list_Panier = new ArrayList<>();


                    System.out.println("Liste d'achats dans un panier :");
                    for (String str : tokens)
                    {

                        String[] onePanierSplit = str.split(";");
                        Panier panier = new Panier();
                        panier.set_id_panier(onePanierSplit[1]);
                        panier.set_traversee_id(onePanierSplit[2]);
                        panier.set_client_id(onePanierSplit[3]);
                        panier.set_prix(onePanierSplit[4]);

                        list_Panier.add(panier);
                    }

                    // 2. Sauvegarder liste dans objet session
                    session.setAttribute("list_Panier", list_Panier);
                    session.setAttribute("action", "GET_PANIER_OK");

                    // 2. Redirect sur menu
                    redirectSurPage("panier.jsp", req, resp);


                } else {
                    System.out.println("Erreur removing item from panier");

                    // Output
                    session.setAttribute("action", "REMOVE_ITEM_PANIER_FAIL");

                    redirectSurPage("error.jsp", req, resp);
                }


                break;

            case "PROMO_LISTE_TRAV_RECH" :
                if(rep.equals("ACK"))
                {
                    String tokenPLTR = tokfull.replaceAll("ACK", "");
                    String token = tokenPLTR.replaceAll("#", "");
                    String[] tokens = token.split("\\|");

                    // 1. boucle : creer liste idtraversees, horaires, destination
                    ArrayList<Traversees> listTravPLTR = new ArrayList<>();

                    System.out.println("Liste de last minutes d'auj :");
                    for (String str : tokens)
                    {

                        String[] oneTravSplit = str.split(";");
                        Traversees trav = new Traversees();
                        trav.set_idTraversees(oneTravSplit[1]);
                        trav.set_heureDep(oneTravSplit[2]);
                        trav.set_destination(oneTravSplit[3]);
                        trav.set_prix(oneTravSplit[4]);

                        listTravPLTR.add(trav);
                    }

                    // 2. Sauvegarder liste dans objet session
                    session.setAttribute("listTrav", listTravPLTR);

                    // 3. Redirect sur achats
                    session.setAttribute("action","PROMO_LISTE_TRAV_TROUV");
                    redirectSurPage("promotions.jsp", req, resp);

                }
                else {
                    System.out.println("Pas de traversees trouvees");
                    // Redirect sur achats
                    session.setAttribute("action","PROMO_LISTE_TRAV_VIDE");
                    redirectSurPage("promotions.jsp", req, resp);
                }




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

    private String MakeRequest(String cmd, ArrayList<String> arrayOfArg, boolean EBOOP) {

        String request = cmd;

        for(String str : arrayOfArg) {
            request += _separator + str;
        }
        if(EBOOP) {
            request += _separator + "EBOOP";
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
