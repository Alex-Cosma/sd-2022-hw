package com.app.bookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.util.Arrays;

@SpringBootApplication
public class BookingApplication {

//    public static final String ACCOUNT_SID = System.getenv("AC7f0ce2f681fbed6b1355149e3e7417eb");
//    public static final String AUTH_TOKEN = System.getenv("c9ddfc64e01b022975d565333ebb981b");


    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);

//        String ACCOUNT_SID = System.getenv("AC7f0ce2f681fbed6b1355149e3e7417eb");
//        String AUTH_TOKEN = System.getenv("c9ddfc64e01b022975d565333ebb981b");
//
//        Twilio.init("AC7f0ce2f681fbed6b1355149e3e7417eb", "c9ddfc64e01b022975d565333ebb981b");
//        Message message = Message.creator(
//                        //new com.twilio.type.PhoneNumber("+40786079644"),
//                        new com.twilio.type.PhoneNumber("+40756285628"),
//                        new com.twilio.type.PhoneNumber("+19895463499"),
//                        "This is the ship that made the Kessel Run in fourteen parsecs?")
////                .setMediaUrl(
////                        Arrays.asList(URI.create("https://c1.staticflickr.com/3/2899/14341091933_1e92e62d12_b.jpg")))
//                .create();
//
//        System.out.println(message.getSid());
    }

}
