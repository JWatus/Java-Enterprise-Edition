package com.isa.usersengine.servlets;

import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.dao.UsersRepositoryDaoBean;
import com.isa.usersengine.domain.User;
import com.isa.usersengine.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/find-user-by-id")
public class FindUserByIdServlet extends HttpServlet {

    @EJB
    UsersRepositoryDao usersRepositoryDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));

        User user = usersRepositoryDao.getUserById(id);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", user);

        Template template = TemplateProvider.createTemplate(getServletContext(), "find-user-by-id.ftlh");

        PrintWriter printWriter = resp.getWriter();

        if (user == null) {
            printWriter.write("User not found");
            return;
        }

        try {
            template.process(dataModel, printWriter);
        } catch (TemplateException e){
            e.printStackTrace();
        }
    }
}
