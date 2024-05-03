package Logica.NeuralNetwork;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
       //NeuralNetwork rna = new NeuralNetwork(4);
//        int[] t = new int[]{3,3,3,5,6};
//        NeuralNetwork rna = new NeuralNetwork(5, t);
//       //rna.printfNetwork();
//        //System.out.println(Function.test(15f));
//       for (int i = 0; i < 100; i++){
//           rna.train(0.45F, 0.0001f, 100);
//       }
        NNM.createInstanciate();
        NNM.instanciate.createNetwork();
        //NNM.instanciate.train();
//        for(int i = 0; i < 10; i++)
          NNM.instanciate.train(0.45F, 0.0001f, 100);
          NNM.instanciate.getNetwork().getMatrix();
         //NNM.instanciate.saveNetwork();

    }
}