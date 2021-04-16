Databasen documentaatio löytyy:
https://developer.android.com/training/data-storage/room


Aluksi Room:in lisäys gradleen.


MainActivity.java + activity_main.xml
= Login screen

CreateUser1.java + activity_create_user2.xml
= Ensimmäinen käyttäjänluonti

CreateUser2.java + activity_create_user2.xml
= Toinen käyttäjänluonti

HomeScreen.java + activity_home_screen.xml
= Testi Home screen

**** Databasen luonti ja komennot ****

UserDatabase.java
= Kannan luonti nimelle "userEntity"

UserEntity.java
= Kannan arvojen määritykset + tableName "users"

UserDao.java
= Kannan lisäys/etsimis/poisto toiminnot

Manifestissä oikea käynnistysjärjestys
