package com.project.controller;

import com.project.app.Main;
import com.project.config.Constants;
import com.project.model.Message;
import com.project.model.Option;
import com.project.model.Supplier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Taxi controller that deals with the requests sent to dave and taxi routes
 */
@RestController
public class TaxiController {

    @GetMapping("/dave")
    public List<Option> davePrices(@RequestParam(value = "pickup") String pickUp,
                                   @RequestParam(value = "dropoff") String dropOff) {
        int timeOut = 10000;

        Supplier supplier = new Supplier();
        Message daveTaxi = supplier
                .getSupplierInformation(Constants.DAVE, timeOut, pickUp, dropOff);
        if(daveTaxi == null) {
            return new ArrayList<>();
        } else {
            daveTaxi.getOptions().sort(Comparator.comparing(Option::getPrice).reversed());

            for (Option option : daveTaxi.getOptions()) {
                option.setSupplier(daveTaxi.getSupplier());
            }
        }

        return daveTaxi.getOptions();
    }

    @GetMapping("/taxi")
    public List<Option> taxiPrices(@RequestParam(value = "pickup") String pickUp,
                                   @RequestParam(value = "dropoff") String dropOff,
                                   @RequestParam(value = "passengers") String passengers) {

        int numPassengers = 0;

        try {
            numPassengers = Integer.parseInt(passengers);
        } catch(NumberFormatException ex) {
            System.out.println("Number of passengers must be an integer");
            ex.getMessage();
        }

        Message joinedList = Main.callApis(pickUp, dropOff);

        if(joinedList.getOptions() == null) {
            return new ArrayList<>();
        } else {
            joinedList.getOptions().sort(Comparator.comparing(Option::getPrice).reversed());
            joinedList.setOptions(joinedList.minimumPrice());
            return joinedList.filterOptions(numPassengers);
        }
    }

}
