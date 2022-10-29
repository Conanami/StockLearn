package org.fuxin.analyst;

import java.util.ArrayList;

import org.fuxin.macd.MacdScore;
import org.fuxin.memory.MapExpert;
import org.fuxin.vo.StockDaily;
import org.fuxin.memory.*;


public class SampleMaker {

	//扫描该代码所有股票
	//得到所有符合的样本
	public static ArrayList<Sample> ScanAll(MapExpert me, String symbol,
			double threshold) {
		ArrayList<Sample> rtlist = new ArrayList<Sample>();
		int step=5;
		ArrayList<StockDaily> dailydata = me.getAlldata().get(symbol);
		if(dailydata==null) return rtlist;
		
		Float totalV= null;
		if(me.getFinancedata().get(symbol).getUnlimit_shares()==null)
			totalV=0f;
		else
			totalV=me.getFinancedata().get(symbol).getUnlimit_shares();
		
		//做样本时带一定的随机性
		for(int i=(int)(5*Math.random());i<dailydata.size()-(C.Samplesize+C.Resultsize)-1;i=i+step)
		{
			if(CalcuRaise(dailydata,i,C.Resultsize)>threshold)
			{
				Sample sample=Make(symbol,dailydata,i,C.Samplesize,C.Resultsize,totalV);
				rtlist.add(sample);
			}
		}
		return rtlist;
	}

	/***
	 * 制作样本
	 * @param symbol
	 * @param dailydata
	 * @param start
	 * @param length
	 * @param resultsize
	 * @param totalV
	 * @return
	 */
	private static Sample Make(String symbol, ArrayList<StockDaily> dailydata, int start, int length, Integer resultsize, Float totalV) {
		Sample rtsample = new Sample();
		int samplestart = start + resultsize;
		int sampleend = samplestart + length - 1;
		
		rtsample.setName(dailydata.get(start).getName());
		
		rtsample.setEnddate(dailydata.get(start).getDate());
		rtsample.setStartdate(dailydata.get(sampleend).getDate());
		rtsample.setPeriod(length+resultsize);
		rtsample.setSymbol(symbol);
		
		rtsample.setRaiserate(CalcuRaise(dailydata,start,resultsize));
		rtsample.setUnlimit_shares(totalV);
	
		
		rtsample.setRaise5d(CalcuRaise(dailydata,samplestart,5));
		rtsample.setRaise10d(CalcuRaise(dailydata,samplestart,10));
		rtsample.setRaise20d(CalcuRaise(dailydata,samplestart,20));
		rtsample.setRaise60d(CalcuRaise(dailydata,samplestart,60));
		rtsample.setRaise120d(CalcuRaise(dailydata,samplestart,120));
		
		rtsample.setTurnover5d(CalcuTurnover(dailydata,samplestart,5));
		rtsample.setTurnover10d(CalcuTurnover(dailydata,samplestart,10));
		rtsample.setTurnover20d(CalcuTurnover(dailydata,samplestart,20));
		rtsample.setTurnover60d(CalcuTurnover(dailydata,samplestart,60));
		rtsample.setTurnover120d(CalcuTurnover(dailydata,samplestart,120));
		
		rtsample.setFakegreencnt(GetFakeGreenCount(dailydata,samplestart,40));
		rtsample.setVupdowncnt(GetVupdownCount(dailydata,samplestart,40));
		
		rtsample.setMacdscore(GetMACDall(dailydata,samplestart));
		rtsample.setCloseprice(dailydata.get(samplestart).getClose());
		
		rtsample.setHalfcost(CalcuCost(dailydata,samplestart,50.0));
		rtsample.setFullcost(CalcuCost(dailydata,samplestart,100.0));
		
		//obv参数的计算
		rtsample.setObvdiff(CalcuObvdiff(dailydata,samplestart));
		rtsample.setObvraise(CalcuObvraise(dailydata,samplestart,20));
		rtsample.setMaobvraise(CalcuMaobv(dailydata,samplestart,20));
		
		return rtsample;
	
	}

	
	private static double CalcuMaobv(ArrayList<StockDaily> dailydata,
			int samplestart, int length) {
		int end = samplestart + length -1 ;
		if(end>=dailydata.size()) end = dailydata.size()-1;
		StockDaily today = dailydata.get(samplestart);
		StockDaily past = dailydata.get(end);
		if(past.getMaOBV()==0)
			return 0;
		else
			return (today.getMaOBV()-past.getMaOBV())/past.getMaOBV();
	}

	private static double CalcuObvraise(ArrayList<StockDaily> dailydata,
			int samplestart, int length) {
		int end = samplestart + length -1 ;
		if(end>=dailydata.size()) end = dailydata.size()-2;
		StockDaily today = dailydata.get(samplestart);
		StockDaily past = dailydata.get(end);
		
		if(past.getOBV()==0)
			return 0;
		else
			return (today.getOBV()-past.getOBV())/past.getOBV();
	}

	private static double CalcuObvdiff(ArrayList<StockDaily> dailydata,
			int samplestart) {
		StockDaily today = dailydata.get(samplestart);
		if(today.getMaOBV()==0)
			return 0;
		else
			return (today.getOBV()-today.getMaOBV())/today.getMaOBV();
		
	}

	/***
	 * 计算多少换手率之内的平均成本
	 * @param dailydata
	 * @param samplestart
	 * @param percentage
	 * @return
	 */
	private static double CalcuCost(ArrayList<StockDaily> dailydata,
			int samplestart, double percentage) {
		// TODO Auto-generated method stub
		//计算多少换手率之内的成本
		double turnovertotal = 0;
		double avgcost = 0;
		double pricetotal=0;
		int i=samplestart;
		double closeprice = dailydata.get(i).getClose();
		while( turnovertotal<percentage && i<dailydata.size() )
		{
			pricetotal += dailydata.get(i).getClose() * dailydata.get(i).getTurnover();
			turnovertotal +=dailydata.get(i).getTurnover();
			i++;
		}
		avgcost = pricetotal/turnovertotal;
		return (closeprice-avgcost)/closeprice;
		
	}

	@SuppressWarnings("unused")
	private static double CalcuHighRaise(ArrayList<StockDaily> dailydata,
			int start, int length) {
		int end = start + length;
		if(end>=dailydata.size()) end = dailydata.size()-1;
		
		double closeprice = dailydata.get(start).getClose();
		double highprice = 0.0;
		for(int i=start;i<end;++i)
		{
			if(dailydata.get(i).getHigh()>highprice)
				highprice = dailydata.get(i).getHigh();
		}
		return (highprice-closeprice)/closeprice;
	}

	private static MacdScore GetMACDall(ArrayList<StockDaily> dailydata,
			int samplestart) {
		MacdScore macd = new MacdScore(dailydata.get(samplestart));
		return macd;
	}

	private static int GetVupdownCount(ArrayList<StockDaily> dailydata,
			int samplestart, int length) {
		// TODO Auto-generated method stub
		int end = samplestart + length - 1;
		if(end>=dailydata.size()) end = dailydata.size()-1;
		int rtCnt=0;
		for(int i=samplestart;i<end-1;++i)
		{
			StockDaily today = dailydata.get(i);
			StockDaily yesterday = dailydata.get(i+1);
			
			if(today.getVolume()/yesterday.getVolume()>=1.8
			 || today.getVolume()/yesterday.getVolume()<=0.6)
				rtCnt++;
		}
		return rtCnt;
	}

	private static int GetFakeGreenCount(ArrayList<StockDaily> dailydata,
			int samplestart, int length) {
		int end = samplestart + length - 1;
		if(end>=dailydata.size()) end = dailydata.size()-1;
		int rtCnt =0 ;
		for(int i=samplestart;i<end;++i)
		{
			//假阳真阴判断条件
			if(dailydata.get(i).getClose()>dailydata.get(i+1).getClose()-0.01
				&& dailydata.get(i).getOpen()>dailydata.get(i).getClose()
				)  
			{
				rtCnt++;
			}
			
		}
		return rtCnt;
	}

	//计算平均换手率
	private static double CalcuTurnover(ArrayList<StockDaily> dailydata,
			int samplestart, int length) {
		double totalturnover=0.0;
		for(int i=samplestart;i<samplestart+length;++i)
		{
			totalturnover += dailydata.get(i).getTurnover();
		}
		return totalturnover/length;
	}

	//计算涨幅
	private static double CalcuRaise(ArrayList<StockDaily> dailydata,
			int start, int length) {
		// TODO Auto-generated method stub
		int end = start + length;
		if(end>=dailydata.size()) end = dailydata.size()-1;
		
		double closeprice = dailydata.get(start).getClose();
		double openprice = dailydata.get(end).getClose();
		
		return (closeprice-openprice)/openprice;
	}

	//得到当前样本，不知道后面的结果
	public static ArrayList<Sample> makeShortSample(MapExpert me) {
		ArrayList<Sample> rtlist = new ArrayList<Sample>();
		for(int i=0;i<me.getSymbolList().size();++i)
		{
			String symbol = me.getSymbolList().get(i);
			
			ArrayList<StockDaily> dailydata = 
					me.getAlldata().get(symbol);
			if(dailydata==null ||me.getFinancedata().get(symbol)==null) continue;
			
			
			Float totalV= null;
			if(me.getFinancedata().get(symbol).getUnlimit_shares()==null)
				totalV=0f;
			else
				totalV=me.getFinancedata().get(symbol).getUnlimit_shares();
		
			Sample sample=Make(symbol,dailydata,0,C.Samplesize,0,totalV);
			rtlist.add(sample);
		}
		return rtlist;
	}

}
