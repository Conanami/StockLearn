package org.fuxin.analyst;

public class SampleResult {
	private Sample sample;
	private Integer meetcnt=0;		//此样本与训练集中多少样本匹配
	private Double winrate=0.0;     //此样本的成功率
	private Double raiserate=0.0;	  //此样本的平均收益率
	private Double worstrate= 2.0;    //此样本最差结果
	private Double bestrate = -2.0;    //此样本最好结果
	
	public SampleResult(Sample now) {
		// TODO Auto-generated constructor stub
		sample = now;
	
	}
	public Sample getSample() {
		return sample;
	}
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	public Integer getMeetcnt() {
		return meetcnt;
	}
	public void setMeetcnt(Integer meetcnt) {
		this.meetcnt = meetcnt;
	}
	public Double getWinrate() {
		return winrate;
	}
	public void setWinrate(Double winrate) {
		this.winrate = winrate;
	}
	public Double getRaiserate() {
		return raiserate;
	}
	public void setRaiserate(Double raiserate) {
		this.raiserate = raiserate;
	}
	
	
	@Override
	public String toString() {
		return "SampleResult [sample=" + sample + ", meetcnt=" + meetcnt
				+ ", winrate=" + winrate + ", raiserate=" + raiserate
				+ ", worstrate=" + worstrate + ", bestrate=" + bestrate + "]";
	}
	
	public void update(Double rr, double winthres) {
		// TODO Auto-generated method stub
		int addcnt = 0;
		if(rr>=winthres) addcnt=1;
		
		winrate = (winrate*meetcnt+addcnt)/(meetcnt+1);
		raiserate = (raiserate*meetcnt+rr)/(meetcnt+1);
		if(rr<worstrate) worstrate=rr;
		if(rr>bestrate)  bestrate=rr;
		meetcnt++;
	}
	
	public Double getWorstrate() {
		return worstrate;
	}
	public void setWorstrate(Double worstrate) {
		this.worstrate = worstrate;
	}
	public Double getBestrate() {
		return bestrate;
	}
	public void setBestrate(Double bestrate) {
		this.bestrate = bestrate;
	}
	
public String insertString() {
		
		return " (symbol,startdate,enddate,period,"
				+ "raise5d,raise10d,raise20d,raise60d,raise120d,"
				+ "turnover5d,turnover10d,turnover20d,turnover60d,turnover120d,"
				+ "fakegreencnt,vupdowncnt,raiserate,"
				+ "worstrate,bestrate,meetcnt,winrate,"
				+ "closeprice,halfcost,fullcost,"
				+ "MACD,DEA,DIFF,"
				+ "OBVdiff,OBVraise,MAOBVraise) values(" +
			   "'" + sample.getSymbol()+"'"+","+
			   "'" + sample.getStartdate()+"'"+","+
			   "'" + sample.getEnddate() + "'"+","+
			   "'" + sample.getPeriod() +"'"+","+
			   "'" + sample.getRaise5d()+"'"+","+
			   "'" + sample.getRaise10d()+"'"+","+
			   "'" + sample.getRaise20d()+"'"+","+
			   "'" + sample.getRaise60d()+"'"+","+
			   "'" + sample.getRaise120d()+"'"+","+
			   "'" + sample.getTurnover5d()+"'"+","+
			   "'" + sample.getTurnover10d()+"'"+","+
			   "'" + sample.getTurnover20d()+"'"+","+
			   "'" + sample.getTurnover60d()+"'"+","+
			   "'" + sample.getTurnover120d()+"'"+","+
			   "'" + sample.getFakegreencnt() +"'"+","+
			   "'" + sample.getVupdowncnt() +"'"+","+
			   "'" + raiserate +"'"+"," +
			   "'" + worstrate +"'"+"," +
			   "'" + bestrate +"'"+"," +
			   "'" + meetcnt +"'"+"," +
			   "'" + winrate +"'"+"," +
			   "'" + sample.getCloseprice()+"'"+","+
			   "'" + sample.getHalfcost()+"'"+","+
			   "'" + sample.getFullcost()+"'"+","+
			    
			   "'" + sample.getMacdscore().MACD+"'"+","+
			   "'" + sample.getMacdscore().DEA+"'"+","+
			   "'" + sample.getMacdscore().DIFF+"'"+","+
			   
 				"'" + sample.getObvdiff()+"'"+","+
 				"'" + sample.getObvraise()+"'"+","+
 				"'" + sample.getMaobvraise()+"'"+
			    ")";
	} 
	
}
