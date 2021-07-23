package br.com.alura.springboot.rest.forum.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@WebMvcTest //usar essa se for pra carregar apenas a camada dos controllers
@SpringBootTest //este carrega todas as camadas, necessario pra testar o controller de segurança
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void deveriaDevolver400CasoDadosDeAutenticacaoIncorretos() throws Exception {
		URI uri = new URI("/auth");
		
		String json = "{\"email\":\"invalido@email.com\",\"senha\":\"1234\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
	}

}
