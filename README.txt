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

1). If the fisrt start you must create DB with Table NOTES. 
    There is a Class initDB with main() method doing that. 
    It initialize the DB, create the Table and populate for the sake
    to check a functionality. I have used Derby because it is imbedded.
    So, during the first run just start main() in initDB.
    locations /src/main/java/com/mynotebookbot/util/initDB
    If you would like to prevent the initial population just comment
    the lines mapper.addUser(); throughout the code in initDB (three 
    following ocurances). 

2). After first step made your bot is ready for HARD WORK. 
    To start your BOT run main() in class called SimpleBot. 
    Now it is working. Check it on Telegram. 

    p.s. do not forget to insert you TOKEN and BOT_NAME. 

3). Enjoy.... :)

Do not hesitate to contact me if any questions. 



