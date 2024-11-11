package cn.tom;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DspServlet2 extends HttpServlet {
    private String scanPackage = "cn.tom.controller";
    private Map<String, Method> urlMethodMap = new HashMap<>();
    private Map<String, Object> controllerMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Class<?> controllerClass = Class.forName(scanPackage + ".BookController");
            Object controllerInstance = controllerClass.newInstance();

            Method addMethod = controllerClass.getMethod("add", HttpServletRequest.class, HttpServletResponse.class);
            Method updateMethod = controllerClass.getMethod("update", HttpServletRequest.class, HttpServletResponse.class);
            Method delMethod = controllerClass.getMethod("del", HttpServletRequest.class, HttpServletResponse.class);
            Method findallMethod = controllerClass.getMethod("findall", HttpServletRequest.class, HttpServletResponse.class);

            urlMethodMap.put("/add.do", addMethod);
            urlMethodMap.put("/update.do", updateMethod);
            urlMethodMap.put("/del.do", delMethod);
            urlMethodMap.put("/findall.do", findallMethod);

            controllerMap.put("/add.do", controllerInstance);
            controllerMap.put("/update.do", controllerInstance);
            controllerMap.put("/del.do", controllerInstance);
            controllerMap.put("/findall.do", controllerInstance);

        } catch (Exception e) {
            throw new ServletException("初始化错误：" + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());

        Method method = urlMethodMap.get(path);
        if (method != null) {
            try {
                Object controller = controllerMap.get(path);
                method.invoke(controller, request, response);
            } catch (Exception e) {
                response.getWriter().write("方法调用错误：" + e.getMessage());
            }
        } else {
            response.getWriter().write("404 未找到");
        }
    }
}
