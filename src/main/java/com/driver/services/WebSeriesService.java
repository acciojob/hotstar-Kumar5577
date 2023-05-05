package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        //Don't forget to save the production and webseries Repo
        if(webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName())!=null)
            throw new Exception("Series is already present");
      ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();
       WebSeries webSeries = new WebSeries();
       webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
       webSeries.setRating(webSeriesEntryDto.getRating());
       webSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
       webSeries.setProductionHouse(productionHouse);
       webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
       if (productionHouse.getWebSeriesList()==null){
           List<WebSeries> webSeries1 = new ArrayList<>();
           webSeries1.add(webSeries);
           productionHouse.setWebSeriesList(webSeries1);
       }
       productionHouse.getWebSeriesList().add(webSeries);

       double rating =0;
       double ans =0;
       List<WebSeries>webSeries1 = productionHouse.getWebSeriesList();
       for(WebSeries webSeries2:webSeries1){
           rating+=webSeries2.getRating();
       }
       ans = rating/webSeries1.size();
    productionHouse.setRatings(ans);
    ProductionHouse saved =productionHouseRepository.save(productionHouse);
    webSeriesRepository.save(webSeries);
        int id = saved.getId();
        return id ;
    }

}
