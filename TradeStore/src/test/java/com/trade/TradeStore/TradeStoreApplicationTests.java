package com.trade.TradeStore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.trade.tradestore.Trade;
import com.trade.tradestore.controller.TradeController;
import com.trade.tradestore.validation.TradeValidation;
import com.trade.tradestore.validation.schedule.TradeMaturity;


@SpringBootTest
class TradeStoreApplicationTests {
	
	private MockMvc mockMvc;
	
	@Spy
	@InjectMocks
	private TradeMaturity checkMatureTrade =new TradeMaturity();
	

	@Autowired 
	private TradeController controller;
	
	@Autowired
	private TradeValidation tradeValidation;
	
	static Trade trade,anotherTrade;
	
	@BeforeAll
	static void setup()
	{
		trade = new Trade();
		trade.setTradeID("T1");
		trade.setVersion(2);
		trade.setBookID("B1");
		trade.setCounterPartyID("CP1");
		trade.setMaturityDate("12/08/2022");
		trade.setCreatedDate("12/04/2022");
		trade.setExpired("N");
		
		anotherTrade = new Trade();
		anotherTrade.setTradeID("T1");
		anotherTrade.setVersion(1);
		anotherTrade.setBookID("B2");
		anotherTrade.setCounterPartyID("CP2");
		anotherTrade.setMaturityDate("11/04/2022");
		anotherTrade.setCreatedDate("12/04/2022");
		anotherTrade.setExpired("N");
		
	}

	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	void checkPostMethod() throws Exception
	{
		RequestBuilder requestBuilder = MockMvcRequestBuilders
	            .post("/CreateTrade")
	            .accept(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"tradeID\":\"T1\",\n"
	            		+ "    \"version\":\"2\",\n"
	            		+ "    \"counterPartyID\":\"CP-1\",\n"
	            		+ "    \"bookID\":\"B1\",\n"
	            		+ "    \"maturityDate\":\"12/08/2022\",\n"
	            		+ "    \"createdDate\":\"11/04/2022\",\n"
	            		+ "    \"expired\":\"N\"\n"
	            		+ "}")
	            .contentType(MediaType.APPLICATION_JSON);		
		mockMvc  = MockMvcBuilders.standaloneSetup(controller).build();
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

	    assertEquals(HttpStatus.OK.value(), response.getStatus());
	    assertEquals("Trade created with ID T1", response.getContentAsString());

	}
	
	@Test
	void checkMatureTrade()
	{
		//assertTrue(tradeValidation.checkMatureTrade("13/04/2022"));
		assertFalse(tradeValidation.checkMatureTrade("12/05/2022"));
	}
	
	@Test
	void checkValidateMaturityDate()
	{
		assertTrue(tradeValidation.validateMaturityDate(trade));
		assertThrows(RuntimeException.class, () -> tradeValidation.validateMaturityDate(anotherTrade));
		assertThat(assertThrows(RuntimeException.class, () -> tradeValidation.validateMaturityDate(anotherTrade))).hasMessage("Maturity date cannot be less than todays date.");
	}
	
	@Test
	void testValidateTradeVerion()
	{
		assertTrue(tradeValidation.validateTradeVerion(trade, 0));
		assertThrows(RuntimeException.class, () -> tradeValidation.validateTradeVerion(anotherTrade, 0));
		assertThat(assertThrows(RuntimeException.class, () -> tradeValidation.validateTradeVerion(anotherTrade, 0))).hasMessage("Rejecting the trade due to lower version.");
	}
	
//	@Test
//	void testCheckMatureTrade()
//	{
//		Awaitility.await().atMost(Duration.ofMillis(60000)).untilAsserted(() -> verify(checkMatureTrade, atLeast(1)).checkMatureTrade());
//	}

}
