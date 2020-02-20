package com.project.app;

import com.project.config.Constants;
import com.project.model.Message;
import com.project.model.Option;
import com.project.model.Supplier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Comparator;
import java.util.List;

@SpringBootApplication @ComponentScan({"com.project"})
public class Main {

    public static void main(String[] args) {

//        if(args.length < 2) {
//            System.out.println("Usage: please supply pickup and drop off latitude" +
//              " and longitude");
//            System.out.println("Or supply pickup, drop off and number of passengers");
//        }

        if(args.length == 0) {
            // launch API
            SpringApplication.run(Main.class, args);
        } else if(args.length == 2) {
            // Console application to print the search results for Dave's Taxis
            String pickUp = args[0];
            String dropOff = args[1];
            int timeOut = 10000;

            Supplier supplier = new Supplier();
            Message daveTaxi = supplier
              .getSupplierInformation(Constants.DAVE, timeOut, pickUp, dropOff);

            if(daveTaxi == null) {
                System.out.println("No results to display");
            } else {
                daveTaxi.getOptions().sort(Comparator.comparing(Option::getPrice).reversed());

                for (Option option : daveTaxi.getOptions()) {
                    System.out.println(option.toString());
                }
            }

        } else if(args.length == 3) {
            // Console application to filter by number of passengers
            String pickUp = args[0];
            String dropOff = args[1];
            int passengers = 0;

            try {
                passengers = Integer.parseInt(args[2]);
            } catch(NumberFormatException ex) {
                System.out.println("Number of passengers must be an integer");
                ex.getMessage();
            }

            Message joinedList = callApis(pickUp, dropOff);

            if(joinedList.getOptions() == null) {
                System.out.println("No results to display");
            } else {
                joinedList.getOptions().sort(Comparator.comparing(Option::getPrice).reversed());
                joinedList.setOptions(joinedList.minimumPrice());
                List<Option> filteredOptions = joinedList.filterOptions(passengers);

                System.out.println();
                for (Option option : filteredOptions) {
                    System.out.println(option.supplierString());
                }
            }
        } else {
            System.out.println("To start api please supply no command line arguments");
            System.out.println("To make a request to dave's taxis please supply pickup " +
                            "and drop off latitude and longitude");
            System.out.println("To call all taxi apis please supply pickup, drop off " +
                    "and number of passengers");
        }
    }

    /**
     * Calls all three taxi apis and returns a joined response message depending
     * on what apis are available
     * @return list of joined responses
     */
    public static Message callApis(String pickUp, String dropOff) {
        int timeOut = 2000;
        Supplier supplier = new Supplier();

        Message daveTaxi = supplier
                .getSupplierInformation(Constants.DAVE, timeOut, pickUp, dropOff);
        Message ericTaxi = supplier
                .getSupplierInformation(Constants.ERIC, timeOut, pickUp, dropOff);
        Message jeffTaxi =supplier
                .getSupplierInformation(Constants.JEFF, timeOut, pickUp, dropOff);

        if(daveTaxi != null) {
            for (Option option : daveTaxi.getOptions()) {
                option.setSupplier(daveTaxi.getSupplier());
            }
        }
        if(ericTaxi != null) {
            for (Option option : ericTaxi.getOptions()) {
                option.setSupplier(ericTaxi.getSupplier());
            }
        }
        if(jeffTaxi != null) {
            for (Option option : jeffTaxi.getOptions()) {
                option.setSupplier(jeffTaxi.getSupplier());
            }
        }

        Message joinedList = new Message();
        joinedList.setOptions(joinedList.joinMessages(daveTaxi, ericTaxi));
        joinedList.setOptions(joinedList.joinMessages(joinedList, jeffTaxi));

        return joinedList;
    }

}
