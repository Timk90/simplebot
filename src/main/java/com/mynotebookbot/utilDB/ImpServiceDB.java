package com.mynotebookbot.utilDB;

import com.mynotebookbot.dao.User;
import com.mynotebookbot.mapper.UserMapper;
import com.mynotebookbot.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImpServiceDB implements ServiceDB{
    SqlSessionFactory factory = MyBatisUtil.buildSqlSessionFactory();


    @Override
    public boolean addUserToDB(User user) {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.addUser(user);
        System.out.println("user was added to DB");
        System.out.println(mapper.getAllUsers().toString());
        session.commit();
        session.close();
        return true;
    }

    @Override
    public User getUserByID(int id) {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setId(id);
        user = mapper.getUserByID(user);
        session.commit();
        session.close();
        return user;
    }

    @Override
    public boolean changeUserInDB(User user) {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.updateUser(user);
        session.commit();
        session.close();
        return  true;
    }

    @Override
    public boolean removeUserFromDB(int id) {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setId(id);
        mapper.deleteUserById(user);
        session.commit();
        session.close();
        return true;
    }

    @Override
    public String showAllUsersFromDB()
    {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Map<Integer, User> users = mapper.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for(User user : users.values()) {
            sb.append(user.toString());
            sb.append("\n ====================== \n");
        }
        session.commit();
        session.close();
        return sb.toString();
    }

    @Override
    public String showSelectedUsersFromDB(List<String> names) {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setName(names.get(0));
        user.setSurename(names.get(1));
        Map<Integer, User> users = mapper.getUserByName(user);
        StringBuilder sb = new StringBuilder();

        for(User tmpuser : users.values()) {
            sb.append(tmpuser.toString());
            sb.append("\n ====================== \n");
        }
        session.commit();
        session.close();
        return sb.toString();
    }

    public static void main(String[] args) {
        //User user = new User("Bill", "Bambukov", "16.02.1989", "Singapore", "Kukmor", "Vislouhova", "422424");
        //System.out.println(user.toString());
        ServiceDB sDB = new ImpServiceDB(){};
        //sDB.addUserToDB(user);
        List<String> names = new ArrayList<>();
        names.add("Вымпел");
        names.add("СССР");
        String str  = sDB.showSelectedUsersFromDB(names);
        System.out.println(str);
        System.out.println(sDB.getUserByID(1).toString());
        //System.out.println(sDB.showAllUsersFromDB());
        User user = new User();
        user.setName("Дима");
        user.setSurename("Руруру");
        user.setBirth("12.12.1990");
        user.setId(1);
        user.setStreet("dsaddad");
        user.setCountry("dsdadada");
        user.setTown("dsdadasd");
        user.setZipcode("434324");
        sDB.changeUserInDB(user);
        System.out.println(sDB.getUserByID(1).toString());
    }

}
