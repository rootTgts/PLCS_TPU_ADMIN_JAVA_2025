package com.wissen.tpu.service;

import org.springframework.stereotype.Service;

@Service
public class PDFService {

//	@Value("${logoImgPath}")
//	private String logoImgPath;
//
//	@Value("${logoImgScale}")
//	private Float[] logoImgScale;

	public byte[] generatePdf(Object object) {
		return null;
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//		try {
//
//			Document document = new Document();
//
//			PdfWriter.getInstance(document, byteArrayOutputStream);
//
//			document.open();
//			addLogo(document);
//
//			document.add(new Paragraph("      Digital Life Certificate for the Year 2024-25   "));
//
//			document.close();
//		} catch (DocumentException e) {
//			throw new RuntimeException("Error generating PDF", e);
//		}
//
//		return byteArrayOutputStream.toByteArray();
//	}
//	private void addDocTitle(Document document) throws DocumentException {
//		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
//		Paragraph p1 = new Paragraph();
//		leaveEmptyLine(p1, 1);
//		p1.add(new Paragraph(reportFileName, COURIER));
//		p1.setAlignment(Element.ALIGN_CENTER);
//		leaveEmptyLine(p1, 1);
//		p1.add(new Paragraph("Report generated on " + localDateString, COURIER_SMALL));
//
//		document.add(p1);
//
//	}
//	private void addLogo(Document document) {
//		try {
//			Image img = Image.getInstance(logoImgPath);
//			img.scalePercent(logoImgScale[0], logoImgScale[1]);
//			img.setAlignment(Element.ALIGN_RIGHT);
//			document.add(img);
//		} catch (DocumentException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	private static void leaveEmptyLine(Paragraph paragraph, int number) {
//		for (int i = 0; i < number; i++) {
//			paragraph.add(new Paragraph(" "));
//		}
//	}
//	private String getPdfNameWithDate() {
//		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
//		return pdfDir+reportFileName+"-"+localDateString+".pdf";
//	}
}}
