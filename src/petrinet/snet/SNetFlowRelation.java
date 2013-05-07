package petrinet.snet;

import petrinet.cwn.abstr.AbstractCWNFlowRelation;
import validate.ParameterException;

public class SNetFlowRelation extends AbstractCWNFlowRelation<SNetPlace, AbstractSNetTransition> {

	public SNetFlowRelation(SNetPlace place, AbstractSNetTransition transition) throws ParameterException {
		super(place, transition);
	}

	public SNetFlowRelation(AbstractSNetTransition transition, SNetPlace place) throws ParameterException {
		super(transition, place);
	}

	@Override
	public String toPNML(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SNetFlowRelation clone(SNetPlace place, AbstractSNetTransition transition, boolean directionPT) {
		SNetFlowRelation result = null;
		try{
			result = new SNetFlowRelation(place, transition);
			result.setDirectionPT(directionPT);
		} catch(ParameterException e){
			e.printStackTrace();
		}
		return result;
	}
	
}
