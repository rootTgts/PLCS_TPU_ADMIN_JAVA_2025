package com.wissen.tpu.service;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.wissen.tpu.repository.DownloadRepository;
import com.wissen.tpu.utils.Utils;

@Service
public class PdfGeneratorService {

	private static final Log logger = LogFactory.getLog(PdfGeneratorService.class);

	private final TemplateEngine templateEngine;
	@Autowired
	DownloadRepository downloadRepo;
	@Autowired
	Utils utils;

	public PdfGeneratorService(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public byte[] generatePdf(String templateName, Map<String, Object> data) {
		Context context = new Context();
		context.setVariables(data); // Pass dynamic data to the template

		// Process the HTML template
		String htmlContent = templateEngine.process(templateName, context);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withHtmlContent(htmlContent, null);
			builder.toStream(outputStream);
			builder.run();

			return outputStream.toByteArray(); // Return generated PDF bytes
		} catch (Exception e) {
			throw new RuntimeException("Error generating PDF", e);
		}
	}

	public Map<String, Object> getByPensionerIdForPDFByApproved(String pensionerId, String dept) {
		Map<String, Object> data = new HashMap<>();
		try {
			Object[] reponse = downloadRepo.findByPensionerIDAndDeptForDownload(pensionerId, dept);

			if (reponse != null) {
				data.put("pensionerId", (reponse[0] != null ? (String) reponse[0] : null));
				data.put("ppoNumber", (reponse[1] != null ? (String) reponse[1] : null));
				data.put("pensionerName", (reponse[2] != null ? (String) reponse[2] : null));
				data.put("mobileNumber", (reponse[3] != null ? (String) reponse[3] : null));

				String inputUUID = (reponse[4] != null ? (String) reponse[4] : null);
				data.put("departmentPhotoBase64", utils.getImageByUuid(inputUUID, pensionerId));

				Timestamp submittedData = reponse[5] != null ? (Timestamp) reponse[5] : null;
				Timestamp approvedDate = reponse[6] != null ? (Timestamp) reponse[6] : null;

				data.put("issuedDateTime", utils.generateDateFormat(approvedDate));
				data.put("SubmittedDate", utils.generateDateFormat(submittedData));

				data.put("status", "Digital Life Certificate Approved"); // Ensure no & here

				data.put("status", data.get("status").toString().replace("&", "&amp;"));
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return data;
	}

	public Map<String, Object> getByPensionerIdForPDFByVerifier(String pensionerId, String dept) {
		Map<String, Object> data = new HashMap<>();
		try {
			Object[] reponse = downloadRepo.findByPensionerIDAndDeptForDownload(pensionerId, dept);

			if (reponse != null) {
				data.put("pensionerId", (reponse[0] != null ? (String) reponse[0] : null));
				data.put("ppoNumber", (reponse[1] != null ? (String) reponse[1] : null));
				data.put("pensionerName", (reponse[2] != null ? (String) reponse[2] : null));
				data.put("mobileNumber", (reponse[3] != null ? (String) reponse[3] : null));

				String inputUUID = (reponse[4] != null ? (String) reponse[4] : null);
				data.put("departmentPhotoBase64", utils.getImageByUuid(inputUUID, pensionerId));

				Timestamp submittedData = reponse[5] != null ? (Timestamp) reponse[5] : null;
				Timestamp approvedDate = reponse[6] != null ? (Timestamp) reponse[6] : null;

				data.put("issuedDateTime", utils.generateDateFormat(approvedDate));
				data.put("SubmittedDate", utils.generateDateFormat(submittedData));

				data.put("status", "Digital Life Certificate Approved"); // Ensure no & here

				data.put("status", data.get("status").toString().replace("&", "&amp;"));
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return data;
	}

}
