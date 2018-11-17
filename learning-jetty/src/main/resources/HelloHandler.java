package xiaofeng;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiao on 2018/8/16.
 */
public class HelloHandler extends AbstractHandler {
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
		System.out.println(target);


		if (request.getContentLength() > 0) {
			InputStream inputStream = null;
			FileOutputStream outputStream = null;
			try {
				inputStream = request.getInputStream();
				// 给新文件拼上时间毫秒，防止重名
				long now = System.currentTimeMillis();
				File file = new File("d:/", "file-" + now + ".txt");
				file.createNewFile();

				outputStream = new FileOutputStream(file);

				byte temp[] = new byte[1024];
				int size = -1;
				while ((size = inputStream.read(temp)) != -1) { // 每次读取1KB，直至读完
					outputStream.write(temp, 0, size);
				}
//					logger.info("File load success.");
			} catch (IOException e) {
//					logger.warn("File load fail.", e);
				request.getRequestDispatcher("/fail.jsp").forward(request, response);
			} finally {
				outputStream.close();
				inputStream.close();
			}
		}
//		request.getRequestDispatcher("/succ.jsp").forward(request, response);
		response.setContentType("application/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);

			response.getWriter().write("hello");
			response.flushBuffer();
		((Request) request).setHandled(true);
	}


}
