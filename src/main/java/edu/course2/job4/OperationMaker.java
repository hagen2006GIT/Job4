package edu.course2.job4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component public class OperationMaker {
    @Autowired Supplier<Model> datareader;
    @Autowired Map<String,Consumer<Model>> outputs=new HashMap<>();
    @Autowired Map<String,BinaryOperator<Model>> middleTransform=new HashMap<>();
    void make() {
        Model model=datareader.get(); // чтение файлов
        middleTransform.get("FIX_APPL_TYPE").apply(model,model); // проверка 4 - тип приложения
        middleTransform.get("FIX_FIO").apply(model,model); // проверка 3 - коррекция ФИО
        middleTransform.get("CHECK_ACCESS_DATE").apply(model,model); // проверка 5 - дата входа в приложение
        outputs.get("TEXT").accept(model);
        outputs.get("H2").accept(model);
        outputs.get("postgre").accept(model);
    }
    void makeByStep() {
        Model model=step1();
        step2(model);
        step3(model);
    }
    Model step1() {
        return datareader.get();
    }
    void step2(Model model) {
        middleTransform.get("FIX_APPL_TYPE").apply(model,model); // проверка 4 - тип приложения
        middleTransform.get("FIX_FIO").apply(model,model); // проверка 3 - коррекция ФИО
        middleTransform.get("CHECK_ACCESS_DATE").apply(model,model); // проверка 5 - дата входа в приложение
    }
    void step3(Model model) {
        outputs.get("TEXT").accept(model);
        outputs.get("H2").accept(model);
        outputs.get("postgre").accept(model);
    }
}
