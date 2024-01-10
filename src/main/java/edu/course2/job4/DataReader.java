package edu.course2.job4;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;
@Component (value="datareader") public class DataReader implements Supplier <Model> {
    private int fileReading=0;
    public int getFileReading() {
        return fileReading;
    }
    @Override public Model get() {
        Model model=new Model();
        String resourcesFiles = "./src/test/resources/";
        String filename;
        String[] lineFromFile;
        DateFormat formatter=new SimpleDateFormat("dd.MM.yyyy");
        Date tmpAccessDate=null;
        String tmpAppl_type=null;

        File f=new File(resourcesFiles);
        System.out.println(f);
        fileReading=f.listFiles().length;
        for (File file:Objects.requireNonNull(f.listFiles())) {
            filename=file.getName();
            try {
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+"."
                        +Thread.currentThread().getStackTrace()[1].getMethodName()
                        +": filename=" + filename);
                String line=reader.readLine();
                while (line!=null) {
                    lineFromFile=line.split(" ");
                    try {
                        tmpAccessDate=formatter.parse(lineFromFile[4]);
                        tmpAppl_type=lineFromFile[5];
                    } catch (ParseException e) {
                        tmpAccessDate=null;
                        tmpAppl_type="unknown";
                    }
                    model.fLine.add(new ModelStructure(lineFromFile[0] // username
                                                        ,lineFromFile[1] // фамилия
                                                        ,lineFromFile[2] // имя
                                                        ,lineFromFile[3] // отчество
                                                        ,tmpAccessDate // дата входа
                                                        ,tmpAppl_type // тип приложения
                    ));
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return model;
    }
}
