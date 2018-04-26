package dt.example;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import dt.core.DecisionTree;
import dt.core.DecisionTreeLearner;
import dt.core.Domain;
import dt.core.Example;
import dt.core.Problem;
import dt.core.Variable;
import dt.core.YesNoDomain;

/**
 * The restaurant WillWait example from AIMA Section 18.3, data
 * from file WillWait-data.txt.
 * <p>
 * Run and pass dataset filename on cmd-line.
 */
public class WillWaitProblem extends Problem {
	
	public WillWaitProblem() {
		super();
		// Input variables
		Domain yesNoDomain = new YesNoDomain();
		this.inputs.add(new Variable("Alternate", yesNoDomain));
		this.inputs.add(new Variable("Bar", yesNoDomain));
		this.inputs.add(new Variable("Fri/Sat", yesNoDomain));
		this.inputs.add(new Variable("Hungry", yesNoDomain));
		this.inputs.add(new Variable("Patrons", new Domain("None", "Some", "Full")));
		this.inputs.add(new Variable("Price",  new Domain("$", "$$", "$$$")));
		this.inputs.add(new Variable("Raining", yesNoDomain));
		this.inputs.add(new Variable("Reservation", yesNoDomain));
		this.inputs.add(new Variable("Type",  new Domain("French", "Italian", "Thai", "Burger")));
		this.inputs.add(new Variable("WaitEstimate",  new Domain("0-10", "10-30", "30-60", ">60")));
		// Output variable
		this.output = new Variable("WillWait", yesNoDomain);
	}
	
	public static void main(String[] args) throws IOException {
		Problem problem = new WillWaitProblem();
		problem.dump();
//		Set<Example> examples = problem.readExamplesFromCSVFile(new File(args[0]));
		Set<Example> examples = problem.readExamplesFromCSVFile(new File("C:/Users/16kin/Documents/CSC 242/Project 4/Decision_Tree_Learning/src/dt/example/WillWait-data.txt"));
		for (Example e : examples) {
			System.out.println(e);
		}
		DecisionTree tree = new DecisionTreeLearner(problem).learn(examples);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		tree.dump();
		tree.test(examples);
	}

}
