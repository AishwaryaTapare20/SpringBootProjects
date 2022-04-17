package com.trade.tradestore.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.trade.tradestore.Trade;

@Repository
public class TradeRepository implements IRepository{
	
	public static ArrayList<Trade> tradeRepository = new ArrayList<Trade>();

	@Override
	public void addTrade(Trade trade) {
		tradeRepository.add(trade);
		
	}

	@Override
	public void updateTrade(Trade newTrade, int index) {
		TradeRepository.tradeRepository.get(index).setCounterPartyID(newTrade.getCounterPartyID());
		TradeRepository.tradeRepository.get(index).setBookID(newTrade.getBookID());
		TradeRepository.tradeRepository.get(index).setMaturityDate(newTrade.getMaturityDate());
		TradeRepository.tradeRepository.get(index).setCreatedDate(newTrade.getCreatedDate());
		TradeRepository.tradeRepository.get(index).setExpired(newTrade.getExpired());
		TradeRepository.tradeRepository.get(index).setTradeID(newTrade.getTradeID());
		
	}

}
