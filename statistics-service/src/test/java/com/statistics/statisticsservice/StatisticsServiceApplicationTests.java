package com.statistics.statisticsservice;

import com.statistics.statisticsservice.service.StatisticsService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =
		SpringBootTest.WebEnvironment.MOCK,
		classes = StatisticsServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-test.properties")
@Ignore
public class StatisticsServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StatisticsService statisticsServiceImpl;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getStatistics_thenStatus200()
			throws Exception {
		mockMvc.perform(get("/statistics/?time=2019-06-12 22:30:56, 2019-06-01 22:30:56")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
