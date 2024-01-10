package edu.course2.job4;

import edu.course2.job4.interfaces.LogTransformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        String logFile=null;
        Annotation[] annotations=bean.getClass().getAnnotations();
        for (Annotation annotation:annotations) {
            if (annotation instanceof LogTransformation) {
                final LogTransformation cacheParams=(LogTransformation) annotation;
                if(bean.getClass().getSimpleName().contains("Middle")){
                    MiddleComponent m1=(MiddleComponent) bean;
                    m1.setLogFile(cacheParams.value());
                }
            }
        }
        return bean;
    }
}
