package com.code_studio.app;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.code_studio.model.Task;

/**
 * App
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        final String FILENAME = "./pom.xml";
        System.out.println("getBuildPackaging: " + getBuildPackaging(FILENAME));
        System.out.println("\"junit-jupiter-api\" version: " + getDependencyVersion(FILENAME, "org.junit.jupiter", "junit-jupiter-api"));
        System.out.println("\"junit-jupiter-engine\" version: " + getDependencyVersion(FILENAME, "org.junit.jupiter", "junit-jupiter-engine"));
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.code_studio.access.jpa");
        EntityManager em = emf.createEntityManager();

        List<Task> taskList = null;
        final String selectAllTask = "SELECT * FROM task";
        
        if(em.createNativeQuery(selectAllTask, Task.class).getResultList() instanceof List) {
        	taskList = (List<Task>) em.createNativeQuery(selectAllTask, Task.class).getResultList();
		}

        if(taskList.size() > 0) {
	        for(int i = 0; i < taskList.size(); i++) {
	        	System.out.println("Id: " + taskList.get(i).getId() + " Todo: " + taskList.get(i).getTodo() + 
	        			" Priority: " + taskList.get(i).getPriority() + 
	        			" Completed: " + taskList.get(i).get_isDone());
	        }
        }
    }

    //   ////   ////   ////   //
    /*      Encapsulation      */
    //   ////   ////   ////   //
    public static DocumentBuilder getDocumentBuilder(final String fileName) {    	
    	DocumentBuilder docBuild = null;
    	
    	if(fileName != null && fileName.trim().length() > 0) {
	    	File file = new File(fileName);
	    	
	    	if(file.canRead()) {
	    		DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
	                        
				try {
					docBuild = docBuildFactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                			
	    	}
    	}
    	
    	return docBuild;
    }
    
    /**
     * Return the build packaging type for a given pom.xml filename.
     * @param fileName
     * @return <code>String</code> packaging
     */
    public static String getBuildPackaging(final String fileName) {
    	String packaging = "";
    	
    	if(getDocumentBuilder(fileName) != null) {
    		DocumentBuilder docBuild = getDocumentBuilder(fileName);
    		
    		try {
				Document domDoc = docBuild.parse(fileName);
				domDoc.normalize();
				
				NodeList node = domDoc.getElementsByTagName("packaging");
				
				if(node.getLength() == 1) {
					packaging = node.item(0).getTextContent();
				}				
				
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    	}
    	
    	return packaging;
    }
    
    /**
     * Return the dependency's version for a given dependency's pom <code>filename</code>,
	 *	<code>groupId</code>, <code>artifactId</code>.
     * @param fileName
     * @param groupId
     * @param artifactId
     * @return
     */
    public static String getDependencyVersion(final String fileName, String groupId, String artifactId) {
    	String version = "";
    	
    	if((groupId != null && groupId.trim().length() > 0) && (artifactId != null && artifactId.trim().length() > 0)) {
	    	
    		if(getDocumentBuilder(fileName) != null) {
	    		DocumentBuilder docBuild = getDocumentBuilder(fileName);
	    		
	    		try {
					Document domDoc = docBuild.parse(fileName);
					domDoc.normalize();
					
					NodeList nodes = domDoc.getElementsByTagName("dependency");
					
					for(int i = 0; i < nodes.getLength(); i++) {					
						
						String groupIdValue = "", artifactIdValue = "";
						
						Element dependency = (Element) nodes.item(i);
						
						//Stock value of groupId for each dependency
						groupIdValue = dependency.getElementsByTagName("groupId").item(0).getTextContent();
						
						//Search param groupId value in groupIdValue e.g. groupIdValue.contentEquals("org.junit.jupiter")
						if(groupIdValue.contentEquals(groupId)) {
							
							artifactIdValue = dependency.getElementsByTagName("artifactId").item(0).getTextContent();
							
							//Search param artifactId value in artifactIdValue e.g. artifactIdValue.contentEquals("junit-jupiter-api")
							if(artifactIdValue.contentEquals(artifactId)) {
								String regex = "^\\$\\{((([a-zA-Z0-9])+([a-zA-Z0-9\\.])*([a-zA-Z0-9])+)|([a-zA-Z0-9]))\\}$";

								System.out.println("Regex res_isPropertyName: " + Pattern.matches(regex, dependency.getElementsByTagName("version").item(0).getTextContent()));
								boolean valueisPropertyName = Pattern.matches(regex, dependency.getElementsByTagName("version").item(0).getTextContent());
								
								//Check in case we have property instead of artifactId's version value
								if(valueisPropertyName) {
									String property = dependency.getElementsByTagName("version").item(0).getTextContent();
									System.out.println("index ${: "+ property.indexOf("${"));System.out.println("index }: "+property.indexOf("}"));
									
									//property starts with "${" and ends by "}" delimiters
									boolean propertyisWithDelimiter = ((property.indexOf("${") == 0) && (property.indexOf("}") == property.length()-1));
									
									System.out.println("propertyisWithDelimiter: " + propertyisWithDelimiter);
									if(propertyisWithDelimiter) {
										//Keep property without its delimiters
										property = property.substring(property.indexOf("${")+2, property.indexOf("}"));
										System.out.println("substr_property: " + property);
										//nodes must be reinitialized to keep the appropriate Element (property)
										nodes = domDoc.getElementsByTagName(property);
										
										if(nodes.getLength() == 1) {
											System.out.println("version$: " + nodes.item(0).getTextContent());
											version = nodes.item(0).getTextContent();
										}										
									}
									
								}
								else {//regular use case
									version = dependency.getElementsByTagName("version").item(0).getTextContent();
								}
								
							}
						}					
					}
					
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	}
    	
    	return version;
    }
}
