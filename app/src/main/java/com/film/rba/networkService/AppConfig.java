package com.film.rba.networkService;

public class AppConfig {

    public static final String TAG = "RBA";

    public static final int RETRY = 5;
    public static final int TIMEOUT = 20 * 1000;
    public static final float backoffMultiplier = 1.0f;


    public static String URL_DEV = "https://rbacineprime.com/api/";


    private static final String DOMAIN = "https://rbacineprime.com/";

    public static final String image_url = DOMAIN + "uploads/";

    private static final String BASE_URL = DOMAIN + "api/";

    public static final String login                  =  BASE_URL + "login";
    public static final String register               =  BASE_URL + "register";
    public static final String logout                 =  BASE_URL + "logout";
    public static final String home                   =  BASE_URL + "home";
    public static final String forgot_password        =  BASE_URL + "forgot_password";
    public static final String change_password        =  BASE_URL + "change_password";
    public static final String profile                =  BASE_URL + "profile";



}