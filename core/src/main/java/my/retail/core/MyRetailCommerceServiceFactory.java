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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.common.AbstractJcrCommerceServiceFactory;
import com.adobe.cq.commerce.common.CommerceSearchProviderManager;
import com.adobe.cq.commerce.common.ServiceContext;
import com.adobe.granite.security.user.UserPropertiesService;
import com.day.cq.wcm.api.LanguageManager;
import com.google.gson.Gson;

/**
 * My.Retail implementation for the {@link CommerceServiceFactory} interface.
 */
@Component
@Service
@Properties(value = {
    @Property(name = Constants.SERVICE_DESCRIPTION, value = "Factory for reference implementation commerce service"),
        @Property(name = "commerceProvider", value = MyRetailConstants.MY_RETAIL_COMMERCEPROVIDER, propertyPrivate = true)
})
public class MyRetailCommerceServiceFactory  extends AbstractJcrCommerceServiceFactory implements CommerceServiceFactory {

    private static Logger LOG = LoggerFactory.getLogger(MyRetailCommerceServiceFactory.class);
    private static Gson gson = new Gson();

    /**
     * Create a new <code>GeoCommerceServiceImpl</code>.
     */
    @Override
    public CommerceService getCommerceService(Resource res) {

        LOG.debug("MyRetailCommerceServiceFactory getCommerceService: {}, {}", res.getName(), res.getPath());
        return new MyRetailCommerceServiceImpl(getServiceContext(), res);
    }

    @Override
    protected void bindLanguageManager(LanguageManager arg0) {

        LOG.debug("MyRetailCommerceServiceFactory bindLanguageManager");
        super.bindLanguageManager(arg0);
    }

    @Override
    protected void bindSearchProviderManager(CommerceSearchProviderManager arg0) {

        LOG.debug("MyRetailCommerceServiceFactory bindSearchProviderManager");
        super.bindSearchProviderManager(arg0);
    }

    @Override
    protected void bindSlingRepository(SlingRepository arg0) {

        LOG.debug("MyRetailCommerceServiceFactory bindSlingRepository");
        super.bindSlingRepository(arg0);
    }

    @Override
    protected void bindSlingSettingsService(SlingSettingsService arg0) {

        LOG.debug("MyRetailCommerceServiceFactory bindSlingSettingsService");
        super.bindSlingSettingsService(arg0);
    }

    @Override
    protected void bindUserPropertiesService(UserPropertiesService arg0) {

        LOG.debug("MyRetailCommerceServiceFactory bindUserPropertiesService");
        super.bindUserPropertiesService(arg0);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        LOG.debug("MyRetailCommerceServiceFactory clone :");
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {

        LOG.debug("MyRetailCommerceServiceFactory finalize :");
        super.finalize();
    }

    @Override
    protected ServiceContext getServiceContext() {

        LOG.debug("MyRetailCommerceServiceFactory getServiceContext");
        return super.getServiceContext();
    }

    @Override
    protected void unbindLanguageManager(LanguageManager arg0) {

        LOG.debug("MyRetailCommerceServiceFactory unbindLanguageManager");
        super.unbindLanguageManager(arg0);
    }

    @Override
    protected void unbindSearchProviderManager(CommerceSearchProviderManager arg0) {

        LOG.debug("MyRetailCommerceServiceFactory unbindSearchProviderManager");
        super.unbindSearchProviderManager(arg0);
    }

    @Override
    protected void unbindSlingRepository(SlingRepository arg0) {

        LOG.debug("MyRetailCommerceServiceFactory unbindSlingRepository");
        super.unbindSlingRepository(arg0);
    }

    @Override
    protected void unbindSlingSettingsService(SlingSettingsService arg0) {

        LOG.debug("MyRetailCommerceServiceFactory unbindSlingSettingsService");
        super.unbindSlingSettingsService(arg0);
    }

    @Override
    protected void unbindUserPropertiesService(UserPropertiesService arg0) {

        LOG.debug("MyRetailCommerceServiceFactory unbindUserPropertiesService");
        super.unbindUserPropertiesService(arg0);
    }
}
