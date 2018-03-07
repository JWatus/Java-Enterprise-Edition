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

@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int age = Integer.parseInt(req.getParameter("age"));

        user.setAge(age);
        user.setId(id);
        user.setLogin(login);
        user.setName(name);
        user.setPassword(password);

        UsersRepositoryDao newUser = new UsersRepositoryDaoBean();
        newUser.addUser(user);

        PrintWriter printWriter = resp.getWriter();

        if (user == null) {
            printWriter.write("User not found");
            return;
        }

        printWriter.write("ID: " + user.getId() + "\n");
        printWriter.write("Name: " + user.getName() + "\n");
        printWriter.write("Login: " + user.getLogin() + "\n");
        printWriter.write("Age: " + user.getAge() + "\n");
        printWriter.write("Password: " + user.getPassword() + "\n");

    }
}
