package com.mynotebookbot.dao;

//This class represent POJO user object with getters and setters
//user with all the fields is the main object of our notebook
public class User{

  int id;
  String name, surename;
  int age;
  String birth;
  String country, town, street;
  String zipcode;

  public User(){};

  public User(String name, String surename, String birth, String country, String town, String street, String zipcode){
      this.name = name;
      this.surename = surename;
      this.birth = birth;
      this.country = country;
      this.town = town;
      this.street = street;
      this.zipcode = zipcode;
  }

  public void setId(int id){
     this.id = id;
  }

  public long getId(){
     return this.id;
  }

  public void setName(String name){
     this.name = name;
  }

  public String getName(){
     return this.name;
  }

  public void setSurename(String surename){
     this.surename = surename;
  }

  public String getSurename(){
     return this.surename;
  }

  //it is possible to add the functionality of Age computation from birth
  // at the momnet it is not used anywhere
  public void setAge(int age){
     this.age = age;
  }
  public int getAge(){
     return this.age;
  }

  public void setBirth(String birth){
     this.birth = birth;
  }

    public String getBirth(){
        return this.birth;
    }


  public void setCountry(String country){
     this.country = country;
  }

  public String getCountry(){
     return this.country;
  }

  public void setTown(String town){
     this.town = town;
  }

  public String getTown(){
     return this.town;
  }

  public void setStreet(String street){
     this.street = street;
  }

  public String getStreet(){
     return this.street;
  }

  public void setZipcode(String zipcode){
     this.zipcode = zipcode;
  }

  public String getZipcode(){
     return this.zipcode;
  }

  @Override
  public String toString(){
      StringBuilder sb = new StringBuilder();
      if(this.getId()>0) sb.append("ID пользователя: "+ this.getId()+"\n");
      sb.append("Имя пользователя: "+ this.getName()+"\n");
      sb.append("Фамилия пользователя: "+ this.getSurename()+"\n");
      sb.append("День рождения: "+ this.getBirth()+"\n");
      sb.append("Cтрана: "+ this.getCountry()+"\n");
      sb.append("Город: "+ this.getTown()+"\n");
      sb.append("Улица: "+ this.getStreet()+"\n");
      sb.append("Почтовый индекс: "+ this.getZipcode()+"\n");
      return sb.toString();
  }

}
