package my.retail.core.mbeans;

import com.adobe.granite.jmx.annotation.Description;

@Description("Example MBean that exposes Workflow model properties.")
public interface WorkflowMBean {

    @Description("The name of the Workflow model.")
    String getModelName();
}
