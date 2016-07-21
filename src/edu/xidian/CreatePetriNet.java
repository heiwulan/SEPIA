package edu.xidian;

import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTNet;

public class CreatePetriNet {
	static PTNet createPTnet1() {
		PTNet ptnet = new PTNet();
		
		ptnet.addPlace("p1");
		ptnet.addPlace("p2");
		ptnet.addTransition("t1");
		ptnet.addTransition("t2");
		ptnet.addTransition("t3");
		ptnet.addFlowRelationPT("p1", "t1",2);
		ptnet.addFlowRelationTP("t1", "p2",1);
		ptnet.addFlowRelationPT("p2", "t3",1);
		ptnet.addFlowRelationPT("p1", "t2",1);
		ptnet.addFlowRelationTP("t2", "p2",1);
		
		PTMarking marking = new PTMarking();
		marking.set("p1", 2);
		ptnet.setInitialMarking(marking);
		//System.out.println(ptnet);
		return ptnet;
	}
}
