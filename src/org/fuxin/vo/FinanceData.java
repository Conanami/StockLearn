package org.fuxin.vo;

import java.util.Calendar;
import java.util.Date;

/***
 * һ����Ʊ����Ҫ�������
 * @author Administrator
 *
 */
public class FinanceData {
	public String symbol;				//��Ʊ����
	private Date reportdate;			//������
	private Float yearRatio;			//�ǰ��꣬��һ�����������ȫ��
	private Float earning_ps;			//ÿ������
	private Float netassets_ps;			//ÿ�ɾ��ʲ�
	private Float netprofit_rose;		//������������
	private Float revenue_rose;			//��Ӫ����������
	private Float unlimit_shares;       //��ͨ��
	
	public FinanceData() {
		
	}


	public FinanceData(String symbol, Date reportdate, Float earning_ps,
			Float netassets_ps, Float netprofit_rose, Float revenue_rose) {
		super();
		this.symbol = symbol;
		this.reportdate = reportdate;
		this.earning_ps = earning_ps;
		this.netassets_ps = netassets_ps;
		this.netprofit_rose = netprofit_rose;
		this.revenue_rose = revenue_rose;
		this.yearRatio = calcuYearRatio(this.reportdate);
	}


	private Float calcuYearRatio(Date reportdate2) {
		Calendar c = Calendar.getInstance();
		c.setTime(reportdate2);
		switch(c.get(Calendar.MONTH))
		{
		case 2:
			return 0.25f;
		case 5:
			return 0.5f; 
		case 8:
			return 0.75f;
		case 11:
			return 1.0f;
		default:
			return 0f;
			
		}
		
		
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public Date getReportdate() {
		return reportdate;
	}


	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
		this.yearRatio = calcuYearRatio(this.reportdate);
	}


	public Float getEarning_ps() {
		return earning_ps;
	}


	public void setEarning_ps(Float earning_ps) {
		this.earning_ps = earning_ps;
	}


	public Float getNetassets_ps() {
		return netassets_ps;
	}


	public void setNetassets_ps(Float netassets_ps) {
		this.netassets_ps = netassets_ps;
	}


	public Float getNetprofit_rose() {
		return netprofit_rose;
	}


	public void setNetprofit_rose(Float netprofit_rose) {
		this.netprofit_rose = netprofit_rose;
	}


	public Float getRevenue_rose() {
		return revenue_rose;
	}


	public void setRevenue_rose(Float revenue_rose) {
		this.revenue_rose = revenue_rose;
	}


	public Float getYearRatio() {
		return yearRatio;
	}


	

	@Override
	public String toString() {
		return "FinanceData [symbol=" + symbol + ", reportdate=" + reportdate
				+ ", yearRatio=" + yearRatio + ", earning_ps=" + earning_ps
				+ ", netassets_ps=" + netassets_ps + ", netprofit_rose="
				+ netprofit_rose + ", revenue_rose=" + revenue_rose
				+ ", unlimit_shares=" + unlimit_shares + "]";
	}


	public String showInSelect() {
		return "������=" + reportdate
				+ ", ÿ������=" + earning_ps
				+ ", ÿ�ɾ��ʲ�=" + netassets_ps + ", ����������="
				+ netprofit_rose + ", ��������=" + revenue_rose 
				+ ", ��ͨ��=" + unlimit_shares;
	}


	public Float getUnlimit_shares() {
		return unlimit_shares;
	}


	public void setUnlimit_shares(Float unlimit_shares) {
		this.unlimit_shares = unlimit_shares;
	}
	
	
	
}
