package de.uni.freiburg.iig.telematik.sepia.traversal;

import java.util.HashMap;
import java.util.List;

import de.invation.code.toval.misc.valuegeneration.StochasticValueGenerator;
import de.invation.code.toval.misc.valuegeneration.ValueGenerationException;
import de.invation.code.toval.validate.InconsistencyException;
import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPetriNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;

/**<pre>
 * 根据概率选择后一次变迁。
 * This flow control chooses the next transition to fire 
 * on the basis of predefined probabilities of occurrences of subsequent transition pairs.
 * chooseNextTransition()获取后一个变迁的规则：
 * 如果没有发射变迁过，随机选择一个变迁t，做为第一个变迁
 * 从t到t1，t2,t3的概率分别为0.2,0.3,0.5,总和必须为1.
 * 【addFlowProbability("t","t1",0.2); addFlowProbability("t","t2",0.3); addFlowProbability("t","t3",0.5);】
 * 根据概率，选择t之后的变迁，最大可能性是t3

 * 
 * @author Thomas Stocker
 *
 */
public class StochasticPNTraverser<T extends AbstractTransition<?,?>> extends RandomPNTraverser<T> {
	
	/** 缺省容差分母 */
	public static final int DEFAULT_TOLERANCE_DENOMINATOR = 1000;
	/** 变迁T*/
	private HashMap<T, StochasticValueGenerator<T>> flowProbabilities = new HashMap<T, StochasticValueGenerator<T>>();
	private int toleranceDenominator;
	/**
	 * 构造基于变迁概率的随机遍历，缺省容差分母：{@link #DEFAULT_TOLERANCE_DENOMINATOR}
	 * @param net
	 */
	public StochasticPNTraverser(AbstractPetriNet<?,T,?,?,?> net) {
		this(net, DEFAULT_TOLERANCE_DENOMINATOR);
	}

	/**
	 * 构造基于变迁概率的随机遍历
	 * @param net
	 * @param toleranceDenominator 容差分母
	 */
	public StochasticPNTraverser(AbstractPetriNet<?,T,?,?,?> net, int toleranceDenominator) {
		super(net);
		Validate.biggerEqual(toleranceDenominator, 1, "Denominator must be >=1.");
		this.toleranceDenominator = toleranceDenominator;
	}
	
	/**
	 * probability fromTransition to toTransition.
	 * fromTransition开始到各个toTransition(s)的概率之和为1，如
	 * addFlowProbability("t","t1",0.2); addFlowProbability("t","t2",0.3); addFlowProbability("t","t3",0.5);
	 * @param fromTransitionID
	 * @param toTransitionID
	 * @param probability
	 */
	public void addFlowProbability(String fromTransitionID, String toTransitionID, double probability) {
		addFlowProbability(net.getTransition(fromTransitionID), net.getTransition(toTransitionID), probability);
	}
	
	/**
	 * probability fromTransition to toTransition.
	 * fromTransition开始到各个toTransition(s)的概率之和为1，如
	 * addFlowProbability("t","t1",0.2); addFlowProbability("t","t2",0.3); addFlowProbability("t","t3",0.5);
	 * @param fromTransition  
	 * @param toTransition
	 * @param probability 0.0 to 1.0
	 */
	public void addFlowProbability(T fromTransition, T toTransition, double probability) {
		Validate.notNull(fromTransition);
		Validate.notNull(toTransition);
		Validate.inclusiveBetween(0.0, 1.0, probability);
		StochasticValueGenerator<T> chooser = flowProbabilities.get(fromTransition);
		if(chooser == null){
			chooser = new StochasticValueGenerator<T>(toleranceDenominator);
			flowProbabilities.put(fromTransition, chooser);
		}
		chooser.addProbability(toTransition, probability);
	}

	/**
	 * 如果没有变迁发射过，随机选择一个变迁t，最为第一次变迁
	 * 从t到t1，t2,t3的概率分别为0.2,0.3,0.5,总和必须为1,
	 * 根据概率，选择t之后的变迁，最大可能性是t3
	 */
	@Override
	public T chooseNextTransition(List<T> enabledTransitions) throws InconsistencyException {
		if(!flowProbabilities.containsKey(net.getLastFiredTransition()))  
			return super.chooseNextTransition(enabledTransitions); // 随机选择一个变迁作为第一个变迁
		if(!isValid())
			throw new InconsistencyException("At least one StochasticChooser is not valid.");
		Validate.notNull(enabledTransitions);
		Validate.noNullElements(enabledTransitions);
		
		if(enabledTransitions.isEmpty())
			return null;
		
		T nextTransition = null;
		try {
			// 根据概率选择最后一次变迁的后一次变迁
			nextTransition = flowProbabilities.get(net.getLastFiredTransition()).getNextValue();
		} catch (ValueGenerationException e) {
			// Cannot happen, since all choosers are valid.
			e.printStackTrace();
		}
		if(!net.getEnabledTransitions().contains(nextTransition))
			throw new InconsistencyException("Cannot fire transition \""+nextTransition+"\" since it is not enabled.");
		return nextTransition;
	}
	
	/**
	 * Checks, if all maintained stochastic choosers are valid.
	 * @return <code>true</code> if all choosers are valid,<br>
	 * <code>false</code> otherwise.
	 * @see StochasticValueGenerator#isValid()
	 */
	@Override
	public boolean isValid(){
		for(StochasticValueGenerator<T> chooser: flowProbabilities.values())
			if(!chooser.isValid())
				return false;
		return true;
	}
}
