package Logica.NeuralNetwork;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase se encarga de cargar la información del fichero
 * para entrenar a la red neuronal. Divide la información en
 * datos de entrenamiento y datos para comprobar el correcto
 * entrenamiento de la red
 */
public class DataSheet implements Serializable{
    private List<Iris> test, train, data;

    private static final long serialVersionUID = 2L;

    public DataSheet()
    {
        test = new ArrayList<Iris>();
        train = new ArrayList<Iris>();
        data = new LinkedList<Iris>();
        readData();
        divideData();
    }

    /**
     * Este método se encarga de leer la información desde el fichero
     * llenar la lista enlazada
     */
    public void readData()
    {
        try{
            FileInputStream stream = new FileInputStream(new File("src/main/java/Logica/NeuralNetwork/IRIS/iris.data"));
            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
            String line = buffer.readLine();
            while(buffer.ready()){
                line = line.replaceAll(",", " ");
                float firstLine = Float.parseFloat(line.substring(0, 3));
                // System.out.println(firstLine);
                float secondLine = Float.parseFloat(line.substring(4, 7));
                //System.out.println(secondLine);
                float thirdLine = Float.parseFloat(line.substring(8, 11));
                //System.out.println(thirdLine);
                float fourLine = Float.parseFloat(line.substring(12, 15));
                //System.out.println(fourLine);
                String fiveLine = line.substring(16);
                //System.out.println(fiveLine);
                data.add(new Iris(firstLine, secondLine, thirdLine, fourLine, fiveLine));
                line = buffer.readLine();
            }

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collections.shuffle(data);
    }

    /**
     * Este método se encarga en dividir la data en datos de entrenamiento y
     * datos de prueba
     */
    public void divideData()
    {
        if(data.size() > 110)
            test.add(data.removeFirst());

        while(!data.isEmpty())
            train.add(data.removeFirst());
    }

    public List<Iris> getDataTrain(){return this.train;}

    public void writeFlower (int [] outsObjetive, ArrayList<Float> finalOutputs, String outFlower){
        try {
            // Sustituir la direccion donde se va a crear el fichero
            FileWriter fw = new FileWriter("src/main/java/Logica/NeuralNetwork/IRIS/save.txt",true);
            for (int i = 0; i < 3; i++) {
                fw.write(outsObjetive[i] + " ");
            }
            fw.write(", [");
            for (int i = 0; i < 3; i++) {
                fw.write(String.valueOf(finalOutputs.get(i)));
                if (i==2)
                    fw.write("]");
                else
                    fw.write(", ");
            }
            fw.write(", Flor de salida: " + outFlower + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
