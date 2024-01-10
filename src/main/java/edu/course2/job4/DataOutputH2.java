package edu.course2.job4;

import edu.course2.job4.modelclasses.Model;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.function.Consumer;

@Component(value="H2") public class DataOutputH2 implements Consumer<Model> {
    @Override public void accept(Model model) {
// создать объект с подключением к H2
        H2DAO dao=null;
        try {
            dao = new H2DAO("jdbc:h2:~/test4");
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
