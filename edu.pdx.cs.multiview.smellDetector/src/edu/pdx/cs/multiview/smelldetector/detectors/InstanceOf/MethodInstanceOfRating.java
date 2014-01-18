package edu.pdx.cs.multiview.smelldetector.detectors.InstanceOf;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.InstanceofExpression;

import edu.pdx.cs.multiview.smelldetector.detectors.MethodSmellRating;

class MethodInstanceOfRating extends MethodSmellRating<InstanceofExpression>{
	private final IMethod method;

	public MethodInstanceOfRating(IMethod m) {
		super(m);
		this.method = m;
	}

	protected ASTVisitor getVisitor() {
		return new ASTVisitor(){
			public boolean visit(InstanceofExpression expr){
				process(expr.resolveTypeBinding(),expr);
				return true;
			}
		};
	}

	
	@Override
	public String toString() {
		return "MethodInstanceOfRating ["+ method.toString()+ "]"  ;
	}
	
	
}