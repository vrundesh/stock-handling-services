package com.stock.service.stockservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =
		SpringBootTest.WebEnvironment.MOCK,
		classes = StockServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-test.properties")
//@Ignore
public class StockServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
	}
	@Test
	public void getProduct_thenStatus200()throws Exception {
		mockMvc.perform(get("/stock/?productId=vegetable-130")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		        .andReturn();
	}

}
