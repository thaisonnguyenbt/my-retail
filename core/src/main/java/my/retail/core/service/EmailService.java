package my.retail.core.service;

import java.util.List;

public interface EmailService {
    
    boolean sendEmail(String messageSubject, String messageBody, List<String> toRecepients, List<String> ccRecepients);

}
