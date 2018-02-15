package com.isa.usersengine.servlets;

import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.dao.UsersRepositoryDaoBean;
import com.isa.usersengine.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/find-user-by-id")
public class FindUserByIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));

        UsersRepositoryDao usersRepositoryDao = new UsersRepositoryDaoBean();
        User user = usersRepositoryDao.getUserById(id);

        PrintWriter printWriter = resp.getWriter();

        if (user == null) {
            printWriter.write("User not found");
            return;
        }

        printWriter.write("ID: " + user.getId() + "\n");
        printWriter.write("Name: " + user.getName() + "\n");
        printWriter.write("Login: " + user.getLogin() + "\n");
        printWriter.write("Age: " + user.getAge() + "\n");

    }
}
