package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.rascalmpl.uri.IURIInputOutputResolver;

public class OSSMeterURIResolver implements IURIInputOutputResolver{
	private Map<String, File> projectsMap = new HashMap<>();

	public void addProject(String projectName, File localStorage) {
		projectsMap.put(projectName, localStorage);
	}
	
	private File getFile(URI uri) throws IOException {
		if (projectsMap.containsKey(uri.getAuthority())) {
			return projectsMap.get(uri.getAuthority());
		}
		throw new FileNotFoundException(uri.toString());
	}
	
	@Override
	public InputStream getInputStream(URI uri) throws IOException {
		return new FileInputStream(getFile(uri));
	}

	@Override
	public Charset getCharset(URI uri) throws IOException {
		return null;
	}

	@Override
	public boolean exists(URI uri) {
		try {
			return getFile(uri).exists();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public long lastModified(URI uri) throws IOException {
		return getFile(uri).lastModified();
	}

	@Override
	public boolean isDirectory(URI uri) {
		try {
			return getFile(uri).isDirectory();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean isFile(URI uri) {
		try {
			return getFile(uri).isFile();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public String[] listEntries(URI uri) throws IOException {
		return getFile(uri).list();
	}

	@Override
	public String scheme() {
		return "ossm";
	}

	@Override
	public boolean supportsHost() {
		return false;
	}

	@Override
	public OutputStream getOutputStream(URI uri, boolean append)
			throws IOException {
		return new FileOutputStream(getFile(uri));
	}

	@Override
	public void mkDirectory(URI uri) throws IOException {
		getFile(uri).mkdir();
	}

	@Override
	public URI getResourceURI(URI uri) throws IOException {
		return getFile(uri).toURI();
	}

}

