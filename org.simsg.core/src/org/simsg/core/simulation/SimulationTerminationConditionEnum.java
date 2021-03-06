package org.simsg.core.simulation;

public enum SimulationTerminationConditionEnum {
	
	SimpleCondition("SimpleCondition"), ComplexCondition("ComplexCondition");
	
	private String name;
	
	private SimulationTerminationConditionEnum(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
