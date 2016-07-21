package de.uni.freiburg.iig.telematik.sepia.petrinet.properties.threaded;

import de.invation.code.toval.thread.AbstractCallable;
import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;

/**
 * 执行任务的Callable，在父类AbstractCallable的callRoutine()中封装执行任务的代码
 * 
 * @author Administrator
 *
 * @param <P>  Place类型
 * @param <T>  Transition类型
 * @param <F>  FlowRelation类型
 * @param <M>  Marking类型
 * @param <S>  Token或FlowRelation类型
 * @param <R>  计算结果类型
 */
public abstract class AbstractPNPropertyCheckerCallable<P extends AbstractPlace<F,S>, 
														T extends AbstractTransition<F,S>, 
														F extends AbstractFlowRelation<P,T,S>, 
														M extends AbstractMarking<S>, 
														S extends Object,
														R> 
                                                     extends AbstractCallable<R> {

	private AbstractCallableGenerator<P,T,F,M,S> generator = null;
	
	protected AbstractPNPropertyCheckerCallable(AbstractCallableGenerator<P,T,F,M,S> generator){
		Validate.notNull(generator);
		this.generator = generator;
	}
	
	protected AbstractCallableGenerator<P,T,F,M,S> getGenerator(){
		return generator;
	}
}
