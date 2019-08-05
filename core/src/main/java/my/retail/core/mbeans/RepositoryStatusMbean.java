package my.retail.core.mbeans;

import com.adobe.granite.jmx.annotation.Description;

@Description("Example MBean that exposes repository properties.")
public interface RepositoryStatusMbean {

    @Description("The name of the repository.")
    String getRepositoryName();

    @Description("The vendor of the repository.")
    String getRepositoryVendor();

    @Description("The URL of repository vendor.")
    String getVendorUrl();
}
