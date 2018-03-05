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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/users-list")
public class UsersListServlet extends HttpServlet {
    @EJB
    UsersRepositoryDao usersRepositoryDao;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = usersRepositoryDao.getUsersList();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("users", list);
        Template template = TemplateProvider.createTemplate(getServletContext(), "users-list.ftlh");
        PrintWriter printWriter = resp.getWriter();
        if (list == null) {
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