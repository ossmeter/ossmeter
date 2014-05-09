import java.util.List;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManager.INCLUDE;
import com.taskadapter.redmineapi.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedMineApi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://demo.redmine.org/projects/titians
		String redmineHost = "http://ta-dev.dyndns.biz/redmine";
		String apiAccessKey = "acbd877fa75d53402fc19c0fff8bea48836a1c7f";
		RedmineManager mgr = new RedmineManager(redmineHost, apiAccessKey);
		try {
            //getUsersAsNotAdmin(new RedmineManager(redmineHost, "notadmin", "notadmin"));
			// getIssueWithRelations(mgr);
			// tryCreateIssue(mgr);
			// tryGetIssues(mgr);
			// tryGetIssue(mgr);
			// tryGetAllIssues(mgr);
			// printCurrentUser(mgr);
			// generateXMLForUser();
			// generateXMLForTimeEntry();
            // getSavedQueries(mgr);
			// getProjects(mgr);
			// tryCreateRelation(mgr);
			// tryGetNews(mgr);
			// getProject(mgr);
			// changeIssueStatus(mgr);
			// getVersion(mgr);
			// getStatuses(mgr);
			// tryUpload(mgr);
//			tryGetRoles(mgr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private static void getProjects(RedmineManager mgr) throws RedmineException {
		List<Project> projects = mgr.getProjects();
		for (Project project : projects) {
			System.out.println(project.getName());
		}
		
		

	}	
		

	

}
