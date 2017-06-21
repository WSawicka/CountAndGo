package app.file;

import app.AlertWindow;
import app.model.AlertEnum;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by ptaszysko on 18.02.2017.
 */
public class XMLWrite {
    public void write(String name, Object object){
        File file = new File(name);
        String data;
        try {
            XStream xStream = new XStream();
            data = xStream.toXML(object);

            PrintWriter fileOutput = new PrintWriter(file);
            fileOutput.print(data);
            fileOutput.flush();
            fileOutput.close();
            new AlertWindow().show(AlertEnum.WRITE_SUCCESS);
        } catch (Exception e) {
            new AlertWindow().show(AlertEnum.ERROR_WHILE_READ_XML);
        }
    }
}
