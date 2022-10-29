package org.fuxin.analyst;

import java.util.ArrayList;

import org.fuxin.memory.MapExpert;
import org.fuxin.vo.StockDaily;

public class Comparor {

	public static ArrayList<CompareResult> ScanAll(MapExpert me, String symbol,
			ArrayList<Sample> samplelist) {
		// TODO Auto-generated method stub
		ArrayList<CompareResult> rtlist = new ArrayList<CompareResult>();
		
		double threshold=-2.0;
		ArrayList<Sample> testlist = SampleMaker.ScanAll(me,symbol,threshold);
		for(int i=0;i<testlist.size();++i)
		{
			for(int j=0;j<samplelist.size();++j)
			{
				//两个样本做匹配的结果
				SampleResult sr= new SampleResult(samplelist.get(j));
				if(testlist.get(i).getSymbol().equals(sr.getSample().getSymbol())!=true)
				{
					if(CheckDate(testlist.get(i),sr)==true)
					{
						CompareResult cr = Comparor.match(testlist.get(i),sr);
						if(MeetCon(cr))
							rtlist.add(cr);
					}
				}
			}
		}
		return rtlist;
	}
	
	private static boolean CheckDate(Sample sample, SampleResult sr) {
		if(sample.getEnddate().after(sr.getSample().getEnddate()))
			return true;
		else
			return false;
	}

	//这个类是决定怎样的匹配结果记录下来
	private static boolean MeetCon(CompareResult cr) {
		// TODO Auto-generated method stub
		if(cr.result==1) return true;
		else
			return false;
	}

	private static CompareResult match(Sample sample, SampleResult sampleResult) {
		// TODO Auto-generated method stub
		CompareResult cr = new CompareResult();
		cr.sample=sample;
		cr.standard=sampleResult;
		
		Sample standard=cr.standard.getSample();
		if(
		Math.abs(sample.getRaise5d()-standard.getRaise5d())<=0.05
		&& Math.abs(sample.getRaise10d()-standard.getRaise10d())<=0.1
		&& Math.abs(sample.getRaise20d()-standard.getRaise20d())<=0.1
		&& Math.abs(sample.getRaise60d()-standard.getRaise60d())<=0.2
		&& Math.abs(sample.getRaise120d()-standard.getRaise120d())<=0.2
		&& Math.abs(sample.getHalfcost()-standard.getHalfcost())<=0.1
		&& Math.abs(sample.getFullcost()-standard.getFullcost())<=0.1
		&& CheckTurnover(sample,standard)
		&& CheckFakeGreen(sample,standard)
		&& CheckVupdown(sample,standard)
		&& CheckMacd(sample,standard)
		&& CheckCloseprice(sample,standard)
		&& CheckOBV(sample,standard)
		)
			cr.result=1;
		else
			cr.result=0;
		return cr;
		
	}

	
	private static boolean CheckOBV(Sample sample, Sample standard) {
		if( DiffRate(sample.getObvdiff(),standard.getObvdiff())<=0.3
		 //&& DiffRate(sample.getObvraise(),standard.getObvraise())<=0.3
		 //&& DiffRate(sample.getMaobvraise(),standard.getMaobvraise())<=0.3
		)
			return true;
		else
			return true;
	}

	private static double DiffRate(double value1, double value2) {
		// TODO Auto-generated method stub
		return Math.abs(value1-value2)/value2;
	}

	private static boolean CheckMacd(Sample sample, Sample standard) {
		if(Math.abs(sample.getMacdscore().MACD/sample.getCloseprice()
				-standard.getMacdscore().MACD/standard.getCloseprice())<0.02
		 &&
		 Math.abs(sample.getMacdscore().DIFF/sample.getCloseprice()
		 			-standard.getMacdscore().DIFF/standard.getCloseprice())<0.02
					
		)
			return true;
		else 
			return false;
	}
	
	private static boolean CheckCloseprice(Sample sample, Sample standard) {
		double deltaprice=Math.abs(sample.getCloseprice()-standard.getCloseprice());
		double deltarate = deltaprice/standard.getCloseprice();
		if(deltarate<0.2) return true;
		else return true;
	}
	private static boolean CheckVupdown(Sample sample, Sample standard) {
		if(Math.abs(sample.getVupdowncnt()-standard.getVupdowncnt())<=3)
			return true;
		else
			return false;
	}

	private static boolean CheckFakeGreen(Sample sample, Sample standard) {
		if(sample.getFakegreencnt()>=standard.getFakegreencnt()
		   && sample.getFakegreencnt()<=standard.getFakegreencnt()+2)
			return true;
		else
			return false;
	}

	private static boolean CheckTurnover(Sample sample, Sample standard) {
		// TODO Auto-generated method stub
		if( IsBigger(sample.getTurnover5d(),sample.getTurnover10d())
			==IsBigger(standard.getTurnover5d(),standard.getTurnover10d())
			&& IsBigger(sample.getTurnover10d(),sample.getTurnover20d())
			==IsBigger(standard.getTurnover10d(),standard.getTurnover20d())
			&& IsBigger(sample.getTurnover20d(),sample.getTurnover60d())
			==IsBigger(standard.getTurnover20d(),standard.getTurnover60d())
			&& IsBigger(sample.getTurnover60d(),sample.getTurnover120d())
			==IsBigger(standard.getTurnover60d(),standard.getTurnover120d())
			&& Math.abs(sample.getTurnover5d()/standard.getTurnover5d()-1)<0.3
		)
			return true;
		else
			return false;
	}

	//比较大小
	private static double IsBigger(double value1, double value2) {
		// TODO Auto-generated method stub
		if(value1>value2) return 1;
		else if(value1<value2) return -1;
		else
		return 0;
	}

	/***
	 * 对样本进行匹配
	 * @param tobeselect
	 * @param standardlist
	 * @return
	 */
	public static ArrayList<CompareResult> SelectAll(		
		ArrayList<Sample> tobeselect, ArrayList<SampleResult> standardlist) 
	
		{
		
		ArrayList<CompareResult> rtlist = new ArrayList<CompareResult>();
				
		for(int i=0;i<tobeselect.size();++i)
		{
			for(int j=0;j<standardlist.size();++j)
			{
				//两个样本做匹配的结果
				CompareResult cr = Comparor.match(tobeselect.get(i),standardlist.get(j));
				if(MeetCon(cr))
					rtlist.add(cr);
			}
		}
		return rtlist;
	}

}
