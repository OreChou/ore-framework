package org.orechou.ore;


import org.orechou.ore.bean.Route;
import org.orechou.ore.utils.HttpMethodConvertor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 一个HttpServlet，用于处理所有的访问请求
 *
 * @Author OreChou
 * @Create 2017-12-07 20:06
 */
@WebServlet(urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        Bootstrap.init();
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取访问的url
        String url = req.getPathInfo();
        // 获取HTTP
        String method = req.getMethod();
        // 生成对应的 Route
        Route route = new Route(url, HttpMethodConvertor.convert(method));


        System.out.println(url);
        System.out.println(method);

    }
}
