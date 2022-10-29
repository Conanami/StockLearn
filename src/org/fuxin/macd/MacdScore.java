package org.fuxin.macd;

import java.util.Date;

import org.fuxin.vo.StockDaily;

/***
 * 记录MACD的各个值ֵ
 * @author Administrator
 *
 */
public class MacdScore {
	public String symbol;				//����
	public String name;					//���
	public Date date;					//����
	public int index;					//�ڴ�������
	public double DIFF;					//DIFFֵ
	public double DEA;					//DEAֵ
	public double MACD;					//MACDֵ����������
	
	public MacdScore(StockDaily stockDaily) {
		symbol =  stockDaily.getSymbol();
		name = stockDaily.getName();
		date = stockDaily.getDate();
		DIFF = stockDaily.getDIFF();
		DEA = stockDaily.getDEA();
		MACD = stockDaily.getMACD();
	}

	

	public MacdScore(String symb, java.sql.Date date, double diff,
			double dea, double macd) {
		this.symbol =  symb;
		this.date = date;
		DIFF = diff;
		DEA =  dea;
		MACD = macd;
	}




	@Override
	public String toString() {
		return "MacdScore [symbol=" + symbol + ", date="
				+ date  + ", DIFF=" + DIFF + ", DEA=" + DEA
				+ ", MACD=" + MACD + "]";
	}
	
}
