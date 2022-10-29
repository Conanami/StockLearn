package org.fuxin.analyst;

import java.util.ArrayList;

public class Statistor {

	/***
	 * 统计每个标准的匹配个数，胜率，等等
	 * @param allsample
	 * @param allcr
	 * @return
	 */
	public static ArrayList<SampleResult> Calcu(ArrayList<Sample> allStandard,
			ArrayList<CompareResult> allcr) {
		ArrayList<SampleResult> rtlist = new ArrayList<SampleResult>();
		for(int i=0;i<allStandard.size();++i)
		{
			Sample now=allStandard.get(i);
			SampleResult nowsr=new SampleResult(now);
			for(int j=0;j<allcr.size();++j)
			{
				CompareResult nowcr = allcr.get(j);
				if(nowcr.standard.getSample().equals(now))
				{
					Double rr=nowcr.sample.getRaiserate();
					nowsr.update(rr,0.005);
				}
			}
			rtlist.add(nowsr);
		}
		return rtlist;
		
	}

}
