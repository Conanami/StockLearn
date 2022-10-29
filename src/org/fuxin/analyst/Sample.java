package org.fuxin.analyst;

import java.util.Date;

import org.fuxin.macd.MacdScore;

public class Sample {
	private double raise120d;
	private double raise60d;
	private double raise20d;
	private double raise10d;
	private double raise5d;
	private double turnover5d;
	private double turnover10d;
	private double turnover20d;
	private double turnover60d;
	private double turnover120d;
	
	//假阴线的个数
	private int fakegreencnt;
	//量伸缩的个数
	private int vupdowncnt;
	
	//50%换手的加权平均成本
	private double halfcost;
	//100%换手的加权平均成本
	private double fullcost;
	
	
	private int period;
	private String symbol;
	private String name;
	private Date startdate;
	private Date enddate;
	private double raiserate;
	private double unlimit_shares;
	
	private MacdScore macdscore;
	private double closeprice;
	
	//这是三个OBV指标
	private double obvraise;
	private double maobvraise;
	private double obvdiff;
	
	
	
	public double getObvraise() {
		return obvraise;
	}
	public void setObvraise(double obvraise) {
		this.obvraise = obvraise;
	}
	public double getMaobvraise() {
		return maobvraise;
	}
	public void setMaobvraise(double maobvraise) {
		this.maobvraise = maobvraise;
	}
	public double getObvdiff() {
		return obvdiff;
	}
	public void setObvdiff(double obvdiff) {
		this.obvdiff = obvdiff;
	}
	public double getRaise120d() {
		return raise120d;
	}
	public void setRaise120d(double raise120d) {
		this.raise120d = raise120d;
	}
	public double getRaise60d() {
		return raise60d;
	}
	public void setRaise60d(double raise60d) {
		this.raise60d = raise60d;
	}
	public double getRaise20d() {
		return raise20d;
	}
	public void setRaise20d(double raise20d) {
		this.raise20d = raise20d;
	}
	public double getRaise10d() {
		return raise10d;
	}
	public void setRaise10d(double raise10d) {
		this.raise10d = raise10d;
	}
	public double getRaise5d() {
		return raise5d;
	}
	public void setRaise5d(double raise5d) {
		this.raise5d = raise5d;
	}
	public double getTurnover5d() {
		return turnover5d;
	}
	public void setTurnover5d(double turnover5d) {
		this.turnover5d = turnover5d;
	}
	public double getTurnover10d() {
		return turnover10d;
	}
	public void setTurnover10d(double turnover10d) {
		this.turnover10d = turnover10d;
	}
	public double getTurnover20d() {
		return turnover20d;
	}
	public void setTurnover20d(double turnover20d) {
		this.turnover20d = turnover20d;
	}
	public double getTurnover60d() {
		return turnover60d;
	}
	public void setTurnover60d(double turnover60d) {
		this.turnover60d = turnover60d;
	}
	public double getTurnover120d() {
		return turnover120d;
	}
	public void setTurnover120d(double turnover120d) {
		this.turnover120d = turnover120d;
	}
	public int getFakegreencnt() {
		return fakegreencnt;
	}
	public void setFakegreencnt(int fakegreencnt) {
		this.fakegreencnt = fakegreencnt;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public double getRaiserate() {
		return raiserate;
	}
	public void setRaiserate(double raiserate) {
		this.raiserate = raiserate;
	}
	
	public String insertString() {
		
		return " (symbol,startdate,enddate,period,"
				+ "raise5d,raise10d,raise20d,raise60d,raise120d,"
				+ "turnover5d,turnover10d,turnover20d,turnover60d,turnover120d,"
				+ "fakegreencnt,vupdowncnt,raiserate) values("
				+				
				"'"+symbol+"'"+","+
			   "'"+startdate+"'"+","+
			   "'"+enddate + "'"+","+
			   "'"+period+"'"+","+
			   "'"+raise5d+"'"+","+
			   "'"+raise10d+"'"+","+
			   "'"+raise20d+"'"+","+
			   "'"+raise60d+"'"+","+
			   "'"+raise120d+"'"+","+
			   "'"+turnover5d+"'"+","+
			   "'"+turnover10d+"'"+","+
			   "'"+turnover20d+"'"+","+
			   "'"+turnover60d+"'"+","+
			   "'"+turnover120d+"'"+","+
			   "'"+fakegreencnt+"'"+","+
			   "'"+vupdowncnt+"'"+","+
			   "'"+raiserate+"'"+")";
			   
			   
	}
	
		
	
	
	
	
	@Override
	public String toString() {
		return "Sample [symbol=" + symbol + ", name=" + name + ", startdate="
				+ startdate + ", enddate=" + enddate + ", raiserate="
				+ raiserate + ", unlimit_shares=" + unlimit_shares
				+ ", macdscore=" + macdscore + ", closeprice=" + closeprice
				+ ", obvraise=" + obvraise + ", maobvraise=" + maobvraise
				+ ", obvdiff=" + obvdiff + ",]";
	}
	
	public double getUnlimit_shares() {
		return unlimit_shares;
	}
	public void setUnlimit_shares(double unlimit_shares) {
		this.unlimit_shares = unlimit_shares;
	}
	public int getVupdowncnt() {
		return vupdowncnt;
	}
	public void setVupdowncnt(int vupdowncnt) {
		this.vupdowncnt = vupdowncnt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enddate == null) ? 0 : enddate.hashCode());
		result = prime * result + period;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sample other = (Sample) obj;
		if (enddate == null) {
			if (other.enddate != null)
				return false;
		} else if (!enddate.equals(other.enddate))
			return false;
		if (period != other.period)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	public MacdScore getMacdscore() {
		return macdscore;
	}
	public void setMacdscore(MacdScore macdscore) {
		this.macdscore = macdscore;
	}
	public double getCloseprice() {
		return closeprice;
	}
	public void setCloseprice(double closeprice) {
		this.closeprice = closeprice;
	}
	public double getHalfcost() {
		return halfcost;
	}
	public void setHalfcost(double halfcost) {
		this.halfcost = halfcost;
	}
	public double getFullcost() {
		return fullcost;
	}
	public void setFullcost(double fullcost) {
		this.fullcost = fullcost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
