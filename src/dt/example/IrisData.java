package dt.example;

import dt.core.DecisionTree;
import dt.core.DecisionTreeLearner;
import dt.core.Example;
import dt.core.Problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IrisData {

    public static void main(String[] args) throws IOException {
        dataType2();
    }

    public static void dataType1() throws IOException {
        Problem problem = new IrisProblem();
//        problem.dump();
//		Set<Example> examples = problem.readExamplesFromCSVFile(new File(args[0]));
        Set<Example> examples = problem.readExamplesFromCSVFile(new File("C:\\Users\\16kin\\Documents\\CSC 242\\Project4New\\Decision_Tree_Learning\\src\\dt\\example\\iris.data.discrete.txt"));
//        for (Example e : examples) {
//            System.out.println(e);
//        }
        Example[] exampArr = new Example[examples.size()];
        examples.toArray(exampArr);

        FileWriter fileWriter = new FileWriter("data.csv");

        for (int i = 1; i <= exampArr.length; i++) {
            Random rand = new Random();
            int s = 0;
            if (i != examples.size()) {
                s = rand.nextInt(examples.size() - i);
            }
            Set<Example> trainingSet = new HashSet<>(Arrays.asList(Arrays.copyOfRange(exampArr, s, s + i)));
//            Set<Example> testingSet = new HashSet<>(Arrays.asList(Arrays.copyOfRange(exampArr, i, exampArr.length)));
            DecisionTree tree = new DecisionTreeLearner(problem).learn(trainingSet);
//            tree.dump();
            double pctCorrect = tree.test(examples);
            System.out.println("Training size: " + i + " -- > " + pctCorrect);
            writeRowToCSV(fileWriter, new Double[]{1.0*i/examples.size(),pctCorrect});
        }
        fileWriter.close();
    }

    public static void dataType2() throws IOException{
        Problem problem = new IrisProblem();
//        problem.dump();
//		Set<Example> examples = problem.readExamplesFromCSVFile(new File(args[0]));
        Set<Example> examples = problem.readExamplesFromCSVFile(new File("C:\\Users\\16kin\\Documents\\CSC 242\\Project4New\\Decision_Tree_Learning\\src\\dt\\example\\iris.data.discrete.txt"));
//        for (Example e : examples) {
//            System.out.println(e);
//        }
        Example[] exampArr = new Example[examples.size()];
        examples.toArray(exampArr);

        FileWriter fileWriter = new FileWriter("data.csv");

        for (int i = 1; i < exampArr.length; i++) {
            Random rand = new Random();
            int s = 0;
            if (i != examples.size()) {
                s = rand.nextInt(examples.size() - i);
            }
            Set<Example> trainingSet = new HashSet<>(Arrays.asList(Arrays.copyOfRange(exampArr, s, s + i)));
            Set<Example> testingSet = new HashSet<>(Arrays.asList(Arrays.copyOfRange(exampArr, 0, s)));
            testingSet.addAll(Arrays.asList(Arrays.copyOfRange(exampArr, s+i, examples.size())));
            DecisionTree tree = new DecisionTreeLearner(problem).learn(trainingSet);
//            tree.dump();
            double pctCorrect = tree.test(testingSet);
            System.out.println("Training size: " + (1.0*i/examples.size()) + " -- > " + pctCorrect);
            writeRowToCSV(fileWriter, new Double[]{1.0*i/examples.size(),pctCorrect});
        }
        fileWriter.close();
    }

    public static void writeRowToCSV(FileWriter writer, Double[] row) throws IOException {
        for (Double d : row){
            writer.append(d.toString());
            writer.append(",");
        }
        writer.append("\n");
    }
}
