package de.uni.freiburg.iig.telematik.sepia.petrinet.properties.mg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import de.uni.freiburg.iig.telematik.sepia.mg.abstr.AbstractMarkingGraph;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;
import de.uni.freiburg.iig.telematik.sepia.petrinet.properties.threaded.AbstractPNPropertyCheckerCallable;

/**
 * 封装计算标识图(MarkingGraph)的Callable
 * 计算任务在callRoutine()中完成。
 * 计算结果类型是标识图AbstractMarkingGraph<M,S,?,?>
 * @author Administrator
 *
 * @param <P>  Place类型
 * @param <T>  Transition类型
 * @param <F>  FlowRelation类型
 * @param <M>  Marking类型
 * @param <S>  Token或FlowRelation类型
 */
public class MGConstructorCallable< P extends AbstractPlace<F,S>, 
									T extends AbstractTransition<F,S>, 
									F extends AbstractFlowRelation<P,T,S>, 
									M extends AbstractMarking<S>, 
									S extends Object> 
            extends AbstractPNPropertyCheckerCallable<P,T,F,M,S,AbstractMarkingGraph<M,S,?,?>> {
	
	private static final String rgGraphNodeFormat = "s%s";
	
	public MGConstructorCallable(MGConstructorCallableGenerator<P,T,F,M,S> generator){
		super(generator);
	}
	
	/**
	 * 计算标识图（MarkingGraph）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AbstractMarkingGraph<M,S,?,?> callRoutine() throws MarkingGraphException, InterruptedException {
		// 保存当前网标识(Marking)
		M savedMarking = (M) getGenerator().getPetriNet().getMarking().clone();
		// 保存标识的队列
		ArrayBlockingQueue<M> queue = new ArrayBlockingQueue<M>(10);
		// 已知标识集（状态集）
		Set<M> allKnownStates = new HashSet<M>();  

		allKnownStates.add(getGenerator().getPetriNet().getInitialMarking()); // add 初始标识 至已知标识集
		AbstractMarkingGraph<M,S,?,?> markingGraph = null;  
		try{
			markingGraph = (AbstractMarkingGraph<M,S,?,?>) getGenerator().getPetriNet().getMarkingGraphClass().newInstance();
		} catch (Exception e) {
			throw new MarkingGraphException("Cannot create new instance of marking graph class", e);
		}
		int stateCount = 0;
		// <标识.toString(),状态名称>，状态名称：即图的顶点名称，每一标识Marking对应一个状态State
		Map<String, String> stateNames = new HashMap<String, String>(); 
		M initialMarking = getGenerator().getPetriNet().getInitialMarking(); // 初始状态，标识
		queue.offer(initialMarking); // 初始标识进入队列
		String stateName = String.format(rgGraphNodeFormat, stateCount++); // 状态名称，s0(初始标识),s1,s2...
		markingGraph.addState(stateName, (M) initialMarking.clone());
		markingGraph.setInitialState(stateName);
		markingGraph.addStartState(stateName);
		stateNames.put(initialMarking.toString(), stateName);
		allKnownStates.add((M) initialMarking.clone());
		
		int calculationSteps = 0;
		try {
			while (!queue.isEmpty()) {
				if (Thread.currentThread().isInterrupted()) {
					throw new InterruptedException();
				}
				calculationSteps++;
				if((calculationSteps >= MGConstruction.MAX_RG_CALCULATION_STEPS)){
					getGenerator().getPetriNet().setMarking(savedMarking);
					throw new StateSpaceException("Reached maximum calculation steps for building marking graph.");
				}
				M nextMarking = queue.poll(); // 出队列
				getGenerator().getPetriNet().setMarking(nextMarking);
//				M marking = (M) nextMarking.clone();
				String nextStateName = stateNames.get(nextMarking.toString());
//				System.out.println("Next marking (" + nextStateName + "): " + nextMarking);

				if(getGenerator().getPetriNet().hasEnabledTransitions()){ // 如果有使能变迁
					String newStateName = null;
					for (T enabledTransition : getGenerator().getPetriNet().getEnabledTransitions()) {

//						System.out.println("   enabled: " + enabledTransition.getName());
						M newMarking = getGenerator().getPetriNet().fireCheck(enabledTransition.getName());
						
						// Check if this marking is already known
						M equalMarking = null;
						for(M storedMarking: allKnownStates){
							if(storedMarking.equals(newMarking)){
								equalMarking = storedMarking;
								break;
							}
						}

//						System.out.println("   new marking: " + newMarking);
						if(equalMarking == null) {
							// This is a new marking
//							System.out.println("   -> New marking");
							queue.offer(newMarking); // 入列
							allKnownStates.add((M) newMarking.clone());
							newStateName = String.format(rgGraphNodeFormat, stateCount++);
							markingGraph.addState(newStateName, (M) newMarking.clone());
							stateNames.put(newMarking.toString(), newStateName);
						} else {
							// This marking is already known
//							System.out.println("   -> Known marking");
							newStateName = stateNames.get(newMarking.toString());
						}
						if (!markingGraph.containsEvent(enabledTransition.getName())) {
							markingGraph.addEvent(enabledTransition.getName(), enabledTransition.getLabel());
						}
//						System.out.println("   add relation: " + nextStateName + " to " + newStateName + " via " + enabledTransition.getName());
						markingGraph.addRelation(nextStateName, newStateName, enabledTransition.getName());
					}
				} else { // 没有使能变迁
					markingGraph.addEndState(nextStateName);
				}
			}
		} catch(InterruptedException e){
			throw e;
		} catch (Exception e) {
			throw new MarkingGraphException("Exception during marking graph construction.<br>Reason: " + e.getMessage(), e);
		}
		
		// 恢复网标识到原来的状态
		getGenerator().getPetriNet().setMarking(savedMarking);
		return markingGraph;
	}

}
