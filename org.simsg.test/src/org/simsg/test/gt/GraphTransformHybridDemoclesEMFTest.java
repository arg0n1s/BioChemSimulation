package org.simsg.test.gt;

import org.simsg.core.persistence.PersistenceManagerEnum;
import org.simsg.core.pm.match.PatternMatchingEngineEnum;
import org.simsg.core.pmc.PatternMatchingControllerEnum;

public class GraphTransformHybridDemoclesEMFTest extends GraphTransformTest {

	@Override
	protected void setEngineType() {
		engineType = PatternMatchingEngineEnum.DemoclesEngine;

	}

	@Override
	protected void setPMCType() {
		pmcType = PatternMatchingControllerEnum.HybridPMC;

	}

	@Override
	protected void setPersistenceType() {
		persistenceType = PersistenceManagerEnum.SimplePersistence;
		
	}

}
