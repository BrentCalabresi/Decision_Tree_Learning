package dt.core;

import java.lang.reflect.Array;
import java.util.*;

import dt.util.ArraySet;

/**
 * Implementation of the decision-tree learning algorithm in AIMA Fig 18.5.
 * This is based on ID3 (AIMA p. 758).
 */
public class DecisionTreeLearner extends AbstractDecisionTreeLearner {
	
	/**
	 * Construct and return a new DecisionTreeLearner for the given Problem.
	 */
	public DecisionTreeLearner(Problem problem) {
		super(problem);
	}
	
	/**
	 * Main recursive decision-tree learning (ID3) method.  
	 */
	@Override
	protected DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parent_examples) {
	    // Must be implemented by you; the following two methods may be useful
		/**
		 *
		 * if examples is empty then return P LURALITY -V ALUE (parent examples)
		 * else if all examples have the same classification then return the classification
		 * else if attributes is empty then return P LURALITY -V ALUE (examples)
		 * else
		 * A argmax a attributes I MPORTANCE (a, examples)
		 * tree â† a new decision tree with root test A
		 * for each value v k of A do
		 * exs {e : e examples and e.A = v k }
		 * subtree D ECISION -T REE -L EARNING (exs, attributes’ A, examples)
		 * add a branch to tree with label (A = v k ) and subtree subtree
		 * return tree
		 *
		 */
		
		if (examples.isEmpty()){
			String pv = pluralityValue(parent_examples);
			return new DecisionTree(pv);
		}
		String uov;
		if ((uov = uniqueOutputValue(examples)) != null){
			return new DecisionTree(uov);
		}
		if (attributes.isEmpty()){
			String pv = pluralityValue(examples);
			return new DecisionTree(pv);
		}
		
		Variable A = mostImportantVariable(attributes, parent_examples);
		DecisionTree tree = new DecisionTree(A);
		for (String vk : A.domain){
			Set<Example> exs = examplesWithValueForAttribute(examples, A, vk);
			List<Variable> attMinA = removeVariable(attributes, A);
			DecisionTree subtree = learn(exs, attMinA, examples);
			tree.children.add(subtree);
		}
		return tree;
	}
	
	protected List<Variable> removeVariable(List<Variable> varList, Variable v){
		List<Variable> listMinA = new LinkedList<>();
		for (Variable var : varList){
			if (var.equals(v)){
				continue;
			}
			listMinA.add(var);
		}
		return listMinA;
	}
	
	/**
	 * Returns the most common output value among a set of Examples,
	 * breaking ties randomly.
	 * I don't do the random part yet.
	 */
	@Override
	protected String pluralityValue(Set<Example> examples) {//todo this was written kinda late and may be pretty jank
	    // Must be implemented by you

		HashMap<String, Integer> outputs = new HashMap<>();
		for (Example e: examples){
			if (!outputs.keySet().contains(e.getOutputValue()))//if our map doesn't contain one of the output values already
				outputs.put(e.outputValue,1);//initialize it with a frequency of 1

			else{//if the output value already exists in the map
//				int newVal = 1 + outputs.get(e.getOutputValue());//current number of occurrences (old # +1)
//				outputs.remove(e.getOutputValue());
				outputs.put(e.getOutputValue(),outputs.get(e.getOutputValue()) + 1);// increment number of occurrences of this example
			}
		}//we should now have a map of unique output strings, each mapped to their respective number of occurrences

//		for (String s: outputs.keySet()){
//			for (String s2: outputs.keySet()){
//				if (outputs.get(s) < outputs.get(s2))
//					outputs.remove(s);	//trim down map so it only contains the most frequent strings
//			}
//		}

		if (outputs.size() == 0) {
			throw new RuntimeException("outputs from plurality method is empty");
		}

		Iterator<String> outputIter = outputs.keySet().iterator();
		String mostFrequent = outputIter.next();
		while (outputIter.hasNext()) {
			String val = outputIter.next();
			if (outputs.get(val) > outputs.get(mostFrequent)) mostFrequent = val;
		}

//		ArrayList<String> values = (ArrayList<String>) outputs.keySet();
//		String mostFrequent = values.get(new Random().nextInt(values.size()));

		return mostFrequent;
}
	
	/**
	 * Returns the single unique output value among the given examples
	 * is there is only one, otherwise null.
	 */
	//todo this needs to be fixed?
	@Override
	protected String uniqueOutputValue(Set<Example> examples) {
	    // Must be implemented by you

		ArrayList<String> outputs = new ArrayList<>();

		for (Example e: examples){
			if (!outputs.contains(e.getOutputValue()))
				outputs.add(e.getOutputValue());
		}
		if (outputs.size() > 1)
			return null;
		else
			return outputs.get(0);
	}
	
	//
	// Utility methods required by the AbstractDecisionTreeLearner
	//

	/**
	 * Return the subset of the given examples for which Variable a has value vk.
	 */
	//TODO not sure about this method

	@Override
	protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
	    // Must be implemented by you

		Set<Example> values = new HashSet<>();
		for (Example e: examples){
			if (e.inputValues.keySet().contains(a)){
				if (a.domain.contains(vk))
					values.add(e);
			}
		}
		return values;
	}
	
	/**
	 * Return the number of the given examples for which Variable a has value vk.
	 */
	@Override
	protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
		int result = 0;
		for (Example e : examples) {
			if (e.getInputValue(a).equals(vk)) {
				result += 1;
			}
		}
		return result;
		
	}

	/**
	 * Return the number of the given examples for which the output has value vk.
	 */
	@Override
	protected int countExamplesWithValueForOutput(Set<Example> examples, String vk) {
	    // Must be implemented by you
		int result = 0;
		for (Example e: examples){
			if (e.getOutputValue().equals(vk)){
				result++;
			}
		}
		return result;
	}

}
