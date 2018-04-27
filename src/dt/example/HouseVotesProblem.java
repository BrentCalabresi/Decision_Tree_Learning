package dt.example;

import dt.core.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class HouseVotesProblem extends Problem {

    public HouseVotesProblem() {
        super();
        Domain yesNoQDomain = new YesNoQDomain();
        Domain partyDomain = new PartyDomain();
        // input vars
        this.inputs.add(new Variable("handicapped-infants", yesNoQDomain));
        this.inputs.add(new Variable("water-project-cost-sharing", yesNoQDomain));
        this.inputs.add(new Variable("adoption-of-the-budget-resolution", yesNoQDomain));
        this.inputs.add(new Variable("physician-fee-freeze", yesNoQDomain));
        this.inputs.add(new Variable("el-salvador-aid", yesNoQDomain));
        this.inputs.add(new Variable("religious-groups-in-schools", yesNoQDomain));
        this.inputs.add(new Variable("anti-satellite-test-ban", yesNoQDomain));
        this.inputs.add(new Variable("aid-to-nicaraguan-contras", yesNoQDomain));
        this.inputs.add(new Variable("mx-missile", yesNoQDomain));
        this.inputs.add(new Variable("immigration", yesNoQDomain));
        this.inputs.add(new Variable("synfuels-corporation-cutback", yesNoQDomain));
        this.inputs.add(new Variable("education-spending", yesNoQDomain));
        this.inputs.add(new Variable("superfund-right-to-sue", yesNoQDomain));
        this.inputs.add(new Variable("crime", yesNoQDomain));
        this.inputs.add(new Variable("duty-free-exports", yesNoQDomain));
        this.inputs.add(new Variable("export-administration-act-south-africa", yesNoQDomain));

        this.output = new Variable("party", partyDomain);
    }

    public static void main(String[] args) throws IOException{
        String filepath = "house-votes-84.data.mod.txt";
        Problem problem = new HouseVotesProblem();
        problem.dump();
//		Set<Example> examples = problem.readExamplesFromCSVFile(new File(args[0]));
        Set<Example> examples = problem.readExamplesFromCSVFile(new File(filepath));
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
