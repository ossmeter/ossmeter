package org.ossmeter.platform.client.api;

import java.util.Iterator;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class MetricListResource extends ServerResource {

	@Get("json")
    public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		Platform platform = Platform.getInstance();
		String json = "{ \"metrics\" : [ ";
		
		Iterator<IMetricProvider> it = platform.getMetricProviderManager().getMetricProviders().iterator();
		
		while (it.hasNext()) {
			IMetricProvider ip = it.next();
			json += "{ \"name\" : \"" + ip.getFriendlyName() + "\", \"type\" : \"" + ip.getClass().getName() + "\", \"description\" : \"" + ip.getSummaryInformation() + "\" }";
			if (it.hasNext()) json += ",";
		}

		json += " ] }";
		return json;
	}

	
}
