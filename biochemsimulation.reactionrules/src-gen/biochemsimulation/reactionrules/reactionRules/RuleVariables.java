/**
 * generated by Xtext 2.16.0
 */
package biochemsimulation.reactionrules.reactionRules;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Variables</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link biochemsimulation.reactionrules.reactionRules.RuleVariables#getVariables <em>Variables</em>}</li>
 * </ul>
 *
 * @see biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage#getRuleVariables()
 * @model
 * @generated
 */
public interface RuleVariables extends EObject
{
  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link biochemsimulation.reactionrules.reactionRules.NumericAssignment}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage#getRuleVariables_Variables()
   * @model containment="true"
   * @generated
   */
  EList<NumericAssignment> getVariables();

} // RuleVariables
