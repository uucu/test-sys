package org.example;

import java.io.IOException;

import org.example.base.BaseServlet;

import com.google.common.base.Strings;

/**
 * @author Kenneth
 * @date 2020/2/20
 */
public class Play extends BaseServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        if (!Strings.isNullOrEmpty(request.getParameter("g"))
                && !Strings.isNullOrEmpty(System.getenv().get("HOST_ID"))) {
            if (!System.getenv().get("HOST_ID").equals(request.getParameter("g"))) {
                defaultResponse(request, response);
            }
        }
        String status = Strings.isNullOrEmpty(request.getParameter("status")) ? "200"
                : request.getParameter("status");
        Integer value = Integer.valueOf(status);
        response.setStatus(value);
        defaultResponse(request, response, "status为空返回200状态码.<br/>" + "返回转状态码为:" + status);
    }
}
