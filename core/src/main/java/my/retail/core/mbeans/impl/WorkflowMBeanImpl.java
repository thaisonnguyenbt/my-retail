package my.retail.core.mbeans.impl;

import javax.management.NotCompliantMBeanException;

import com.adobe.granite.jmx.annotation.AnnotatedStandardMBean;
import com.day.cq.workflow.model.WorkflowModel;

import my.retail.core.mbeans.WorkflowMBean;

public class WorkflowMBeanImpl extends AnnotatedStandardMBean implements WorkflowMBean {

    WorkflowModel model;

    public WorkflowMBeanImpl(WorkflowModel inmodel) throws NotCompliantMBeanException {

        super(WorkflowMBean.class);
        model = inmodel;
    }

    @Override
    public String getModelName() {

        return model.getTitle();
    }
}