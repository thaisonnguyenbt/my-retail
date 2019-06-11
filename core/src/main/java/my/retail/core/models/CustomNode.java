package my.retail.core.models;

import javax.inject.Inject;

public class CustomNode {
	
    @Inject
    private String title;
	
    @Inject
    private String resourceType;

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getResourceType() {

        return resourceType;
    }

    public void setResourceType(String resourceType) {

        this.resourceType = resourceType;
    }

}