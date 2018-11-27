package com.mynotebookbot.util;

import com.mynotebookbot.dao.User;
import com.mynotebookbot.mapper.UserMapper;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.*;

/*
   This class would not needed in real DB. In our case it just creates DB and Table.
   e.g. Initializing class. Used once during the first run of app when there is no DB and Table
 */
public class InitDB {

    UserMapper mapper;

    public static void main(String[] args) {
        init();
    }

    static void init() {
        Connection conn = null;
        Statement stmt;
      String createSQL =
                "CREATE TABLE notes ("+
                "id integer not null generated always as identity (start with 1, increment by 1),"+
                "NAME varchar(30) NOT NULL,"+
                "SURENAME varchar(30) NOT NULL,"+
                "BIRTH varchar(10) NOT NULL,"+
                "COUNTRY varchar(30), "+
                "STREET varchar(30), "+
                "TOWN varchar(30), "+
                "ZIPCODE varchar(6))";

        //String createSQL = "DROP TABLE notes";


        try {
            Driver derbyEmbeddedDriver = new EmbeddedDriver();
            DriverManager.registerDriver(derbyEmbeddedDriver);
            conn = DriverManager.getConnection("jdbc:derby:notesDB;create=true", "sa", "pass123");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.execute(createSQL);
            conn.commit();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("in connection" + ex);
        }

        //populating table
            User user1 = new User("Андрей", "Васильев", "16.08.1984", "Россия", "Москва", "Проспект мира 1", "420098");
            User user2 = new User("Георгий", "Сидоров", "26.11.1992", "Россия", "Москва", "Ленина 1", "420098");
            User user3 = new User("Иван", "Сидоров", "21.01.2010", "Россия", "Москва", "Ленина 1", "420098");

            SqlSessionFactory factory = MyBatisUtil.buildSqlSessionFactory();

            SqlSession session = factory.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.addUser(user1);
            mapper.addUser(user2);
            mapper.addUser(user3);
            System.out.println("user was added to DB");
            System.out.println(mapper.getAllUsers().toString());
            session.commit();
            session.close();
    }
}
