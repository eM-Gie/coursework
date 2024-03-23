import java.util.Random;

public class generateTest {

    public static void getTest(int how_many, int how_long){
        Random rand = new Random(); //instance of random class
        int[][] tab = new int[how_many][how_long];

        for(int i=0; i<how_many; i++) {
            System.out.print("int[] tab"+i+" = {");
            for (int j = 0; j < how_long; j++) {
                System.out.print(rand.nextInt(19)+1);
                if(j!=how_long-1) System.out.print(",");
            }
            System.out.println("};");
            System.out.println("System.out.print("+i+");");
            int waga = rand.nextInt(25)+10;
            System.out.println("iter_pakuj("+waga+",tab"+i+");");
            System.out.println("rec_pakuj("+waga+",tab"+i+");");
        }
    }

    public static void main(String[] args) {
        getTest(10,5);
    }
}
