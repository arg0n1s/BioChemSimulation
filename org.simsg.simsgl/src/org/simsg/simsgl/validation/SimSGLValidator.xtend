/*
 * generated by Xtext 2.16.0
 */
package org.simsg.simsgl.validation

import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check
import java.util.HashSet
import org.simsg.simsgl.simSGL.Agent
import org.simsg.simsgl.simSGL.Variable
import org.simsg.simsgl.simSGL.ArithmeticVariable
import org.simsg.simsgl.simSGL.Initial
import org.simsg.simsgl.simSGL.ArithmeticValue
import org.simsg.simsgl.simSGL.NumericFromVariable
import org.simsg.simsgl.simSGL.NumericFromLiteral
import org.simsg.simsgl.simSGL.ValidAgentPattern
import org.simsg.simsgl.simSGL.SingleSitePattern
import org.simsg.simsgl.simSGL.BoundAnyLink
import org.simsg.simsgl.simSGL.WhatEver
import org.simsg.simsgl.simSGL.BoundAnyOfTypeLink
import org.simsg.simsgl.simSGL.Pattern
import org.simsg.simsgl.simSGL.PatternAssignment
import org.simsg.simsgl.simSGL.AssignFromPattern
import org.simsg.simsgl.simSGL.AssignFromVariable
import org.simsg.simsgl.simSGL.Observation
import org.simsg.simsgl.simSGL.Rule
import org.simsg.simsgl.simSGL.Site
import org.simsg.simsgl.simSGL.SimSGLPackage
import org.simsg.simsgl.simSGL.RuleBody
import org.simsg.simsgl.simSGL.NumericAssignment
import org.simsg.simsgl.simSGL.VoidAgentPattern
import org.simsg.simsgl.simSGL.MultiLinkSitePattern
import org.simsg.simsgl.simSGL.BoundLink
import org.simsg.simsgl.simSGL.IndexedFreeLink
import org.simsg.simsgl.simSGL.SitePattern

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class SimSGLValidator extends AbstractSimSGLValidator {
	
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
	def checkArithmeticVariableFormat(ArithmeticVariable aVar) {
		if(aVar.value.value.contains(" ")) {
			error('Arithmetic variables may not contain any whitespaces!', null)
			return false
		}
		return true
	}
	
	@Check
	def checkInitialIdUnique(Initial initial) {
		val rootElement = EcoreUtil2.getRootContainer(initial)
		var candidates = EcoreUtil2.getAllContentsOfType(rootElement, Initial);
		var c = 0
		for(candidate : candidates) {
			var current = candidate as Initial
			if(current.name.equals(initial.name)) {
				c++;
			}
			if(c>1) {
				error('Initial IDs must be unique.', null)
				c = 1;
			}
		}
				
	}
	
	@Check
	def checkInitialCountInteger(Initial initial) {
		val countVar = initial.count
		var arithVal = null as ArithmeticValue
		if(countVar instanceof NumericFromVariable) {
			val numVar = countVar as NumericFromVariable
			arithVal = numVar.valueVar.value
			
		}else {
			val numLit = countVar as NumericFromLiteral
			arithVal = numLit.value
		}
		
		if(!arithVal.value.matches("^(\\d)*$")) {
			error('Initial count variable must be of type unsigned integer.', null)
		}else {
			val num = Integer.valueOf(arithVal.value)
			if(num==0) {
				warning('Initial count variables equal to 0 will lead to zero instantiated agents.', null)
			}
		}
				
	}
	
	@Check
	def checkInitialIllegalLinkStates(Initial initial) {
		val pattern = patternFromPatternAssignment(initial.initialPattern)
		for(ap : pattern.agentPatterns) {
			if(ap instanceof ValidAgentPattern) {
				val vap = ap  as ValidAgentPattern
				for(sp : vap.sitePatterns.sitePatterns) {
					if(sp instanceof SingleSitePattern){
						val slsp = sp as SingleSitePattern
						val linkState = slsp.linkState.linkState
						if(linkState instanceof BoundAnyLink || linkState instanceof WhatEver || linkState instanceof BoundAnyOfTypeLink) {
							error('Illegal initial link state! A pattern may only be instantiated with link states of Type: FreeLink("free"), IndexedLink("INT")', null)
						}
					}else {
						//ToDo MultiLink validation
					}
					
				}
			}
			
		}
	}
	
	def Pattern patternFromPatternAssignment(PatternAssignment pa) {
		if(pa instanceof AssignFromPattern) {
			val afp = pa as AssignFromPattern
			return afp.pattern
		}else {
			val afv = pa as AssignFromVariable
			return afv.patternVar.pattern
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
					error('Site IDs within Agents must be unique.', SimSGLPackage.Literals.AGENT__SITES)
					c = 1;
				}
			}
			c = 0
			
		}
				
	}
	
	@Check
	def checkStateIdUnique(Site site) {
		var candidates =  EcoreUtil2.getAllContentsOfType(site, org.simsg.simsgl.simSGL.State);
			var c = 0
			
			for(candidate : candidates) {
				var current = candidate as org.simsg.simsgl.simSGL.State
				var name = current.name
				
				for(candidate2 : candidates) {
					var current2 = candidate2 as org.simsg.simsgl.simSGL.State
					var name2 = current2.name
					if(name.equals(name2)) {
						c++;
					}
					if(c>1) {
						error('State IDs within Sites must be unique.', SimSGLPackage.Literals.SITE__STATES)
						c = 1;
					}
				}
				c = 0
			
			}
				
	}
	
	
	@Check
	def checkRuleVariables(RuleBody ruleBody) {
		var op = ruleBody.operator
		var variables = ruleBody.variables.variables
		if(op.equals("<->")) {
			if(variables.size() != 2) {
				error('Bi-Directional rules must have two reaction rate variables.', SimSGLPackage.Literals.RULE_BODY__VARIABLES)
			}
		}else {
			if(variables.size() != 1) {
				error('Uni-Directional rules must have one reaction rate variable.', SimSGLPackage.Literals.RULE_BODY__VARIABLES)
			}
		}
		for(variable : variables) {
			var value = valueOfNumericAssignment(variable)
			var faulty = false;
			if(value.contains(" ")) {
				error('Arithmetic variables may not contain any white spaces!', SimSGLPackage.Literals.RULE_BODY__VARIABLES)
				faulty = true;
			}
			if(!value.matches("^(-)?(\\d)+(\\.)(\\d)+E(-|\\+)(\\d)+$") && !value.matches("^(-)?(\\d)*$") && !value.matches("^(-)?(\\d)+(\\.)(\\d)+$")) {
				error('Given expression uses an unknown number format.', SimSGLPackage.Literals.RULE_BODY__VARIABLES)
				faulty = true;
			}
			if(!faulty) {
				var numValue = Double.valueOf(value)
				if(numValue < 0) {
					error('Uni-Directional rules must have positive reaction rates.', SimSGLPackage.Literals.RULE_BODY__VARIABLES)
				}
				if(numValue == 0) {
					warning('Uni-Directional rules with rates equal to 0 will be inactive.', SimSGLPackage.Literals.RULE_BODY__VARIABLES)
				}
			}
			
		}		
	}
	
	@Check
	def checkRuleArgs(RuleBody ruleBody) {
		val lhs = patternFromPatternAssignment(ruleBody.lhs)
		val rhs = patternFromPatternAssignment(ruleBody.rhs)
		var equalNumOfArgs = true
		// check for equal number of args
		if(lhs.agentPatterns.size != rhs.agentPatterns.size) {
			error('Number of arguments on the left hand side of the rule, must match number of arguments on the right hand side.', 
				SimSGLPackage.Literals.RULE_BODY__LHS
			)
			error('Number of arguments on the right hand side of the rule, must match number of arguments on the left hand side.', 
				SimSGLPackage.Literals.RULE_BODY__RHS
			)
			equalNumOfArgs = false
		}
		// check whether two args with the same index specify a void type -> not allowed
		if(equalNumOfArgs){
			var idx = 0
			for(ap : lhs.agentPatterns) {
				if(ap instanceof VoidAgentPattern) {
					val ap2 = rhs.agentPatterns.get(idx)
					if(ap2 instanceof VoidAgentPattern) {
						error('Two arguments at the same index on lhs and rhs can not be of void type.', 
							SimSGLPackage.Literals.RULE_BODY__LHS
						)
						error('Two arguments at the same index on lhs and rhs can not be of void type.', 
							SimSGLPackage.Literals.RULE_BODY__RHS
						)
					}
				}else {
					// check consistency of agent types
					val ap_1 = ap as ValidAgentPattern
					val ap2 = rhs.agentPatterns.get(idx)
					if(!(ap2 instanceof VoidAgentPattern)) {
						val ap_2 = ap2 as ValidAgentPattern
						if(!ap_1.agent.name.equals(ap_2.agent.name)) {
							error('Two arguments at the same index on lhs and rhs must have the same agent type.', 
							SimSGLPackage.Literals.RULE_BODY__LHS
							)
							error('Two arguments at the same index on lhs and rhs must have the same agent type.', 
							SimSGLPackage.Literals.RULE_BODY__RHS
							)
						}
					}
					// check order and consistency of sites, link states and site states
					if(!(ap2 instanceof VoidAgentPattern)) {
						val ap_2 = ap2 as ValidAgentPattern
						if(ap_1.sitePatterns.sitePatterns.size != ap_2.sitePatterns.sitePatterns.size){
							error('Two arguments at the same index on lhs and rhs must have the same amount of sites.', 
							SimSGLPackage.Literals.RULE_BODY__LHS
							)
							error('Two arguments at the same index on lhs and rhs must have the same amount of sites.', 
							SimSGLPackage.Literals.RULE_BODY__RHS
							)
						}
						for(var i=0; i<ap_1.sitePatterns.sitePatterns.size; i++) {
							val sp_1 = ap_1.sitePatterns.sitePatterns.get(i);
							val sp_2 = ap_2.sitePatterns.sitePatterns.get(i);
							if(sp_1 instanceof SingleSitePattern && sp_2 instanceof SingleSitePattern) {
								val ssp_1 = sp_1 as SingleSitePattern;
								val ssp_2 = sp_2 as SingleSitePattern;
								
								if(ssp_1.site != ssp_2.site){
									error('Two arguments at the same index on lhs and rhs must have the same sites.', 
									SimSGLPackage.Literals.RULE_BODY__LHS
									)
									error('Two arguments at the same index on lhs and rhs must have the same sites.', 
									SimSGLPackage.Literals.RULE_BODY__RHS
									)
								}
								val st_1 = ssp_1.state;
								val st_2 = ssp_2.state;
								if(st_1 === null && st_2 !== null){
									error('If an argument on the rhs defines a state, the corresponding argument on the lhs must define a state as well.', 
									SimSGLPackage.Literals.RULE_BODY__RHS
									)
								}
								if(st_2 === null && st_1 !== null){
									error('If an argument on the lhs defines a state, the corresponding argument on the rhs must define a state as well.', 
									SimSGLPackage.Literals.RULE_BODY__LHS
									)
								}
							}
							
						}
					}
				}
				idx++;	
			}
		}
		
	}

	def valueOfNumericAssignment(NumericAssignment na){
		var value = "0"
		if(na instanceof NumericFromLiteral) {
			val nl = na as NumericFromLiteral
			value = nl.value.value
		}else {
			val nv = na as NumericFromVariable
			val ae = nv.valueVar.value
			value = ae.value
		}
		return value
	}
	
	@Check
	def checkAgentPatternSites(ValidAgentPattern agentPattern) {
		var candidates = EcoreUtil2.getAllContentsOfType(agentPattern, SitePattern);
		var sites = agentPattern.agent.sites.sites
		var siteSet = new HashSet<Site>(sites.size())
		siteSet.addAll(sites)
		var sitePatternSet = new HashSet<Site>()
		
		for(candidate : candidates) {
			var site = null as Site
			if(candidate instanceof SingleSitePattern) {
				site = (candidate as SingleSitePattern).site
			}else {
				site = (candidate as MultiLinkSitePattern).site
			}
			
			if(!siteSet.contains(site)) {
				error('This Agent does not have a site with ID='+site.name, SimSGLPackage.Literals.VALID_AGENT_PATTERN__SITE_PATTERNS)
			}
			if(sitePatternSet.contains(site)) {
				error('You may not redefine the same site multiple times.', SimSGLPackage.Literals.VALID_AGENT_PATTERN__SITE_PATTERNS)
			}else {
				sitePatternSet.add(site)
			}
			
		}
	}
	
	@Check
	def checkIndexedLinkConstraint(BoundLink boundLink) {
		var pattern = null as Pattern
		var eObj = boundLink.eContainer
		while(!(eObj instanceof Pattern) && eObj !== null) {
			eObj = eObj.eContainer
		}
		if(eObj instanceof Pattern) {
			pattern = eObj
		}
		var candidates = EcoreUtil2.getAllContentsOfType(pattern, BoundLink);
		var c = 1
		val thisNum = Integer.valueOf(boundLink.state)
		for(cnd : candidates) {
			val candidate = cnd as BoundLink
			val cNum = Integer.valueOf(candidate.state)
			if(cNum == thisNum && !candidate.equals(boundLink)) {
				c++
			}
			if(c>2){
				error('This indexed link refers to more than two end-points aka. sites.', SimSGLPackage.Literals.BOUND_LINK__STATE)
			}
		}
		if(c<2) {
			error('This indexed link must refer to exactly two end-points aka. sites.', SimSGLPackage.Literals.BOUND_LINK__STATE)
		}
	}
	
	@Check
	def checkIndexedFreeLinkConstraint(IndexedFreeLink freeLink) {
		var pattern = null as Pattern
		var eObj = freeLink.eContainer
		while(!(eObj instanceof Pattern) && eObj !== null) {
			eObj = eObj.eContainer
		}
		if(eObj instanceof Pattern) {
			pattern = eObj
		}
		var candidates = EcoreUtil2.getAllContentsOfType(pattern, IndexedFreeLink);
		var c = 1
		val thisNum = Integer.valueOf(freeLink.state)
		for(cnd : candidates) {
			val candidate = cnd as IndexedFreeLink
			val cNum = Integer.valueOf(candidate.state)
			if(cNum == thisNum && !candidate.equals(freeLink)) {
				c++
			}
			if(c>2){
				error('This indexed link deletion refers to more than two end-points aka. sites.', SimSGLPackage.Literals.INDEXED_FREE_LINK__STATE)
			}
		}
		if(c<2) {
			error('This indexed link deletion must refer to exactly two end-points aka. sites.', SimSGLPackage.Literals.INDEXED_FREE_LINK__STATE)
		}
	}
	
}
