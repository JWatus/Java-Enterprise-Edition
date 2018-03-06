package com.isa.usersengine.cdi;

import com.isa.usersengine.exceptions.UserImageNotFound;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Properties;

@RequestScoped
public class FileUploadProcessorBean {

    private static final String SETTINGS_FILE = "settings.properties";

    public String getUploadImageFilesPath() throws IOException {

        Properties settings = new Properties();
        settings.load(Thread.currentThread().
                getContextClassLoader().getResource(SETTINGS_FILE).openStream());
        return settings.getProperty("Upload.Path.Images");
    }

    public File uploadImageFile(Part filePart) throws IOException, UserImageNotFound { // otrzymuje Part (plik) ktorego otrzymal servlet w req

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (fileName == null || fileName.isEmpty()) {
            throw new UserImageNotFound("No user image has been uploaded");
        }

        File file = new File(getUploadImageFilesPath() + fileName); // tworzy plik file na sciezce,
        // argument - sciezka do naszego nowego pliku

        InputStream fileContent = filePart.getInputStream(); // stream z danymi, czytamy przeslany plik
        OutputStream os = new FileOutputStream(file); // stream  przyjmujacy dane,
        // dla pustego swiezego pliku do ktorego chcemy zapisywac

        byte[] buffer = new byte[1024]; // plik dzielony na czesci majace 1024 bajty,chunki
        int bytesRead;
        // kopiowanie fragmentow wyslanego pliku forularzem do miejsca w kotrym jest nasz nowy plik
        // do ktoego zapisujemy
        while ((bytesRead = fileContent.read(buffer)) != -1) { // zczytujemy z pliku po kolei chunki - ostatni
            // ma 1024 lub mniej bajtow
            os.write(buffer, 0, bytesRead); // zapisujemy zawartosc tablicy buffer z info od ktorego indexu
            // i jakiej wielkosci czesc tej tablicy mamy zapisac
            // kazdy chunk traktowany jako swiezy buffor, metoda read przechodzi do kolejnego chunka
        }

        // przyslany plik zniknie za chwile bo jest tymczasowy
        // cale info z niego mamy zapisane w nowym pliku

        fileContent.close();
        os.flush();
        os.close();

        return file;
    }


}
