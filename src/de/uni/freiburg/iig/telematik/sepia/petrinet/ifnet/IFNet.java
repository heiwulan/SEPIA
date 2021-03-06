package de.uni.freiburg.iig.telematik.sepia.petrinet.ifnet;

import java.util.Set;

import de.invation.code.toval.types.Multiset;
import de.uni.freiburg.iig.telematik.sepia.event.CapacityEvent;
import de.uni.freiburg.iig.telematik.sepia.mg.ifnet.IFNetMarkingGraph;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.ifnet.abstr.AbstractIFNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.ifnet.abstr.AbstractIFNetTransition;


public class IFNet extends AbstractIFNet<IFNetPlace, 
										 AbstractIFNetTransition<IFNetFlowRelation>, 
										 IFNetFlowRelation, 
										 IFNetMarking, 
										 RegularIFNetTransition, 
										 DeclassificationTransition> {

	private static final long serialVersionUID = -2155147434115176455L;

	public IFNet() {
		super();
	}

	public IFNet(Set<String> places, Set<String> transitions, IFNetMarking initialMarking) {
		super(places, transitions, initialMarking);
	}

	@Override
	public IFNet newInstance() {
		return new IFNet();
	}
	
	@Override
	public IFNetMarking createNewMarking() {
		return new IFNetMarking();
	}	

	@Override
	protected IFNetPlace createNewPlace(String name, String label) {
		return new IFNetPlace(name, label);
	}
	
	@Override
	protected RegularIFNetTransition createNewRegularTransition(String name, String label, boolean isSilent) {
		return new RegularIFNetTransition(name, label, isSilent);
	}

	@Override
	protected DeclassificationTransition createNewDeclassificationTransition(String name, String label, boolean isSilent) {
		return new DeclassificationTransition(name, label, isSilent);
	}

	@Override
	protected AbstractIFNetTransition<IFNetFlowRelation> createNewTransition(String name, String label, boolean isSilent) {
		return createNewRegularTransition(name, label, isSilent);
	}




	@Override
	protected IFNetFlowRelation createNewFlowRelation(IFNetPlace place, AbstractIFNetTransition<IFNetFlowRelation> transition, Multiset<String> constraint) {
		return new IFNetFlowRelation(place, transition, constraint);
	}

	@Override
	protected IFNetFlowRelation createNewFlowRelation(AbstractIFNetTransition<IFNetFlowRelation> transition, IFNetPlace place, Multiset<String> constraint) {
		return new IFNetFlowRelation(transition, place, constraint);
	}

	@Override
	protected IFNetFlowRelation createNewFlowRelation(IFNetPlace place, AbstractIFNetTransition<IFNetFlowRelation> transition) {
		return new IFNetFlowRelation(place, transition);
	}

	@Override
	protected IFNetFlowRelation createNewFlowRelation(AbstractIFNetTransition<IFNetFlowRelation> transition, IFNetPlace place) {
		return new IFNetFlowRelation(transition, place);
	}

	@Override
	public void capacityChanged(CapacityEvent<? extends AbstractPlace<IFNetFlowRelation, Multiset<String>>> o) {}

	@Override
	public Class<?> getMarkingGraphClass() {
		return IFNetMarkingGraph.class;
	}
	
}
