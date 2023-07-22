package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TraxEnvsConf {

    @Value("${trax.it.istraining:false}")
    private boolean isTraining;

    @Value("${trax.it.username}")
    private String itUsername;

    @Value("${trax.it.password}")
    private String itPassword;

    @Value("${trax.it.categories.live}")
    private List<String>  itCategoriesLive;

    @Value("${trax.it.categories.training}")
    private List<String>  itCategoriesTraining;

    @Value("${trax.de.username}")
    private  String deUsername;

    @Value("${trax.de.password}")
    private  String dePassword;

    @Value("${trax.de.categories.live}")
    private List<String>  deCategoriesLive;

    @Value("${trax.de.categories.training}")
    private List<String>  deCategoriesTraining;
    private User italianUser;
    private User germanUser;


    @PostConstruct
    public void setup(){
        this.italianUser = new User(itUsername,itPassword);
        this.germanUser= new User(deUsername,dePassword);
    }


    public String getCategory(String category, User user){
        if (germanUser.username.equals(user.username))
            return germanCategory(category);

        return italianCategory(category);
    }

    private String germanCategory(String category) {
        if (isTraining)
          return getCategory(category,deCategoriesTraining);

        return getCategory(category,deCategoriesLive);
    }

    private String italianCategory(String category) {

        if (isTraining)
            return getCategory(category,itCategoriesTraining);

        return getCategory(category,itCategoriesLive);
    }

    private String getCategory(String category, List<String> categories) {
        for (String str : categories){
            if (str.contains(category))
                return str.substring(str.indexOf(".") + 1);
        }
        return null;
    }

    public User getAccount(String country){
        if ("de".equals(country))
            return germanUser;
        return italianUser;
    }


    public static class User{

        public User(String username,String password){
            this.username = username;
            this.password = password;
        }

        private String username;
        private String password;
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }
}
