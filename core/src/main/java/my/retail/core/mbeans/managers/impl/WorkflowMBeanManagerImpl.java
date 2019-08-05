package my.retail.core.mbeans.managers.impl;

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.management.ObjectName;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;

import my.retail.core.mbeans.WorkflowMBean;
import my.retail.core.mbeans.impl.WorkflowMBeanImpl;
import my.retail.core.mbeans.managers.WorkflowMBeanManager;

@Component(immediate = true)
@Service(value = WorkflowMBeanManager.class)
public class WorkflowMBeanManagerImpl implements WorkflowMBeanManager {

    // The ComponentContext provides access to the BundleContext
    private ComponentContext componentContext;

    // Use the SlingRepository service to read model nodes
    @Reference
    private SlingRepository repository = null;

    // Use the WorkflowService service to create WorkflowModel objects
    @Reference
    private WorkflowService workflowservice = null;

    private Session session;

    // Details about model nodes
    private static final String MODEL_ROOT = "/etc/workflow/models";
    private static final String MODEL_NODE = "model";

    private Set<String> modelIds = new HashSet<String>();

    // Storage for ServiceRegistrations for MBean services
    private Collection<ServiceRegistration<?>> mbeanRegistrations = new Vector<ServiceRegistration<?>>(0, 1);

    @Activate
    @SuppressWarnings("deprecation")
    protected void activate(ComponentContext ctx) {

        // Traverse the repository and load the model nodes
        try {
            session = repository.loginAdministrative(null);
            // load and store model node paths
            if (session.nodeExists(MODEL_ROOT)) {
                getModelIds(session.getNode(MODEL_ROOT));
            }
            // Create MBeans for each model
            for (String modid : modelIds) {
                makeMBean(modid);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Add JMX domain and key properties to a collection Instantiate a WorkflowModel and its WorkflowMBeanImpl object Register the MBean OSGi service
     */
    private void makeMBean(String modelId) {

        // create MBean for the model
        try {
            Dictionary<String, String> mbeanProps = new Hashtable<String, String>();
            // These properties appear on the JMX Console home page
            mbeanProps.put("jmx.objectname", "my.retail:type=workflow_model,id=" + ObjectName.quote(modelId));
            WorkflowSession wfsession = workflowservice.getWorkflowSession(session);
            WorkflowMBeanImpl mbean = new WorkflowMBeanImpl(wfsession.getModel(modelId));

            ServiceRegistration<?> serviceregistration = componentContext.getBundleContext().registerService(WorkflowMBean.class.getName(), mbean, mbeanProps);
            // Store the ServiceRegistration objects for deactivation
            mbeanRegistrations.add(serviceregistration);
        } catch (Throwable t) {
        }
    }

    /**
     * Traverses the repository branch below a given Node. Stores the path of each model node.
     */
    private void getModelIds(Node node) throws RepositoryException {

        try {
            NodeIterator iter = node.getNodes();
            while (iter.hasNext()) {
                Node n = iter.nextNode();
                // Look for "jcr:content" nodes
                if (n.getName().equals("jcr:content")) {
                    // get the path of the model node and save it
                    if (n.hasNode(MODEL_NODE)) {
                        modelIds.add(n.getNode(MODEL_NODE).getPath());
                    }
                } else {
                    // Scan child nodes
                    getModelIds(n);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Log out of the JCR session and unregister WorkflowMBean services
     */
    @Deactivate
    protected void deactivate() {

        session.logout();
        session = null;
        for (ServiceRegistration<?> sr : mbeanRegistrations) {
            sr.unregister();
        }
    }
}
