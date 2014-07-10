package org.ossmeter.metricprovider.rascal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;
import org.rascalmpl.interpreter.utils.RascalManifest;

/**
 * See @link {@link RascalManifest}. This class adds support for OSGI bundles.
 */
public class RascalBundleManifest extends RascalManifest {

  public List<String> getSourceRoots(Bundle project) {
    return getSourceRoots(manifest(project));
  }
  
  public String getMainModule(Bundle project) {
    return getMainModule(manifest(project));
  }
  
  public String getMainFunction(Bundle project) {
    return getMainFunction(manifest(project));
  }

  public List<String> getRequiredBundles(Bundle project) {
    return getRequiredBundles(manifest(project));
  }
  
  private InputStream manifest(Bundle bundle) {
    URL rascalMF = bundle.getResource(META_INF_RASCAL_MF);

    try {
      if (rascalMF != null) {
        return FileLocator.openStream(bundle, new Path(META_INF_RASCAL_MF), false);
      }
    }
    catch (IOException e) {
      // do nothing, it's expected that some bundles do not have RASCAL.MF files
    }
    
    return null;
  }
  
  public boolean hasManifest(Bundle bundle) {
    return hasManifest(manifest(bundle));
  }
}

