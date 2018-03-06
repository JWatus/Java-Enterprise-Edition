package com.isa.usersengine.servlets;


import com.isa.usersengine.cdi.FileUploadProcessorBean;
import com.isa.usersengine.dao.UsersRepositoryDao;
import com.isa.usersengine.dao.UsersRepositoryDaoBean;
import com.isa.usersengine.domain.User;
import com.isa.usersengine.exceptions.UserImageNotFound;

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
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet {

    Logger logger = Logger.getLogger(getClass().getName());

    @EJB
    UsersRepositoryDao usersRepositoryDao;

    @Inject
    FileUploadProcessorBean fileUploadProcessorBean;

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


        Part filePart = req.getPart("image");           // image - nazwa pola z formularza
        File file = null;
        try {
            file = fileUploadProcessorBean.uploadImageFile(filePart); // wykonanie tej metody powoduje ze na dysku jest
            // fizyczna forma zaladowanego pliku przez uzytkowanika
            user.setImageURL("/images/" + file.getName());
        } catch(UserImageNotFound userImageNotFound) {
            logger.log(Level.SEVERE, userImageNotFound.getMessage());
        }

        usersRepositoryDao.addUser(user);       // TWORZYC JAKO BEANY W EE
        resp.sendRedirect("/users-list");

    }
}

// parametry z doPost -> dodaje usera -> idzie do metody add -> widzi ze interceptor
// -> modyfikuje metode -> wraca do servletu i dodaje usera -> przekierowuje do /users-list i wyswietla

// redirect - odnosnik, przejscie na inna strone, generuje nowa instacje requestu
// dispatcher, forward - przekazanie parametrow requestu dalej - podmienia widok i przekierowuje uzytkownika

// persistanceContest - wskazuje jak ma byc stworzony obiekt entityMenagera, sprawdza w persistance.xml i web.xml

// ZADANIE add and edit - login, haslo i inne uzupelnione tez powinny byc validowane - dla czasu tego nie roblismy
// AddUserServlet, Filter



// funkcje hashujace np md5 (udowodniono ze nie jest deterministyczna - unikac), sha1 - obczaic
// algorytm base64 - oncode i decode plikow

// funkcja mieszajaca - nieodwracalna, base64 - odwracalny


