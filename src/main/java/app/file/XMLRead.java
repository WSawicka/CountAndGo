package app.file;

import app.AlertWindow;
import app.model.AlertEnum;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Optional;

/**
 * Created by ptaszysko on 18.02.2017.
 */
public class XMLRead {
   public Optional<Object> read(String name){
       String data;
       File file = new File(name);
       Object object = new Object();
       try {
           data = FileUtils.readFileToString(file, "CP1250");
           XStream xstream = new XStream();

           if (StringUtils.isNotEmpty(data)){
               object = xstream.fromXML(data);
               new AlertWindow().show(AlertEnum.READ_SUCCESS);
           } else {
               new AlertWindow().show(AlertEnum.EMPTY_FILE);
           }
       } catch (Exception e){
           new AlertWindow().show(AlertEnum.ERROR_WHILE_READ_XML);
       }
       return Optional.of(object);
   }
}
