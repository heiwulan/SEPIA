package de.uni.freiburg.iig.telematik.sepia.petrinet.properties.mg;

import de.invation.code.toval.thread.AbstractCallable;
import de.invation.code.toval.thread.ExecutorListener;
import de.uni.freiburg.iig.telematik.sepia.mg.abstr.AbstractMarkingGraph;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;

/**
 * 接口：计算标识图（MarkingGraph）
 * @author Administrator
 *
 * @param <P>  Place类型
 * @param <T>  Transition类型
 * @param <F>  FlowRelation类型
 * @param <M>  Marking类型
 * @param <S>  Token或FlowRelation类型
 */
public interface MGCalculator<	P extends AbstractPlace<F,S>, 
								T extends AbstractTransition<F,S>, 
								F extends AbstractFlowRelation<P,T,S>, 
								M extends AbstractMarking<S>, 
								S extends Object> {
	/** 获取标识图 */
	public AbstractMarkingGraph<M,S,?,?> getMarkingGraph() throws MarkingGraphException;
	/** 计算，执行任务 */
	public void runCalculation();
	/** 返回执行任务的Callable */
	public AbstractCallable<AbstractMarkingGraph<M,S,?,?>> createCallable();
	/** 添加任务执行情况的监听 */
	public void addExecutorListener(ExecutorListener<AbstractMarkingGraph<M,S,?,?>> listener);
	/** 取消任务执行情况的监听 */
	public void removeExecutorListener(ExecutorListener<AbstractMarkingGraph<M,S,?,?>> listener);

}
