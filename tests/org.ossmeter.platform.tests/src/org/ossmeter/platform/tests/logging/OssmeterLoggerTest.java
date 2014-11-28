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
package org.ossmeter.platform.tests.logging;

import static org.junit.Assert.fail;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.junit.Test;
import org.ossmeter.platform.logging.OssmeterLogger;

public class OssmeterLoggerTest {

	@Test
	public void test() {
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("ossmeter.logger.test");

		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		
		logger.warn("I'm warning you.");
		logger.debug("The bus has hit the house.");
	}

}
