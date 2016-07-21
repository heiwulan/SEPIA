package de.uni.freiburg.iig.telematik.sepia.petrinet.properties.threaded;

import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPetriNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;

/**
 * 数据核心(Generator),封装PetriNet成员,是运行任务的核心
 * AbstractPetriNet<P,T,F,M,S> petriNet;
 * @author Administrator
 *
 * @param <P>  Place类型
 * @param <T>  Transition类型
 * @param <F>  FlowRelation类型
 * @param <M>  Marking类型
 * @param <S>  Token或FlowRelation类型
 */
public abstract class AbstractCallableGenerator<P extends AbstractPlace<F,S>, 
												T extends AbstractTransition<F,S>, 
												F extends AbstractFlowRelation<P,T,S>, 
												M extends AbstractMarking<S>, 
												S extends Object> {
	
	private AbstractPetriNet<P,T,F,M,S> petriNet = null;
	
	protected AbstractCallableGenerator(AbstractPetriNet<P,T,F,M,S> petriNet){
		Validate.notNull(petriNet);
		this.petriNet = petriNet;
	}
	
	/**
	 * 复制数据核心
	 * @param generator 数据核心
	 */
	protected <N extends AbstractCallableGenerator<P,T,F,M,S>> AbstractCallableGenerator(N generator){
		Validate.notNull(generator);
		this.petriNet = generator.getPetriNet();
	}
	
	/**
	 * 获取数据核心，AbstractPetriNet<P,T,F,M,S> petriNet
	 * @return
	 */
	public AbstractPetriNet<P,T,F,M,S> getPetriNet() {
		return petriNet;
	}

}
