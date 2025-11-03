package com.showcar.showcar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:your-email@gmail.com}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Error sending email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendOrderConfirmationEmail(String customerEmail, String customerName, String carName, 
                                          String orderId, String depositAmount) {
        String subject = "Xác nhận đơn đặt cọc - ShowCar";
        String body = String.format(
            "Xin chào %s,\n\n" +
            "Cảm ơn bạn đã đặt cọc tại ShowCar!\n\n" +
            "Thông tin đơn hàng:\n" +
            "- Mã đơn hàng: %s\n" +
            "- Xe: %s\n" +
            "- Số tiền đặt cọc: %s VNĐ\n\n" +
            "Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất để hoàn tất thủ tục.\n\n" +
            "Trân trọng,\n" +
            "Đội ngũ ShowCar",
            customerName, orderId, carName, depositAmount
        );
        
        sendEmail(customerEmail, subject, body);
    }

    public void sendTestDriveConfirmationEmail(String customerEmail, String customerName, 
                                             String carName, String preferredDate) {
        String subject = "Xác nhận yêu cầu lái thử - ShowCar";
        String body = String.format(
            "Xin chào %s,\n\n" +
            "Cảm ơn bạn đã đăng ký lái thử tại ShowCar!\n\n" +
            "Thông tin yêu cầu:\n" +
            "- Xe: %s\n" +
            "- Ngày mong muốn: %s\n\n" +
            "Chúng tôi sẽ liên hệ với bạn để xác nhận lịch hẹn lái thử.\n\n" +
            "Trân trọng,\n" +
            "Đội ngũ ShowCar",
            customerName, carName, preferredDate
        );
        
        sendEmail(customerEmail, subject, body);
    }

    public void sendMarketingEmail(String customerEmail, String customerName, String carName, 
                                  String carPrice, String promotion) {
        String subject = "Khuyến mãi đặc biệt - ShowCar";
        String body = String.format(
            "Xin chào %s,\n\n" +
            "Chúng tôi có một chương trình khuyến mãi đặc biệt dành cho bạn!\n\n" +
            "Xe %s hiện đang có giá: %s VNĐ\n" +
            "%s\n\n" +
            "Liên hệ ngay với chúng tôi để được tư vấn chi tiết!\n\n" +
            "Trân trọng,\n" +
            "Đội ngũ ShowCar",
            customerName, carName, carPrice, promotion
        );
        
        sendEmail(customerEmail, subject, body);
    }
}

