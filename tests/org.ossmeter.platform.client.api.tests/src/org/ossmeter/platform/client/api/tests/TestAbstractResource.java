package org.ossmeter.platform.client.api.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.restlet.Response;
import org.restlet.data.MediaType;

public abstract class TestAbstractResource {

	protected void validateResponse(Response response, int expectedResponse) {
		assertEquals(expectedResponse, response.getStatus().getCode());
		assertTrue(response.isEntityAvailable());
		assertTrue(response.getEntity().getMediaType().equals(MediaType.APPLICATION_JSON));
		
	}
}
