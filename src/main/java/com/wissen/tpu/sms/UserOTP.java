//package com.wissen.tpu.sms;
//
//import java.net.URLEncoder;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//@Service
//public class UserOTP {
//
//    private static final Log logger = LogFactory.getLog(UserOTP.class);
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Value("${OTPbaseUrl}")
//    private String baseUrl;
//
//    @Value("${securityKey}")
//    private String securityKey;
//
//	
//
//    public String generateOTP(String mobile, String otp) {
//    	 
//        logger.info("Starting OTP generation process.");
//        logger.debug("Mobile Number: " + mobile + ", OTP: " + otp);
//
//        try {
//            // Construct the OTP message
//            String message = String.format(
//                    "Dear User, Your OTP is %s. This is valid for only 15 minutes and can be used only once. TGTS(T App Folio).",
//                    otp);
//            logger.debug("Generated OTP message: " + message);
//
//            // Encode the message
//            String encodedMessage = URLEncoder.encode(message, "UTF-8");
//            logger.debug("Encoded OTP message: " + encodedMessage);
//
//            // Construct the request URL
//            String URL=baseUrl+"?da="+mobile
//            +"&msg=Dear User, Your OTP is "+otp+". This is valid for only 15 minutes and can be used only once. T App Folio."
//            +"&x_s_key=928DD503A81AD7E744D9A3F054304F7A2A1EF66F76BDB880E28D51E4293F9160";
//            
//          //  String requestUrl = String.format("%s?da=%s&msg=%s&x_s_key=%s", baseUrl, mobile, encodedMessage, securityKey);
//            logger.info("Constructed OTP request URL: " + URL);
//
//           
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            // Make the request using RestTemplate.exchange
//            ResponseEntity<String> responseEntity = restTemplate.exchange(
//            		URL,
//                    HttpMethod.GET,
//                    entity,
//                    String.class
//            );
//
//            // Extract the response body
//            String response = responseEntity.getBody();
//            logger.info("OTP successfully sent. Response: " + response);
//
//            return response;
//
//        } catch (Exception e) {
//            logger.error("Error during OTP generation: " + e.getMessage(), e);
//            return "Failed to send OTP: " + e.getMessage();
//        }
//    }
//
//}
