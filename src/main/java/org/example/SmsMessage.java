package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsMessage {
    private String phone;
    private String message;


    @Override
    public String toString() {
        return "SmsMessage [phoneNumber=" + phone + ", message=" + message + "]";
    }
}
