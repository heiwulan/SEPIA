package de.uni.freiburg.iig.telematik.sepia.petrinet.properties.mg;

import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPetriNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;
import de.uni.freiburg.iig.telematik.sepia.petrinet.properties.boundedness.BoundednessCheckGenerator;
import de.uni.freiburg.iig.telematik.sepia.petrinet.properties.threaded.AbstractCallableGenerator;

/**
 * 
 * @author Administrator
 *
 * @param <P>  Place类型
 * @param <T>  Transition类型
 * @param <F>  FlowRelation类型
 * @param <M>  Marking类型
 * @param <S>  Token或FlowRelation类型
 */
public class MGConstructorCallableGenerator<P extends AbstractPlace<F,S>, 
											T extends AbstractTransition<F,S>, 
											F extends AbstractFlowRelation<P,T,S>, 
											M extends AbstractMarking<S>, 
											S extends Object> 
                                          extends AbstractCallableGenerator<P,T,F,M,S> {

	public MGConstructorCallableGenerator(AbstractPetriNet<P,T,F,M,S> petriNet) {
		super(petriNet);
	}
	
	public MGConstructorCallableGenerator(BoundednessCheckGenerator<P,T,F,M,S> generator){
		super(generator.getPetriNet());
	}

}
