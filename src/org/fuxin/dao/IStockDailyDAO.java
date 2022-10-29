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
	//�������ݿ���������ݣ��ŵ��ڴ���
	public HashMap<String, ArrayList<StockDaily>> findAllinMap(
			ArrayList<String> symbolList, HashMap<String, FinanceData> financedata) throws Exception;
	//�������ݿ������е����ݣ��ŵ��ڴ���
	

	
	//���մ����б�Ҫѡ�ɵĻ�׼�գ������ݷ��ص��ڴ���
	public HashMap<String, ArrayList<StockDaily>> findRecentinMap(
			ArrayList<String> symbolList, Date selectday, HashMap<String, FinanceData> financedata)
					throws Exception;
	
	//���մ����б��Ѳ������ݷ����ڴ���
	public HashMap<String, FinanceData> findFinanceinMap(
			ArrayList<String> symbolList) throws Exception;

	
	//ȡ�û��д�������
	public ArrayList<StockDaily> findIndexData(String symbol) throws Exception;
	
	
}

