package edu.pdx.cs.multiview.smelldetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.graphics.Color;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.InstanceOf.InstanceoftDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.dataClump.DataClumpDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.featureEnvy.FeatureEnvyDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.largeClass.LargeClassDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.largeMethod.LargeMethodDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.messageChain.MessageChainDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.switchStatement.SwitchDetector;
import edu.pdx.cs.multiview.smelldetector.detectors.typecast.TypecastDetector;
import edu.pdx.cs.multiview.smelldetector.ui.Flower;

public class SmellDetectorManager {

	private static Comparator<SmellDetector<?>> comparator = new Comparator<SmellDetector<?>>(){
		public int compare(SmellDetector<?> s1, SmellDetector<?> s2) {
			return (int)(1000*(s1.order() - s2.order()));
		}};
		
	private static Flower flower;
	private static Map<SmellDetector<?>, Color> smells;
	
	public Map<SmellDetector<?>,Color> smells(Flower f) {
		
		if(flower!=f){
			dispose();
			initSmells(f);
		}else if(smells==null){
			initSmells(f);
		}
		
		return smells;
	}

	private void initSmells(Flower f) {
		ArrayList<SmellDetector<?>> list = new ArrayList<SmellDetector<?>>();
		list.add(new LargeMethodDetector(f));
		list.add(new FeatureEnvyDetector(f));
		list.add(new LargeClassDetector(f));
		list.add(new DataClumpDetector(f));
		list.add(new TypecastDetector(f));
		list.add(new InstanceoftDetector(f));
		list.add(new SwitchDetector(f));
		list.add(new MessageChainDetector(f));
		
		//addStubs(f, list);
		
		Collections.sort(list, comparator);
		
		smells = mapColorsOnto(list);
	}
	
	public static void dispose(){
		
		if(smells==null)
			return;
		
		for(Color c : smells.values())
			c.dispose();
		smells.clear();
		smells = null;
	}	
	
	private Map<SmellDetector<?>, Color> mapColorsOnto(List<SmellDetector<?>> detectors) {
		
		Map<SmellDetector<?>, Color> map = new TreeMap<SmellDetector<?>, Color>(comparator);
		
		int i = 0;
		for(Color c : ColorManager.gradient(detectors.size()))
			map.put(detectors.get(i++),c);
		
		return map;
	}
}
