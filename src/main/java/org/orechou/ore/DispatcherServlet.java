package org.orechou.ore;


import org.orechou.ore.bean.Handler;
import org.orechou.ore.bean.Params;
import org.orechou.ore.bean.Route;
import org.orechou.ore.holder.BeanHolder;
import org.orechou.ore.holder.ControllerHolder;
import org.orechou.ore.utils.HttpMethodConvertor;
import org.orechou.ore.utils.ReflectionUtil;
import org.orechou.ore.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 一个HttpServlet，用于处理所有的访问请求
 *
 * @Author OreChou
 * @Create 2017-12-07 20:06
 */
@WebServlet(urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

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
        // 请求的参数
        Params params = new Params();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = req.getParameter(name);
            params.put(name, value);
        }

        // 若没有找到相应的路由, 路由失败，需要返回 404 页面
        if (!ControllerHolder.isValidRoute(route)) {
            LOGGER.info(url + " " + method + "  Not Found");
            resp.setStatus(404);
        } else { // 根据相关的路由信息找到了其对应的处理函数
            Handler handler = ControllerHolder.getHandler(route);
            Object result = ReflectionUtil.invokeMethod(BeanHolder.getBeanInstance(handler.getControllerClass()), handler.getMethod(), params);
            ResponseUtil.writeJson(resp, result);
        }

    }
}
