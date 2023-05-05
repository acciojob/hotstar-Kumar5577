package com.driver.repository;

import com.driver.model.Subscription;
import com.driver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {



}
