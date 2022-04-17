package com.trade.tradestore.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.springframework.stereotype.Component;

import com.trade.tradestore.Trade;
import com.trade.tradestore.repository.TradeRepository;

@Component
public class TradeValidation implements IValidation{

	@Override
	public boolean validateTradeVerion(Trade trade, int index) 
	{
		boolean isVersionGreater = TradeRepository.tradeRepository.stream().anyMatch(i -> i.getTradeID().equals(trade.getTradeID()) && i.getVersion() > trade.getVersion());
		if(isVersionGreater)
		{
			throw new RuntimeException("Rejecting the trade due to lower version.");
		}
		return isVersionGreater;
	}

	@Override
	public boolean validateMaturityDate(Trade trade) 
	{
		LocalDate todayDate = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate maturityDate = LocalDate.parse(trade.getMaturityDate(), format);
		
		if(maturityDate.compareTo(todayDate) < 0)
			throw new RuntimeException("Maturity date cannot be less than todays date.");
		return maturityDate.compareTo(todayDate)>= 0? true:false;
	}

	@Override
	public boolean checkMatureTrade(String date) 
	{
		LocalDate today = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate maturityDate = LocalDate.parse(date,format);
		
		return maturityDate.compareTo(today)== 0? true:false;
	}
	
	

}
