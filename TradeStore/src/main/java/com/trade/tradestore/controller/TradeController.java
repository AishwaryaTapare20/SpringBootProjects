package com.trade.tradestore.controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.trade.tradestore.Trade;
import com.trade.tradestore.repository.IRepository;
import com.trade.tradestore.repository.TradeRepository;
import com.trade.tradestore.validation.IValidation;
import com.trade.tradestore.validation.TradeValidation;

@RestController
public class TradeController {
	
	@Autowired
	private IValidation validate;
	
	@Autowired
	private IRepository repository;
	
	@PostMapping("/createTrade")
	public String createTrade(@RequestBody Trade trade)
	{
		try
		{
			int index = -1;
			if(!TradeRepository.tradeRepository.isEmpty())
			{
				for(Trade t:TradeRepository.tradeRepository )
				{
					if(t.getTradeID().equals(trade.getTradeID()))
					{
						index = TradeRepository.tradeRepository.indexOf(t);
						
						break;

					}
				}
				if(index != -1)
				{
					if(validate.validateTradeVerion(trade,index))
					{
						if(validate.validateMaturityDate(trade))
						{
							if(TradeRepository.tradeRepository.get(index).equals(trade))
							{
								repository.updateTrade(trade, index);
							}
							else
							{
								repository.addTrade(trade);
							}
						}
						
					}
				}
				else
				{
					if(validate.validateMaturityDate(trade))
					{
						repository.addTrade(trade);
					}
					
				}
			}
			else
			{
				if(validate.validateMaturityDate(trade))
				{
					repository.addTrade(trade);
				}
			}
			
			
		}
		catch(RuntimeException e)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
		}
		return "Trade created with ID " + trade.getTradeID();
	}
	
	@GetMapping("/FetchTrades")
	public ArrayList<Trade> getAllTrades()
	{
		return TradeRepository.tradeRepository;
	}

}
