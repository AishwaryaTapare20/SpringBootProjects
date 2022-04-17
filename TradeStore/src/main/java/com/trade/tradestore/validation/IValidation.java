package com.trade.tradestore.validation;

import com.trade.tradestore.Trade;

public interface IValidation {
	
	boolean validateTradeVerion(Trade trade, int index);
	boolean validateMaturityDate(Trade trade);
	boolean checkMatureTrade(String date);

}
