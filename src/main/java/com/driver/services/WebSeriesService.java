package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo

       if(webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName())!=null)
           throw new Exception("Series is already present");
       WebSeries webSeries = new WebSeries();
       webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
       webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
       webSeries.setProductionHouse(productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get());
       webSeries.setRating(webSeriesEntryDto.getRating());
       webSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
       webSeriesRepository.save(webSeries);
        List<WebSeries>webSeries1 = webSeriesRepository.findAll();
        double rating = 0;
        int totalSeries = webSeries1.size();
        for(WebSeries webSeries2 :webSeries1){
            rating+=webSeries2.getRating();
        }
        rating = rating/totalSeries;
        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();
        productionHouse.setRatings(rating);
        productionHouse.getWebSeriesList().add(webSeries);
        productionHouseRepository.save(productionHouse);

        int id = webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName()).getId();
        return id ;
    }

}
