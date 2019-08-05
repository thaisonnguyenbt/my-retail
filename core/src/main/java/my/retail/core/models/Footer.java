/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2017 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package my.retail.core.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

import my.retail.core.util.MyRetailHelper;
import my.retail.core.util.UrlHelper;


@Model(adaptables = { SlingHttpServletRequest.class })
public class Footer extends BaseSightlyModel {
	
	@ScriptVariable
	private PageManager pageManager;

	@ScriptVariable
	private Page currentPage;
	
	private List<Page> items = new ArrayList<Page>();
    private int currentYear;

    @Override
    protected void init() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        populateItems();
    }

    private void populateItems() {

        Page rootPage = MyRetailHelper.findRoot(currentPage);
        if (rootPage != null) {
            Iterator<Page> pageIterator = rootPage.listChildren(new PageFilter());
            while (pageIterator.hasNext()) {
                items.add(UrlHelper.resolveRedirectPage(pageIterator.next(), pageManager));
            }
        }
    }

    public List<Page> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getCurrentYear() {
        return currentYear;
    }
}
