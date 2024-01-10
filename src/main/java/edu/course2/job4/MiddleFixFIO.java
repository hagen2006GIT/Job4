package edu.course2.job4;

import edu.course2.job4.interfaces.LogTransformation;
import edu.course2.job4.modelclasses.Model;
import edu.course2.job4.modelclasses.ModelStructure;
import org.springframework.stereotype.Component;
import java.util.function.BinaryOperator;

// проверка 3 - промежуточная компонента проверки данных исправляет ФИО так, чтобы каждыё его компонент начинался с большой буквы
@LogTransformation("fix_fio.log")
@Component(value="FIX_FIO") public class MiddleFixFIO extends MiddleComponent implements BinaryOperator<Model> {
    @Override public Model apply(Model model, Model model2) {
        loggingString.append(this.getClass().getName()+"\n");
        loggingString.append("старт операции: ").append(formatter.format(new java.util.Date().getTime())+"\n");
        loggingString.append("модель ДО старта операции:"+model.toString()+"\n");
        for (ModelStructure mod : model2.fLine) {
            mod.setFioF(getCapsFirst(mod.getFioF()));
            mod.setFioI(getCapsFirst(mod.getFioI()));
            mod.setFioO(getCapsFirst(mod.getFioO()));
        }
        loggingString.append("модель ПОСЛЕ обработки:"+model.toString()+"\n");
        logging(this.getLogFile(),loggingString);
        return model2;
    }
    private String getCapsFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
