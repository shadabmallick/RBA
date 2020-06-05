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


    public static final String login                  = BASE_URL + "login";
    public static final String register               = BASE_URL + "register";
    public static final String logout                 = BASE_URL + "logout";
    public static final String home                   = BASE_URL + "home";
    public static final String forgot_password        = BASE_URL + "forgot_password";
    public static final String change_password        = BASE_URL + "change_password";
    public static final String profile                = BASE_URL + "profile";
    public static final String update_profile         = BASE_URL + "update_profile";
    public static final String showMore               = BASE_URL + "showMore";
    public static final String videodetails           = BASE_URL + "videodetails";
    public static final String search_video           = BASE_URL + "search_video";



    /// url ...
    public static final String FAQ_URL = DOMAIN + "faq";
    public static final String ABOUT_US_URL = DOMAIN + "about";
    public static final String CONTACT_US_URL = DOMAIN + "contact";
    public static final String TERMS_URL = DOMAIN + "terms_of_use";
    public static final String PRIVACY_URL = DOMAIN + "privacy";

}