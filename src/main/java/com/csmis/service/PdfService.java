package com.csmis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {
	private final ResourceLoader resourceLoader;

	public PdfService(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void storePdf(MultipartFile pdfFile) throws IOException {
        // Get the filename of the PDF file

        // Create a Path object for the resource directory
        Path resourceDirectory = Paths.get("src", "main", "resources","pdfs");

        // Create a Path object for the PDF file
        Path thisweek_pdfPath = resourceDirectory.resolve("ThisWeek.pdf");
        Path nextweek_pdfPath = resourceDirectory.resolve("NextWeek.pdf");

        // Read the contents of the PDF file into a byte array
        byte[] fileData = Files.readAllBytes(nextweek_pdfPath);

        // Write the PDF file to the resource directory
        Files.write(thisweek_pdfPath, fileData);
        Files.write(nextweek_pdfPath, pdfFile.getBytes());
    }

	public String getPdfAsByteString(String fileName) throws IOException {
        // Create a Path object for the resource directory
        Path resourceDirectory = Paths.get("src", "main", "resources","pdfs");

        // Create a Path object for the PDF file
        Path pdfPath = resourceDirectory.resolve(fileName);

        // Read the contents of the PDF file into a byte array
        byte[] fileData = Files.readAllBytes(pdfPath);

        // Encode the byte array using Base64 encoding
        String byteString = Base64.getEncoder().encodeToString(fileData);

        return byteString;
    }

	public Resource getPdfResource(String fileName) {
        // Create a Path object for the resource directory
        Path resourceDirectory = Paths.get("src", "main", "resources","pdfs");

        // Create a Path object for the PDF file
        Path pdfPath = resourceDirectory.resolve(fileName);

        // Get the Resource object for the PDF file
        Resource pdfResource = resourceLoader.getResource("file:" + pdfPath.toString());

        return pdfResource;
    }
}
