package com.code_studio.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    
    @Test
    public void testGetDependencyVersionNotFound() {
    	final String FILENAME = "./pom.xml";
    	String dependencyVersion, groupId = "org.hibernate" , artifactId = "hibernate-spatial", expected;
    	
    	dependencyVersion = App.getDependencyVersion(FILENAME, groupId, artifactId);
    	expected = "Error: element <artifact>" + artifactId + "</artifact> not found for <groupId>" + groupId + "</groupId>.";
    	assertEquals(expected, dependencyVersion, "dependencyVersion not matched with expected value");
    	
    	
    	String dependencyVersion2, groupId2 = "unknown" , artifactId2 = "unfounded", expected2;
    	
    	dependencyVersion2 = App.getDependencyVersion(FILENAME, groupId2, artifactId2);
    	expected2 = "Error: no such element in file. Unable to locate <groupId>" + groupId2 + "</groupId> \nand <artifactId>" + artifactId2 + "</artifactId>.";
    	assertEquals(expected2, dependencyVersion2, "dependencyVersion not matched with expected value");
    }
}