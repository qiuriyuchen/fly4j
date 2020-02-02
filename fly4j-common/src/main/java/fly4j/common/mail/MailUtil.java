package fly4j.common.mail;

import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * smtp 发送邮件 端口25 sll加密端口994/465
 * pop3 接收邮件 端口:110  SLL加密类型端口：995
 * imap 接收邮件 网易邮箱的端口是：143  SLL加密类型端口：993
 * Created by guanpanpan on 2015/12/4.
 */
public class MailUtil {


    public static void sendMail(String subject, String content, String filename, String mailTo, MailConfig mailConfig) {
        forceSendMail(subject, content, filename, mailTo, mailConfig);
    }

    private static void forceSendMail(String subject, String content, String filename, String mailTo, MailConfig mailConfig) {
        System.out.println("sendMail real:" + subject);
        try {
            if (StringUtils.isBlank(mailTo)) return;


            //设置邮件发送服务器
            Properties props = getProperties(mailConfig);


            Session session = Session.getDefaultInstance(props, null); //获得邮件会话对象
            MimeMessage mimeMsg = new MimeMessage(session); //创建MIME邮件对象


            //设置邮件主题
            mimeMsg.setSubject(subject);
            //设置收信人
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            //设置发信人
            mimeMsg.setFrom(new InternetAddress(mailConfig.getFrom())); //设置发信人
            // 设置抄送人
            if (StringUtils.isNotBlank(mailConfig.getCopyto())) {
                mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailConfig.getCopyto()));
            }


            //设置邮件正文
            Multipart multipart = new MimeMultipart();
            //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html;charset=GBK");
            multipart.addBodyPart(bodyPart);
            //添加附件
            if (StringUtils.isNotBlank(filename)) {
                BodyPart bodyPartAtt = new MimeBodyPart();
                FileDataSource fileds = new FileDataSource(filename);
                bodyPartAtt.setDataHandler(new DataHandler(fileds));
                bodyPartAtt.setFileName(fileds.getName());

                multipart.addBodyPart(bodyPartAtt);

            }
            mimeMsg.setContent(multipart);


            mimeMsg.saveChanges();
            System.out.println("正在发送邮件....");

            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), mailConfig.getUsername(), mailConfig.getPassword());
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
//            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
            //transport.send(mimeMsg);

            System.out.println("发送邮件成功！");
            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Properties getProperties(MailConfig mailConfig) {
        Properties props = System.getProperties(); //获得系统属性对象
        props.put("mail.smtp.host", mailConfig.getSmtp()); //设置SMTP主机
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        //邮箱发送服务器端口,这里设置为465端口
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        return props;
    }

}
