package com.example.authentication.Service.impl;

import com.example.authentication.Service.FirebaseService;
import com.example.authentication.controller.exception.ApplicationException;
import com.example.authentication.request.FcmRequest;
import com.example.authentication.request.TestResquest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public String sendFcmMessage(FcmRequest request) {
        Message message = Message.builder()
                .setToken(request.getDeviceToken())
                .putData("title", request.getTitle())
                .putData("body", request.getBody())
                .build();
        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            System.err.println("Exception " + e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @SneakyThrows
    public String saveFirebase(TestResquest request) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> a =
                dbFirestore.collection("Web-NDA").document("NDA").set(request);
        return a.get().getUpdateTime().toString();
    }
}
