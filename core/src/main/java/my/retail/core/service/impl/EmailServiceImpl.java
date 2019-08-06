package my.retail.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

import my.retail.core.service.EmailService;

@Component(immediate = true, label = "MyRetail Email Service", description = "MyRetail Email Service")
@Service
public class EmailServiceImpl implements EmailService {

    private static Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Reference
    private MessageGatewayService messageGatewayService;

    @Override
    public boolean sendEmail(String messageSubject, String messageBody, List<String> toRecepients, List<String> ccRecepients) {

        try {
            ArrayList<InternetAddress> recipientEmailToAddresses = new ArrayList<InternetAddress>();
            ArrayList<InternetAddress> recipientEmailCcAddresses = new ArrayList<InternetAddress>();

            if (toRecepients != null && !toRecepients.isEmpty()) {
                toRecepients.forEach(email -> {
                    try {
                        recipientEmailToAddresses.add(new InternetAddress(email));
                    } catch (AddressException e) {
                        LOG.error("Invalid To Recipient Email Address: {}", email);
                    }
                });
            }

            if (ccRecepients != null && !ccRecepients.isEmpty()) {
                ccRecepients.forEach(email -> {
                    try {
                        recipientEmailCcAddresses.add(new InternetAddress(email));
                    } catch (AddressException e) {
                        LOG.error("Invalid CC Recipient Email Address: {}", email);
                    }
                });
            }


            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setCharset("UTF-8");
            htmlEmail.setFrom("no-reply@myretail.com");

            if (recipientEmailToAddresses.size() > 0) {
                htmlEmail.setTo(recipientEmailToAddresses);
            }
            if (recipientEmailCcAddresses.size() > 0) {
                htmlEmail.setCc(recipientEmailCcAddresses);
            }
            htmlEmail.setSubject(messageSubject);
            htmlEmail.setHtmlMsg(messageBody);

            MessageGateway<HtmlEmail> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);
            messageGateway.send(htmlEmail);

            return true;

        } catch (EmailException e) {
            LOG.error(e.getMessage(), e);
        }

        return true;
    }
}
