/*
 * generated by Xtext 2.12.0
 */
package biochemsimulation.reactionrules.validation

import biochemsimulation.reactionrules.reactionRules.Agent
import biochemsimulation.reactionrules.reactionRules.Observation
import biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage
import biochemsimulation.reactionrules.reactionRules.Rule
import biochemsimulation.reactionrules.reactionRules.Site
import biochemsimulation.reactionrules.reactionRules.Variable
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class ReactionRulesValidator extends AbstractReactionRulesValidator {
	
//	public static val INVALID_NAME = 'invalidName'
//
//	@Check
//	def checkGreetingStartsWithCapital(Greeting greeting) {
//		if (!Character.isUpperCase(greeting.name.charAt(0))) {
//			warning('Name should start with a capital', 
//					ReactionRulesPackage.Literals.GREETING__NAME,
//					INVALID_NAME)
//		}
//	}

	@Check
	def checkAgentIdUnique(Agent agent) {
		val rootElement = EcoreUtil2.getRootContainer(agent)
		var candidates = EcoreUtil2.getAllContentsOfType(rootElement, Agent);
		var c = 0
		for(candidate : candidates) {
			var current = candidate as Agent
			if(current.name.equals(agent.name)) {
				c++;
			}
			if(c>1) {
				error('Agent IDs must be unique.', null)
				c = 1;
			}
		}
				
	}
	
	@Check
	def checkVariableIdUnique(Variable variable) {
		val rootElement = EcoreUtil2.getRootContainer(variable)
		var candidates = EcoreUtil2.getAllContentsOfType(rootElement, Variable);
		var c = 0
		for(candidate : candidates) {
			var current = candidate as Variable
			if(current.name.equals(variable.name)) {
				c++;
			}
			if(c>1) {
				error('Variable IDs must be unique.', null)
				c = 1;
			}
		}
				
	}
	
	@Check
	def checkObservationIdUnique(Observation observation) {
		val rootElement = EcoreUtil2.getRootContainer(observation)
		var candidates = EcoreUtil2.getAllContentsOfType(rootElement, Observation);
		var c = 0
		for(candidate : candidates) {
			var current = candidate as Observation
			if(current.name.equals(observation.name)) {
				c++;
			}
			if(c>1) {
				error('Observation IDs must be unique.', null)
				c = 1;
			}
		}
				
	}
	
	@Check
	def checkRuleIdUnique(Rule rule) {
		val rootElement = EcoreUtil2.getRootContainer(rule)
		var candidates = EcoreUtil2.getAllContentsOfType(rootElement, Rule);
		var c = 0
		for(candidate : candidates) {
			var current = candidate as Rule
			if(current.name.equals(rule.name)) {
				c++;
			}
			if(c>1) {
				error('Rule IDs must be unique.', null)
				c = 1;
			}
		}
				
	}
	
	@Check
	def checkSiteIdUnique(Agent agent) {
		var candidates = EcoreUtil2.getAllContentsOfType(agent, Site);
		var c = 0
		for(candidate : candidates) {
			var current = candidate as Site
			var name = current.name
			
			for(candidate2 : candidates) {
				var current2 = candidate2 as Site
				var name2 = current2.name
				if(name.equals(name2)) {
					c++;
				}
				if(c>1) {
					error('Site IDs within Agents must be unique.', ReactionRulesPackage.Literals.AGENT__SITES)
					c = 1;
				}
			}
			c = 0
			
		}
				
	}
	
	@Check
	def checkStateIdUnique(Site site) {
		var candidates =  EcoreUtil2.getAllContentsOfType(site, biochemsimulation.reactionrules.reactionRules.State);
			var c = 0
			
			for(candidate : candidates) {
				var current = candidate as biochemsimulation.reactionrules.reactionRules.State
				var name = current.name
				
				for(candidate2 : candidates) {
					var current2 = candidate2 as biochemsimulation.reactionrules.reactionRules.State
					var name2 = current2.name
					if(name.equals(name2)) {
						c++;
					}
					if(c>1) {
						error('State IDs within Sites must be unique.', ReactionRulesPackage.Literals.SITE__STATES)
						c = 1;
					}
				}
				c = 0
			
			}
				
	}
}
