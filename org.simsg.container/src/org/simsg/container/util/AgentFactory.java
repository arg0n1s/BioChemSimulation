package org.simsg.container.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.simsg.container.Agent;

public class AgentFactory extends EObjectFactory<Agent, org.simsg.simsgl.simSGL.Agent>{
	
	public AgentFactory(EPackage ecorePackage, EClassRegistry<org.simsg.simsgl.simSGL.Agent> agentClassRegistry) {
		super(ecorePackage, agentClassRegistry);
	}
	
	@Override
	public Agent createObject(String typeClass) {
		Agent agent = (Agent)ecoreFactory.create(classRegistry.getRegisteredClass(typeClass));
		agent.setID(System.nanoTime());
		return agent;
	}

	@Override
	public Agent createObject(EClass typeClass) {
		Agent agent = (Agent)ecoreFactory.create(typeClass);
		agent.setID(System.nanoTime());
		return agent;
	}
}
