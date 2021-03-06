package com.isa.usersengine.servlets;

import com.isa.usersengine.cdi.FileUploadProcessor;
import com.isa.usersengine.dao.UsersRepositoryDao;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/add-user")
@MultipartConfig
public class AddUserServlet extends HttpServlet {

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
        PrintWriter printWriter = resp.getWriter();
        Map<String, Object> dataModel = new HashMap<>();
        // przekazanie do freemarkera wprowadzonych parametrow i ewentualnych errorow
        List<String> errors = (List<String>) req.getSession().getAttribute("errors");
        if (errors != null && !errors.isEmpty()) {
            dataModel.put("errors", errors);
            dataModel.put("user", req.getSession().getAttribute("user"));
            req.getSession().removeAttribute("error");
            req.getSession().removeAttribute("user");       // po wszysktim usuwanie sesyjnych atrybutow zeby nie zalegaly w pamieci
                                                                    // i zeby nie byly widoczne dla innych klientow
        }

        try {
            template.process(dataModel, printWriter);
        } catch (TemplateException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setId(Integer.parseInt(req.getParameter("id")));
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

        usersRepositoryDao.addUser(user);

        resp.sendRedirect("/users-list");
    }
}


