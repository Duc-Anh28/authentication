package com.example.authentication.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SenderEmailRequest {
    private String subject;
    private String msg;
    private String sendToAddress;
    private String sendFromAddress;
}
