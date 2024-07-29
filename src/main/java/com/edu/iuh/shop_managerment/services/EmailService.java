package com.edu.iuh.shop_managerment.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    JavaMailSender javaMailSender;

    public void sendPaymentConfirmationEmail(String to, String customerName, double totalPrice) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String htmlContent = String.format(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                        ".container { background-color: #ffffff; margin: auto; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); max-width: 600px; }" +
                        ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #dddddd; }" +
                        ".header h1 { margin: 0; color: #333333; }" +
                        ".content { margin: 20px 0; text-align: center; }" +
                        ".content h2 { color: #555555; }" +
                        ".content p { margin: 5px 0; color: #666666; }" +
                        ".footer { text-align: center; color: #888888; font-size: 12px; margin-top: 20px; border-top: 1px solid #dddddd; padding-top: 20px; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<div class='header'>" +
                        "<h1>Xác Nhận Thanh Toán</h1>" +
                        "</div>" +
                        "<div class='content'>" +
                        "<h2>Chào %s,</h2>" +
                        "<p>Cảm ơn bạn đã thanh toán cho đơn hàng tại cửa hàng của chúng tôi.</p>" +
                        "<p>Số tiền thanh toán: %.2f VND</p>" +
                        "<p>Chúng tôi sẽ sớm xử lý và gửi đơn hàng cho bạn.</p>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc số điện thoại. Cảm ơn bạn đã mua sắm tại cửa hàng của chúng tôi!</p>" +
                        "<p>&copy; 2024 Bags Shop. All rights reserved.</p>" +
                        "</div>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                customerName, totalPrice
        );

        helper.setTo(to);
        helper.setSubject("Xác Nhận Thanh Toán");
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

}
