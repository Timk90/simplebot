==========================================
Simple TelegramBot
==========================================

This bot represents the simple Notebook in telegramm if you need it for any reasons. It has several commands including: adding new note with users' data to DB, editing and removing it. As well as you can look at all the notes at any time. 

HOW TO USE:------------------------------------------------------------

0). Start MAVEN goal to download three jars from repositiory:
    (i) derby-10.13.1.1.jar
    (ii) mybatis-3.4.6.jar
    (iii) telegrambots-4.1-jar-with-dependencies.jar 
    these three jars are nessecary to make the BOT working.

1). If the it is first start you must create DB with a table NOTES. 
    There is a Class initDB with main() method doing that. 
    It initializes DB, create the table and populate it for the sake of
    checking of functionality. I have used Derby DB because it is embedded.
    So, during the first run just start main() in initDB.
    locations: /src/main/java/com/mynotebookbot/util/initDB
    If you would like to prevent an initial population tnen just comment
    the lines mapper.addUser(); throughout the code in initDB 
    (three fallowing occurrences). 

2). After first steps done your bot is ready for HARD WORK. 
    To start your BOT run main() in class called SimpleBot.Class 
    Now it is working. Check it on Telegram. 

    p.s. do not forget to insert your correct TOKEN and BOT_NAME. 

3). Enjoy.... :)

Do not hesitate to contact me if any questions. 
Best. 


