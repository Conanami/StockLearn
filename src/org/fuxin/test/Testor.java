package org.fuxin.test;

import org.fuxin.analyst.AutoLearn;
import org.fuxin.analyst.Stockselector;
import org.fuxin.memory.C;
import org.fuxin.memory.MapExpert;
import org.fuxin.util.ScopeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Testor {
    public static void main (String[] args) throws Exception
    {
        sop("Start loading...");
        long start = System.currentTimeMillis();
        //learn();
        select();
        //test();
        long end = System.currentTimeMillis();
        sop((end-start)+"ms");
    }

   
	private static void test() throws ParseException {
		// TODO Auto-generated method stub
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date endday = sdf.parse("2016-04-14");
        int scope = C.MA_ZX_CY;
        String queryStr = "%820";
        ArrayList<String> symbolList = ScopeUtil.getSymbol(queryStr, scope);
        MapExpert me = new MapExpert(symbolList,true,endday);
        System.out.println(me.getAlldata().get("SH600820").get(20));
	}

	private static void select() throws ParseException {
		// TODO Auto-generated method stub
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date endday = sdf.parse("2017-04-17");
        int scope = C.MA_ZX_CY;
        String queryStr = "%";
        ArrayList<String> symbolList = ScopeUtil.getSymbol(queryStr, scope);
        MapExpert me = new MapExpert(symbolList,true,endday);
        Stockselector.select(me);
  
	}

	private static void sop(String s) {
        System.out.println(s);
    }

    

    public static void learn() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date endday = sdf.parse("2016-03-28");
        int scope = C.MA_ZX_CY;																																																																																			
        String queryStr = "%";
        ArrayList<String> symbolList = ScopeUtil.getSymbol(queryStr, scope);
        MapExpert me = new MapExpert(symbolList,true,endday);
        AutoLearn.learn(me);   //自动学习
    }
}
