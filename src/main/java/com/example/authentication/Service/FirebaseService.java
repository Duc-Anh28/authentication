package com.example.authentication.Service;

import com.example.authentication.request.FcmRequest;
import com.example.authentication.request.TestResquest;

public interface FirebaseService {
    String sendFcmMessage(FcmRequest request);
    String saveFirebase(TestResquest request);
}
