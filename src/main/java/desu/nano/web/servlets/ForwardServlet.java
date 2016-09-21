package desu.nano.web.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * This servlet catch request with path '/wiki/', transform it path to GET-param and redirect it to main page
 *
 * Created by Ker on 20.09.2016.
 */
public class ForwardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo().substring(1);
        Cookie langCookie = Arrays.asList(req.getCookies()).stream().filter(c -> "lang".equals(c.getName())).findFirst().get();
        String lang = langCookie == null ? "" : "&lang=" + langCookie.getValue();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.xhtml?page=" + path + lang);
        dispatcher.forward(req,resp);
    }
}
