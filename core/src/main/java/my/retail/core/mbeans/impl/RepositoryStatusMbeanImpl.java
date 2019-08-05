package my.retail.core.mbeans.impl;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;

import com.adobe.granite.jmx.annotation.AnnotatedStandardMBean;

import my.retail.core.mbeans.RepositoryStatusMbean;

@Component(immediate = true)
@Property(name = "jmx.objectname", value = "my.retail:type=CRX")
@Service(value = DynamicMBean.class)
public class RepositoryStatusMbeanImpl extends AnnotatedStandardMBean implements RepositoryStatusMbean {

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_UNARY)
    private SlingRepository repository;

    public RepositoryStatusMbeanImpl() throws NotCompliantMBeanException {

        super(RepositoryStatusMbean.class);
    }

    @Override
    public String getRepositoryName() {

        return repository.getDescriptor("jcr.repository.name");
    }

    @Override
    public String getRepositoryVendor() {

        return repository.getDescriptor("jcr.repository.vendor");
    }

    @Override
    public String getVendorUrl() {

        return repository.getDescriptor("jcr.repository.vendor.url");
    }
}
