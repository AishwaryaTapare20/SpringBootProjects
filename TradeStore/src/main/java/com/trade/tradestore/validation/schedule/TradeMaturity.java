package com.trade.tradestore.validation.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trade.tradestore.Trade;
import com.trade.tradestore.repository.TradeRepository;
import com.trade.tradestore.validation.TradeValidation;

@Component
public class TradeMaturity {
	
	@Autowired
	TradeValidation validation;
	
	//@Scheduled(fixedDelay = 30000)
	@Scheduled(cron = "${interval}")
	public void checkMatureTrade()
	{
		if(!TradeRepository.tradeRepository.isEmpty())
		{
			for(Trade trade: TradeRepository.tradeRepository) 
			{
				if(validation.checkMatureTrade(trade.getMaturityDate()))
				{
					trade.setExpired("Y");
				}
			}
			System.out.println("Scheduler called successfully.");
		}
	}

}
