package com.isa.usersengine.servlets;

import com.isa.usersengine.cdi.MaxPulse;
import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.dao.UsersRepositoryDaoBean;
import com.isa.usersengine.domain.User;
import com.isa.usersengine.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/find-user-by-id")
public class FindUserByIdServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass().getName());

    @EJB
    UsersRepositoryDao usersRepositoryDao;

    @Inject
    MaxPulse maxPulse;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));

        usersRepositoryDao = new UsersRepositoryDaoBean();
        User user = usersRepositoryDao.getUserById(id);

        PrintWriter printWriter = resp.getWriter();

        if (user == null) {
            req.setAttribute("id", Integer.toString(id));
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/404");
            requestDispatcher.forward(req, resp);

//            resp.sendRedirect("/404");

            return;
        }

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", user);
        dataModel.put("maxPulse", maxPulse.getMaxPulse(user.getGender(), user.getAge()));

        Template template = TemplateProvider.createTemplate(getServletContext(), "find-user-by-id.ftlh");

        try {
            template.process(dataModel, printWriter);
        } catch (TemplateException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
