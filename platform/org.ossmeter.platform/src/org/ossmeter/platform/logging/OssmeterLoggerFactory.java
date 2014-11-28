/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ossmeter.platform.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class OssmeterLoggerFactory implements LoggerFactory {

	public OssmeterLoggerFactory() {
		
	}
	
	@Override
	public Logger makeNewLoggerInstance(String loggerName) {
		return new OssmeterLogger(loggerName);
	}

}
