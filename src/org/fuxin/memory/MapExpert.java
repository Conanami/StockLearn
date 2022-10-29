package org.fuxin.memory;


import org.fuxin.factory.DAOFactory;
import org.fuxin.vo.FinanceData;
import org.fuxin.vo.StockDaily;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***
 * �ڴ�ӳ��ר�ң�����ݿ����ȫ�������ڴ棬�����Ժ�ķ���ʹ��
 * @author Administrator
 *
 */
public class MapExpert {
	//���������е����
	private HashMap<String,ArrayList<StockDaily>> alldata = new HashMap<String,ArrayList<StockDaily>>();
	//�����ǹ�Ʊ�����б�
	private ArrayList<String> symbolList = null;
	//���������еĲ������
	private HashMap<String,FinanceData> financedata = new HashMap<String,FinanceData>();
	//���������еĴ������
	private ArrayList<StockDaily> shindex = null;
	public MapExpert(ArrayList<String> symbolList) {
		try {
			this.symbolList = symbolList;
			setFinancedata(DAOFactory.getStockDailyDAOInstance().findFinanceinMap(this.symbolList));
			setAlldata(DAOFactory.getStockDailyDAOInstance().findAllinMap(this.symbolList,this.financedata));
			setIndexdata(DAOFactory.getStockDailyDAOInstance().findIndexData("SH000001"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 专门计算各种指标的
	 * @param findIndexData
	 */
	private void setIndexdata(ArrayList<StockDaily> findIndexData) {
		// TODO Auto-generated method stub
		System.out.println("������ָ֤�����...."+findIndexData.size());
		this.setShindex(findIndexData);
		setMACDdata(alldata);
		
	}

	public MapExpert(ArrayList<String> symbolList, boolean b) {
		try {
			this.symbolList = symbolList;
			if(b==true)
			{
				setFinancedata(DAOFactory.getStockDailyDAOInstance().findFinanceinMap(this.symbolList));
				setAlldata(DAOFactory.getStockDailyDAOInstance().findRecentinMap(symbolList,this.financedata));
				setIndexdata(DAOFactory.getStockDailyDAOInstance().findIndexData("SH000001"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMACDdata(HashMap<String, ArrayList<StockDaily>> alldata2) {
		// TODO Auto-generated method stub
		for(int i=0;i<symbolList.size();++i)
		{
			String symbol = symbolList.get(i);
			ArrayList<StockDaily> dailydata = null;
			if(alldata2.get(symbol)!=null)
				dailydata=alldata2.get(symbol);
			else continue;
			setMacdOBVtosymbol(dailydata);
		}
	}

	/***
	 * 同时计算MACD和OBV
	 * @param dailydata
	 */
	private void setMacdOBVtosymbol(ArrayList<StockDaily> dailydata) {
		for(int i=dailydata.size()-1;i>=0;--i)
		{
			if(i==dailydata.size()-1)
			{
				StockDaily today =dailydata.get(i);
				calcuMACDfirst(today);
				calcuOBVfirst(today);
			}
			else
			{
				StockDaily today = dailydata.get(i);
				StockDaily yesterday = dailydata.get(i+1);
				calcuMACDall(today,yesterday);
				calcuOBVall(today,yesterday);
			}
			
		}
		
	}

	/***
	 * 计算后面所有的OBV
	 * @param today
	 * @param yesterday
	 */
	private void calcuOBVall(StockDaily today, StockDaily yesterday) {
		today.setOBV(calcuObv(yesterday,today));
		today.setMaOBV((long) calcuMA(yesterday.getOBV(),today.getOBV(),30));
	}

	/***
	 * 计算OBV，有昨天
	 * @param yesterday
	 * @param today
	 * @return
	 */
	private double calcuObv(StockDaily yesterday, StockDaily today) {
		if(yesterday.getClose()<today.getClose())
			return yesterday.getOBV()+today.getVolume();
		else
			if(yesterday.getClose()>today.getClose())
				return yesterday.getOBV()-today.getVolume();
			else
				return 0;
	}

	/***
	 * 计算第一天的OBV
	 * @param today
	 */
	private void calcuOBVfirst(StockDaily today) {
		// TODO Auto-generated method stub
		today.setOBV(calcuObv(today));
		today.setMaOBV((long) calcuMA(0,today.getOBV(),30));
	}

	/***
	 * 计算移动平均公式
	 * @param prema
	 * @param todayvalue
	 * @param length
	 * @return
	 */
	private double calcuMA(double prema, double todayvalue, int length) {
		if(length!=0)
			return prema*(length-1)/length+todayvalue/length;
		else 
			return 0;
	}

	/***
	 * 计算第一天的obv，没有昨天的情况
	 * @param today
	 * @return
	 */
	
	private double calcuObv(StockDaily today) {
		if(today.getOpen()<today.getClose())
			return today.getVolume();
		else
			if(today.getOpen()>today.getClose())
				return -today.getVolume();
			else
				return 0;
	}

	/***
	 * 计算MACD的所有值
	 * @param today
	 * @param yesterday
	 */
	private void calcuMACDall(StockDaily today, StockDaily yesterday) {
		today.setEMA12(calcuEMA(yesterday.getEMA12(),today.getClose(),12));
		today.setEMA26(calcuEMA(yesterday.getEMA26(),today.getClose(),26));
		today.setDIFF(calcuDiff(today.getEMA12(),today.getEMA26()));
		today.setDEA(calcuDEA(yesterday.getDEA(),today.getDIFF(),9));
		today.setMACD(calcuMACD(today.getDIFF(),today.getDEA()));
		
	}

	/***
	 * 计算第一天的MACD
	 * @param today
	 */
	private void calcuMACDfirst(StockDaily today) {
		today.setEMA12(calcuEMA(0.0f,today.getClose(),12));
		today.setEMA26(calcuEMA(0.0f,today.getClose(),26));
		today.setDIFF(calcuDiff(today.getEMA12(),today.getEMA26()));
		today.setDEA(calcuDEA(0f,today.getDIFF(),9));
		today.setMACD(calcuMACD(today.getDIFF(),today.getDEA()));
	}

	
	private double calcuDiff(double ema12, double ema26) {
		// TODO Auto-generated method stub
		return ema12-ema26;
	}

	private double calcuEMA(double d, Float closeprice, int length) {
		// TODO Auto-generated method stub
		return d*(length-1f)/(length+1f)+closeprice*2/(length+1f);
	}

	private double calcuDEA(double predea, double nowdiff, int length) {
		return predea*(length-1f)/(length+1f)+nowdiff*2/(length+1f);
	}

	private double calcuMACD(double diff, double dea) {
		return 2*(diff-dea);
	}

	public MapExpert(ArrayList<String> symbolList, boolean b, Date selectday) {
		try {
			this.symbolList = symbolList;
			if(b==true)
			{
				setFinancedata(DAOFactory.getStockDailyDAOInstance().findFinanceinMap(this.symbolList));
				setAlldata(DAOFactory.getStockDailyDAOInstance().findRecentinMap(symbolList,selectday,this.financedata));
				setIndexdata(DAOFactory.getStockDailyDAOInstance().findIndexData("SH000001"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getSymbolList() {
		return symbolList;
	}
	public void setSymbolList(ArrayList<String> symbolList) {
		this.symbolList = symbolList;
	}

	public HashMap<String,ArrayList<StockDaily>> getAlldata() {
		return alldata;
	}

	public void setAlldata(HashMap<String,ArrayList<StockDaily>> alldata) {
		this.alldata = alldata;
	}

	public HashMap<String,FinanceData> getFinancedata() {
		return financedata;
	}

	public void setFinancedata(HashMap<String,FinanceData> financedata) {
		this.financedata = financedata;
	}

	public ArrayList<StockDaily> getShindex() {
		return shindex;
	}

	public void setShindex(ArrayList<StockDaily> shindex) {
		this.shindex = shindex;
	}
		
}
