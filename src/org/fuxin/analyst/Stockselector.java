package org.fuxin.analyst;

import java.util.ArrayList;

import org.fuxin.dao.Dba;
import org.fuxin.memory.MapExpert;
import org.fuxin.util.FuOutput;

public class Stockselector {

	public static void select(MapExpert me) {
		// TODO Auto-generated method stub
		try {
			ArrayList<SampleResult> standardlist = (ArrayList<SampleResult>) Dba.getStandard();
			//鎶妋e閲岄潰姣忎釜閮藉仛鎴愭牱鏈紝鐒跺悗涓巗tandlist杩涜姣旇緝
			//绗﹀悎鏉′欢鐨勮褰曚笅鏉�
			ArrayList<Sample> tobeselect=SampleMaker.makeShortSample(me);
			ArrayList<CompareResult> crlist=Comparor.SelectAll(tobeselect,standardlist);
			//把出现多次的股票选出来
			ArrayList<CompareResult> filterlist = NewFilter.filterByTimes(crlist,2);
			
			FuOutput.writeToFile(filterlist, "select");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
