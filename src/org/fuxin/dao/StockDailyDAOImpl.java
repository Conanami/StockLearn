package org.fuxin.dao;


import org.fuxin.vo.FinanceData;
import org.fuxin.vo.StockDaily;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class StockDailyDAOImpl implements IStockDailyDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	public StockDailyDAOImpl(Connection conn)
	{
		this.conn = conn;
	}
	
	public List<StockDaily> findAll(String symbol,Date startdate,Date enddate) throws Exception
	{
		List<StockDaily> rtlist = new ArrayList<StockDaily>();
		String sql = "SELECT symbol,date,open,low,high,close,"
				+ "volume,amount,name FROM daily_par "
				+ "WHERE symbol=? and date>=>? and date<=?";
		
		this.pstmt = this.conn.prepareStatement(sql);
		
		this.pstmt.setString(1, symbol);
		this.pstmt.setDate(2, new java.sql.Date(startdate.getTime()));
		this.pstmt.setDate(3, new java.sql.Date(startdate.getTime()));
		
		ResultSet rs = this.pstmt.executeQuery();
		StockDaily sd = null;
		while(rs.next())
		{
			sd = new StockDaily();
			sd.setSymbol(rs.getString(1));
			sd.setDate(rs.getDate(2));
			sd.setOpen(rs.getFloat(3));
			sd.setLow(rs.getFloat(4));
			sd.setHigh(rs.getFloat(5));
			sd.setClose(rs.getFloat(6));
			sd.setVolume(rs.getLong(7));
			sd.setAmount(rs.getLong(8));
			sd.setName(rs.getString(9));
			rtlist.add(sd);
		}
		
		this.pstmt.close();
		return rtlist;
	}

	@Override
	public List<String> findSymbol(String keyWord) throws Exception {
		// TODO Auto-generated method stub
		List<String> rtlist = new ArrayList<String>();
		String sql = "SELECT distinct(symbol) "
				+ " FROM daily_par "
				+ "WHERE symbol like ?";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, keyWord);
		ResultSet rs = this.pstmt.executeQuery();
		String rtsymbol = null;
		while(rs.next())
		{
			rtsymbol=rs.getString(1);
			rtlist.add(rtsymbol);
		}
		this.pstmt.close();
		return rtlist;
	}

	
	public List<String> findStockSymbol(String keyWord) throws Exception {
		// TODO Auto-generated method stub
		List<String> rtlist = new ArrayList<String>();
		String sql = "SELECT distinct(symbol) "
				+ " FROM daily_par "
				+ "WHERE symbol LIKE ? AND (symbol like 'SZ00%' OR "
				+ "symbol LIKE 'SH60%' OR symbol like 'SZ30%')";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, keyWord);
		ResultSet rs = this.pstmt.executeQuery();
		String rtsymbol = null;
		while(rs.next())
		{
			rtsymbol=rs.getString(1);
			rtlist.add(rtsymbol);
		}
		this.pstmt.close();
		return rtlist;
	}

	
	
	/***
	 * b�Ƿ������С��
	 * c�Ƿ������ҵ��
	 */
	@Override
	public ArrayList<String> findStockSymbol(String keyWord, boolean mainban, boolean zxb ,boolean cyb)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> rtlist = new ArrayList<String>();
		
		String sql = "SELECT distinct(symbol) "
				+ " FROM daily_par "
				+ "WHERE symbol LIKE ? AND ( 1=0 ";
		
		if(mainban)
		{
			sql = sql + " OR symbol like 'SZ000%' OR "
				+ "symbol LIKE 'SH60%' ";
		}
		if(zxb)
		{
			sql = sql + " OR symbol like 'SZ002%' ";
		}
		
		if(cyb)
		{
			sql = sql +  " OR symbol like 'SZ30%' ";
		}
		
		sql = sql +")";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, keyWord);
		ResultSet rs = this.pstmt.executeQuery();
		String rtsymbol = null;
		while(rs.next())
		{
			rtsymbol=rs.getString(1);
			rtlist.add(rtsymbol);
		}
		this.pstmt.close();
		return rtlist;
	}

	public ArrayList<Date> findSymbolOldday(String symbol, Integer interval)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Date> rtdaylist = new ArrayList<Date>();
		String sql = "SELECT date "
				+ " FROM daily_par "
				+ "WHERE symbol=? order by date desc";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, symbol);
		ResultSet rs = this.pstmt.executeQuery();
		int cnt = interval/2 + new Random().nextInt(interval);
		int tmp = 0;
		while(rs.next())
		{
			tmp++;
			if(tmp==cnt)
			{
				rtdaylist.add(rs.getDate(1));
				tmp=0;
				cnt = (int) (interval/1.3 + new Random().nextInt(interval));
			}
		}
		this.pstmt.close();
		return rtdaylist;
	}

	@Override
	public ArrayList<StockDaily> findAll(String symbol) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<StockDaily> rtlist = new ArrayList<StockDaily>();
		String sql = "SELECT symbol,date,open,low,high,close,"
				+ "volume,amount,name FROM daily_par "
				+ "WHERE symbol=? order by date desc";
		
		this.pstmt = this.conn.prepareStatement(sql);
		
		this.pstmt.setString(1, symbol);
				
		ResultSet rs = this.pstmt.executeQuery();
		StockDaily sd = null;
		while(rs.next())
		{
			sd = new StockDaily();
			sd.setSymbol(rs.getString(1));
			sd.setDate(rs.getDate(2));
			sd.setOpen(rs.getFloat(3));
			sd.setLow(rs.getFloat(4));
			sd.setHigh(rs.getFloat(5));
			sd.setClose(rs.getFloat(6));
			sd.setVolume(rs.getLong(7));
			sd.setAmount(rs.getLong(8));
			sd.setName(rs.getString(9));
			rtlist.add(sd);
		}
		
		this.pstmt.close();
		return rtlist;
	}

	@Override
	public HashMap<String, ArrayList<StockDaily>> findAllinMap(
			ArrayList<String> symbolList, HashMap<String, FinanceData> financedata) throws Exception {
		HashMap<String,ArrayList<StockDaily>> rtmap = new HashMap<String,ArrayList<StockDaily>>(2600);
		int cnt = 0;
		for(String symbol:symbolList)
		{
			Float unlimit_shares = financedata.get(symbol).getUnlimit_shares();
			cnt++;
			System.out.println("MapExpert:"+symbol);
			ArrayList<StockDaily> rtlist = new ArrayList<StockDaily>();
			String sql = "SELECT symbol,date,open,low,high,close,"
				+ "volume,amount,name FROM daily_par "
				+ "WHERE symbol=? order by date desc";
		
			this.pstmt = this.conn.prepareStatement(sql);
		
			this.pstmt.setString(1, symbol);
				
			ResultSet rs = this.pstmt.executeQuery();
			StockDaily sd = null;
			while(rs.next())
			{
				sd = new StockDaily();
				sd.setSymbol(rs.getString(1));
				sd.setDate(rs.getDate(2));
				sd.setOpen(rs.getFloat(3));
				sd.setLow(rs.getFloat(4));
				sd.setHigh(rs.getFloat(5));
				sd.setClose(rs.getFloat(6));
				sd.setVolume(rs.getLong(7));
				sd.setAmount(rs.getLong(8));
				sd.setName(rs.getString(9));
				sd.setTurnover((float)rs.getLong(7)/unlimit_shares);
				rtlist.add(sd);
			}
		
			this.pstmt.close();
			rtmap.put(symbol,rtlist);
			if(cnt%200==0) System.gc();
		}
		return rtmap;
	}


	

	@Override
	public HashMap<String, ArrayList<StockDaily>> findRecentinMap(
			ArrayList<String> symbolList,HashMap<String, FinanceData> financedata) throws Exception {
		HashMap<String,ArrayList<StockDaily>> rtmap = new HashMap<String,ArrayList<StockDaily>>(2600);
		
		int cnt = 0;
		for(String symbol:symbolList)
		{
			Float unlimit_shares = financedata.get(symbol).getUnlimit_shares();
			cnt ++;
			System.out.println("MapExpert:"+symbol);
			ArrayList<StockDaily> rtlist = new ArrayList<StockDaily>();
			String sql = "SELECT symbol,date,open,low,high,close,"
				+ "volume,amount,name FROM daily_par "
				+ "WHERE symbol=? order by date desc limit 600";
		
			this.pstmt = this.conn.prepareStatement(sql);
		
			this.pstmt.setString(1, symbol);
				
			ResultSet rs = this.pstmt.executeQuery();
			StockDaily sd = null;
			while(rs.next())
			{
				sd = new StockDaily();
				sd.setSymbol(rs.getString(1));
				sd.setDate(rs.getDate(2));
				sd.setOpen(rs.getFloat(3));
				sd.setLow(rs.getFloat(4));
				sd.setHigh(rs.getFloat(5));
				sd.setClose(rs.getFloat(6));
				sd.setVolume(rs.getLong(7));
				sd.setAmount(rs.getLong(8));
				sd.setName(rs.getString(9));
				sd.setTurnover((float)rs.getLong(7)/unlimit_shares);
				rtlist.add(sd);
			}
		
			this.pstmt.close();
			rtmap.put(symbol,rtlist);
			if(cnt%200==0)
				System.gc();
		}
		return rtmap;
	}

	
	@Override
	public HashMap<String, ArrayList<StockDaily>> findRecentinMap(
			ArrayList<String> symbolList, Date selectday,HashMap<String, FinanceData> financedata) throws Exception {
		HashMap<String,ArrayList<StockDaily>> rtmap = new HashMap<String,ArrayList<StockDaily>>();
		int cnt = 0;
		for(String symbol:symbolList)
		{
			
			Float unlimit_shares = null;
			
			if(financedata.get(symbol)==null) unlimit_shares = -10000f;
			else 
				unlimit_shares = financedata.get(symbol).getUnlimit_shares();
			cnt ++;
			System.out.println("MapExpert:"+symbol);
			ArrayList<StockDaily> rtlist = new ArrayList<StockDaily>();
			String sql = "SELECT symbol,date,open,low,high,close,"
				+ "volume,amount,name FROM daily_par "
				+ "WHERE symbol=? and date<=? order by date desc limit 600";
		
			this.pstmt = this.conn.prepareStatement(sql);
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.pstmt.setString(1, symbol);
			this.pstmt.setString(2, sdf.format(selectday));
				
			ResultSet rs = this.pstmt.executeQuery();
			StockDaily sd = null;
			while(rs.next())
			{
				sd = new StockDaily();
				sd.setSymbol(rs.getString(1));
				sd.setDate(rs.getDate(2));
				sd.setOpen(rs.getFloat(3));
				sd.setLow(rs.getFloat(4));
				sd.setHigh(rs.getFloat(5));
				sd.setClose(rs.getFloat(6));
				sd.setVolume(rs.getLong(7));
				sd.setAmount(rs.getLong(8));
				sd.setName(rs.getString(9));
				sd.setTurnover((float)rs.getLong(7)/unlimit_shares);
				rtlist.add(sd);
			}
		
			this.pstmt.close();
			if(rtlist.size()>200)
				rtmap.put(symbol,rtlist);
			if(cnt%200==0)
				System.gc();
		}
		return rtmap;
	}

	//�Ѳ������װ���ڴ�
	@Override
	public HashMap<String, FinanceData> findFinanceinMap(
			ArrayList<String> symbolList) throws Exception {
		HashMap<String,FinanceData> rtmap = new HashMap<String,FinanceData>();
		for(String symbol:symbolList)
		{
			System.out.println("Finance:"+symbol);
			FinanceData fd = new FinanceData();
			String sql = "SELECT symbol,"
					+ "reportdate,"
					+ "earning_ps,"
					+ "net_assets_ps,"
					+ "netprofit_rose,"
					+ "revenue_rose, "
					+ "unlimit_shares "
					+ " FROM t_finance "
					+ " WHERE symbol=? order by reportdate desc ";
		
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, symbol);
			
				
			ResultSet rs = this.pstmt.executeQuery();
			
			if(rs.next())
			{
				fd.setSymbol(rs.getString(1));
				fd.setReportdate(rs.getDate(2));
				fd.setEarning_ps(rs.getFloat(3));
				fd.setNetassets_ps(rs.getFloat(4));
				fd.setNetprofit_rose(rs.getFloat(5));
				fd.setRevenue_rose(rs.getFloat(6));
				fd.setUnlimit_shares(rs.getFloat(7));
			}
		
			this.pstmt.close();
			
			if(fd.symbol!=null)
			{
				rtmap.put(symbol,fd);
				System.out.println(fd);
			}
		}
		return rtmap;
	}



	public ArrayList<StockDaily> findIndexData(String symbol) throws Exception
	{
		ArrayList<StockDaily> rtlist = new ArrayList<StockDaily>();
		String sql = "SELECT symbol,date,open,low,high,close,"
				+ "volume,amount,name FROM daily_par "
				+ "WHERE symbol=? order by date desc limit 600";
		
		this.pstmt = this.conn.prepareStatement(sql);
		
		this.pstmt.setString(1, symbol);
				
		ResultSet rs = this.pstmt.executeQuery();
		StockDaily sd = null;
		while(rs.next())
		{
			sd = new StockDaily();
			sd.setSymbol(rs.getString(1));
			sd.setDate(rs.getDate(2));
			sd.setOpen(rs.getFloat(3));
			sd.setLow(rs.getFloat(4));
			sd.setHigh(rs.getFloat(5));
			sd.setClose(rs.getFloat(6));
			sd.setVolume(rs.getLong(7));
			sd.setAmount(rs.getLong(8));
			sd.setName(rs.getString(9));
			rtlist.add(sd);
		}
		
		this.pstmt.close();
		return rtlist;
	}

	
			
	
}

	
