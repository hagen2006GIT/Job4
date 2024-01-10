package edu.course2.job4;

import edu.course2.job4.modelclasses.ModelStructure;

import java.sql.*;
import java.util.List;

public class H2DAO {
    Connection con;
    public H2DAO(String url) throws SQLException {
        this.con=DriverManager.getConnection(url);
    }
    public void selectUsers() throws Exception {
        Statement statement=con.createStatement();
        ResultSet resultDML=statement.executeQuery("SELECT * FROM users ORDER BY id");
        System.out.println("(H2) TABLE users");
        while (resultDML.next()) {
            int id=resultDML.getInt("id");
            String username=resultDML.getString("username");
            String fio=resultDML.getString("fio");
            System.out.println(id+" | "+username+" | "+fio);
        }
    }
    public void selectLogins() throws Exception {
        Statement statement=con.createStatement();
        ResultSet resultDML=statement.executeQuery("SELECT * FROM Logins");
        System.out.println("(H2) TABLE logins");
        while (resultDML.next()) {
            int id=resultDML.getInt("id");
            java.util.Date date2=resultDML.getTimestamp("access_date");
            String userid=resultDML.getString("userid");
            String application=resultDML.getString("application");
            System.out.println(id + " | " + date2.toString() + " | " + userid + " | " + application);
        }
    }
    public void postData(List<ModelStructure> mList, boolean clrTable) throws Exception {
        Statement statement=con.createStatement();
        ResultSet resultDML;
// создание последовательности для IDшников
        statement.execute("CREATE SEQUENCE IF NOT EXISTS SEQID MINVALUE 158");
// создание таблиц, если они ещё не существуют
        statement.execute("CREATE TABLE IF NOT EXISTS users "+ // users
                "("+
                "id bigint not null primary key"+
                ",username varchar"+
                ",fio varchar" +
                ")"
        );
        statement.execute("CREATE TABLE IF NOT EXISTS logins" + // logins
                "("+
                "id bigint not null primary key"+
                ",access_date timestamp"+
                ",userid bigint"+
                ",application varchar"+
                ")"
        );
        if(clrTable) { // по требованию почистим таблицы
            statement.execute("DELETE FROM users");
            statement.execute("DELETE FROM logins");
//            statement.execute("DROP SEQUENCE SEQID");
        }
// заполняем таблицы данными из Model
        for (ModelStructure mod:mList) {
            if (mod.getAccess_date()!=null) {
                resultDML=statement.executeQuery("select nextval('PUBLIC','SEQID')");
                resultDML.first();
                int seqID=resultDML.getInt(1);
                statement.execute("INSERT INTO users VALUES " +
                        "('"+seqID+"'"+
                        ",'"+mod.getUsername()+"'"+
                        ",'"+mod.getFioF()+" "+mod.getFioI()+" "+mod.getFioO()+"'"+
                        ")"
                );
                java.util.Date dateAccess=mod.getAccess_date();
                statement.execute("INSERT into logins values"+
                        "((select nextval('PUBLIC','SEQID'))"+
                        ",'"+new Timestamp(dateAccess.getTime())+"'"+
                        ",'"+seqID+"'"+
                        ",'"+mod.getAppl_type()+"'"+
                        ")"
                );
            }
        }
    }
}