package org.fuxin.analyst;

import java.util.ArrayList;

public class NewFilter {

	public static ArrayList<CompareResult> filterByTimes(
			ArrayList<CompareResult> crlist, int cnt) {
		// TODO Auto-generated method stub
		int count = 1;
		ArrayList<CompareResult> rtlist = new ArrayList<CompareResult>();
		String oldsymbol="";
		String newsymbol="";
		for(int i=0;i<crlist.size();++i)
		{
			if(i==0)
				oldsymbol="";
			else
				oldsymbol=newsymbol;
			
			newsymbol=crlist.get(i).sample.getSymbol();
			if(oldsymbol.equals(newsymbol))
			{
				count++;
			}
			else
				count=1;
			
			if(count>=cnt)
				rtlist.add(crlist.get(i));
		}
		return rtlist;
	}

}
