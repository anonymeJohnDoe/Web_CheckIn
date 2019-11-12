package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
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
        System.out.println("Do Post ");
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do Get ");
        process(req, resp);

    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if(action == null) {
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
        } else {
            switch (action) {
                case "login":

                    break;
            }
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
            _separator = ";";
            _endOfLine = "#";
            sc.log("Connect to server");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readPropertyFile(){

        //Lecture PROPERTY FILE
        Properties _propFile = new Properties();
        InputStream _InStream = null;
        try
        {
            System.getProperty("user.dir");
            _InStream = new FileInputStream("D:\\Workspace\\Web_CheckIn\\resources\\config.properties");
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


}
