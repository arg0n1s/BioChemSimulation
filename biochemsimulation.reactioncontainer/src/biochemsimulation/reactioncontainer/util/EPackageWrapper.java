package biochemsimulation.reactioncontainer.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

public class EPackageWrapper {
	EPackage ePack;
	
	private Map<String, EClass> classMap;
	private Map<String, EReference> referenceMap;
	
	public EPackageWrapper(EPackage ePack) {
		this.ePack = ePack;
		mapElements();
	}
	
	private void mapElements() {
		classMap = new HashMap<String, EClass>();
		referenceMap = new HashMap<String, EReference>();
		
		ePack.getEClassifiers().forEach(classifier-> {
			if(classifier instanceof EClass) {
				EClass eClass = (EClass)classifier;
				classMap.put(eClass.getName(), eClass);
				eClass.getEStructuralFeatures().forEach(reference->{
					if(reference instanceof EReference) {
						EReference eRef = (EReference)reference;
						referenceMap.putIfAbsent(reference.getName(), eRef);
					}
				});
			}
			
		});
	}
	
	public EPackage getPackage( ) {
		return ePack;
	}
	
	public EClass getClass(String typeName) {
		return classMap.get(typeName);
	}
	
	public EReference getEReference(String refName) {
		return referenceMap.get(refName);
	}
}
