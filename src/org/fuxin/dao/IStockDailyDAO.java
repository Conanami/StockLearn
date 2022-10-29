package org.fuxin.dao;

import org.fuxin.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface IStockDailyDAO {
	public List<StockDaily> findAll(String symbol, Date startdate, Date enddate) throws Exception;
	public List<String> findSymbol(String keyWord) throws Exception;
	public List<String> findStockSymbol(String keyWord) throws Exception;
	
	public ArrayList<String> findStockSymbol(String str, boolean mainban, boolean zxban, boolean cyban) throws Exception;
	
	public ArrayList<Date> findSymbolOldday(String symbol, Integer inteval) throws Exception;
	
	public ArrayList<StockDaily> findAll(String symbol) throws Exception;
	
	
	public HashMap<String, ArrayList<StockDaily>> findRecentinMap(
			ArrayList<String> symbolList, HashMap<String, FinanceData> financedata) throws Exception;
	//返回数据库最近的数据，放到内存中
	public HashMap<String, ArrayList<StockDaily>> findAllinMap(
			ArrayList<String> symbolList, HashMap<String, FinanceData> financedata) throws Exception;
	//返回数据库中所有的数据，放到内存中
	

	
	//按照代码列表，要选股的基准日，把数据返回到内存中
	public HashMap<String, ArrayList<StockDaily>> findRecentinMap(
			ArrayList<String> symbolList, Date selectday, HashMap<String, FinanceData> financedata)
					throws Exception;
	
	//按照代码列表，把财务数据返回内存中
	public HashMap<String, FinanceData> findFinanceinMap(
			ArrayList<String> symbolList) throws Exception;

	
	//取得沪市大盘数据
	public ArrayList<StockDaily> findIndexData(String symbol) throws Exception;
	
	
}

