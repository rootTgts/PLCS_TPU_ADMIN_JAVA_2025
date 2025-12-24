package com.wissen.tpu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	private static final Log logger = LogFactory.getLog(Utils.class);

	@Value("${image.base.path}")
	private String assetsBasePath;

	public String getImageByUuid(String uuid, String pensionerId) {
		String path = assetsBasePath + "/InputImages/images/" + uuid.replace("-", "/") + "/" + pensionerId + ".png";
		logger.info("Attempting to retrieve image from path: " + path); // Log the path being accessed

		File imageFile = new File(path);

		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage); // You can handle the exception as per your logic
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			logger.info("Successfully encoded the image for Pensioner ID: " + pensionerId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + pensionerId;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	public String getPensionerImageAsBase64PNG(String department, String pensionerId) {
		String imagePath = assetsBasePath + "/targetImages/" + department + "/" + pensionerId + ".png"; // Assuming
																										// images
																										// are .jpg
		logger.info(
				"Attempting to fetch pensioner image for Pensioner ID: " + pensionerId + " from path: " + imagePath);

		File imageFile = new File(imagePath);
		if (!imageFile.exists()) {
			logger.error("Image not found for Pensioner ID: " + pensionerId);
			// throw new IllegalArgumentException("Image not found for pensionerId: " +
			// pensionerId);
			return null;
		}

		try {
			// Read file into byte array
			byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

			// Encode to Base64
			logger.info("Successfully read and encoded the image for Pensioner ID: " + pensionerId);
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			logger.error("Error reading image file for Pensioner ID: " + pensionerId, e);
			// throw new RuntimeException("Error reading image file: " + e.getMessage(), e);
			return null;
		}
	}

	public String getPensionerImageAsBase64JPG(String department, String pensionerId) {
		String imagePath = assetsBasePath + "/targetImages/" + department + "/" + pensionerId + ".jpg"; // Assuming
																										// images
																										// are .jpg
		logger.info(
				"Attempting to fetch pensioner image for Pensioner ID: " + pensionerId + " from path: " + imagePath);

		File imageFile = new File(imagePath);
		if (!imageFile.exists()) {
			logger.error("Image not found for Pensioner ID: " + pensionerId);
			// throw new IllegalArgumentException("Image not found for pensionerId: " +
			// pensionerId);
			return null;
		}

		try {
			// Read file into byte array
			byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

			// Encode to Base64
			logger.info("Successfully read and encoded the image for Pensioner ID: " + pensionerId);
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			logger.error("Error reading image file for Pensioner ID: " + pensionerId, e);
			// throw new RuntimeException("Error reading image file: " + e.getMessage(), e);
			return null;
		}
	}

	public String getPensionerImageAsBase64JPEG(String department, String pensionerId) {
		String imagePath = assetsBasePath + "/targetImages/" + department + "/" + pensionerId + ".jpeg"; // Assuming
																											// images
																											// are .jpg
		logger.info(
				"Attempting to fetch pensioner image for Pensioner ID: " + pensionerId + " from path: " + imagePath);

		File imageFile = new File(imagePath);
		if (!imageFile.exists()) {
			logger.error("Image not found for Pensioner ID: " + pensionerId);
			// throw new IllegalArgumentException("Image not found for pensionerId: " +
			// pensionerId);
			return null;
		}

		try {
			// Read file into byte array
			byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

			// Encode to Base64
			logger.info("Successfully read and encoded the image for Pensioner ID: " + pensionerId);
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			logger.error("Error reading image file for Pensioner ID: " + pensionerId, e);
			// throw new RuntimeException("Error reading image file: " + e.getMessage(), e);
			return null;
		}
	}

	public String generateDateFormat(Timestamp dateTime) {
		// Convert Timestamp to LocalDateTime
		LocalDateTime localDateTime = dateTime.toLocalDateTime();

		// Formatter for the desired output
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");

		// Format and return the date
		return localDateTime.format(outputFormatter);
	}

	public String getImageByUuidForDownload(String uuid, String pensionerId) {
		String path = assetsBasePath + "/images/" + uuid.replace("-", "/") + "/" + pensionerId + ".png";
		logger.info("Attempting to retrieve image from path: " + path); // Log the path being accessed

		File imageFile = new File(path);

		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage); // You can handle the exception as per your logic
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			logger.info("Successfully encoded the image for Pensioner ID: " + pensionerId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + pensionerId;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	public byte[] getPensionerImage(String department, String pensionerId) {

		String basePath = assetsBasePath + "/targetImages/" + department + "/" + pensionerId;

		String[] extensions = { ".png", ".jpg", ".jpeg" };

		File imageFile = null;

		for (String ext : extensions) {
			File file = new File(basePath + ext);
			if (file.exists()) {
				imageFile = file;
				break;
			}
		}

		if (imageFile == null) {
			logger.error("Image not found for Pensioner ID: {} " + pensionerId);
			return null; // Better: throw 404
		}

		logger.info("Fetching pensioner image. ID: {}  Path: {} " + pensionerId + " " + imageFile.getAbsolutePath());

		try {
			return Files.readAllBytes(imageFile.toPath());
		} catch (IOException e) {
			logger.error("Error reading image file for Pensioner ID: {}" + pensionerId, e);
			return null;
		}
	}

	public void deletePensionerImage(String department, String pensionerId) {

		String basePath = assetsBasePath + "/targetImages/" + department + "/" + pensionerId;
		String[] extensions = { ".png", ".jpg", ".jpeg" };

		boolean deletedAtLeastOne = false;

		for (String ext : extensions) {
			File file = new File(basePath + ext);
			if (file.exists()) {
				file.delete();
				deletedAtLeastOne = true;
			}
		}

		if (!deletedAtLeastOne) {
			throw new RuntimeException("No image found to delete for pensionerId: " + pensionerId);
		}
	}

	public void createPensionerImage(String department, String pensionerId, String base64Image) {

		if (base64Image == null || base64Image.trim().isEmpty()) {
			throw new RuntimeException("Base64 image is required");
		}

		// Remove data:image/...;base64, if present
		String cleanedBase64 = base64Image.trim();
		String extension = ".png"; // default

		if (cleanedBase64.startsWith("data:image")) {
			int commaIndex = cleanedBase64.indexOf(',');
			String meta = cleanedBase64.substring(0, commaIndex);
			cleanedBase64 = cleanedBase64.substring(commaIndex + 1);

			if (meta.contains("jpeg") || meta.contains("jpg")) {
				extension = ".jpg";
			} else if (meta.contains("png")) {
				extension = ".png";
			}
		}

		byte[] imageBytes;
		try {
			imageBytes = Base64.getDecoder().decode(cleanedBase64);
		} catch (Exception e) {
			throw new RuntimeException("Invalid Base64 image");
		}

		String baseDir = assetsBasePath + "/targetImages/" + department;
		File dir = new File(baseDir);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fullPath = baseDir + "/" + pensionerId + extension;
		File imageFile = new File(fullPath);

		if (imageFile.exists()) {
			throw new RuntimeException("Image already exists for pensionerId: " + pensionerId);
		}

		try (FileOutputStream fos = new FileOutputStream(imageFile)) {
			fos.write(imageBytes);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create image file");
		}
	}

}
