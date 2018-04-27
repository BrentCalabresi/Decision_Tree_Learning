package dt.example;

import dt.core.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class IrisProblem extends Problem{

    public IrisProblem(){
        super();
        // Input variables
        Domain yesNoDomain = new YesNoDomain();
        Domain classification = new Domain("Iris Setosa", "Iris Versicolour", "Iris Virginica");

        this.inputs.add(new Variable("Sepal length",  new Domain("S","MS","ML","L")));
        this.inputs.add(new Variable("Sepal width",  new Domain("S","MS","ML","L")));
        this.inputs.add(new Variable("Petal length",  new Domain("S","MS","ML","L")));
        this.inputs.add(new Variable("Petal width",  new Domain("S","MS","ML","L")));

        // Output variable
        this.output = new Variable("Classification", classification);
    }

    public static void main(String[] args) {
        Problem problem = new IrisProblem();
        problem.dump();
        Set<Example> examples = null;
        try {
            examples = problem.readExamplesFromCSVFile(new File("iris.data.discrete.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Example e : examples) {
            System.out.println(e);
        }
        DecisionTree tree = new DecisionTreeLearner(problem).learn(examples);

        tree.dump();
        tree.test(examples);
    }
}
