package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.example.base.BaseServlet;

import com.google.common.base.Strings;

/**
 * @author Kenneth
 * @date 2020/2/20
 */
public class Thread extends BaseServlet {
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
        String count = Strings.isNullOrEmpty(request.getParameter("count")) ? "10"
                : request.getParameter("count");
        String sec = Strings.isNullOrEmpty(request.getParameter("sec")) ? "3"
                : request.getParameter("sec");

        String result = createExecutorService(Integer.valueOf(count), Integer.valueOf(sec));

        defaultResponse(request, response,
                "count为空循环10次.<br/>sec为空睡眠3秒<br/>" + "返回转状态码为:" + result);
    }

    private static final ExecutorService executorService = new ScheduledThreadPoolExecutor(66);

    private String createExecutorService(Integer count, Integer sec) {
        Integer i = 1;
        List<Future> futureList = new ArrayList<>();
        Random random = new Random();
        while (true) {
            ExecutorService executor = executorService;
            Future<String> future = executor.submit(() -> {

                int nextInt = random.nextInt(10);
                System.out.println("nextInt:" + nextInt);
                if (nextInt > 5) {
                    if (nextInt > 8) {
                        try {
                            TimeUnit.SECONDS.sleep(1000000000);
                        } catch (Exception ex) {
                        }
                    }
                    throw new Exception("随机数大于5报异常!");
                }
                return "My Thread !";
            });
            futureList.add(future);
            if (i.equals(count)) {
                break;
            }
            System.out.println("line:" + i);
            i++;
        }

        StringBuffer sb = new StringBuffer();
        for (Future future : futureList) {
            try {
                Object o = future.get(10, TimeUnit.SECONDS);
                sb.append(o + "<br/>");
                System.out.println("future:" + o);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        return sb.toString() + "返回行数:" + futureList.size();

    }
}
