package edu.course2.job4;

import edu.course2.job4.modelclasses.ModelStructure;

import java.sql.*;
import java.util.List;

public class PostgreDAO {
    Connection con;
    public PostgreDAO(List<String> url) throws SQLException {
        this.con= DriverManager.getConnection(url.get(0) // строка подключения
                ,url.get(1) // user
                ,url.get(2) //pass
        );
    }
    public void selectUsers() throws Exception {
        Statement statement=con.createStatement();
        ResultSet resultDML=statement.executeQuery("SELECT * FROM users ORDER BY id");
        System.out.println("(Postgre) TABLE users");
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
        System.out.println("(Postgre) TABLE logins");
        while (resultDML.next()) {
            int id=resultDML.getInt("id");
            java.util.Date date2=resultDML.getTimestamp("access_date");
            String userid=resultDML.getString("user_id");
            String application=resultDML.getString("application");
            System.out.println(id + " | " + date2.toString() + " | " + userid + " | " + application);
        }
    }
    public void postData(List<ModelStructure> mList, boolean clrTable) throws Exception {
        Statement statement=con.createStatement();
        ResultSet resultDML;
        if(clrTable) { // по требованию почистим таблицы
            statement.execute("DELETE FROM users");
            statement.execute("DELETE FROM logins");
            statement.execute("DROP SEQUENCE SEQID");
        }
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
                ",user_id bigint"+
                ",application varchar"+
                ")"
        );
// заполняем таблицы данными из Model
        for (ModelStructure mod:mList) {
            if (mod.getAccess_date()!=null) {
                resultDML=statement.executeQuery("SELECT NEXTVAL('SEQID') FROM generate_series(1,2)");
                resultDML.next();
                int seqID=resultDML.getInt(1);
                resultDML.next();
                int seqID2=resultDML.getInt(1);
                statement.execute("INSERT INTO users VALUES " +
                        "('"+seqID+"'"+
                        ",'"+mod.getUsername()+"'"+
                        ",'"+mod.getFioF()+" "+mod.getFioI()+" "+mod.getFioO()+"'"+
                        ")"
                );
                java.util.Date dateAccess=mod.getAccess_date();
                statement.execute("INSERT into logins values"+
                        "('"+seqID2+"'"+
                        ",'"+new Timestamp(dateAccess.getTime())+"'"+
                        ",'"+seqID+"'"+
                        ",'"+mod.getAppl_type()+"'"+
                        ")"
                );
            }
        }
    }
}
