package com.yugioh.cardmaker.server;

public class BaseURL {

    public static String baseURL = "http://192.168.0.13:5050/";

    public static String login    = baseURL + "user/login";
    public static String register = baseURL + "user/registration";

    //card
    public static String card = baseURL + "card/cards/";
    public static String editCard = baseURL + "card/change/";
    public static String deleteCard = baseURL + "card/delete/";
    public static String inputCard = baseURL + "card/input";
}