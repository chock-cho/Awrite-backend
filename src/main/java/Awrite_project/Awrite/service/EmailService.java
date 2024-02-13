package Awrite_project.Awrite.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.mail.internet.*;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final HttpSession session;

    /* 회원 가입 시 인증 코드 전송 로직 */
    public void sendEmail(String to, String verificationCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String content = "<p><span style=\"font-size: 12pt;\">안녕하세요.</span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"font-size: 9pt;\">이메일 인증을 완료하면 사각사각의 모든 기능을 이용하실 수 있습니다. " +
                "아래의 코드를 입력하여 인증을 완료하세요.</span></p>\n" +
                "<p style=\"text-align: center;\"><strong><span style=\"color: #ecf0f1; background-color: #3598db;\">"+
                verificationCode+"</span></strong></p>\n" +
                "<p><span style=\"font-size: 9pt;\">사각사각에 회원가입하거나 이메일 인증을 요청한 적이 없는데 메일을 받으셨다면, " +
                "본 메일을 무시하셔도 좋습니다.</span></p>";

        helper.setTo(to);
        helper.setSubject("[사각사각] 이메일 인증을 완료해주세요");
        helper.setText(content, true);

        javaMailSender.send(mimeMessage);

    }

    /* 비밀번호 재설정 로직 */
    public void sendResetEmail(String to, String resetLink) throws MessagingException {
        // 세션에서의 닉네임 가져오기
        String nickname = (String)session.getAttribute("nickname");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String content = "<p><span style=\"font-size: 14pt;\">안녕하세요. </span></p>\n" +"<p>&nbsp;</p>\n"+
                "<p><span style=\"font-size: 14pt;\"> 본 이메일을 통해 계정의 비밀번호를 재설정할 수 있습니다."+
        "아래 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다.</span></p>\n" +
                "<p style=\"text-align: center;\"><a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; " +
                "background-color: #3598db; color: #000000; text-decoration: none; border-radius: 5px;\">비밀번호 재설정으로 이동</a></p>\n";

        helper.setTo(to);
        helper.setSubject("[사각사각] 비밀번호를 재설정할 수 있습니다.");
        helper.setText(content, true);

        javaMailSender.send(mimeMessage);

    }

}
