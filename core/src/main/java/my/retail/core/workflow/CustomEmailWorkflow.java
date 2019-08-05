package my.retail.core.workflow;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

@Component
@Service
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "MyRetail Email workflow process implementation."),
        @Property(name = Constants.SERVICE_VENDOR, value = "Adobe"),
        @Property(name = "process.label", value = "MyRetail Email Workflow Process") })
public class CustomEmailWorkflow implements WorkflowProcess {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    // Inject a MessageGatewayService
    @Reference
    private MessageGatewayService messageGatewayService;

    @Override
    public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap args) throws WorkflowException {

        try {
            log.info("Here in execute method"); // ensure that the execute method is invoked

            // Declare a MessageGateway service
            MessageGateway<Email> messageGateway;

            // Set up the Email message
            Email email = new SimpleEmail();

            // Set the mail values
            String emailToRecipients = "thaison.nguyen@ncs.com.sg";

            email.addTo(emailToRecipients);
            email.setSubject("AEM Custom Step");
            email.setFrom("thaison.nguyen@ncs.com.sg");
            email.setMsg("This message is to inform you that the CQ content has been changed");

            // Inject a MessageGateway Service and send the message
            messageGateway = messageGatewayService.getGateway(Email.class);

            // Check the logs to see that messageGateway is not null
            messageGateway.send(email);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
