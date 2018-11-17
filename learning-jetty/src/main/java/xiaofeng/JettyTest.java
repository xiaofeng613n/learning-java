package xiaofeng;

/**
 * Created by xiaofeng on 2018/11/5
 * Description:
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.SessionManager;
//import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.mortbay.jetty.SessionManager;
import org.mortbay.jetty.servlet.HashSessionManager;


public class JettyTest {

    /**
     * 通过ServletContextHandler来作为handler
     * @throws Exception
     */
    public void addServletHandler1() throws Exception {
        Server server = new Server(9090);
        ServletContextHandler hand = new ServletContextHandler();
        hand.addServlet(new ServletHolder(new HttpServlet() {
            private static final long serialVersionUID = 166145627009966144L;
            public void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>Hello World</h1>");
                HttpSession s = req.getSession();
                String name = (String) s.getAttribute("name");
                response.getWriter().println("<h1>Session is:</h1>" + s + "," + name);
                response.getWriter().print("<br/>" + this.getServletContext().getRealPath("/"));
            }
        }), "/a");

        hand.addServlet(new ServletHolder(new HttpServlet() {
            private static final long serialVersionUID = 8426818915524669191L;
            public void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>Hello World!你好!</h1>");
                HttpSession s = req.getSession();
                s.setAttribute("name", "Jack");
                response.getWriter().print("<br/><a href='a'>你好杰克！</a>");
            }

        }), "/");

        // 设置内嵌的jetty支持session，默认情况下不支持session
        SessionManager sm = new HashSessionManager();
        hand.setSessionHandler(new SessionHandler(sm));
        server.setHandler(hand);
        server.start();
        server.join();
    }

    public static void main(String[] args) throws Exception {
        new JettyTest().addServletHandler1();
    }



    /**
     * 通过WebAppContext的set方法指定web.xml地址和项目地址以及url
     * @throws Exception
     */
    public void projectHandler2() throws Exception {
        String webapp = "D:\\programfiles\\MyEclipse10\\wk3\\day04\\WebRoot";
        Server server = new Server(80);
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webapp + "/WEB-INF/web.xml");
        context.setResourceBase(webapp);
        context.setContextPath("/");
        server.setHandler(context);
        server.start();
        server.join();
    }

    /**
     * 直接再WebAppContext构造函数中传入项目路径和url上下文
     * @throws Exception
     */
    public void projectHandler1() throws Exception {
        String webapp = "D:\\WebRoot";
        Server server = new Server(80);
        WebAppContext context = new WebAppContext(webapp, "/abc");
        server.setHandler(context);
        server.start();
        server.join();
    }
}