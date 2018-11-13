package com.furuida.controller;

import com.furuida.utils.BlockQueueConsumer;
import com.furuida.utils.ConsumerWork;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @program: furuidaapp
 * @description:
 * @author: fuzhengquan
 * @create: 2018-11-07 14:24
 **/
@WebServlet(name = "wxServlet",
        urlPatterns = "/wx/test",
        loadOnStartup = 1
)
public class HandlerServlet extends HttpServlet {
    private static final long serialVersionUID = 7109220574468622594L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        new Thread(() -> {
            BlockQueueConsumer.getInstance().consumer();
        }).start();
        System.out.println("servlet初始化2...");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.append("url:" + request.getRequestURI()+ ", str="+request.getQueryString() + ", param=" + request.getParameter("code"));
        pw.append("Hello Servlet!<br>" );

        //servletName
        pw.append("servletName：" + getServletName() + "<br>");

        //initParam
        ServletConfig servletConfig = this.getServletConfig();
        Enumeration<String> paramNames = servletConfig.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            pw.append(paramName + "：" + servletConfig.getInitParameter(paramName) + "<br>");
        }

        pw.close();

    }
}
