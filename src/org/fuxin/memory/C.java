package org.fuxin.memory;

import java.util.ArrayList;

import org.fuxin.analyst.CompareResult;
import org.fuxin.analyst.Sample;

public class C {
	public final static Integer SHSZ = 0;
	public final static Integer ZXB = 1;
	public final static Integer CYB = 2;
	public final static Integer MA_ZX = 3;
	public final static Integer MA_CY = 4;
	public final static Integer ZX_CY = 5;
	public final static Integer MA_ZX_CY = 6;
	
	public static final Integer Samplesize= 120;
	public static final Integer Resultsize = 5;
	
	public static final ArrayList<Sample> allsample=new ArrayList<Sample>();
	public static final ArrayList<CompareResult> allcr = new ArrayList<CompareResult>();
	
	
}
