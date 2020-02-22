package org.example.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kenneth
 * @date 2020/2/21
 */
public class BaseServlet extends HttpServlet {
    public String systemInfo() {
        SystemInfo.getIPAddress();
        SystemInfo.getMAC();
        SystemInfo.getSystemName();
        return "<<< SystemName: " + SystemInfo.getSystemName() + " >>><br/>" //
                + "<<< MAC: " + SystemInfo.getMAC() + " >>><br/>"//
                + "<<< IPAddress: " + SystemInfo.getIPAddress() + " >>><br/>";
    }

    public void defaultResponse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println(systemInfo() + "正常返回.");
        out.flush();
        out.close();
        return;
    }

    public void defaultResponse(HttpServletRequest request, HttpServletResponse response,
            String msg) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println(systemInfo() + msg);
        out.flush();
        out.close();
    }
}
