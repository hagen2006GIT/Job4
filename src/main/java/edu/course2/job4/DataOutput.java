package edu.course2.job4;

import edu.course2.job4.modelclasses.Model;
import edu.course2.job4.modelclasses.ModelStructure;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;


@Component (value="TEXT") public class DataOutput implements Consumer<Model> {
    @Override public void accept(Model model) {
        DateFormat formatter=new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("TEXT: "+Thread.currentThread().getStackTrace()[1].getClassName()+"."
                +Thread.currentThread().getStackTrace()[1].getMethodName()
                +": ");
        System.out.println(String.format("%1$"+140+"s"," ").replace(' ','-'));
        System.out.printf("%-10s %-20s %-25s %-25s %-25s %-15s %-25s %n","Проверка","Логин","Фамилия","Имя","Отчество"
                ,"Дата входа","Тип приложения");
        System.out.println(String.format("%1$"+140+"s"," ").replace(' ','-'));
        for (ModelStructure md:model.fLine) {
            if(md.getAccess_date()!=null){
                System.out.printf("%-10s %-20s %-25s %-25s %-25s %-15s %-25s %n",md.isDataOk(),md.getUsername(),md.getFioF(),md.getFioI(),md.getFioO()
                        ,formatter.format(md.getAccess_date()),md.getAppl_type());
            } else {
                System.out.printf("%-10s %-20s %-25s %-25s %-25s %n",md.isDataOk(),md.getUsername(),md.getFioF(),md.getFioI(),md.getFioO());
            }
        }
        System.out.println(String.format("%1$"+140+"s"," ").replace(' ','-'));
    }
}
