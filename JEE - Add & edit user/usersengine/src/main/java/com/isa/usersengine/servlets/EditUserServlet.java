package com.isa.usersengine.servlets;

import com.isa.usersengine.cdi.FileUploadProcessor;
import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.domain.Gender;
import com.isa.usersengine.domain.User;
import com.isa.usersengine.exceptions.UserImageNotFound;
import com.isa.usersengine.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/edit-user")
@MultipartConfig
public class EditUserServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass().getName());

    File templatesPath;
    Template template;

    @EJB
    UsersRepositoryDao usersRepositoryDao;

    @Inject
    FileUploadProcessor fileUploadProcessor;

    @Override
    public void init() throws ServletException {
        try {
            template = TemplateProvider.createTemplate(getServletContext(), "add-edit-user.ftlh");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String parameterUserID = req.getParameter("id");
        int userID = -1;
        User user;
        if (parameterUserID != null && !parameterUserID.isEmpty()) {
            userID = Integer.parseInt(parameterUserID);
        }

        PrintWriter printWriter = resp.getWriter();
        Map<String, Object> dataModel = new HashMap<>();

        if (userID > 0) {
            user = usersRepositoryDao.getUserById(userID);
            dataModel.put("user", user);
            dataModel.put("action", "edit-user");
        }

        try {
            template.process(dataModel, printWriter);
        } catch (TemplateException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = usersRepositoryDao.getUserById(Integer.parseInt(req.getParameter("id")));
        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setAge(Integer.parseInt(req.getParameter("age")));

        Part filePart = req.getPart("image");
        File file = null;
        try {
            file = fileUploadProcessor.uploadImageFile(filePart);
            user.setImageURL("/images/" + file.getName());
        } catch (UserImageNotFound userImageNotFound) {
            logger.log(Level.SEVERE, userImageNotFound.getMessage());
        }


        resp.sendRedirect("/users-list");
    }
}
