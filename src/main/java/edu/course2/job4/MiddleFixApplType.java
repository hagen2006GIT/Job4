package edu.course2.job4;

import edu.course2.job4.interfaces.LogTransformation;
import edu.course2.job4.modelclasses.Model;
import edu.course2.job4.modelclasses.ModelStructure;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.function.BinaryOperator;

// проверка 4 - промежуточная компонента проверяет, что тип приложения соответствует одному из: "web", "mobile".
// Если там записано что-либо иное, то оно преобразуется к виду "other."+значение
@LogTransformation("fix_apple_type.log")
@Component(value="FIX_APPL_TYPE") public class MiddleFixApplType extends MiddleComponent implements BinaryOperator<Model> {
    @Override public Model apply(Model model, Model model2) {
        loggingString.append(this.getClass().getName()+"\n");
        loggingString.append("старт операции: ").append(formatter.format(new java.util.Date().getTime())+"\n");
        loggingString.append("модель ДО старта операции:"+model.toString()+"\n");
        for(ModelStructure mod:model2.fLine) {
            if(!mod.getAppl_type().equals("mobile") && !mod.getAppl_type().equals("web"))
                mod.setAppl_type("other."+mod.getAppl_type());
        }
        loggingString.append("модель ПОСЛЕ обработки:"+model.toString()+"\n");
        logging(this.getLogFile(),loggingString);
        return model2;
    }
}
