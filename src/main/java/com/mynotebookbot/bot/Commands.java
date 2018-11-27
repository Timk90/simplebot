package com.mynotebookbot.bot;

import com.mynotebookbot.dao.User;
import com.mynotebookbot.util.ImpValidator;
import com.mynotebookbot.utilDB.ImpServiceDB;
import com.mynotebookbot.utilDB.ServiceDB;
import java.util.*;

/*
       This class provides the functionality for all the bot's commands.
       There are several commands such as:
       /add - it adds new user to the DB
       /replace - change existing user
       /remove - it removes user from DB (by id, otherwise it removes all users with the same names)
       /show - it shows a particular user typing his Name and Surname separated by space
       /showAll - it shows all the users from DB

       created by Timk...
*/

class Commands{

     //a list of all the available bot's commands
     static String[] array = {"/add", "/remove", "/replace", "/help", "/show", "/showAll"};
     private static final ArrayList<String> commands = new ArrayList(Arrays.asList(array));
     private static ImpValidator validator = new ImpValidator();
     private static String result = "";
     private static String cursorPosition = "";
     private static User currentUser;
     private static String currentState = "empty";
     private static ServiceDB db = new ImpServiceDB();

     // command {/show} Name Surename
     public static String showUser(String nameAndSurname){
         if(nameAndSurname.equals("/show") || nameAndSurname.equals(commands.get(4))){
             return "введите ФИО, интересующего Вас пользователя в порядке: Имя, Фамилия (через пробел)";
         }else{
             ArrayList<String> names = new ArrayList<String>();
             if(validator.checkNameSurename(nameAndSurname)){
                 names.addAll(Arrays.asList(nameAndSurname.split("\\s")));
             }else{
                 return "Провереть правильно ввода ФИО пользователя. Имя и Фамилия должны быть отделены пробелом (например: Иванов Иван)";
             }
             System.out.println(names);
             String output = db.showSelectedUsersFromDB(names);
             //search for user in DB and show all the details
             cursorPosition = "";
             setCurrentState("empty");
             output = (output.length()==0) ? "Пользователя(ей) с указанным ФИО нет в базе данных, повторите поиск или забейте..." : output;
             return output;
         }
     }

     // add new user {/add} to DB. Mandatory fields: Name, Surename, birth
     public static String addUser(String field){

             if(cursorPosition.length() == 0 && currentState.equals(commands.get(0))){
                currentUser = new User();
                cursorPosition = "name";
                return "Введите имя:";
             }else if(cursorPosition.equals("name") && currentState.equals(commands.get(0))){
                if(field.length() ==0){return "Имя не может быть пустым!";}
                if(!validator.checkUserName(field)){return "Имя должно состоять из 2-25 символов от А до Я";}
                currentUser.setName(field);
                cursorPosition = "surename";
                return "Введите фамилию:";
             }else if(cursorPosition.equals("surename") && currentState.equals(commands.get(0))){
                if(field.length() ==0){return "Фамилия не может быть пустой!";}
                if(!validator.checkUserName(field)){return "Фамилия должна состоять из 2-25 символов от А до Я";}
                currentUser.setSurename(field);
                cursorPosition = "birth";
                return "Введите дату рождения в формате: дд.мм.гггг:";
             }else if(cursorPosition.equals("birth") && currentState.equals(commands.get(0))){
                if(field.length() ==0){return "Дата рождения не может отсутствовать!";}
                if(!validator.checkUserBirth(field)){return "Дата рождения должна быть введена в формате дд.мм.гггг, д - день, м - месяц, г - год.";}
                currentUser.setBirth(field);
                cursorPosition = "country";
                return "Введите страну:";
             }else if(cursorPosition.equals("country") && currentState.equals(commands.get(0))){
                currentUser.setCountry(field);
                cursorPosition = "town";
                return "Введите город:";
             }else if(cursorPosition.equals("town") && currentState.equals(commands.get(0))){
                currentUser.setTown(field);
                cursorPosition = "street";
                return "Улицу:";
             }else if(cursorPosition.equals("street") && currentState.equals(commands.get(0))){
                currentUser.setStreet(field);
                cursorPosition = "zipcode";
                return "Введите почтовый индекс:";
             }else if(cursorPosition.equals("zipcode") && currentState.equals(commands.get(0))) {
                 if(!validator.checkUserZip(field)){return "почтовый код состоит из 6 цифр";}
                 currentUser.setZipcode(field);
                 if(currentUser.getName().length()>0 && currentUser.getSurename().length() >0 && currentUser.getBirth().length() > 0){
                     db.addUserToDB(currentUser);
                     System.out.println("user has been added: "+currentUser.toString());
                     cursorPosition = "";
                     setCurrentState("empty");
                     return "Запись добавлена! \n" + currentUser.toString();
                 }else{
                     return "ошибка при добавлении пользователя";
                 }
             }
         setCurrentState("empty");
         return "неизвестное прервание";
     }

     // remove user {/remove} from DB by ID
     public static String removeUser(String id){
         if(id.equals("/remove") || id.equals(commands.get(1))){
             return "введите id пользователя, которого следует удалить...на веки веков...";
         }else{
             try {
                 int intID = Integer.parseInt(id);
                 User user = new User();
                 user = db.getUserByID(intID);
                 cursorPosition = "";
                 setCurrentState("empty");
                 try{
                     if(user.getName().length()>0){
                         db.removeUserFromDB(intID);
                     }
                     return "Запись успешно удалена!";
                 }catch (NullPointerException e){
                     return "Пользователя(ей) с указанным id нет в базе данных, повторите попытку... или забейте...";
                 }

             }catch(NumberFormatException e){
                 return "введите правильный id пользователя. Некорректный ввод.";
             }
         }
     }

     // change the user's data {/replace}
     public static String replaceUser(String id){
         if(id.equals("/replace") || id.equals(commands.get(2))) {
             setCurrentState("/replace");
             cursorPosition = "";
             currentUser = null;
             return "введите id пользователя, которого нужно отредактировать.";
         }else{
             try {
                 User user = new User();
                 if(currentUser == null){
                     int intID = Integer.parseInt(id);
                     currentUser = db.getUserByID(intID);
                 }
                 try{
                     if(currentUser.getId()>0){
                         String str = id;
                         if(cursorPosition.equals("") && getCurrentState().equals("/replace")){
                             cursorPosition = "name";
                             System.out.println(str);
                             return "ТЕКУЩИЕ ДАННЫЕ ПОЛЬЗОВАТЕЛЯ:\n"+currentUser.toString()+"\n введите новое имя пользователя";

                         }else if(cursorPosition.equals("name") && getCurrentState().equals("/replace")){
                             String name = "";
                             if(validator.checkUserName(str)){
                                 name = str;
                                 cursorPosition = "surename";
                                 currentUser.setName(name);
                                 return "Введите новую фамилию, текущая: "+currentUser.getSurename();
                             }else{
                                 return "Вы ввели некорректное имя, попробуйте заново";
                             }
                         }else if(cursorPosition.equals("surename") && getCurrentState().equals("/replace")){
                             String surename = "";
                             if(validator.checkUserName(str)) {
                                 surename = str;
                                 cursorPosition = "birth";
                                 currentUser.setSurename(surename);
                                 return "Введите новую дату рождения, текущая: " + currentUser.getBirth();
                             }else {
                                 return "Вы ввели некорректную фамилию, попробуйте заново";
                             }
                         }else if(cursorPosition.equals("birth") && getCurrentState().equals("/replace")){
                             String birth = "";
                             if(validator.checkUserBirth(str)){
                                 birth = str;
                                 cursorPosition = "country";
                                 currentUser.setBirth(birth);
                                 return "Введите новую страну, текущая: "+currentUser.getCountry();
                             }else{
                                 return "Вы ввели некорректную дату рождения, попробуйте заново";
                             }
                         }else if(cursorPosition.equals("country") && getCurrentState().equals("/replace")){
                             String country = "";
                                 country = str;
                                 cursorPosition = "town";
                                 currentUser.setCountry(str);
                                 return "Введите новый город, текущий: "+currentUser.getTown();
                         }else if(cursorPosition.equals("town") && getCurrentState().equals("/replace")){
                             String town = "";
                             town = str;
                             cursorPosition = "street";
                             currentUser.setTown(str);
                             return "Введите новую улицу, текущая: "+currentUser.getStreet();
                         }else if(cursorPosition.equals("street") && getCurrentState().equals("/replace")){
                             String street = "";
                             street = str;
                             cursorPosition = "zipcode";
                             currentUser.setStreet(str);
                             return "Введите новый почтовый индекс, текущий: "+currentUser.getZipcode();
                         }else if(cursorPosition.equals("zipcode") && getCurrentState().equals("/replace")) {
                            String zip = "";
                            if(validator.checkUserZip(str)){
                                zip = str;
                                cursorPosition = "";
                                currentUser.setZipcode(str);
                            }else{
                                return "Вы ввели некорректный индекс, все ваши письма потеряются....АХАХАХ (ацкий смех...)";
                            }
                             db.changeUserInDB(currentUser);

                         }
                         String tmp = currentUser.toString();
                         currentUser = null;
                         cursorPosition = "";
                         setCurrentState("empty");
                         return "НОВЫЕ ДАННЫЕ ПОЛЬЗОВАТЕЛЯ\n" + tmp + "\n Данные пользователя успешно изменены!";

                     }
                     return "Запись изменена!";
                 }catch (NullPointerException e){
                     return "Пользователя(ей) с указанным id нет в базе данных, повторите попытку... или забейте...";
                 }

             }catch(NumberFormatException e){
                 return "введите правильный id пользователя. Некорректный ввод.";
             }
         }
     }

     // return the list of commands available for our bot {/help}
     public static String getAllCommands(){
        setCurrentState("empty");
        return getInitCommand();
     }

     // generate a list of commands available for our bot pushing {/help} with description
     public static String getInitCommand(){

       StringBuilder sb = new StringBuilder();
       sb.append(commands.get(0)+" добавляет запись (нового пользователя) \n");
       sb.append(commands.get(1)+" удаляет запись \n");
       sb.append(commands.get(2)+" изменяет запись \n");
       sb.append(commands.get(3)+" описание доступных команд \n");
       sb.append(commands.get(4)+" поиск записи по ФИО пользователя \n");
       sb.append(commands.get(5)+" показать все записи \n");
       return sb.toString();
     }

     // list of already added to DB {/showAll} with description
     public static String showAllUsers(){
       //Exctract and show all the users from DB
         cursorPosition = "";
         currentUser = null;
         db.showAllUsersFromDB();
         setCurrentState("empty");
         return db.showAllUsersFromDB()+"\nBсе записи книги";
     }


     // search for a command the user's data {/replace}
     public static String getCommand(String txtMsg, String state) {
         String output = "";

         if (getCurrentState().equals("empty") && equalsToCommandMsg(txtMsg)) {
             System.out.println("txtMsg 1= "+txtMsg);
             System.out.println("state 1= "+state);
             output = actionCmd(txtMsg, txtMsg);
         } else if (!getCurrentState().equals("empty") && !equalsToCommandMsg(txtMsg)) {
             System.out.println("txtMsg 2= "+txtMsg);
             System.out.println("state = 2"+state);
             output = actionCmd(txtMsg, getCurrentState());
         } else if (!getCurrentState().equals("empty") && equalsToCommandMsg(txtMsg)) {
             System.out.println("txtMsg 3 = "+txtMsg);
             System.out.println("state 3 = "+state);
             setCurrentState(txtMsg);
             cursorPosition = "";
             output = actionCmd(txtMsg, txtMsg);
         }else{
              setCurrentState("empty");
              output = "Возник вопрос? Попробуйте выбрать среди доступных команд /help \n Можете связаться с нами, но мы Вам вряд ли поможем.";
         }

          result = output;
          return result;
     }

    public static String getCurrentState(){
        return currentState;
     }

    public static void setCurrentState(String st){
        currentState = st;
    }

/*
!========================================================================================!
!Below there are auxiliary methods helping to work insede the main methods located above.!
!========================================================================================!
 */

//it checks if the msg from user coincides with some of command
    private static boolean equalsToCommandMsg(String msg){
         boolean flag = false;
         for(String str : commands){
             if(str.equals(msg)){flag = true;}
         }
         return flag;
    }
//checking the users' msg
    private static String actionCmd(String txtMsg, String state) {
        String output = "";
        System.out.println("Current state "+getCurrentState());
        if (getCurrentState().equals("empty") || equalsToCommandMsg(txtMsg)){
            switch (commands.indexOf(state)) {
                case 0:
                    setCurrentState(commands.get(0));
                    output = "Следуйте инструкциям: \n" + addUser(txtMsg);
                    break;
                case 1:

                    setCurrentState(commands.get(1));
                    output = "Напишите ID пользователя, которого нужно удалить. \n";
                    break;
                case 2:
                    setCurrentState(commands.get(2));
                    output = "Напишите ID пользователя, которого нужно заменить \n";
                    break;
                case 3:
                    setCurrentState(commands.get(3));
                    output = getAllCommands().toString();
                    break;
                case 4:
                    setCurrentState(commands.get(4));
                    output = showUser(txtMsg);
                    break;
                case 5:
                    setCurrentState(commands.get(5));
                    output = showAllUsers();
                    break;
                default:
                    output = "error in action command 1 block";
                    break;
            }
        }else if(!equalsToCommandMsg(txtMsg)){
            switch (commands.indexOf(state)) {
                case 0:
                    output = addUser(txtMsg);
                    break;
                case 1:
                    output = removeUser(txtMsg);
                    break;
                case 2:
                    output = replaceUser(txtMsg);
                    break;
                case 3:
                    output = getAllCommands().toString();
                    break;
                case 4:
                    output = showUser(txtMsg).toString();
                    break;
                case 5:
                    output = showAllUsers().toString();
                    break;
                default:
                    output = "error in action command 2 block";
                    break;
            }
        }
        return  output;
    }
}
