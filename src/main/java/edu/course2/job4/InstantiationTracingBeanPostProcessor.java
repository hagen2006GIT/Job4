package edu.course2.job4;

import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;

public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {
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
                    System.out.println("AfterInitialization: "+m1.getLogFile());
                }
            }
        }
        return bean;
    }
}
