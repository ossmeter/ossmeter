/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;

public class ServiceLoaderMetricProviderManager implements IMetricProviderManager {
	
	static ServiceLoader<IMetricProvider> mpLoader = ServiceLoader.load(IMetricProvider.class);
	
	@Override
	public List<IMetricProvider> getMetricProviders() {
		List<IMetricProvider> providers = new ArrayList<IMetricProvider>();
		Iterator<IMetricProvider> it = mpLoader.iterator();
		while (it.hasNext()) {
			providers.add(it.next());
		}
		
		System.out.println("MPs: " + providers);
		return providers;
	}
}
