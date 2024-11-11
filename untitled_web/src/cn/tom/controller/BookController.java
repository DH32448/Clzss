package cn.tom.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author X
 */
public class BookController {
    public BookController() {
        System.out.println(this.getClass()  + "本构造方法， 只能被调用一次，并且有一次");
    }
    // 参数的绑定， 尝试绑定 HttpServletRequest, HttpServletResponse
    public void add(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println(this.getClass() + " Add 方法被调用");
        try {
            response.getWriter().write("<h2>" + this.getClass() + " Add 方法被调用" + "</h2>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // 参数的绑定， 尝试绑定 HttpServletRequest, HttpServletResponse
    public void update(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println(this.getClass() + " update 方法被调用");
        try {
            response.getWriter().write("<h2>" + this.getClass() + " update 方法被调用" + "</h2>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // 参数的绑定， 尝试绑定 HttpServletRequest, HttpServletResponse
    public void del(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println(this.getClass() + " del 方法被调用");
        try {
            response.getWriter().write("<h2>" + this.getClass() + " del 方法被调用" + "</h2>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // 参数的绑定， 尝试绑定 HttpServletRequest, HttpServletResponse
    public void findall(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println(this.getClass() + " findall 方法被调用");
        try {
            response.getWriter().write("<h2>" + this.getClass() + " findall 方法被调用" + "</h2>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
