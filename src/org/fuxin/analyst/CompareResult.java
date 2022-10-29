package org.fuxin.analyst;

public class CompareResult  {
	public Sample sample;
	public SampleResult standard;
	public int result;
	@Override
	public String toString() {
		return "CompareResult [sample=" + sample + ", standard=" + standard
				+ ", result=" + result + "]";
	}

}
