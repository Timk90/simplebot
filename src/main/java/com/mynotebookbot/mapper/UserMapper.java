package com.mynotebookbot.mapper;

import com.mynotebookbot.dao.User;
import org.apache.ibatis.annotations.*;
import java.util.Map;

//I have decided to use MyBatis (the only thing I have slightly learnt)
//to provide all the work with DB instead of using plain SQL command in the code
//it helps to split realization from manipulations (MVC model) without thinking of SQL things
public interface UserMapper {

    @Select("SELECT * FROM notes WHERE id=#{id}")
    public User getUserByID(User user);

    @Insert("INSERT INTO notes (name, surename, country, street, town, birth, zipcode) VALUES (#{name}, #{surename}, #{country}, #{street}, #{town}, #{birth}, #{zipcode})")
    void addUser(User user);

    @Update("Update notes set name=#{name}, surename=#{surename}, birth=#{birth}, country=#{country}, street=#{street}, town=#{town},  zipcode=#{zipcode} where id=#{id}")
    void updateUser(User user);

    @Delete("Delete from notes where id=#{id}")
    void deleteUserById(User user);

    @Select("SELECT * FROM notes WHERE name = #{name} AND surename = #{surename}")
    @MapKey("id")
    Map<Integer, User> getUserByName(User user);

    @Select("select * from notes ")
    @MapKey("id")
    Map<Integer, User> getAllUsers();

}
