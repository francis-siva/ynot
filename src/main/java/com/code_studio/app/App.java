package com.code_studio.app;

import java.io.File;
import java.io.IOException;

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
								version = dependency.getElementsByTagName("version").item(0).getTextContent();
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
