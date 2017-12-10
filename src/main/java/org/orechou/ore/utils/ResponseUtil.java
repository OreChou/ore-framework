package org.orechou.ore.utils;

import com.google.gson.Gson;
import org.orechou.ore.constants.MimeType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回的工具类
 *
 * @Author OreChou
 * @Create 2017-12-10 10:31
 */
public final class ResponseUtil {

    public static void writeJson(HttpServletResponse response, Object result) throws IOException {
        response.setContentType(MimeType.JSON);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(result));
        out.flush();
        out.close();
    }

}
