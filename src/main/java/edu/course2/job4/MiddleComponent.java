package edu.course2.job4;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.BinaryOperator;

@Component public class MiddleComponent implements BinaryOperator <Model> {
    @Getter @Setter String logFile;
    StringBuilder loggingString=new StringBuilder();
    DateFormat formatter=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    @Override
    public Model apply(Model model, Model model2) {
        return null;
    }
    public void init() {}
    void logging(String filename,StringBuilder loggingStr){
        if(!(filename==null)) {
            var context = new AnnotationConfigApplicationContext("edu.course2.job4"); // прочитал контекст пакета
            Util utils = context.getBean("util", Util.class);
            try {
                utils.data2Log(filename,loggingStr.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

