package org.ossmeter.platform.vcs.workingcopy.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManager;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.vcs.git.GitRepository;

public class GitWorkingCopyManager implements WorkingCopyManager {

  public GitWorkingCopyManager() {
    // DO NOT REMOVE
  }

  @Override
  public boolean appliesTo(VcsRepository repository) {
    return repository instanceof GitRepository;
  }

  @Override
  public void checkout(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      if (workingDirectory.exists()) {
        // we assume we did it before and will now just pull
        Process p = Runtime.getRuntime().exec(new String[] { "git", "pull"}, new String[] { }, workingDirectory);
        p.waitFor();
      }
      else {
        // we clone
        Process p = Runtime.getRuntime().exec(new String[] { "git", "clone", repository.getUrl(), workingDirectory.getAbsolutePath() });
        p.waitFor();
      }
      Process p = Runtime.getRuntime().exec(new String[] {"git", "checkout", revision }, null, workingDirectory);
      p.waitFor();
    } catch (IOException | InterruptedException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
  }

  @Override
  public void getDiff(File workingDirectory, String lastRevision, String endRevision, final Map<String, Integer> added, final Map<String, Integer> deleted) {
//	  if (lastRevision == null) {
//		  lastRevision = "";
//	  }
		try {
		  List<String> commandArgs = new ArrayList<>(Arrays.asList(new String[] { "git", "log", endRevision, "--numstat" }));
		  
		  if (lastRevision != null) {
			commandArgs.remove(2);
		    commandArgs.add(2, lastRevision+".."+endRevision);
		  }
		  /* 
		   * this little workaround makes sure the indexes we get for the diffs is in the form
		   * workingCopyRoot+"/"+itemPath (relative - in the sense how I made it relative in the SVNManager)
		   */
		  for (String path: workingDirectory.list()) {
			  // I hate this!!! :(
			  if (!path.contains(".DS_Store")) {
				  commandArgs.add(path);
			  }
		  }
		  ProcessBuilder pb = new ProcessBuilder(commandArgs);
		  pb.redirectErrorStream(true);
		  pb.directory(workingDirectory);
		  final Process p = pb.start();
		  
		  
		  //Process p = Runtime.getRuntime().exec(commandArgs.toArray(new String[0]), null, workingDirectory);
//		  Thread reader = new Thread() {
//			  public void run() {
				  try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
					  String line;
					  while ((line = reader.readLine()) != null) {
						String[] lineParts = line.split("\\s+");
						if (lineParts.length == 3 && lineParts[0].matches("\\d+") && lineParts[1].matches("\\d+")) {
						  int addedLines = Integer.parseInt(lineParts[0]);
						  int deletedLines = Integer.parseInt(lineParts[1]);
						  String key = lineParts[2];
						  if (added.containsKey(key)) {
							addedLines += added.get(key);
						  }
						  if (deleted.containsKey(key)) {
						    deletedLines += deleted.get(key);
						  }
						  added.put(key, addedLines);
						  deleted.put(key, deletedLines);
						} else {
						  System.err.println("Line is not a valid num stat from git or the file is a binary");
						}
					  }
				  } catch (IOException e) {
					  throw new RuntimeException(e);
				  }
				  
//			  }
//		  };
//		  reader.start();
		  p.waitFor();
//		  reader.join();
		} catch (IOException | InterruptedException e) {
		  throw new RuntimeException(e);
		}
  }
}
