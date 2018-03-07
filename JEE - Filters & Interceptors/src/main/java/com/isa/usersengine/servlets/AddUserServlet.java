package com.isa.usersengine.servlets;


import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.dao.UsersRepositoryDaoBean;
import com.isa.usersengine.domain.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet {

    @EJB
    UsersRepositoryDao usersRepositoryDao;

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

        usersRepositoryDao.addUser(user);       // TWORZYC JAKO BEANY W EE
        resp.sendRedirect("/users-list");
    }
}

// parametry z doPost -> dodaje usera -> idzie do metody add -> widzi ze interceptor
// -> modyfikuje metode -> wraca do servletu i dodaje usera -> przekierowuje do /users-list i wyswietla