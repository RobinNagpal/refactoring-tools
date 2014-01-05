package edu.pdx.cs.multiview.smelldetector.indexer;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;

import edu.pdx.cs.multiview.jdt.util.JDTUtils;
import edu.pdx.cs.multiview.smelldetector.SmellDetectorManager;
import edu.pdx.cs.multiview.smelldetector.detectors.SmellDetector;
import edu.pdx.cs.multiview.smelldetector.methods.dataClump.ClumpsAtClassLevel;

public class SmellMetaDataProcessor {

	ArrayList<SmellDetector<?>> methodSmellDetectors = new ArrayList<SmellDetector<?>>();
	
	public SmellMetaDataProcessor(JavaEditor activeEditor, SmellDetectorManager manager) {
		IJavaProject project = JDTUtils.getCompilationUnit(activeEditor).getJavaProject();
		this.methodSmellDetectors = manager.getMethodSmellDetectors();
		saveSmellMetadataForProject(project);
	}
	
	private void saveSmellMetadataForProject(IJavaProject project) {
		try {
			IPackageFragment[] buildPackages = project.getPackageFragments();
			saveSmellMetadataForPackages(buildPackages);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	private void saveSmellMetadataForPackages(IPackageFragment[] buildPackages) {
		try {
			for (int i = 0; i < buildPackages.length; i++) {
				IPackageFragment packageFragment = buildPackages[i];
				if (packageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
					saveSmellMetadataForPackage(packageFragment);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		System.gc();
	}

	private void saveSmellMetadataForPackage(IPackageFragment packageFragment) {
		try {
			ICompilationUnit[] compilationUnits = packageFragment.getCompilationUnits();
			for (int j = 0; j < compilationUnits.length; j++) {
				ICompilationUnit iCompilationUnit = compilationUnits[j];
				System.out.println(iCompilationUnit.getElementName());
				saveSmellMetadataForFile(iCompilationUnit);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	private void saveSmellMetadataForFile(ICompilationUnit iCompilationUnit) {
		try {
			IType[] allTypes = iCompilationUnit.getAllTypes();
			for (IType iType : allTypes) {
				IMethod[] methods = iType.getMethods();
				for (IMethod iMethod : methods) {
					saveSmellMetadataForMethod(iMethod);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

	}
	
	private void saveSmellMetadataForMethod(IMethod iMethod) throws JavaModelException {
	
		
	}


}
