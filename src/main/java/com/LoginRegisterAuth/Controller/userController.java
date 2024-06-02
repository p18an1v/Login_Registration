package com.LoginRegisterAuth.Controller;

import com.LoginRegisterAuth.Model.LoginRequest;
import com.LoginRegisterAuth.Model.User;
import com.LoginRegisterAuth.Model.otpData;
import com.LoginRegisterAuth.Service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private otpController otpController;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^(\\+91|91)?[7-9]\\d{9}$");

    private static final Logger logger = LoggerFactory.getLogger(userController.class);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        logger.info("Register request received for user: " + user.getEmail());
        if (user.getFullName().isEmpty() || !user.getFullName().matches("[a-zA-Z ]+")) {
            return ResponseEntity.badRequest().body("Invalid full name.");
        }
        if (!MOBILE_PATTERN.matcher(user.getMobile()).matches()) {
            return ResponseEntity.badRequest().body("Invalid mobile number.");
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return ResponseEntity.badRequest().body("Invalid email address.");
        }
        if (user.getUsername().length() <= 3) {
            return ResponseEntity.badRequest().body("Username must be longer than 3 characters.");
        }
        if (user.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long.");
        }
        if (userService.userExists(user.getUsername(), user.getEmail())) {
            return ResponseEntity.badRequest().body("User with this username or email already exists.");
        }

        userService.saveUser(user);

        otpData otp = new otpData(user.getEmail(), 0);
        otpController.sendOtp(otp);

        return ResponseEntity.ok("User OTP sent for verification successfully.");
    }

    @PostMapping("/verify-registration-otp")
    public ResponseEntity<?> verifyRegistrationOtp(@RequestBody otpData otp) {
        Map<String, String> verificationResult = otpController.verifyOtp(otp);
        String message = verificationResult.get("message");
        if ("OTP verified successfully".equals(message)) {
            return ResponseEntity.ok("User registration completed successfully.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }
}
