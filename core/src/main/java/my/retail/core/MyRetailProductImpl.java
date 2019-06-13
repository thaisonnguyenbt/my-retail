/*******************************************************************************
 * Copyright 2016 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package my.retail.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.common.AbstractJcrProduct;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import my.retail.core.util.MyRetailHelper;

public class MyRetailProductImpl extends AbstractJcrProduct {

    private static Logger LOG = LoggerFactory.getLogger(MyRetailProductImpl.class);

    public static final String PN_IDENTIFIER = "identifier";
    public static final String PN_PRICE = "price";

    protected final ResourceResolver resourceResolver;
    protected final PageManager pageManager;
    protected final Page productPage;
    protected String brand = null;

    public MyRetailProductImpl(Resource resource) {
        super(resource);

        resourceResolver = resource.getResourceResolver();
        pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager != null) {
            productPage = pageManager.getContainingPage(resource);
        } else {
            productPage = null;
        }
        if (productPage != null) {
            LOG.info("WeRetailProductImpl_INIT: Not null ! {}, {}", resource.getPath(), productPage.getPath());
        } else {
            LOG.info("WeRetailProductImpl_INIT: Is null !! {}", resource.getPath());
        }
    }

    @Override
    public String getSKU() {
        String sku = getProperty(PN_IDENTIFIER, String.class);
        // Products don't have unique ids for size, so append the size to the sku:
        String size = getProperty("size", String.class);
        if (size != null && size.length() > 0) {
            sku += "-" + size;
        }
        return sku;
    }

    @Override
    public <T> T getProperty(String name, Class<T> type) {
        if (name.equals("brand")) {
            return (T) getBrand();
        }

        return super.getProperty(name, type);
    }

    @Override
    public <T> T getProperty(String name, String selectorString, Class<T> type) {
        if (name.equals("brand")) {
            return (T) getBrand();
        }

        return super.getProperty(name, selectorString, type);
    }

    public String getBrand() {
        // A null value is considered as non-initialized
        if (brand == null) {
            // Get value from root page title
            if (productPage != null)
                brand = MyRetailHelper.getPageTitle(productPage.getAbsoluteParent(2));
            // Make sure that the value is not null, to avoid initializing it again
            if (StringUtils.isBlank(brand))
                brand = "";
        }
        return brand;
    }
}
