package com.code_studio.app;

/*import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;*/

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testGetDependencyVersion() {
    	final String FILENAME = "./pom.xml";
    	String dependencyVersion, groupId = "org.hibernate" , artifactId = "hibernate-core";
    	
    	dependencyVersion = App.getDependencyVersion(FILENAME, groupId, artifactId);
    	assertEquals("5.4.30.Final", dependencyVersion, "dependencyVersion not matched with expected value");    	
    	

    	String dependencyVersion2, groupId2 = "org.postgresql" , artifactId2 = "postgresql";
    	
    	dependencyVersion2 = App.getDependencyVersion(FILENAME, groupId2, artifactId2);
    	assertEquals("42.2.19", dependencyVersion2, "dependencyVersion not matched with expected value");
    }
    
    @Test
    public void testGetDependencyVersionValueIsPropertyName() {
    	final String FILENAME = "./pom.xml";
    	String dependencyVersion, groupId = "org.junit.jupiter" , artifactId ="junit-jupiter-api";
    	
    	dependencyVersion = App.getDependencyVersion(FILENAME, groupId, artifactId);
    	assertEquals("5.7.1", dependencyVersion, "dependencyVersion not matched with expected value");
    	
    	
    	String dependencyVersion2, groupId2 = "org.junit.jupiter" , artifactId2 = "junit-jupiter-engine";
    	
    	dependencyVersion2 = App.getDependencyVersion(FILENAME, groupId2, artifactId2);
    	assertEquals("5.7.1", dependencyVersion2, "dependencyVersion not matched with expected value");
    }
    
    @Disabled("Disabled until more security added")
    @Test
    public void testGetDependencyVersionNoFound() {//follow this step ->https://www.baeldung.com/junit-assert-exception
    	final String FILENAME = "./pom.xml";
    	String dependencyVersion, groupId = "unknown" , artifactId ="unfounded";
    	
    	dependencyVersion = App.getDependencyVersion(FILENAME, groupId, artifactId);
    	System.out.println("dependencyVersion: " + dependencyVersion);
    	assertEquals("Error: no such element in file. Unable to locate <groupId> " + groupId, dependencyVersion, "dependencyVersion not matched with expected value");
    }
}