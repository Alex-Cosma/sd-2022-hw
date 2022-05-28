package com.lab4.demo.sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SMSService {
    private final String ACCOUNT_SID = "AC9a343478abbcda1183efd99e509d5db4";

    private final String AUTH_TOKEN = "c58bdcefdf96748da06cd4b6bfd6b0f4";

    private final String FROM_NUMBER = "+16515041944";

    public void send() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println("here");
        Message message = Message.creator(new PhoneNumber("+40755266018"), new PhoneNumber(FROM_NUMBER), "Best Music Player Library Ever!")
                .create();

    }
}
