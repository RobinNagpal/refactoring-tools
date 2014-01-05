package edu.pdx.cs.multiview.smelldetector.methods.tooManyArguments;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Color;

import edu.pdx.cs.multiview.jface.annotation.AnnTransaction;
import edu.pdx.cs.multiview.jface.annotation.Highlight;
import edu.pdx.cs.multiview.smelldetector.detectors.SmellExplanationOverlay;


public class TooManyArgumentsOverlay extends SmellExplanationOverlay<TooManyArgumentsSmellInstance>{

	private Map<IMethod, Color> methodsToColors = new HashMap<IMethod, Color>();
	private AnnTransaction annotations = new AnnTransaction();
	
	public TooManyArgumentsOverlay(TooManyArgumentsSmellInstance inst, ISourceViewer sv) {
		super(inst,sv);
		init(inst);
	}

	
	private void init(TooManyArgumentsSmellInstance inst) {
		
		
		for(Entry<IMethod, Integer> entry : inst.getMethodToNumberOfArguments().entrySet()){
			int red = (int)(inst.magnitude() * 255);
			final Color c = new Color(null,red,255-red,0);
			IMethod m = entry.getKey();	
			
			methodsToColors.put(m,c);
			
			try {
				ISourceRange range = m.getSourceRange();			
				Position p  = new Position(range.getOffset(),range.getLength());
				
				annotations.add(new Highlight(c), p);
				
			} catch (JavaModelException e1) {
				e1.printStackTrace();
			}
		}
		
		allocateColors(methodsToColors.values());
		addAnnotations(annotations);

	}


	public Color colorFor(IMethod m){
		return methodsToColors.get(m);
	}
}