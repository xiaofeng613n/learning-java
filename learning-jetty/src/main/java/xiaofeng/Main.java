package xiaofeng;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiao on 2018/8/16.
 */
public class Main {

	public static void main(String[] args) {
		Server jettyServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setReuseAddress(true);
		connector.setPort(8800);
		jettyServer.setConnectors(new Connector[] {connector});
		jettyServer.setHandler(new HelloHandler());

		try {
			jettyServer.start();
			while (!jettyServer.isStarted()) {
				Thread.sleep(500);
			}
		} catch (Exception ex) {
			//LOG.error("Error starting Jetty. JSON Metrics may not be available.", ex);
		}
		System.out.println("ok");
	}
}
