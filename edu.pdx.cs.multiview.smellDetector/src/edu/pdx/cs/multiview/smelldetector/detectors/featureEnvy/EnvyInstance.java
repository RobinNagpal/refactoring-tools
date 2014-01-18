package edu.pdx.cs.multiview.smelldetector.detectors.featureEnvy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.jdt.util.MemberReference;
import edu.pdx.cs.multiview.smelldetector.detectors.SmellInstance;

/**
 * I represent a set of feature envy smells for
 * a region of an {@link ICompilationUnit}
 */
class EnvyInstance extends ClassEnvyRating implements SmellInstance{
	
	private Collection<IMethod> visibleMethods;
	
	public EnvyInstance(ClassEnvyRating rating, Collection<IMethod> visibleMethods){
		super.rs = rating.rs;
		this.visibleMethods = visibleMethods;
	}

	/*
	 * Magnitudes are aggregations of the per-method magnitudes.
	 */
	public double calculateMagnitude() {
		double maxMagnitude = 0;
		for (MethodEnvyRating methodRating : ratings()) {
			maxMagnitude = Math.max(methodRating.magnitude(),maxMagnitude);
		}

		return Math.min(maxMagnitude,1.0);
	}

	@Override
	protected Collection<MethodEnvyRating> ratings() {
		
		List<MethodEnvyRating> ratings = new LinkedList<MethodEnvyRating>();
		//collect values on key predicates
		for(Map.Entry<IMethod, MethodEnvyRating> r : rs.entrySet())
			if(visibleMethods.contains(r.getKey()))
				ratings.add(r.getValue());
		
		return ratings;
	}
	
	@SuppressWarnings("serial")
	public Set<MemberReference> uniqueThisReferences(){ 
		//this code sort of duplicates FeatureEnvyOverlay>>assignReferences
		Collection<MemberReference> thisRefs = 
			thisReferences(new HashSet<MemberReference>(){
				public boolean add(MemberReference ref){
					for(MemberReference otherRef : this){
						if(otherRef.equals(ref)){
							((MemberReferenceDuplicate)otherRef).dups++;
							return false;
						}
					}
					return super.add(new MemberReferenceDuplicate(ref));
				}
		});
		return new TreeSet<MemberReference>(thisRefs);
	}
	
	public List<MemberReference> thisReferences(){
		return thisReferences(new ArrayList<MemberReference>());
	}

	private <C extends Collection<MemberReference>> C thisReferences(C coll) {
		
		for(MethodEnvyRating rating : ratings()){
			coll.addAll(rating.thisReferences());
		}
		
		return coll;
	}
	

	@Override
	public int uniqueClassesReferenced() {	
		return super.uniqueClassesReferenced() +
			(uniqueThisReferences().isEmpty() ? 0 : 1);
	}
	
	@Override
	public int uniqueItemsReferenced() {
		return super.uniqueItemsReferenced() +
					uniqueThisReferences().size();
	}
	
	public List<MemberReference> references(){
		List<MemberReference> refs = new ArrayList<MemberReference>();

		refs.addAll(items());
		refs.addAll(thisReferences());
		
		return refs;
	}
}