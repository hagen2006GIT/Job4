package edu.course2.job4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    public static void main(String[] args) throws Exception {
        Logger logger = LoggerFactory.getLogger(Starter.class);
        logger.info("Hello World");

//        Util.testFilesGenerate();
//        var context=new AnnotationConfigApplicationContext("ru.course2.spring1");
//        context.getBean("operationMaker",OperationMaker.class).make();
//        ApplicationContext ctx= SpringApplication.run(Starter.class);
//        DepartmentDAO dao=new DepartmentDAO();
//        System.out.println(dao.selectAll());
    }
}
