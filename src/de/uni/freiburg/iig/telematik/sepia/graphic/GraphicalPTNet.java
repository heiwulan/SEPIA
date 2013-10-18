package de.uni.freiburg.iig.telematik.sepia.graphic;

import de.uni.freiburg.iig.telematik.sepia.graphic.netgraphics.AbstractPNGraphics;
import de.uni.freiburg.iig.telematik.sepia.graphic.netgraphics.AbstractPTGraphics;
import de.uni.freiburg.iig.telematik.sepia.petrinet.AbstractPetriNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTTransition;

/**
 * Container class with a {@link PTNet} and its graphical information as {@link AbstractPTGraphics}.
 * 
 * @author Thomas Stocker
 * @author Adrian Lange
 */
public class GraphicalPTNet extends AbstractGraphicalPN<PTPlace, PTTransition, PTFlowRelation, PTMarking, Integer> {

	public GraphicalPTNet(AbstractPetriNet<PTPlace, PTTransition, PTFlowRelation, PTMarking, Integer> petriNet, AbstractPNGraphics<PTPlace, PTTransition, PTFlowRelation, PTMarking, Integer> petriNetGraphics) {
		super(petriNet, petriNetGraphics);
	}

	@Override
	public PTNet getPetriNet() {
		return (PTNet) super.getPetriNet();
	}

	@Override
	public AbstractPTGraphics getPetriNetGraphics() {
		return (AbstractPTGraphics) super.getPetriNetGraphics();
	}
}
