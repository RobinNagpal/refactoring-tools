package edu.pdx.cs.multiview.smelldetector.detectors.featureEnvy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import edu.pdx.cs.multiview.jdt.util.MemberReference;
import edu.pdx.cs.multiview.smelldetector.detectors.MethodSmellRating;

/**
 * I'm an envy rating for one method
 */
class MethodEnvyRating extends MethodSmellRating<MemberReference>{

	private List<MemberReference> thisReferences;
	
	public MethodEnvyRating(IMethod m) {
		super(m);
	}

	@Override
	protected ASTVisitor getVisitor() {
		return new ASTVisitor(){
			
			public boolean visit(MethodInvocation inv){
				proces(MemberReference.with(inv));
				return true;
			}
			
			public boolean visit(SimpleName name){
				
				IBinding binding = name.resolveBinding();
				if(binding != null && binding instanceof IVariableBinding){
					IVariableBinding vbind = (IVariableBinding)binding;
					if(vbind.isField()){
						proces(MemberReference.with(name,vbind));
						return false;
					}
				}
				
				return true;
			}
			
			public boolean visit(ClassInstanceCreation constructor){				
				proces(MemberReference.with(constructor));				
				return true;
			}

			private void proces(MemberReference mb) {
				if(mb==null)
					return;
				
				//if referenced class is null (typically an array.length), skip it
				if(mb.referencedClass()==null)
					return;
				
				process(mb.referencedClass(),mb);
			}
		};
	}
	
	protected void process(ITypeBinding clazz, MemberReference e) {
		if(e.declaringClass().resolveBinding().equals(clazz)){
			thisReferences().add(e);
		}else{
			super.process(clazz, e);
		}
	}
	
	public List<MemberReference> thisReferences(){
		if(thisReferences==null)
			thisReferences = new ArrayList<MemberReference>();
		return thisReferences;
	}

	/**
	 * @return a value between 0 and 1
	 */
	public double magnitude() {
		
		int classRefs = classesReferenced().size();
		
		if(classRefs==0)
			return 0.0;
		
		int thisMemberRefs = thisReferences().size();
			
		int remainder = 0;
		for (ITypeBinding clazz : classesReferenced()) {
			List<MemberReference> refs = super.smells(clazz);
			remainder += Math.max(0,refs.size() - thisMemberRefs);		
		}
		
		return Math.min(1.0,Math.log(remainder) / 4);
	}
}