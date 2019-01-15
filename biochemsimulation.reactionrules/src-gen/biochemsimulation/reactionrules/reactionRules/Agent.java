/**
 * generated by Xtext 2.16.0
 */
package biochemsimulation.reactionrules.reactionRules;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link biochemsimulation.reactionrules.reactionRules.Agent#getSites <em>Sites</em>}</li>
 * </ul>
 *
 * @see biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage#getAgent()
 * @model
 * @generated
 */
public interface Agent extends ReactionProperty
{
  /**
   * Returns the value of the '<em><b>Sites</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sites</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sites</em>' containment reference.
   * @see #setSites(Sites)
   * @see biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage#getAgent_Sites()
   * @model containment="true"
   * @generated
   */
  Sites getSites();

  /**
   * Sets the value of the '{@link biochemsimulation.reactionrules.reactionRules.Agent#getSites <em>Sites</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sites</em>' containment reference.
   * @see #getSites()
   * @generated
   */
  void setSites(Sites value);

} // Agent
