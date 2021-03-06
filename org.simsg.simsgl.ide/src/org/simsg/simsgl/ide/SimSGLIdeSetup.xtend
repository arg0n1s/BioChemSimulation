/*
 * generated by Xtext 2.16.0
 */
package org.simsg.simsgl.ide

import com.google.inject.Guice
import org.eclipse.xtext.util.Modules2
import org.simsg.simsgl.SimSGLRuntimeModule
import org.simsg.simsgl.SimSGLStandaloneSetup

/**
 * Initialization support for running Xtext languages as language servers.
 */
class SimSGLIdeSetup extends SimSGLStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new SimSGLRuntimeModule, new SimSGLIdeModule))
	}
	
}
