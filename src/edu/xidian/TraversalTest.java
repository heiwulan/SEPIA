package edu.xidian;

import de.invation.code.toval.validate.InconsistencyException;
import de.uni.freiburg.iig.telematik.sepia.exception.PNException;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTTransition;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.traverse.RandomPTTraverser;
import de.uni.freiburg.iig.telematik.sepia.traversal.PNTraversalUtils;

public class TraversalTest {

	/**
	 * 随机遍历
	 */
	static void RandomPTTraverserTest(PTNet ptnet) {		
		System.out.println("===Petri Net Traversal====");
		RandomPTTraverser t = new RandomPTTraverser(ptnet);
		for (int i = 1; ptnet.hasEnabledTransitions(); i++) {
			System.out.println(i + ": " + ptnet.getEnabledTransitions().size()); // 1: 2
			                                                                     // 2: 1
			try {
				PTTransition tran = t.chooseNextTransition(ptnet.getEnabledTransitions());
				System.out.println("chooseNextTransition: " + tran);
				// t.chooseNextTransition(ptnet.getEnabledTransitions()).fire();
				tran.fire();
			} catch (InconsistencyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(ptnet);
		}
		System.out.println("no more enabled transitions");
	}
	
	public static void main(String[] args) {
		PTNet ptnet = CreatePetriNet.createPTnet1();
		System.out.println("ptnet\n" + ptnet);
		
		//RandomPTTraverserTest((PTNet) ptnet.clone());
		RandomPTTraverserTest(ptnet);
		
		System.out.println(ptnet.getEnabledTransitions().size()); 
		
		// simulates the Petri net ptnet 10 times and prints out all distinct traces. Thereby it
		// limits the number of activities per sequence to 100.
		PNTraversalUtils.testTraces(ptnet, 10, 100, true,false,false);
	}
}
