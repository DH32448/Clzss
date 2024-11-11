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

public class DspServlet3 extends HttpServlet {
    // 扫描的包路径
    private String scanPackage = "cn.tom.controller";
    // 存储 URL 和对应的方法映射
    private Map<String, Method> urlMethodMap = new HashMap<>();
    // 存储 URL 和对应的控制器实例映射
    private Map<String, Object> controllerMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            // 类对象 BookController
            Class<?> conClass = Class.forName(scanPackage + ".BookController");
            Object conInstance = conClass.newInstance();

            // 获取 Controller 中的方法
            Method addMethod = conClass.getMethod("add", HttpServletRequest.class, HttpServletResponse.class);
            Method updateMethod = conClass.getMethod("update", HttpServletRequest.class, HttpServletResponse.class);
            Method delMethod = conClass.getMethod("del", HttpServletRequest.class, HttpServletResponse.class);
            Method findallMethod = conClass.getMethod("findall", HttpServletRequest.class, HttpServletResponse.class);

            // 将 URL 和方法进行映射
            urlMethodMap.put("/add.do", addMethod);
            urlMethodMap.put("/update.do", updateMethod);
            urlMethodMap.put("/del.do", delMethod);
            urlMethodMap.put("/findall.do", findallMethod);

            // 将 URL 和控制器实例进行映射
            controllerMap.put("/add.do", conInstance);
            controllerMap.put("/update.do", conInstance);
            controllerMap.put("/del.do", conInstance);
            controllerMap.put("/findall.do", conInstance);

            // AuthorController
            Class<?> autConClass = Class.forName(scanPackage + ".AuthorController");
            Object autConInstance = autConClass.newInstance();
            Method autAddMethod = autConClass.getMethod("add", HttpServletRequest.class, HttpServletResponse.class);
            Method autUpdateMethod = autConClass.getMethod("update", HttpServletRequest.class, HttpServletResponse.class);

            urlMethodMap.put("/authorAdd.do", autAddMethod);
            urlMethodMap.put("/authorUpdate.do", autUpdateMethod);
            controllerMap.put("/authorAdd.do", autConInstance);
            controllerMap.put("/authorUpdate.do", autConInstance);

        } catch (Exception e) {
            // 初始化失败时抛出 ServletException
            throw new ServletException("初始化错误：" + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的路径
        String path = request.getServletPath();

        // 根据路径获取对应的方法
        Method method = urlMethodMap.get(path);
        if (method != null) {
            try {
                // 获取对应的控制器实例
                Object controller = controllerMap.get(path);
                // 调用方法并传递请求和响应对象
                method.invoke(controller, request, response);
            } catch (Exception e) {
                // 方法调用失败时返回错误信息
                response.getWriter().write("方法调用错误：" + e.getMessage());
            }
        } else {
            // 未找到对应的方法时返回 404 错误
            response.getWriter().write("404 未找到");
        }
    }
}
