package com.isa.usersengine.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/welcome-user")
public class WeclomeUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");

        if (name == null || name.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        PrintWriter printWriter = resp.getWriter();
        printWriter.write("<!DOCTYPE html>");
        printWriter.write("<html>");
        printWriter.write("<body>");
        printWriter.write("Hello:" + name + "!");
        printWriter.write("</body>");
        printWriter.write("</html>");
    }
}
