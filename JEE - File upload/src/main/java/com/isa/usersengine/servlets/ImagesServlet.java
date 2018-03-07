package com.isa.usersengine.servlets;

import com.isa.usersengine.cdi.FileUploadProcessorBean;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/images/*") // sciezka do zakodowanych plikow
public class ImagesServlet extends HttpServlet {

    @Inject
    FileUploadProcessorBean fileUploadProcessorBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filename = URLDecoder.decode(req.getPathInfo().substring(1), "UTF-8"); // substring 1 - omija images, dostajemy sama nazwe pliku
        File file = new File(fileUploadProcessorBean.getUploadImageFilesPath(), filename); // pobranie z naszego dysku
        resp.setHeader("Content-Type", getServletContext().getMimeType(filename)); //content-type zapisany wewnatrz obrazka
        resp.setHeader("Content-Lenght", String.valueOf(file.length())); // wielkosc obiektu
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\""); // definicja formy prezentacji
        Files.copy(file.toPath(), resp.getOutputStream()); // kopiujemy ze sciezki do outputstreamu na odpowiedzi bo
        // serwer na zwrocic nam obrazek - wysylamy caly stream z pliku
    }
}
