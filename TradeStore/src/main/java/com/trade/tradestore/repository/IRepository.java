package com.trade.tradestore.repository;

import com.trade.tradestore.Trade;

public interface IRepository {

	void addTrade(Trade trade);
	
	void updateTrade(Trade newtrade, int index);
}
