package com.salespointfx2.www.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {
	private static final String INSTANCE_ID = "instance108464"; // Reemplaza con tu ID de UltraMsg
	private static final String TOKEN = "z9e4s2vvyuktm470"; // Reemplaza con tu Token de UltraMsg
	private static final String API_URL = "https://api.ultramsg.com/" + INSTANCE_ID + "/messages/chat";

	public String sendWhatsAppMessage(String numero, String mensaje) {
		RestTemplate restTemplate = new RestTemplate();

		// Crear el cuerpo de la petición
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("token", TOKEN);
		requestBody.put("to", numero);
		requestBody.put("body", mensaje);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
		return response.getBody();
	}
}
