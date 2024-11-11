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

public class DspServlet extends HttpServlet {
    private String scanPackage = "cn.tom.controller";  // 控制器扫描包路径
    private Map<String, Method> urlMethodMap = new HashMap<>();  // 存储URL与方法的映射
    private Map<String, Object> controllerMap = new HashMap<>();  // 存储URL与控制器实例的映射

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("初始化方法：程序启动后第一次请求时执行。");

        try {
            // 动态加载控制器类
            Class<?> controllerClass = Class.forName(scanPackage + ".BookController");
            Object controllerInstance = controllerClass.newInstance();  // 创建控制器实例

            // 获取控制器方法
            Method addMethod = controllerClass.getMethod("add", HttpServletRequest.class, HttpServletResponse.class);
            Method updateMethod = controllerClass.getMethod("update", HttpServletRequest.class, HttpServletResponse.class);
            Method delMethod = controllerClass.getMethod("del", HttpServletRequest.class, HttpServletResponse.class);
            Method findallMethod = controllerClass.getMethod("findall", HttpServletRequest.class, HttpServletResponse.class);

            // 将URL路径与对应的方法映射关系保存到urlMethodMap中
            urlMethodMap.put("/add.do", addMethod);
            urlMethodMap.put("/update.do", updateMethod);
            urlMethodMap.put("/del.do", delMethod);
            urlMethodMap.put("/findall.do", findallMethod);

            // 将URL路径与对应的控制器实例映射关系保存到controllerMap中
            controllerMap.put("/add.do", controllerInstance);
            controllerMap.put("/update.do", controllerInstance);
            controllerMap.put("/del.do", controllerInstance);
            controllerMap.put("/findall.do", controllerInstance);


            // AuthorController
            Class<?> authorControllerClass = Class.forName(scanPackage + ".AuthorController");
            Object authorControllerInstance = authorControllerClass.newInstance();
            Method authorAddMethod = authorControllerClass.getMethod("add", HttpServletRequest.class, HttpServletResponse.class);
            Method authorUpdateMethod = authorControllerClass.getMethod("update", HttpServletRequest.class, HttpServletResponse.class);

            // 映射新的 URL 到方法
            urlMethodMap.put("/authorAdd.do", authorAddMethod);
            urlMethodMap.put("/authorUpdate.do", authorUpdateMethod);
            controllerMap.put("/authorAdd.do", authorControllerInstance);
            controllerMap.put("/authorUpdate.do", authorControllerInstance);

        } catch (Exception e) {
            throw new ServletException("初始化错误：" + e.getMessage(), e);  // 初始化失败时抛出异常
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);  // POST请求转发到GET请求处理方法
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求路径，去除上下文路径部分
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // 根据路径从urlMethodMap中获取对应的方法
        Method method = urlMethodMap.get(path);
        if (method != null) {
            try {
                // 获取对应路径的控制器实例并反射调用相应的方法
                Object controller = controllerMap.get(path);
                method.invoke(controller, request, response);
            } catch (Exception e) {
                response.getWriter().write("方法调用错误：" + e.getMessage());  // 方法调用异常时返回错误信息
            }
        } else {
            response.getWriter().write("404 未找到");  // 路径未找到时返回404错误
        }
    }
}