package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        int price =0;
        if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.BASIC)
            price = 500 + subscriptionEntryDto.getNoOfScreensRequired()*200;
        else if (subscriptionEntryDto.getSubscriptionType()==SubscriptionType.PRO) {
            price = 800 + subscriptionEntryDto.getNoOfScreensRequired()*250;


        }
        else

            price = 1000 + 350*subscriptionEntryDto.getNoOfScreensRequired();
        subscription.setTotalAmountPaid(price);
        subscription.setStartSubscriptionDate(Date.from(Instant.now()));
        subscriptionRepository.save(subscription);
        return price;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user = userRepository.findById(userId).get();
        Integer upgradeDifference = 0;
        if(user.getSubscription().getSubscriptionType()==SubscriptionType.ELITE)
            throw new Exception ("Already the best Subscription");
        if (user.getSubscription().getSubscriptionType()==SubscriptionType.BASIC){
            upgradeDifference =(800 + user.getSubscription().getNoOfScreensSubscribed()*250) - user.getSubscription().getTotalAmountPaid();
            user.getSubscription().setSubscriptionType(SubscriptionType.PRO);
            user.getSubscription().setStartSubscriptionDate(Date.from(Instant.now()));
            user.getSubscription().setTotalAmountPaid(800 + user.getSubscription().getNoOfScreensSubscribed()*250);
        }
        if (user.getSubscription().getSubscriptionType()==SubscriptionType.PRO){
            upgradeDifference =(1000 + user.getSubscription().getNoOfScreensSubscribed()*350) - user.getSubscription().getTotalAmountPaid();
            user.getSubscription().setSubscriptionType(SubscriptionType.ELITE);
            user.getSubscription().setStartSubscriptionDate(Date.from(Instant.now()));
            user.getSubscription().setTotalAmountPaid(1000 + user.getSubscription().getNoOfScreensSubscribed()*350);
        }

         userRepository.save(user);

        return upgradeDifference;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<Subscription> subscriptions= subscriptionRepository.findAll();
       int totalRevenue = 0;
       for(Subscription subscription:subscriptions){
           totalRevenue+=subscription.getTotalAmountPaid();
       }

        return totalRevenue;
    }

}
