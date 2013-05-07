package petrinet.transform;

import petrinet.AbstractFlowRelation;
import petrinet.AbstractMarking;
import petrinet.AbstractPetriNet;
import petrinet.AbstractPlace;
import petrinet.AbstractTransition;
import validate.ParameterException;
import validate.Validate;

public abstract class PNTransformer<P extends AbstractPlace<F,S>, 
						   			T extends AbstractTransition<F,S>, 
						   			F extends AbstractFlowRelation<P,T,S>, 
						   			M extends AbstractMarking<S>, 
						   			S extends Object>  {
	
	protected AbstractPetriNet<P,T,F,M,S> net = null;
	
	public PNTransformer(AbstractPetriNet<P,T,F,M,S> net) throws ParameterException{
		Validate.notNull(net);
		this.net = net;
	}
	
	public abstract AbstractPetriNet<P,T,F,M,S> applyTransformation();

}
