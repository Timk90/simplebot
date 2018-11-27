package com.mynotebookbot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Class provides functions to check the input date corresponds to RegEx
public class ImpValidator implements Validator {

    @Override
    public boolean checkUserName(String name) {
        String regex = "^[а-яА-Я]{2,25}$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean checkUserBirth(String birth) {
        String regex = "^(0?[1-9]|[1-2][0-9]|3[0-1])\\.(0[1-9]|1[0-2])\\.(1[8-9]\\d\\d|20[0-1][0-8])$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(birth);
        return matcher.matches();
    }

    @Override
    public boolean checkUserZip(String zip) {
        String regex = "^[0-9]{6}$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(zip);
        return matcher.matches();
    }

    @Override
    public boolean checkNameSurename(String nameSurename) {
        String regex = "^[а-яА-Я]{2,25}\\s[а-яА-Я]{2,25}$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(nameSurename);
        return matcher.matches();
    }

    public static void main(String[] args) {
        ImpValidator validator = new ImpValidator();
    // just for checking validator's functionality
    }
}
