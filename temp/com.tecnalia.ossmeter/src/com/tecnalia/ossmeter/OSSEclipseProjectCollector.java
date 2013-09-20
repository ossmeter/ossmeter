package com.tecnalia.ossmeter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;

import com.tecnalia.ossmeter.model.eclipseproject.EclipseProject;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectFactory;
import com.tecnalia.ossmeter.util.XML;


/**
 * Retrieve projects data from Eclipse by using an EMF meta-model representing an EclipseProject
 */
public class OSSEclipseProjectCollector {
	public static final String URL_ECLIPSE_PROJECTS="http://projects.eclipse.org";
	public static final String URL_LIST_PROJECTS=URL_ECLIPSE_PROJECTS+"/list-of-projects";
	public static final String URL_SUMMARY_PROJECTS="http://www.eclipse.org/projects/tools/status.php";
	public static final String CSS_SELECTOR_TO_SEARCH_SUMMARY="#maincontent table";
	public static final String CSS_SELECTOR_TO_SEARCH_LIST=".view-content table";

	public static final int COLUMN_PROJECTTOP=0;
	public static final int COLUMN_PROJECT=1;
	public static final int COLUMN_COMMITERS=7;
	public static final int COLUMN_ORGANIZATIONS=8;	
	
	private final EclipseWorld eclipseWorld;
	private  final Logger logger;
	
	
	
	public OSSEclipseProjectCollector(){
		logger=Logger.getLogger(this.getClass().getName());
		//create main object to load projects
		/*EclipseprojectPackage eclipseprojectPackage=EclipseprojectPackage.eINSTANCE;
		EClass eClass=(EClass)eclipseprojectPackage.getEClassifier(EclipseOrg.class.getSimpleName());
		eclipseOrg=(EclipseOrg) eclipseprojectPackage.getEclipseprojectFactory().create(eClass);
		*/
		eclipseWorld=EclipseprojectFactory.eINSTANCE.createEclipseWorld();
	}
	
	
	public void collectDataFromInternet() throws Exception{
		try{
			loadProjectsFromStatusPage(); 
		}catch(Exception e){
			logger.log(Level.SEVERE,"Error when collecting data from Eclipse. Reason: "+e.getMessage(),e);
			throw e;
		}
	}
	
	protected void loadProjectsFromStatusPage() throws Exception{
		XML xml=new XML(getCleanText());
		List<Element> rows=xml.get("tr");
		Map<Integer,EclipseProject> mapLevels=new TreeMap<Integer,EclipseProject>();
		for(int r=0;r<rows.size();r++){
			Element row=rows.get(r);
			List<Element> columns=XML.getChildrenElements(row);
			if (columns.isEmpty())continue;
			Element td=columns.get(0);
			String[] data=processTD(td);
			if (data==null)continue;
			int level=Integer.valueOf(data[0]);
			String projectName=data[1];
			String projectURL=data[2];
			EclipseProject eclipseProject=eclipseWorld.getProjectAtAnyDepth(projectName);
			if (eclipseProject==null){eclipseProject= EclipseprojectFactory.eINSTANCE.createEclipseProject();}
			mapLevels.put(level, eclipseProject);
			eclipseProject.setName(projectName);
			eclipseProject.setUrl(projectURL);
			if(eclipseWorld.getProjectAtAnyDepth(projectName)==null){
				int parentLevel=level-1;
				if (parentLevel==-1){
					eclipseWorld.getProjects().add(eclipseProject);
				}else{
					mapLevels.get(parentLevel).getProjects().add(eclipseProject);
				}
			}
		}
	}
	
	private String[] processTD(Element td) throws Exception{
		try {
			final String LITERAL_CLASS="class";
			final String LITERAL_INDENT="indent";
			String classNames=td.getAttribute(LITERAL_CLASS);
			//get level
			int index=classNames.indexOf(LITERAL_INDENT);
			if (index==-1){return null;}
			int level=Integer.valueOf(classNames.substring(index+LITERAL_INDENT.length()));
			Element anchor=XML.getChildrenElements(td).get(0);
			String href=URL_ECLIPSE_PROJECTS+anchor.getAttribute("href");
			String projectName=anchor.getFirstChild().getNodeValue();
			return new String[]{Integer.toString(level),projectName,href};
		} catch (Exception e) {
			throw new Exception("Error when processing column "+td.toString()+"."+e.getMessage());
		}
	}
	
  public void print(){
	  System.out.println("LIST OF ECLIPSE PROJECTS");
	  int level=0;
	  for(EclipseProject project:eclipseWorld.getProjects()){
		  print(project,level+1);
	  }
  }
  
  private void print(EclipseProject parent,int level){
	  String t="";
	  for (int r=0;r<level;r++)t=t+"\t";
	  System.out.println(t+parent.getName());
	  for(EclipseProject project:parent.getProjects()){
		  print(project,level+1);
	  }
  }
  
  /**
   * Get the html part relative to projects (because the list-of-projects url is not xhtml->SAX error)
   * @return
   * @throws Exception
   */
  private String getCleanText() throws Exception{
	  URL url=new URL(URL_LIST_PROJECTS);
      URLConnection con = url.openConnection();
      BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String line;
      StringBuilder sb=new StringBuilder();
      while ((line = rd.readLine()) != null)  {sb.append(line+"\n");      }
      rd.close(); 
      String text=sb.toString();
      int index1=text.indexOf("<table class=\"views-table cols-4\"");
      int index2=text.indexOf("<tbody",index1);
      int index3=text.indexOf("</tbody>",index2);
      text=text.substring(index2,index3+"</tbody>".length());
      return text;
  }
  
  public static void main(String[] args) throws Exception {
	OSSEclipseProjectCollector collector=new OSSEclipseProjectCollector();
	collector.collectDataFromInternet();
	collector.print();
  }
  

  
}
