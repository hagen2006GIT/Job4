package edu.course2.job4;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component (value="util") public class Util {
    public void data2Log(String filename,String logStr) throws IOException {
        File fLog=new File(filename);
        if(!fLog.exists()){
            fLog.createNewFile();
        }
        FileWriter fWriter=new FileWriter(fLog,true);
        fWriter.write(logStr+"\n");
        fWriter.close();
    }
    public int testFilesGenerate() throws IOException {
        int filesGen=0;
        File file1=new File("./src/test/resources/testFile1.txt");
        File file2=new File("./src/test/resources/testFile2.txt");
        if(file1.exists()) file1.delete();
        if(file1.createNewFile()) {
            FileWriter writer1=new FileWriter(file1);
            writer1.write("username1 иванов Степан Семенович 01.12.1975 mobile"+"\n");
            writer1.write("username2 Петров сергей васильевич desktop"+"\n");
            writer1.close();
            filesGen++;
        }
        if(file2.exists()) file2.delete();
        if(file2.createNewFile()) {
            FileWriter writer2=new FileWriter(file2);
            writer2.write("username4 Евстигнеев Валентин Наумович 15.02.2008 web"+"\n");
            writer2.write("username6 Васнецов Леонид Константинович 02.04.2015 desktop"+"\n");
            writer2.close();
            filesGen++;
        }
        return filesGen;
    }
}
