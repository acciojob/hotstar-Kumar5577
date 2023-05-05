package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        userRepository.save(user);
       int id = userRepository.findByName(user.getName()).getId();
        return id;
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        List<WebSeries>webSeries = webSeriesRepository.findAll();
        User user = userRepository.findById(userId).get();
        SubscriptionType subscriptionType = user.getSubscription().getSubscriptionType();
       int count =0;
       for (WebSeries webSeries1 :webSeries){
           if (webSeries1.getSubscriptionType()==subscriptionType)
               count++;
       }
        return count;
    }


}
