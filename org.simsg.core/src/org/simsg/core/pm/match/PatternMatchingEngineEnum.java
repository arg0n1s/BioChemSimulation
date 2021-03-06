package org.simsg.core.pm.match;

public enum PatternMatchingEngineEnum {
	ViatraEngine("ViatraEngine"), DemoclesEngine("DemoclesEngine"), ParallelViatraEngine("ParallelViatraEngine");
	
	private String name;
	private PatternMatchingEngineEnum(String name) {
		this.name = name;
	}
	
	 @Override
	 public String toString() {
		 return name;
	 }
}
