package edu.course2.job4;

import edu.course2.job4.interfaces.OperationMaker;
import edu.course2.job4.modelclasses.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Component public class OperationMakerImpl implements OperationMaker {
    @Autowired Supplier<Model> datareader;
    @Autowired Map<String,Consumer<Model>> outputs=new HashMap<>();
    @Autowired Map<String,BinaryOperator<Model>> middleTransform=new HashMap<>();
    @Bean public OperationMakerImpl operationMaker() {
        return new OperationMakerImpl();
    }
    public void make() {
        Model model=datareader.get(); // чтение файлов
        middleTransform.get("FIX_APPL_TYPE").apply(model,model); // проверка 4 - тип приложения
        middleTransform.get("FIX_FIO").apply(model,model); // проверка 3 - коррекция ФИО
        middleTransform.get("CHECK_ACCESS_DATE").apply(model,model); // проверка 5 - дата входа в приложение
        outputs.get("TEXT").accept(model);
        outputs.get("H2").accept(model);
        outputs.get("postgre").accept(model);
    }
    public void makeByStep() {
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
