package com.isa.usersengine.servlets;

import com.isa.usersengine.cdi.RandomUserCDIApplicationDao;
import com.isa.usersengine.cdi.RandomUserCDIRequestDao;
import com.isa.usersengine.cdi.RandomUserCDISessionDao;
import com.isa.usersengine.domain.User;

import javax.inject.Inject;
import javax.persistence.DiscriminatorColumn;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/random-user")
public class RandomUserServlet extends HttpServlet {

    @Inject
    RandomUserCDIApplicationDao randomUserCDIApplicationDao;

    @Inject
    RandomUserCDISessionDao randomUserCDISessionDao;

    @Inject
    RandomUserCDIRequestDao randomUserCDIRequestDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter printWriter = resp.getWriter();

        User userOne = randomUserCDIRequestDao.getRandomUser();
        printWriter.write("Random User by request scoped:\n");
        printWriter.write("\n\n");
        printWriter.write("ID: " + userOne.getId() + "\n");
        printWriter.write("\n\n");

        User userTwo = randomUserCDISessionDao.getRandomUser();
        printWriter.write("Random User by session scoped:\n");
        printWriter.write("\n\n");
        printWriter.write("ID: " + userTwo.getId() + "\n");
        printWriter.write("\n\n");

        User userThree = randomUserCDIApplicationDao.getRandomUser();
        printWriter.write("Random User by application scoped:\n");
        printWriter.write("\n\n");
        printWriter.write("ID: " + userThree.getId() + "\n");
        printWriter.write("\n\n");
    }
}
