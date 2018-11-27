package com.mynotebookbot.util;

//a scheme for validator implementations
public interface Validator {

    public boolean checkUserName(String name);
    public boolean checkUserBirth(String birth);
    public boolean checkUserZip(String zip);
    public boolean checkNameSurename(String nameSurename);


}
