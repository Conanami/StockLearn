package org.fuxin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fuxin.analyst.Sample;
import org.fuxin.analyst.SampleResult;
import org.fuxin.macd.MacdScore;
import org.fuxin.vo.StockDaily;

public class Dba {
	private static Connection conn = null;
    private static Statement st = null;
    private static PreparedStatement pstmt = null;
	public static void writeToDb(ArrayList<SampleResult> srlist, double winrate, Double raiserate, Integer meetcnt) {
		ArrayList<String> sqllist = new ArrayList<String>();
		for(int i=0;i<srlist.size();++i)
		{
			SampleResult sr = srlist.get(i);
			if(sr.getWinrate()>=winrate 
					&& sr.getRaiserate()>=raiserate
					&& sr.getMeetcnt()>=meetcnt )
			{
				String sql="insert into sample "
					+ sr.insertString();
				sqllist.add(sql);
			}
		}
		
		try {
			executebatch(sqllist);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void executebatch(ArrayList<String> sqllist) throws ClassNotFoundException {
		
	    try {
	        TryConnection();
	    	
	        for(int i=0;i<sqllist.size();++i)
	        {
	        	st.execute(sqllist.get(i));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally{
	        try {
	            if(conn!=null)conn.close();
	            if(st!=null)st.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
	}

	private static void TryConnection() throws ClassNotFoundException {
		// TODO Auto-generated method stub
        try {
        	Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/stock", "root", "root");
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void execute(String sql) {
		 try {
		        TryConnection();
		        st.execute(sql);
		    } catch(ClassNotFoundException e){
		        e.printStackTrace();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally{
		        try {
		            if(conn!=null)conn.close();
		            if(st!=null)st.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
	}

	public static List<SampleResult> getStandard() throws ClassNotFoundException {
		 
		try {
		        TryConnection();
		        List<SampleResult> rtlist = new ArrayList<SampleResult>();
				String sql = "SELECT * "
						+ " FROM sample ";
				
				pstmt = conn.prepareStatement(sql);
				
				
				ResultSet rs = pstmt.executeQuery();
			
				while(rs.next())
				{
					Sample sample = new Sample();
					sample.setSymbol(rs.getString("symbol"));
					sample.setStartdate(rs.getDate("startdate"));
					sample.setEnddate(rs.getDate("enddate"));
					sample.setRaise5d(rs.getDouble("raise5d"));
					sample.setRaise10d(rs.getDouble("raise10d"));
					sample.setRaise20d(rs.getDouble("raise20d"));
					sample.setRaise60d(rs.getDouble("raise60d"));
					sample.setRaise120d(rs.getDouble("raise120d"));
					sample.setTurnover5d(rs.getDouble("turnover5d"));
					sample.setTurnover10d(rs.getDouble("turnover10d"));
					sample.setTurnover20d(rs.getDouble("turnover20d"));
					sample.setTurnover60d(rs.getDouble("turnover60d"));
					sample.setTurnover120d(rs.getDouble("turnover120d"));
					sample.setFakegreencnt(rs.getInt("fakegreencnt"));
					sample.setVupdowncnt(rs.getInt("vupdowncnt"));
					
					sample.setCloseprice(rs.getDouble("closeprice"));
					sample.setHalfcost(rs.getDouble("halfcost"));
					sample.setFullcost(rs.getDouble("fullcost"));
					
					sample.setObvdiff(rs.getDouble("OBVdiff"));
					sample.setObvraise(rs.getDouble("OBVraise"));
					sample.setMaobvraise(rs.getDouble("MAOBVraise"));
					
					sample.setMacdscore(new MacdScore(rs.getString("symbol"),
													rs.getDate("enddate"),
													rs.getDouble("DIFF"),
													rs.getDouble("DEA"),
													rs.getDouble("MACD")));
					
					SampleResult result = new SampleResult(sample);
					result.setRaiserate(rs.getDouble("raiserate"));
					result.setBestrate(rs.getDouble("bestrate"));
					result.setWorstrate(rs.getDouble("worstrate"));
					result.setWinrate(rs.getDouble("winrate"));
					result.setMeetcnt(rs.getInt("meetcnt"));
					
					rtlist.add(result);
				}
				
					pstmt.close();
					return rtlist;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return null;
				
		 		 
	}
	

}
