/**
 * generated by Xtext 2.12.0
 */
package biochemsimulation.reactionrules.generator;

import biochemsimulation.reactionrules.reactionRules.Agent;
import biochemsimulation.reactionrules.reactionRules.AgentInstance;
import biochemsimulation.reactionrules.reactionRules.AgentInstanceLinkState;
import biochemsimulation.reactionrules.reactionRules.AgentInstanceSiteState;
import biochemsimulation.reactionrules.reactionRules.AgentPattern;
import biochemsimulation.reactionrules.reactionRules.AssignFromPattern;
import biochemsimulation.reactionrules.reactionRules.AssignFromVariable;
import biochemsimulation.reactionrules.reactionRules.ExactLink;
import biochemsimulation.reactionrules.reactionRules.IndexedLink;
import biochemsimulation.reactionrules.reactionRules.Initial;
import biochemsimulation.reactionrules.reactionRules.LinkState;
import biochemsimulation.reactionrules.reactionRules.ModelLocation;
import biochemsimulation.reactionrules.reactionRules.ModelPath;
import biochemsimulation.reactionrules.reactionRules.ModelUri;
import biochemsimulation.reactionrules.reactionRules.Pattern;
import biochemsimulation.reactionrules.reactionRules.PatternAssignment;
import biochemsimulation.reactionrules.reactionRules.ReactionProperty;
import biochemsimulation.reactionrules.reactionRules.ReactionRulesFactory;
import biochemsimulation.reactionrules.reactionRules.SemiLink;
import biochemsimulation.reactionrules.reactionRules.Site;
import biochemsimulation.reactionrules.reactionRules.SitePattern;
import biochemsimulation.reactionrules.reactionRules.SiteState;
import biochemsimulation.reactionrules.reactionRules.WhatEver;
import biochemsimulation.reactionrules.reactionRules.impl.ReactionRuleModelImpl;
import biochemsimulation.reactionrules.reactionRules.impl.ReactionRulesFactoryImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class ReactionRulesGenerator extends AbstractGenerator {
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.generateInitialConditions(resource);
    this.saveResource(resource);
  }
  
  public void generateInitialConditions(final Resource resource) {
    EObject _get = resource.getContents().get(0);
    ReactionRuleModelImpl model = ((ReactionRuleModelImpl) _get);
    final Function1<ReactionProperty, Boolean> _function = (ReactionProperty x) -> {
      return Boolean.valueOf((x instanceof Initial));
    };
    Iterable<ReactionProperty> initials = IterableExtensions.<ReactionProperty>filter(model.getReactionProperties(), _function);
    for (final ReactionProperty init : initials) {
      {
        final Initial i = ((Initial) init);
        this.agentInstancesFromInitial(resource, i);
      }
    }
  }
  
  public void agentInstancesFromInitial(final Resource resource, final Initial initial) {
    final PatternAssignment content = initial.getInitialPattern();
    final Integer n = Integer.valueOf(initial.getCount());
    if ((content instanceof AssignFromPattern)) {
      final AssignFromPattern c = ((AssignFromPattern) content);
      this.agentInstancesFromPattern(resource, c.getPattern(), (n).intValue(), initial.getName());
    } else {
      final AssignFromVariable va = ((AssignFromVariable) content);
      this.agentInstancesFromPattern(resource, va.getPatternVar().getPattern(), (n).intValue(), initial.getName());
    }
  }
  
  public void agentInstancesFromPattern(final Resource resource, final Pattern pattern, final int n, final String prefix) {
    EObject _get = resource.getContents().get(0);
    ReactionRuleModelImpl model = ((ReactionRuleModelImpl) _get);
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, n, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        int _size = pattern.getAgentPatterns().size();
        final HashMap<String, List<AgentInstance>> linksA = new HashMap<String, List<AgentInstance>>(_size);
        int _size_1 = pattern.getAgentPatterns().size();
        final HashMap<String, List<Site>> linksS = new HashMap<String, List<Site>>(_size_1);
        EList<AgentPattern> _agentPatterns = pattern.getAgentPatterns();
        for (final AgentPattern agentPattern : _agentPatterns) {
          {
            AgentPattern ap = ((AgentPattern) agentPattern);
            Agent agent = ap.getAgent();
            AgentInstance agentI = this.createNewAgentInstance(agent, ap, prefix, (i).intValue(), linksA, linksS);
            model.getReationContainer().getAgentInstances().add(agentI);
          }
        }
        Set<String> _keySet = linksA.keySet();
        for (final String linkID : _keySet) {
          {
            final AgentInstance lInstance = linksA.get(linkID).get(0);
            final AgentInstance rInstance = linksA.get(linkID).get(1);
            final Site lSite = linksS.get(linkID).get(0);
            final Site rSite = linksS.get(linkID).get(1);
            final Function1<AgentInstanceLinkState, Boolean> _function = (AgentInstanceLinkState x) -> {
              return Boolean.valueOf(x.getSite().equals(lSite));
            };
            final AgentInstanceLinkState lLinkState = IterableExtensions.<AgentInstanceLinkState>findFirst(lInstance.getLinkStates(), _function);
            final Function1<AgentInstanceLinkState, Boolean> _function_1 = (AgentInstanceLinkState x) -> {
              return Boolean.valueOf(x.getSite().equals(rSite));
            };
            final AgentInstanceLinkState rLinkState = IterableExtensions.<AgentInstanceLinkState>findFirst(rInstance.getLinkStates(), _function_1);
            lLinkState.setAttachedAgent(rInstance.getAgent());
            lLinkState.setAttachedSite(rSite);
            rLinkState.setAttachedAgent(lInstance.getAgent());
            rLinkState.setAttachedSite(lSite);
            lLinkState.setAttachedAgentInstance(rInstance);
            rLinkState.setAttachedAgentInstance(lInstance);
          }
        }
      }
    }
  }
  
  public AgentInstance createNewAgentInstance(final Agent agent, final AgentPattern ap, final String prefix, final int iteration, final HashMap<String, List<AgentInstance>> linksA, final HashMap<String, List<Site>> linksS) {
    final ReactionRulesFactory factory = ReactionRulesFactoryImpl.init();
    AgentInstance agentI = factory.createAgentInstance();
    String _name = agent.getName();
    String _plus = ((prefix + ":") + _name);
    String _plus_1 = (_plus + ".Instance@#");
    String _plus_2 = (_plus_1 + Integer.valueOf(iteration));
    agentI.setName(_plus_2);
    agentI.setAgent(agent);
    EList<SitePattern> _sitePatterns = ap.getSitePatterns().getSitePatterns();
    for (final SitePattern sitePattern : _sitePatterns) {
      {
        Site _site = sitePattern.getSite();
        Site site = ((Site) _site);
        LinkState _linkState = sitePattern.getLinkState();
        LinkState oldLinkState = ((LinkState) _linkState);
        SiteState _state = sitePattern.getState();
        SiteState oldSiteState = ((SiteState) _state);
        LinkState _createLinkState = factory.createLinkState();
        LinkState newLinkState = ((LinkState) _createLinkState);
        AgentInstanceLinkState _createAgentInstanceLinkState = factory.createAgentInstanceLinkState();
        AgentInstanceLinkState aiLinkState = ((AgentInstanceLinkState) _createAgentInstanceLinkState);
        AgentInstanceSiteState aiSiteState = factory.createAgentInstanceSiteState();
        if ((oldLinkState != null)) {
          newLinkState.setLinkState(EcoreUtil.<LinkState>copy(oldLinkState.getLinkState()));
          LinkState _linkState_1 = newLinkState.getLinkState();
          if ((_linkState_1 instanceof IndexedLink)) {
            LinkState _linkState_2 = newLinkState.getLinkState();
            final IndexedLink link = ((IndexedLink) _linkState_2);
            this.insertLinkInLinkMap(link.getState(), agentI, site, linksA, linksS);
          }
          if ((((newLinkState.getLinkState() instanceof WhatEver) || (newLinkState.getLinkState() instanceof ExactLink)) || (newLinkState.getLinkState() instanceof SemiLink))) {
            newLinkState.setLinkState(factory.createFreeLink());
          }
        } else {
          newLinkState.setLinkState(factory.createFreeLink());
        }
        if ((oldSiteState != null)) {
          aiSiteState.setSiteState(EcoreUtil.<SiteState>copy(oldSiteState));
        } else {
          aiSiteState.setSiteState(factory.createSiteState());
        }
        aiLinkState.setSite(site);
        aiLinkState.setLinkState(newLinkState);
        aiSiteState.setSite(site);
        agentI.getLinkStates().add(aiLinkState);
        agentI.getSiteStates().add(aiSiteState);
      }
    }
    int _size = ap.getSitePatterns().getSitePatterns().size();
    boolean _lessEqualsThan = (_size <= 0);
    if (_lessEqualsThan) {
      EList<Site> _sites = agent.getSites().getSites();
      for (final Site site : _sites) {
        {
          AgentInstanceLinkState aiLinkState = factory.createAgentInstanceLinkState();
          aiLinkState.setSite(site);
          LinkState newLinkState = factory.createLinkState();
          newLinkState.setLinkState(factory.createFreeLink());
          aiLinkState.setLinkState(newLinkState);
          agentI.getLinkStates().add(aiLinkState);
        }
      }
    }
    return agentI;
  }
  
  public boolean insertLinkInLinkMap(final String linkID, final AgentInstance aI, final Site s, final HashMap<String, List<AgentInstance>> linksA, final HashMap<String, List<Site>> linksS) {
    boolean _xblockexpression = false;
    {
      boolean _containsKey = linksA.containsKey(linkID);
      boolean _not = (!_containsKey);
      if (_not) {
        LinkedList<AgentInstance> _linkedList = new LinkedList<AgentInstance>();
        linksA.put(linkID, _linkedList);
        LinkedList<Site> _linkedList_1 = new LinkedList<Site>();
        linksS.put(linkID, _linkedList_1);
      }
      linksA.get(linkID).add(aI);
      _xblockexpression = linksS.get(linkID).add(s);
    }
    return _xblockexpression;
  }
  
  public String saveResource(final Resource resource) {
    String _xblockexpression = null;
    {
      EObject _get = resource.getContents().get(0);
      final ReactionRuleModelImpl model = ((ReactionRuleModelImpl) _get);
      final String name = model.getModel().getName();
      final String projectPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
      String uriName = "";
      ModelLocation _location = model.getModel().getLocation();
      boolean _tripleEquals = (_location == null);
      if (_tripleEquals) {
        uriName = (((("file:" + projectPath) + "model/instances/") + name) + ".xmi");
      } else {
        ModelLocation _location_1 = model.getModel().getLocation();
        if ((_location_1 instanceof ModelPath)) {
          ModelLocation _location_2 = model.getModel().getLocation();
          final ModelPath path = ((ModelPath) _location_2);
          uriName = "file://";
          path.getPath();
        } else {
          ModelLocation _location_3 = model.getModel().getLocation();
          final ModelUri path_1 = ((ModelUri) _location_3);
          uriName = path_1.getUri();
        }
      }
      final URI uri1 = URI.createURI(uriName);
      URI uri2 = resource.getURI();
      uri2 = uri2.trimFileExtension();
      uri2 = uri2.appendFileExtension("xmi");
      this.saveModelToURI(model, name, uri1);
      _xblockexpression = this.saveModelToURI(model, name, uri2);
    }
    return _xblockexpression;
  }
  
  public String saveModelToURI(final EObject model, final String name, final URI uri) {
    String _xblockexpression = null;
    {
      final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
      Map<String, Object> m = reg.getExtensionToFactoryMap();
      XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
      m.put(name, _xMIResourceFactoryImpl);
      ResourceSetImpl resourceSet = new ResourceSetImpl();
      Resource _createResource = resourceSet.createResource(uri);
      XMIResource resource = ((XMIResource) _createResource);
      resource.getContents().add(model);
      final Map<Object, Object> saveOptions = resource.getDefaultSaveOptions();
      saveOptions.put(XMIResource.OPTION_ENCODING, "UTF-8");
      saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
      saveOptions.put(XMIResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
      saveOptions.put(XMIResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION, Boolean.TRUE);
      String _xtrycatchfinallyexpression = null;
      try {
        String _xblockexpression_1 = null;
        {
          resource.save(saveOptions);
          String _path = uri.path();
          String _plus = ("Model saved to: " + _path);
          _xblockexpression_1 = InputOutput.<String>println(_plus);
        }
        _xtrycatchfinallyexpression = _xblockexpression_1;
      } catch (final Throwable _t) {
        if (_t instanceof IOException) {
          final IOException e = (IOException)_t;
          e.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
}
