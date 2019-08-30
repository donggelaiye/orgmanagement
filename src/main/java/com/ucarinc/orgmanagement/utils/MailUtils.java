package com.ucarinc.orgmanagement.utils;


import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author wtf
 * @date 2019/8/10 11:15
 */
public class MailUtils {

    /**
     * @param Femail       收件邮箱
     * @param accountLogin 登录账户
     * @param empName      员工姓名
     * @return boolean 发送成功为true，发送失败返回false
     * @author wtf
     * @description 邮件发送
     * @date 2019/8/10 11:15
     */
    public boolean sendResetPwdEmail(String Femail, String accountLogin, String empName) throws MessagingException, IOException, javax.mail.MessagingException {
        Properties props = new Properties();
        // 读取配置文件
        props.load(this.getClass().getResourceAsStream("/config/mailConfig.properties"));
        Session session = Session.getInstance(props);
        // 创建邮件对象
        Message msg = new MimeMessage(session);
        try {
            msg.setSubject("修改密码");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        // 设置邮件内容
        String msgContent = "亲爱的用户 " + empName + " ，您好，<br/><br/>"
                + "您在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "提交重置密码的请求。<br/><br/>"
                + "请打开以下链接重置密码：<br/><br/>"
                + "<a href=\"http://" + props.getProperty("url") + "/personal/resetPwd?accountLogin=" + accountLogin + "\" target='_blank'>http://" + props.getProperty("url") + "/personal/resetPwd?accountLogin=" + accountLogin + "</a><br/><br/>"
                + "感谢使用本系统。" + "<br/><br/>"
                + "此为自动发送邮件，请勿直接回复！";

        msg.setContent(msgContent, "text/html;charset=utf-8");// 设置邮件内容，为html格式
        // 设置发件人
        msg.setFrom(new InternetAddress(MimeUtility.encodeText("修改密码") + " <" + props.getProperty("mail.username") + ">"));// 设置邮件来源
        Transport transport = session.getTransport(props.getProperty("mail.transport.protocol"));
        // 连接邮件服务器
        System.out.println(props.getProperty("mail.username") + props.getProperty("mail.password"));
        transport.connect(props.getProperty("mail.host"), props.getProperty("mail.username"), props.getProperty("mail.password"));
        // 发送邮件
        try {
            transport.sendMessage(msg, new Address[]{new InternetAddress(Femail)});
        }catch (javax.mail.MessagingException e){
            return false;
        }finally {
            transport.close();
        }
        // 关闭连接
        return true;
    }
}
