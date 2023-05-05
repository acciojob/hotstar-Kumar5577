package com.driver.services;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionHouse = new ProductionHouse();
        productionHouse.setRatings(0);
        productionHouse.setWebSeriesList(new ArrayList<>());
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouseRepository.save(productionHouse);
        int id = productionHouseRepository.findByName(productionHouseEntryDto.getName()).getId();
        return id;
    }



}
