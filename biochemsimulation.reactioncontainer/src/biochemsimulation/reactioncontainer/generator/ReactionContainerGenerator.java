package biochemsimulation.reactioncontainer.generator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import biochemsimulation.reactioncontainer.ReactionContainer;
import biochemsimulation.reactioncontainer.ReactioncontainerFactory;
import biochemsimulation.reactioncontainer.ReactioncontainerPackage;
import biochemsimulation.reactioncontainer.SimAgent;
import biochemsimulation.reactioncontainer.SimBound;
import biochemsimulation.reactioncontainer.SimSite;
import biochemsimulation.reactioncontainer.impl.ReactioncontainerFactoryImpl;
import biochemsimulation.reactionrules.reactionRules.Initial;
import biochemsimulation.reactionrules.reactionRules.Pattern;
import biochemsimulation.reactionrules.reactionRules.ReactionRulesPackage;
import biochemsimulation.reactionrules.reactionRules.ValidAgentPattern;
import biochemsimulation.reactionrules.reactionRules.impl.ReactionRuleModelImpl;
import biochemsimulation.reactionrules.utils.PatternUtils;

public class ReactionContainerGenerator {
	private String projectPath;
	private URI modelLocation;
	private Resource modelResource;
	private ReactionRuleModelImpl model;
	private boolean isInitialized;
	
	private ReactioncontainerFactory factory;
	
	private void init() {
		ReactionRulesPackage.eINSTANCE.eClass();
		ReactioncontainerPackage.eINSTANCE.eClass();
		factory = ReactioncontainerFactoryImpl.init();
		
		projectPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		projectPath = projectPath.replaceFirst("/bin", "");
	}
	
	public ReactionContainerGenerator(URI modelLocation) {
		init();
		
		this.modelLocation = modelLocation;
		isInitialized = loadResource();
		if(isInitialized)
			isInitialized = loadModel();
		
	}
	
	public ReactionContainerGenerator(Resource model) {
		init();
		
		modelResource = model;
		isInitialized = model != null;
		if(isInitialized) {
			modelLocation = modelResource.getURI();
			isInitialized = loadModel();
		}
	}
	
	private boolean loadResource() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ReactionRules", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		modelResource = null;
		try {
			modelResource = rs.getResource(modelLocation, true);
		}catch(Exception e) {
			System.out.println("Exceptionn occured while loading a resource: "+e.getClass()+": "+e.getMessage());
			return false;
		}
		if(modelResource != null)
			return true;
		
		return false;
		
	}
	
	private boolean loadModel() {
		model = (ReactionRuleModelImpl) modelResource.getContents().get(0);
		if(model != null) 
			return true;
		
		return false;
	}
	
	public void doGenerate() throws IOException{
		if(!isInitialized) {
			throw new RuntimeException("ReactionContainerGenerator is uninitialized because the given resource containing the ReactionRules model could not be loaded.");
		}
		List<Initial> initials = new LinkedList<Initial>();
		model.getReactionProperties().forEach(x -> { 
			if(x instanceof Initial) initials.add((Initial) x); 
			});
		
		List<AgentTemplate> templates = new LinkedList<AgentTemplate>();
		for(Initial init : initials) {
			Pattern pa = PatternUtils.patternFromPatternAssignment(init.getInitialPattern());
			if(!PatternUtils.isPatternEmpty(pa)) {
				for(ValidAgentPattern ap : PatternUtils.getValidAgentPatterns(pa.getAgentPatterns())) {
					templates.add(new AgentTemplate(init, ap));
				}
			}
		}
		
		ReactionContainer containerModel = factory.createReactionContainer();
		for(AgentTemplate at : templates) {
			for(int i = 0; i<at.getCount(); i++) {
				containerModel.getSimAgent().add(at.createInstance(factory));
			}
		}
		
		for(SimAgent sa : containerModel.getSimAgent()) {
			System.out.println("For Agent: "+sa.getName());
			for(SimSite sss : sa.getSimSite()) {
				System.out.println("New SimSite stats:");
				System.out.println("SimAgent_Name: "+sss.getSimAgent().getName());
				System.out.println("SimAgent_Type: "+sss.getType());
				//System.out.println("SimSite_Type: "+sss.getSimSiteState().getType());
				System.out.println("SimAgent_Name of Site1: "+sss.getSimLinkstate().getSimSite1().getSimAgent().getName());
				if(sss.getSimLinkstate() instanceof SimBound) {
					SimBound saglkjn = (SimBound)sss.getSimLinkstate();
					if(saglkjn.getSimSite2()!=null) {
						System.out.println("SimAgent_Name of Site2: "+((SimBound)sss.getSimLinkstate()).getSimSite2().getSimAgent().getName());
					}
				}
					
			}
		}
		String uriName = "file:"+projectPath+"model/instances/"+model.getModel().getName()+".xmi";
		System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		URI uri = URI.createURI(uriName);
		saveModelToURI(containerModel, model.getModel().getName(), uri);
	}
	
	private void saveModelToURI(EObject model, String name, URI uri){
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put( "*",
				new XMIResourceFactoryImpl());
		ResourceSet resourceSet = new ResourceSetImpl();
		XMIResource resource = (XMIResource) resourceSet.createResource(uri);
		resource.getContents().add(model);
		
		Map<Object, Object> saveOptions = resource.getDefaultSaveOptions();
		saveOptions.put(XMIResource.OPTION_ENCODING,"UTF-8");
		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_SAVE_TYPE_INFORMATION,Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION, Boolean.TRUE);
		
		try {
			resource.save(saveOptions);
			System.out.println("Model saved to: "+uri.path());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
