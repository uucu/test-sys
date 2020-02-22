package org.example;

import java.io.IOException;
import java.util.List;

import org.example.base.BaseServlet;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * @author Kenneth
 * @date 2020/2/20
 */
public class Loop extends BaseServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    List<String> list = Lists.newArrayListWithCapacity(10);

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
        String count = Strings.isNullOrEmpty(request.getParameter("count")) ? "10"
                : request.getParameter("count");
        System.out.println("start:" + list.size());
        while (true) {
            list.add("No:" + list.size());
            System.out.println(list.size());
            if (list.size() == Integer.valueOf(count)) {
                break;
            }
        }
        defaultResponse(request, response,
                "count为循环次数,count为空默认10次,0为死循环慎用!<br/>" + "循环次数为:" + count);
    }
}
