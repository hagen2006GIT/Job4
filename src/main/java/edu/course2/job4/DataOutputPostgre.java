package edu.course2.job4;

import edu.course2.job4.modelclasses.Model;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component(value="postgre") public class DataOutputPostgre implements Consumer<Model> {
    @Override public void accept(Model model) {
// создать объект с подключением к Postgre
        PostgreDAO dao=null;
        List<String> lstURL=new ArrayList<>();
        lstURL.add("jdbc:postgresql://localhost:5432/postgres");
        lstURL.add("postgres");
        lstURL.add("12345");
        try {
            dao = new PostgreDAO(lstURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
// запись в базу H2
        try {
            dao.postData(model.fLine,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
// проверим выборку из таблицы users
        try {
            dao.selectUsers();
            dao.selectLogins();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
