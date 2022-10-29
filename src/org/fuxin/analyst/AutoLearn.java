package org.fuxin.analyst;

import java.util.ArrayList;

import org.fuxin.dao.Dba;
import org.fuxin.memory.C;
import org.fuxin.memory.MapExpert;
import org.fuxin.util.FuOutput;

/**
 * Created by Administrator on 2016/4/5.
 */
public class AutoLearn {


    public static void learn(MapExpert me) {
        //找到一个周涨幅15%以上的样本
    	double threshold=0.25;
        for(int i=0;i<me.getSymbolList().size();++i) 
        {
            String symbol = me.getSymbolList().get(i);
            //String symbol ="SH600820";
            if(me.getFinancedata().get(symbol)!=null)
            {
            	ArrayList<Sample> samplelist = SampleMaker.ScanAll(me,symbol,threshold);
                C.allsample.addAll(samplelist);
            	for(int j=0;j<me.getSymbolList().size();++j)
                {
                	String nowsymbol=me.getSymbolList().get(j);
                	if(me.getFinancedata().get(nowsymbol)!=null)
                	{
                		FuOutput.sop("now sample:"+symbol+" compare to "+nowsymbol);
                		ArrayList<CompareResult> crlist=Comparor.ScanAll(me,nowsymbol,samplelist);
                		C.allcr.addAll(crlist);
                	}
                }
            }
        }
        
        ArrayList<SampleResult> srlist=Statistor.Calcu(C.allsample,C.allcr);
        FuOutput.writeToFile(srlist, "sr");
        FuOutput.writeToFile(C.allsample, "spl");
        
        Dba.execute("delete from sample");
        Double winrate = 0.7;
        Double raiserate = 0.01;
        Integer meetcnt = 20;
        Dba.writeToDb(srlist,winrate,raiserate,meetcnt);
        
        FuOutput.writeToFile(C.allcr,"cr");
        //提取特征值
        //按照这个样本的特征值比对所有股票
        //得到该样本的成功率，盈利率
        //记录并保存该样本
        //继续查找下一个样本
        //直到全部找完
        //把成功率的最高的样本取出，做成样本集
    }
}
