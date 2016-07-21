package de.uni.freiburg.iig.telematik.sepia.petrinet.properties.threaded;

import de.invation.code.toval.thread.SingleThreadExecutorService;
import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;

/**
 * 独立线程执行任务。
 * 任务封装在：AbstractPNPropertyCheckerCallable的callRoutine()中。
 * 子类实现SingleThreadExecutorService的createCallable(),使其返回执行上述任务的Callable对象。
 * 通过getGenerator()获取数据核心PetriNet
 * 
 * @author Administrator
 *
 * @param <P>  Place类型
 * @param <T>  Transition类型
 * @param <F>  FlowRelation类型
 * @param <M>  Marking类型
 * @param <S>  Token或FlowRelation类型
 * @param <V>  计算结果类型
 * @param <Z>  计算结果类型
 * @param <E>  异常类型
 */
public abstract class AbstractThreadedPNPropertyChecker<P extends AbstractPlace<F,S>, 
														T extends AbstractTransition<F,S>, 
														F extends AbstractFlowRelation<P,T,S>, 
														M extends AbstractMarking<S>, 
														S extends Object,
														V,Z,E extends Exception> 
                                                     extends SingleThreadExecutorService<V,Z,E>{
	
	private AbstractCallableGenerator<P,T,F,M,S> generator = null;
	
	protected AbstractThreadedPNPropertyChecker(AbstractCallableGenerator<P,T,F,M,S> generator){
		super();
		Validate.notNull(generator);
		this.generator = generator;
	}
	
	/** 获取数据核心*/
	protected AbstractCallableGenerator<P,T,F,M,S> getGenerator(){
		return generator;
	}
	
	/**
	 * 开启线程，执行任务。
	 */
	public final void runCalculation() {
		setUpAndRun();
	}

}
