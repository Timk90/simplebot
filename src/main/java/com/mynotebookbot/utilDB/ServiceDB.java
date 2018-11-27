package com.mynotebookbot.utilDB;

import com.mynotebookbot.dao.User;
import java.util.List;


public interface ServiceDB {

    public boolean addUserToDB(User user);
    public boolean changeUserInDB(User user);
    public boolean removeUserFromDB(int id);
    public User getUserByID(int id);
    public String showAllUsersFromDB();
    public String showSelectedUsersFromDB(List<String> names);
}
