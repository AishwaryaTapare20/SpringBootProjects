package com.trade.tradestore;


public class Trade {
	

	private String tradeID;
	
	private int version;
	
	private String counterPartyID;
	
	private String bookID;
	
	private String maturityDate;
	
	private String createdDate;
	
	private String expired;
	
	public String getTradeID() {
		return tradeID;
	}
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getCounterPartyID() {
		return counterPartyID;
	}
	public void setCounterPartyID(String counterPartyID) {
		this.counterPartyID = counterPartyID;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		
		if(obj instanceof Trade)
			return true;
		
		Trade tradeObj = (Trade)obj;
		return this.tradeID == tradeObj.tradeID && this.version == tradeObj.version;
	}
	
	@Override 
	public int hashCode()
	{
		return this.version;
	}

}
