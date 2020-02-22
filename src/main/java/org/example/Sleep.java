package org.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.base.BaseServlet;

import com.google.common.base.Strings;

/**
 * @author Kenneth
 * @date 2020/2/20
 */
public class Sleep extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        String sec = Strings.isNullOrEmpty(request.getParameter("sec")) ? "10"
                : request.getParameter("sec");

        if (!Strings.isNullOrEmpty(request.getParameter("g"))
                && !Strings.isNullOrEmpty(System.getenv().get("HOST_ID"))) {
            if (!System.getenv().get("HOST_ID").equals(request.getParameter("g"))) {
                defaultResponse(request, response);
            }
        }

        try {
            Long value = Long.valueOf(sec);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        defaultResponse(request, response, "sec为空默认10秒.<br/>" + "睡眠了:" + sec + "秒");
    }
}
