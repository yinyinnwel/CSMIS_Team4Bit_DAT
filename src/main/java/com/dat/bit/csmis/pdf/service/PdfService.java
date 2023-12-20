package com.dat.bit.csmis.pdf.service;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

//	private final ResourceLoader resourceLoader;
//
//    public PdfService(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }


    
    public void storeThisPdf_TomcatServer(MultipartFile pdfFile) throws IOException {
        String fileName = StringUtils.cleanPath("thisweek.pdf");

        // Get the webapps directory of the Tomcat server
        String tomcatDirectory = System.getProperty("catalina.home") + File.separator + "webapps";

        // Create a Path object for the PDF file directory
        Path pdfDirectory = Paths.get(tomcatDirectory, "pdfs");

        // Create the PDF file directory if it doesn't exist
        if (!Files.exists(pdfDirectory)) {
            Files.createDirectory(pdfDirectory);
        }

        // Create a Path object for the PDF file
        Path pdfPath = pdfDirectory.resolve(fileName);

        // Write the PDF file to the Tomcat server
        Files.write(pdfPath, pdfFile.getBytes());
    }
    
    public void storeNextPdf_TomcatServer(MultipartFile pdfFile) throws IOException {
        String fileName = StringUtils.cleanPath("nextweek.pdf");

        // Get the webapps directory of the Tomcat server
        String tomcatDirectory = System.getProperty("catalina.home") + File.separator + "webapps";

        // Create a Path object for the PDF file directory
        Path pdfDirectory = Paths.get(tomcatDirectory, "pdfs");

        // Create the PDF file directory if it doesn't exist
        if (!Files.exists(pdfDirectory)) {
            Files.createDirectory(pdfDirectory);
        }

        // Create a Path object for the PDF file
        Path pdfPath = pdfDirectory.resolve(fileName);

        // Write the PDF file to the Tomcat server
        Files.write(pdfPath, pdfFile.getBytes());
    }
    
    public String getPdfAsByteString_fromTomcatServer(String fileName) throws IOException {
        // Get the webapps directory of the Tomcat server
        String tomcatDirectory = System.getProperty("catalina.home") + File.separator + "webapps";
        


        // Create a Path object for the PDF file
        Path pdfPath = Paths.get(tomcatDirectory, "pdfs", fileName);

        // Read the contents of the PDF file into a byte array
        byte[] fileData = Files.readAllBytes(pdfPath);


        // Encode the byte array using Base64 encoding
        String byteString = Base64.getEncoder().encodeToString(fileData);


        return byteString;
    }

    
    
//*************************************************************************************** 
//***************** These code are for local storage development*************************
//***************************************************************************************
    
	public void storeThisPdf_localDevelopment(MultipartFile pdfFile) throws IOException {
		// Get the filename of the PDF file
		String fileName = StringUtils.cleanPath("thisweek.pdf");

		// Create a Path object for the resource directory
		Path resourceDirectory = Paths.get("src", "main", "resources", "pdfs");

		// Create a Path object for the PDF file
		Path pdfPath = resourceDirectory.resolve(fileName);

		// Write the PDF file to the resource directory

		Files.write(pdfPath, pdfFile.getBytes());
	}
    
    
	public void storeNextPdf_localDevelopment(MultipartFile pdfFile) throws IOException {
		// Get the filename of the PDF file
		String fileName = StringUtils.cleanPath("nextweek.pdf");

		// Create a Path object for the resource directory
		Path resourceDirectory = Paths.get("src", "main", "resources", "pdfs");

		// Create a Path object for the PDF file
		Path pdfPath = resourceDirectory.resolve(fileName);

		// Write the PDF file to the resource directory
		Files.write(pdfPath, pdfFile.getBytes());
	}



	public String getPdfAsByteString_fromLocalDevelopment(String fileName) throws IOException {
		// Create a Path object for the resource directory
		Path resourceDirectory = Paths.get("src", "main", "resources", "pdfs");

		// Create a Path object for the PDF file
		Path pdfPath = resourceDirectory.resolve(fileName);

		// Read the contents of the PDF file into a byte array
		byte[] fileData = Files.readAllBytes(pdfPath);

		// Encode the byte array using Base64 encoding
		String byteString = Base64.getEncoder().encodeToString(fileData);

		return byteString;
	}
    


//    public Resource getPdfResource(String fileName) {
//        // Create a Path object for the resource directory
//        Path resourceDirectory = Paths.get("src", "main", "resources","pdfs");
//
//        // Create a Path object for the PDF file
//        Path pdfPath = resourceDirectory.resolve(fileName);
//
//        // Get the Resource object for the PDF file
//        Resource pdfResource = resourceLoader.getResource("file:" + pdfPath.toString());
//
//        return pdfResource;
//    }
}
