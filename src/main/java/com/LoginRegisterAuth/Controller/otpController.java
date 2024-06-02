package com.LoginRegisterAuth.Controller;

import com.LoginRegisterAuth.Model.User;
import com.LoginRegisterAuth.Model.otpData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import com.LoginRegisterAuth.Service.userService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class otpController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private userService userService;

    private Map<String, Integer> otpStore = new HashMap<>();

    @PostMapping("/send-otp")
    public Map<String, String> sendOtp(@RequestBody otpData otpData) {
        int otp = generateOtp();
        otpStore.put(otpData.getEmail(), otp);

        sendEmail(otpData.getEmail(), "Your OTP Code", "Your OTP code is: " + otp);

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully");
        return response;
    }

    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestBody otpData otpData) {
        Map<String, String> response = new HashMap<>();
        Integer storedOtp = otpStore.get(otpData.getEmail());

        if (storedOtp != null && storedOtp == otpData.getOtp()) {
            User user = userService.findByEmail(otpData.getEmail());
            if (user != null) {
                user.setOtpVerified(true);
                userService.saveUser(user);
            }
            response.put("message", "OTP verified successfully");
        } else {
            response.put("message", "Invalid OTP");
        }

        return response;
    }

    private int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
