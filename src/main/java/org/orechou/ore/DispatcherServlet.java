package org.orechou.ore;


import com.google.gson.Gson;
import org.orechou.ore.annotation.RequestBody;
import org.orechou.ore.bean.Handler;
import org.orechou.ore.bean.Params;
import org.orechou.ore.bean.Route;
import org.orechou.ore.constants.MimeType;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.stream.Stream;

/**
 * 一个HttpServlet，用于处理所有的访问请求
 * TODO: 1.service 方法应该重构，要能够处理多种提交数据的情况
 * TODO: 2.service 方法应该变成多线程的方法
 *
 * @Author OreChou
 * @Create 2017-12-07 20:06
 */
@WebServlet(urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    /**
     * 初始化 Servlet
     * @param config
     * @throws ServletException
     */
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
        // 获取ContentType
        String contentType = req.getContentType();
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
            Object result;

            if (contentType.equals(MimeType.JSON)) {
                // 读取request中提交的body数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));

                Stream<String> lines = reader.lines();
                StringBuffer json = new StringBuffer();
                lines.forEach(line -> json.append(line));
                Object param = null;
                for (Parameter parameter : handler.getMethod().getParameters()) {
                    // 一个方法中应该只有一个参数有@RequestBody注解
                    if (parameter.isAnnotationPresent(RequestBody.class)) {
                        Class<?> parameterClass = parameter.getType();
                        Gson gson = new Gson();
                        param = gson.fromJson(json.toString(), parameterClass);
                        break;
                    }
                }
                result = ReflectionUtil.invokeMethod(BeanHolder.getBeanInstance(handler.getControllerClass()), handler.getMethod(), param);
            } else {
                result = ReflectionUtil.invokeMethod(BeanHolder.getBeanInstance(handler.getControllerClass()), handler.getMethod(), params);
            }
            ResponseUtil.writeJson(resp, result);
        }

    }
}
