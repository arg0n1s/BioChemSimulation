/**
 * generated by Xtext 2.12.0
 */
package biochemsimulation.reactionrules.validation;

import biochemsimulation.reactionrules.reactionRules.Agent;
import biochemsimulation.reactionrules.reactionRules.AgentPattern;
import biochemsimulation.reactionrules.reactionRules.ArithmeticValue;
import biochemsimulation.reactionrules.reactionRules.ArithmeticVariable;
import biochemsimulation.reactionrules.reactionRules.AssignFromPattern;
import biochemsimulation.reactionrules.reactionRules.AssignFromVariable;
import biochemsimulation.reactionrules.reactionRules.BoundAnyLink;
import biochemsimulation.reactionrules.reactionRules.BoundAnyOfTypeLink;
import biochemsimulation.reactionrules.reactionRules.BoundLink;
import biochemsimulation.reactionrules.reactionRules.Initial;
import biochemsimulation.reactionrules.reactionRules.LinkState;
import biochemsimulation.reactionrules.reactionRules.NumericAssignment;
import biochemsimulation.reactionrules.reactionRules.NumericFromLiteral;
import biochemsimulation.reactionrules.reactionRules.NumericFromVariable;
import biochemsimulation.reactionrules.reactionRules.Observation;
import biochemsimulation.reactionrules.reactionRules.Pattern;
import biochemsimulation.reactionrules.reactionRules.PatternAssignment;
import biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage;
import biochemsimulation.reactionrules.reactionRules.Rule;
import biochemsimulation.reactionrules.reactionRules.RuleBody;
import biochemsimulation.reactionrules.reactionRules.Site;
import biochemsimulation.reactionrules.reactionRules.SitePattern;
import biochemsimulation.reactionrules.reactionRules.ValidAgentPattern;
import biochemsimulation.reactionrules.reactionRules.Variable;
import biochemsimulation.reactionrules.reactionRules.WhatEver;
import biochemsimulation.reactionrules.validation.AbstractReactionRulesValidator;
import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class ReactionRulesValidator extends AbstractReactionRulesValidator {
  @Check
  public void checkAgentIdUnique(final Agent agent) {
    final EObject rootElement = EcoreUtil2.getRootContainer(agent);
    List<Agent> candidates = EcoreUtil2.<Agent>getAllContentsOfType(rootElement, Agent.class);
    int c = 0;
    for (final Agent candidate : candidates) {
      {
        Agent current = ((Agent) candidate);
        boolean _equals = current.getName().equals(agent.getName());
        if (_equals) {
          c++;
        }
        if ((c > 1)) {
          this.error("Agent IDs must be unique.", null);
          c = 1;
        }
      }
    }
  }
  
  @Check
  public void checkVariableIdUnique(final Variable variable) {
    final EObject rootElement = EcoreUtil2.getRootContainer(variable);
    List<Variable> candidates = EcoreUtil2.<Variable>getAllContentsOfType(rootElement, Variable.class);
    int c = 0;
    for (final Variable candidate : candidates) {
      {
        Variable current = ((Variable) candidate);
        boolean _equals = current.getName().equals(variable.getName());
        if (_equals) {
          c++;
        }
        if ((c > 1)) {
          this.error("Variable IDs must be unique.", null);
          c = 1;
        }
      }
    }
  }
  
  @Check
  public boolean checkArithmeticVariableFormat(final ArithmeticVariable aVar) {
    boolean _contains = aVar.getValue().getValue().contains(" ");
    if (_contains) {
      this.error("Arithmetic variables may not contain any whitespaces!", null);
      return false;
    }
    return true;
  }
  
  @Check
  public void checkInitialIdUnique(final Initial initial) {
    final EObject rootElement = EcoreUtil2.getRootContainer(initial);
    List<Initial> candidates = EcoreUtil2.<Initial>getAllContentsOfType(rootElement, Initial.class);
    int c = 0;
    for (final Initial candidate : candidates) {
      {
        Initial current = ((Initial) candidate);
        boolean _equals = current.getName().equals(initial.getName());
        if (_equals) {
          c++;
        }
        if ((c > 1)) {
          this.error("Initial IDs must be unique.", null);
          c = 1;
        }
      }
    }
  }
  
  @Check
  public void checkInitialCountInteger(final Initial initial) {
    final NumericAssignment countVar = initial.getCount();
    ArithmeticValue arithVal = ((ArithmeticValue) null);
    if ((countVar instanceof NumericFromVariable)) {
      final NumericFromVariable numVar = ((NumericFromVariable) countVar);
      arithVal = numVar.getValueVar().getValue();
    } else {
      final NumericFromLiteral numLit = ((NumericFromLiteral) countVar);
      arithVal = numLit.getValue();
    }
    boolean _matches = arithVal.getValue().matches("^(\\d)*$");
    boolean _not = (!_matches);
    if (_not) {
      this.error("Initial count variable must be of type unsigned integer.", null);
    } else {
      final Integer num = Integer.valueOf(arithVal.getValue());
      if (((num).intValue() == 0)) {
        this.warning("Initial count variables equal to 0 will lead to zero instantiated agents.", null);
      }
    }
  }
  
  @Check
  public void checkInitialIllegalLinkStates(final Initial initial) {
    final Pattern pattern = this.patternFromPatternAssignment(initial.getInitialPattern());
    EList<AgentPattern> _agentPatterns = pattern.getAgentPatterns();
    for (final AgentPattern ap : _agentPatterns) {
      if ((ap instanceof ValidAgentPattern)) {
        final ValidAgentPattern vap = ((ValidAgentPattern) ap);
        EList<SitePattern> _sitePatterns = vap.getSitePatterns().getSitePatterns();
        for (final SitePattern sp : _sitePatterns) {
          {
            final LinkState linkState = sp.getLinkState().getLinkState();
            if ((((linkState instanceof BoundAnyLink) || (linkState instanceof WhatEver)) || (linkState instanceof BoundAnyOfTypeLink))) {
              this.error("Illegal initial link state! A pattern may only be instantiated with link states of Type: FreeLink(\"free\"), IndexedLink(\"INT\")", null);
            }
          }
        }
      }
    }
  }
  
  public Pattern patternFromPatternAssignment(final PatternAssignment pa) {
    if ((pa instanceof AssignFromPattern)) {
      final AssignFromPattern afp = ((AssignFromPattern) pa);
      return afp.getPattern();
    } else {
      final AssignFromVariable afv = ((AssignFromVariable) pa);
      return afv.getPatternVar().getPattern();
    }
  }
  
  @Check
  public void checkObservationIdUnique(final Observation observation) {
    final EObject rootElement = EcoreUtil2.getRootContainer(observation);
    List<Observation> candidates = EcoreUtil2.<Observation>getAllContentsOfType(rootElement, Observation.class);
    int c = 0;
    for (final Observation candidate : candidates) {
      {
        Observation current = ((Observation) candidate);
        boolean _equals = current.getName().equals(observation.getName());
        if (_equals) {
          c++;
        }
        if ((c > 1)) {
          this.error("Observation IDs must be unique.", null);
          c = 1;
        }
      }
    }
  }
  
  @Check
  public void checkRuleIdUnique(final Rule rule) {
    final EObject rootElement = EcoreUtil2.getRootContainer(rule);
    List<Rule> candidates = EcoreUtil2.<Rule>getAllContentsOfType(rootElement, Rule.class);
    int c = 0;
    for (final Rule candidate : candidates) {
      {
        Rule current = ((Rule) candidate);
        boolean _equals = current.getName().equals(rule.getName());
        if (_equals) {
          c++;
        }
        if ((c > 1)) {
          this.error("Rule IDs must be unique.", null);
          c = 1;
        }
      }
    }
  }
  
  @Check
  public void checkSiteIdUnique(final Agent agent) {
    List<Site> candidates = EcoreUtil2.<Site>getAllContentsOfType(agent, Site.class);
    int c = 0;
    for (final Site candidate : candidates) {
      {
        Site current = ((Site) candidate);
        String name = current.getName();
        for (final Site candidate2 : candidates) {
          {
            Site current2 = ((Site) candidate2);
            String name2 = current2.getName();
            boolean _equals = name.equals(name2);
            if (_equals) {
              c++;
            }
            if ((c > 1)) {
              this.error("Site IDs within Agents must be unique.", ReactionRulesPackage.Literals.AGENT__SITES);
              c = 1;
            }
          }
        }
        c = 0;
      }
    }
  }
  
  @Check
  public void checkStateIdUnique(final Site site) {
    List<biochemsimulation.reactionrules.reactionRules.State> candidates = EcoreUtil2.<biochemsimulation.reactionrules.reactionRules.State>getAllContentsOfType(site, biochemsimulation.reactionrules.reactionRules.State.class);
    int c = 0;
    for (final biochemsimulation.reactionrules.reactionRules.State candidate : candidates) {
      {
        biochemsimulation.reactionrules.reactionRules.State current = ((biochemsimulation.reactionrules.reactionRules.State) candidate);
        String name = current.getName();
        for (final biochemsimulation.reactionrules.reactionRules.State candidate2 : candidates) {
          {
            biochemsimulation.reactionrules.reactionRules.State current2 = ((biochemsimulation.reactionrules.reactionRules.State) candidate2);
            String name2 = current2.getName();
            boolean _equals = name.equals(name2);
            if (_equals) {
              c++;
            }
            if ((c > 1)) {
              this.error("State IDs within Sites must be unique.", ReactionRulesPackage.Literals.SITE__STATES);
              c = 1;
            }
          }
        }
        c = 0;
      }
    }
  }
  
  @Check
  public void checkRuleVariables(final RuleBody ruleBody) {
    String op = ruleBody.getOperator();
    EList<NumericAssignment> variables = ruleBody.getVariables().getVariables();
    boolean _equals = op.equals("<->");
    if (_equals) {
      int _size = variables.size();
      boolean _notEquals = (_size != 2);
      if (_notEquals) {
        this.error("Bi-Directional rules must have two reaction rate variables.", ReactionRulesPackage.Literals.RULE_BODY__VARIABLES);
      }
    } else {
      int _size_1 = variables.size();
      boolean _notEquals_1 = (_size_1 != 1);
      if (_notEquals_1) {
        this.error("Uni-Directional rules must have one reaction rate variable.", ReactionRulesPackage.Literals.RULE_BODY__VARIABLES);
      }
    }
    for (final NumericAssignment variable : variables) {
      {
        String value = this.valueOfNumericAssignment(variable);
        boolean faulty = false;
        boolean _contains = value.contains(" ");
        if (_contains) {
          this.error("Arithmetic variables may not contain any whitespaces!", ReactionRulesPackage.Literals.RULE_BODY__VARIABLES);
          faulty = true;
        }
        if ((((!value.matches("^(-)?(\\d)+(\\.)(\\d)+E(-|\\+)(\\d)+$")) && (!value.matches("^(-)?(\\d)*$"))) && (!value.matches("^(-)?(\\d)+(\\.)(\\d)+$")))) {
          this.error("Given expression does not adhere to any known number format.", ReactionRulesPackage.Literals.RULE_BODY__VARIABLES);
          faulty = true;
        }
        if ((!faulty)) {
          Double numValue = Double.valueOf(value);
          if (((numValue).doubleValue() < 0)) {
            this.error("Uni-Directional rules must have positive reaction rates.", ReactionRulesPackage.Literals.RULE_BODY__VARIABLES);
          }
          if (((numValue).doubleValue() == 0)) {
            this.warning("Uni-Directional rules with rates equal to 0 will be inactive.", ReactionRulesPackage.Literals.RULE_BODY__VARIABLES);
          }
        }
      }
    }
  }
  
  @Check
  public void checkRuleNumberOfArgs(final RuleBody ruleBody) {
    final Pattern lhs = this.patternFromPatternAssignment(ruleBody.getLhs());
    final Pattern rhs = this.patternFromPatternAssignment(ruleBody.getRhs());
    int _size = lhs.getAgentPatterns().size();
    int _size_1 = rhs.getAgentPatterns().size();
    boolean _notEquals = (_size != _size_1);
    if (_notEquals) {
      this.error("Number of arguments on the left hand side of the rule, must match number of arguments on the right hand side.", 
        ReactionRulesPackage.Literals.RULE_BODY__LHS);
      this.error("Number of arguments on the right hand side of the rule, must match number of arguments on the left hand side.", 
        ReactionRulesPackage.Literals.RULE_BODY__RHS);
    }
  }
  
  public String valueOfNumericAssignment(final NumericAssignment na) {
    String value = "0";
    if ((na instanceof NumericFromLiteral)) {
      final NumericFromLiteral nl = ((NumericFromLiteral) na);
      value = nl.getValue().getValue();
    } else {
      final NumericFromVariable nv = ((NumericFromVariable) na);
      final ArithmeticValue ae = nv.getValueVar().getValue();
      value = ae.getValue();
    }
    return value;
  }
  
  @Check
  public void checkAgentPatternSites(final ValidAgentPattern agentPattern) {
    List<SitePattern> candidates = EcoreUtil2.<SitePattern>getAllContentsOfType(agentPattern, SitePattern.class);
    EList<Site> sites = agentPattern.getAgent().getSites().getSites();
    int _size = sites.size();
    HashSet<Site> siteSet = new HashSet<Site>(_size);
    siteSet.addAll(sites);
    for (final SitePattern candidate : candidates) {
      {
        SitePattern sp = ((SitePattern) candidate);
        Site spSite = sp.getSite();
        boolean _contains = siteSet.contains(spSite);
        boolean _not = (!_contains);
        if (_not) {
          String _name = spSite.getName();
          String _plus = ("This Agent does not have a site with ID=" + _name);
          this.error(_plus, ReactionRulesPackage.Literals.VALID_AGENT_PATTERN__SITE_PATTERNS);
        }
      }
    }
  }
  
  @Check
  public void checkIndexedLinkConstraint(final BoundLink boundLink) {
    Pattern pattern = ((Pattern) null);
    EObject eObj = boundLink.eContainer();
    while (((!(eObj instanceof Pattern)) && (eObj != null))) {
      eObj = eObj.eContainer();
    }
    if ((eObj instanceof Pattern)) {
      pattern = ((Pattern)eObj);
    }
    List<BoundLink> candidates = EcoreUtil2.<BoundLink>getAllContentsOfType(pattern, BoundLink.class);
    int c = 1;
    final Integer thisNum = Integer.valueOf(boundLink.getState());
    for (final BoundLink cnd : candidates) {
      {
        final BoundLink candidate = ((BoundLink) cnd);
        final Integer cNum = Integer.valueOf(candidate.getState());
        if ((Objects.equal(cNum, thisNum) && (!candidate.equals(boundLink)))) {
          c++;
        }
        if ((c > 2)) {
          this.error("This indexed link refers to more than two end-points aka. sites.", ReactionRulesPackage.Literals.BOUND_LINK__STATE);
        }
      }
    }
    if ((c < 2)) {
      this.error("This indexed link must refer to exactly two end-points aka. sites.", ReactionRulesPackage.Literals.BOUND_LINK__STATE);
    }
  }
}
