package cn.tom.controller;

import cn.tom.anno.MyConter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@MyConter
public class AuthorController {
    public void add(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("Author Add 方法被调用");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("Author Update 方法被调用");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
