package edu.course2.job4;

import edu.course2.job4.modelclasses.Model;
import edu.course2.job4.modelclasses.ModelStructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.*;

public class TestFor4 {
    AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext("edu.course2.job4");
    Logger logger=LoggerFactory.getLogger(Starter.class);

    Model fillMockModel() {
        Model modelMock=new Model(); // тестовая модель данных для заглушки
        modelMock.fLine.add(new ModelStructure("loginMock1" // username
                ,"Смирнов" // фамилия
                ,"Николай" // имя
                ,"Петрович" // отчество
                ,new java.util.Date() // дата входа
                ,"desktop" // тип приложения
        ));
        modelMock.fLine.add(new ModelStructure("loginMock2" // username
                ,"Николаев" // фамилия
                ,"Валентин" // имя
                ,"Ильич" // отчество
                ,new java.util.Date() // дата входа
                ,"mobile" // тип приложения
        ));
        modelMock.fLine.add(new ModelStructure("loginMock2" // username
                ,"Куковицкий" // фамилия
                ,"Иван" // имя
                ,"Васильевич" // отчество
                ,new java.util.Date() // дата входа
                ,"web" // тип приложения
        ));
        return modelMock;
    }

// testDataReaderInvoke() проверяет, что вызывается метод чтения данных с исходными данными
    @Test @DisplayName("reading mock testing") public void testDataReaderInvoke() {
        Model modelAfterRead;
        logger.info("testDataReaderInvoke() проверяет, что вызывается метод чтения данных с исходными данными");
        DataReader dr= Mockito.mock(DataReader.class); // создал объект для заглушки
        Mockito.when(dr.get()).thenReturn(fillMockModel());
        modelAfterRead=dr.get(); // имитация вызова метода чтения данных
        System.out.println("modelAfterRead:\n"+modelAfterRead);
        Mockito.verify(dr).get(); // верификация: был ли вызван метод класса DataReader - .get()
    }

// testPostLog() - проверим, лог работы операции для класса, помеченного аннтацией @LogTransformation формируется
    @Test @DisplayName("form DATA-log before/after working")public void testPostLog() {
//        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        logger.info("testPostLog() - проверим, лог работы операции для класса, помеченного аннтацией @LogTransformation формируется");
        ApplicationContext ac=new AnnotationConfigApplicationContext("edu.course2.job4");
        Model modelMockRead=fillMockModel();
        File fLog=new File("fix_access_date.log");
        if(fLog.exists()) fLog.delete();
//        MiddleCheckAccessDate m2=ac.getBean(MiddleCheckAccessDate.class);
        MiddleCheckAccessDate m2=ac.getBean("middleCheckDate",MiddleCheckAccessDate.class);
        m2.apply(modelMockRead,modelMockRead);
        if (fLog.exists()){
            logger.info("Тест выполнен успешно. Лог-файл создан: "+fLog.getName());
        } else {
            logger.error("Тест завершился с ошибкой. Ожидаемый результат - создание лог файла "+fLog.getName());
        }
        Assertions.assertTrue(fLog.exists());
    }

// testMiddleFixApplType() - проверим, что промежуточная проверка на тип приложения меняет входные данные
// в соответствии с условием задачи
    @Test @DisplayName("working middle component") public void testMiddleFixApplType(){
//        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        logger.info("testMiddleFixApplType() - проверим, что промежуточная проверка на тип приложения меняет входные данные");
        logger.info("в соответствии с условием задачи");
        ApplicationContext ac=new AnnotationConfigApplicationContext("edu.course2.job4");
        Model modelMockRead=fillMockModel();
        String strApplType=modelMockRead.fLine.get(0).getAppl_type();
        MiddleFixApplType m1=ac.getBean(MiddleFixApplType.class);
        m1.apply(modelMockRead,modelMockRead);
        logger.info("modelAfterRead:\n"+modelMockRead);
        Assertions.assertEquals(strApplType,modelMockRead.fLine.get(0).getAppl_type());
    }

// метод testFullWork() выполняет проверку интеграционного взаимодействия всех компонент
// метод make() класса OperationMaker делает:
// 1. последовательно читает исходные файлы
// 2. выполняет промежуточную обработку независимыми компонентами
// 3. записывает обработанные данные в консоль и в БД (два варианта - в Postgre и в H2)
    @Test @DisplayName("full work testing") public void testFullWork() {
        logger.info("метод testFullWork() выполняет проверку интеграционного взаимодействия всех компонент");
        logger.info("метод makeByStep() класса OperationMaker делает:");
        logger.info("\t"+"1. последовательно читает исходные файлы");
        logger.info("\t"+"2. выполняет промежуточную обработку независимыми компонентами");
        logger.info("\t"+"3. записывает обработанные данные в консоль и в БД (два варианта - в Postgre и в H2)");
        var context=new AnnotationConfigApplicationContext("edu.course2.job4");
        context.getBean("operationMaker",OperationMakerImpl.class).makeByStep();
    }

// testFileGeneration() проверяет функционал генерации файлов с исходными данными и его взаимодействие
// с компонентой чтения этих файлов
// проверка - сравниваем количества сгенерированных файлов с количеством файлов,
// прочитанных компонентой
    @Test @DisplayName("files generation testing") public void testFileGeneration() throws IOException {
        int fGen=0;
        logger.info("testFileGeneration() проверяет функционал генерации файлов с исходными данными и его взаимодействие");
        logger.info("с компонентой чтения этих файлов");
        logger.info("проверка - сравниваем количества сгенерированных файлов с количеством файлов");
        logger.info("прочитанных компонентой");
// утилита для генерации файлов с исходными данными
        Util utils=ctx.getBean("util",Util.class);
        fGen=utils.testFilesGenerate(); // генерация файлов (утилита возвращает количество сгенерированнх данных)
// компонента чтения данных их файлов
        DataReader dr=ctx.getBean("datareader",DataReader.class);
        dr.get(); // прочитал файлы (количество прочитанных файлов запишет в поле fileReading класса DataReader)
        Assertions.assertEquals(fGen,dr.getFileReading());
    }
}
