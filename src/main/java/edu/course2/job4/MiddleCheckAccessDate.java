package edu.course2.job4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.BinaryOperator;

// проверка 5 - промежуточная компонента проверки даты проверяет её наличие.
// Если дата не задана, то человек не вносится в базу, а сведения о имени файла и значения человека заносятся в отдельный лог
@LogTransformation ("fix_access_date.log")
@Component(value="CHECK_ACCESS_DATE") public class MiddleCheckAccessDate extends MiddleComponent implements BinaryOperator<Model> {
    @Override public Model apply(Model model, Model model2) {
        loggingString.append(this.getClass().getName()+"\n");
        loggingString.append("старт операции: ").append(formatter.format(new java.util.Date().getTime())+"\n");
        loggingString.append("модель ДО старта операции:"+model.toString()+"\n");
        var context=new AnnotationConfigApplicationContext("edu.course2.job4"); // прочитал контекст пакета
        Util utils=context.getBean("util",Util.class);
        for(ModelStructure mod:model2.fLine) {
            if(mod.getAccess_date()==null) {
                mod.setDataOk(false); // строку на импортирую в БД (признак - false)
                try { // и сведения по соответствующей строке в файле заношу в лог
                    utils.data2Log("./src/test/importError.log",mod.gerFullString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        loggingString.append("модель ПОСЛЕ обработки:"+model.toString()+"\n");
        logging(this.getLogFile(),loggingString);
        return model2;
    }
}
