package com.example.authentication.controller;

import com.example.authentication.Service.AuthenticationService;
import com.example.authentication.Service.FirebaseService;
import com.example.authentication.Service.RateLimitingService;
import com.example.authentication.controller.exception.ApplicationException;
import com.example.authentication.request.authentication.LoginRequest;
import com.example.authentication.response.SuccessResponse;
import com.example.authentication.response.account.AccountResponse;
import com.example.authentication.response.authentication.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authentication;
    private final RateLimitingService rateLimitingService;
    private final MessageSource messageSource;
    private final FirebaseService firebaseService;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> login(@Validated @RequestBody LoginRequest form) {
        var isActive = rateLimitingService.preventSpamLogin(form.getEmail());
        if (!isActive) {
            throw new ApplicationException("login.email-inactive");
        }
        var authenticationResponse = authentication.login(form);
        var message = messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale());
        SuccessResponse<AuthenticationResponse> response = new SuccessResponse<>(
                HttpStatus.OK.value(),
                message,
                authenticationResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<SuccessResponse<AccountResponse>> getUserInfo() {
        AccountResponse account = authentication.getUserInfo();
        SuccessResponse<AccountResponse> response = new SuccessResponse<>(
                HttpStatus.OK.value(),
                null,
                account
        );
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/fire-base")
//    public ResponseEntity<SuccessResponse<?>> fireBase(@RequestBody FcmRequest request) {
//        var a = firebaseService.sendFcmMessage(request);
//        var message = messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale());
//        SuccessResponse<?> response = new SuccessResponse<>(
//                HttpStatus.OK.value(),
//                message,
//                null
//        );
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/fcm-token")
//    public ResponseEntity<String> getFcmToken(HttpServletRequest request) {
//        String idToken = request.getHeader("Authorization"); // Get the Firebase ID token from the Authorization header
//        try {
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//            String fcmToken = decodedToken.getClaims().get("fcm_token").toString();
//            return ResponseEntity.ok(fcmToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Error verifying Firebase ID token");
//        }
//    }

}
