// Marianna Gawron - 1

import java.util.Scanner;


// stos wykorzystywany przy iter_pakuj
class StackArray {
    private int maxSize;
    private int[] elem;
    private int t; // indeks szczytu stosu
    private int i=0;

    public StackArray(int size) {
        maxSize = size;
        elem = new int [maxSize];
        t = maxSize;
    }
    public void push(int x) { // wstawia element na szczyt stosu
        t--;
        elem[t] = x;
    }
    public int pop(){ // usuwa element ze szczytu stosu
        t++;
        return elem[t-1];
    }
    public boolean isempty(){ // zwraca true, jezeli stos pusty
        return t==maxSize;
    }
    public void display(){
        for(int i=maxSize-1; i>=t; i--)
            System.out.print(" "+elem[i]);
    }
    public int getBegining(){
        i++;
        if(maxSize-i>=t)
         return elem[maxSize-i];
        else return -1;
    }
}

public class Source {

    // zwraca prawde jezeli istnieje wypelnienie plecaka o okreslonej wadze,
    // elementy wlozone do plecaka sa oznakowanie flaga
    //zwraca falsz jezeli nie istnieje takie wypelnienie
    static boolean recursive(int waga, int[] tab, int n, int[] flag) {
        if (waga < tab[n]) {
            flag[n] = 1;
            if (n + 1 != tab.length)
                return recursive(waga, tab, n + 1, flag);
            else return false;
        } else if (waga > tab[n]) {
            if (n + 1 == tab.length) return false;
            if (recursive(waga - tab[n], tab, n + 1, flag)) {
                flag[n] = 2;
                return true;
            } else if (n != 0 && flag[n - 1] != 0 && n + 1 != tab.length) return recursive(waga, tab, n + 1, flag);
            else {
                flag[n] = 0;
                if (n + 1 != tab.length) return recursive(waga, tab, n + 1, flag);
                else return false;
            }
        } else { // if (waga == tab[n])
            flag[n] = 2;
            return true;
        }
    }

    // funkcja tworzy pomocnicza tabelk, przechowywujaca informacje o spakowanych/ niespakowanych elementach
    // wywoluje funkcje recursive, ktora de facto pakuje plecak
    // wyswietla odpowiednie wyjscie
    static void rec_pakuj(int waga, int[] tab) {
        int[] flag = new int[tab.length];
        for (int i = 0; i < tab.length; i++)
            flag[i] = 1;

        if (recursive(waga, tab, 0, flag)) {// wywolujemy funkcje recursive zwraca ona prawde lub falsz
            System.out.print("REC: " + waga + " =");
            for (int i = 0; i < tab.length; i++)
                if (flag[i] == 2)
                    System.out.print(" " + tab[i]);
            System.out.println();
        } else System.out.println("BRAK ");
    }


    // funkcja dziala analogicznie do funkcji recursive, tylko zaimplementowana iteracyjnie z wykorzystaniem stosu
    static boolean iterative(int waga, int[] tab, StackArray stack) {
        int n = 0;
        while (true) {
            if (waga < tab[n]) {
                if (n + 1 != tab.length) n++;
                else {
                    if (stack.isempty()) return false;
                    n = stack.pop();
                    waga += tab[n];
                    n++;
                }
            } else if (waga > tab[n]) {
                if (n + 1 == tab.length)
                    if (stack.isempty()) {
                        return false;
                    } else {
                        n = stack.pop();
                        waga += tab[n];
                        n++;
                    }
                else {
                    stack.push(n);
                    waga -= tab[n];
                    n++;
                }
            } else {
                stack.push(n);
                return true;
            }
        }
    }

    // funkcja tworzy pomocnicza tabelk, przechowywujaca informacje o spakowanych/ niespakowanych elementach
    // wywoluje funkcje iterative, ktora de facto pakuje plecak
    // wyswietla odpowiednie wyjscie
    static void iter_pakuj(int waga, int[] tab) {
        StackArray stack = new StackArray(tab.length);


        if (iterative(waga, tab, stack)) {// wywolujemy funkcje recursive zwraca ona prawde lub falsz
            System.out.print("ITER: " + waga + " =");
            int i = stack.getBegining();
            while (i != -1) {
                System.out.print(" " + tab[i]);
                i = stack.getBegining();
            }
            System.out.println();//  Brak wyswietlany przy przzez recursive
        }//else System.out.println("BRAK");
    }


    static void wejscie() {
        Scanner scanner = new Scanner(System.in);
        int sets = scanner.nextInt();

        for (int i = 0; i < sets; i++) {// dla kazdego zestawu
            int waga = scanner.nextInt();
            int count = scanner.nextInt();
            int[] tab = new int[count];

            for (int j = 0; j < count; j++)
                tab[j] = scanner.nextInt();
            rec_pakuj(waga, tab);
            iter_pakuj(waga, tab);
        }
    }

    public static void main(String[] args) {
        wejscie();
        //    test6();

    }
/*

    static void test1(){
        int[] tab= {11,8,7,6,0};
        rec_pakuj( 20, tab);
        int[] tab2 = {1,1,1,1,1};
        rec_pakuj(2,tab2);
        int[] tab3 = {1,1,1,1,1};
        rec_pakuj(5,tab3);
        int[] tab4 = {1,1,1,1,1};
        rec_pakuj(6,tab4);
    }

    static void test2(){
//        int[] tab= {11,8,7,6,5};
//        iter_pakuj( 20, tab);
//        int[] tab2 = {1,1,1,1,1};
//        iter_pakuj(2,tab2);
//        int[] tab3 = {1,1,1,1,1};
//        iter_pakuj(5,tab3);
//        int[] tab4 = {1,1,1,1,1};
//        iter_pakuj(6,tab4);

        int[] tab0 = {3,8,5,13,16};
        iter_pakuj(21,tab0);
        int[] tab1 = {13,1,15,4,19};
        iter_pakuj(13,tab1);
        int[] tab2 = {17,11,8,2,17};
        iter_pakuj(8,tab2);
//        int[] tab3 = {4,17,5,4,15};
//        iter_pakuj(0,tab3);
        int[] tab4 = {3,14,18,19,15};
        iter_pakuj(8,tab4);
        int[] tab5 = {13,6,2,7,3};
        iter_pakuj(7,tab5);
        int[] tab6 = {18,17,4,11,8};
        iter_pakuj(11,tab6);
        int[] tab7 = {10,7,5,18,5};
        iter_pakuj(11,tab7);
        int[] tab8 = {4,4,12,6,13};
        iter_pakuj(2,tab8);
        int[] tab9 = {11,6,19,14,12};
        iter_pakuj(14,tab9);
    }
    public static void test3(){
        int[] tab0 = {2,11,11,6,18};
        System.out.print(0);
        iter_pakuj(8,tab0);
        int[] tab1 = {8,1,4,14,18};
        System.out.print(1);
        iter_pakuj(11,tab1);
        int[] tab2 = {12,3,15,2,10};
        System.out.print(2);
        iter_pakuj(3,tab2);
        int[] tab3 = {17,8,10,19,14};
        System.out.print(3);
        iter_pakuj(8,tab3);
        int[] tab4 = {14,13,7,8,17};
        System.out.print(4);
        iter_pakuj(1,tab4);
        int[] tab5 = {5,14,3,2,5};
        System.out.print(5);
        iter_pakuj(7,tab5);
        int[] tab6 = {15,16,19,17,19};
        System.out.print(6);
        iter_pakuj(10,tab6);
        int[] tab7 = {16,16,5,16,10};
        System.out.print(7);
        iter_pakuj(15,tab7);
        int[] tab8 = {13,13,3,13,3};
        System.out.print(8);
        iter_pakuj(21,tab8);
        int[] tab9 = {4,16,8,3,18};
        System.out.print(9);
        iter_pakuj(6,tab9);
    }
    public static void test4(){
        int[] tab0 = {12,19,16,12,1};
        System.out.print(0);
        iter_pakuj(14,tab0);
        rec_pakuj(14,tab0);
        int[] tab1 = {1,7,1,17,4};
        System.out.print(1);
        iter_pakuj(32,tab1);
        rec_pakuj(32,tab1);
        int[] tab2 = {17,5,12,3,12};
        System.out.print(2);
        iter_pakuj(26,tab2);
        rec_pakuj(26,tab2);
        int[] tab3 = {15,13,13,17,12};
        System.out.print(3);
        iter_pakuj(32,tab3);
        rec_pakuj(32,tab3);
        int[] tab4 = {7,7,6,5,17};
        System.out.print(4);
        iter_pakuj(23,tab4);
        rec_pakuj(23,tab4);
        int[] tab5 = {11,11,6,19,9};
        System.out.print(5);
        iter_pakuj(17,tab5);
        rec_pakuj(17,tab5);
        int[] tab6 = {6,19,10,8,4};
        System.out.print(6);
        iter_pakuj(25,tab6);
        rec_pakuj(25,tab6);
        int[] tab7 = {6,2,12,14,15};
        System.out.print(7);
        iter_pakuj(28,tab7);
        rec_pakuj(28,tab7);
        int[] tab8 = {7,14,5,6,12};
        System.out.print(8);
        iter_pakuj(16,tab8);
        rec_pakuj(16,tab8);
        int[] tab9 = {16,1,6,16,10};
        System.out.print(9);
        iter_pakuj(23,tab9);
        rec_pakuj(23,tab9);
    }

    public static void test5(){
        int[] tab0 = {8,15,11,16,16};
        System.out.print(0);
        iter_pakuj(15,tab0);
        rec_pakuj(15,tab0);
        int[] tab1 = {5,14,18,1,6};
        System.out.print(1);
        iter_pakuj(12,tab1);
        rec_pakuj(12,tab1);
        int[] tab2 = {8,9,11,19,4};
        System.out.print(2);
        iter_pakuj(16,tab2);
        rec_pakuj(16,tab2);
        int[] tab3 = {7,1,19,4,15};
        System.out.print(3);
        iter_pakuj(28,tab3);
        rec_pakuj(28,tab3);
        int[] tab4 = {12,18,1,12,13};
        System.out.print(4);
        iter_pakuj(17,tab4);
        rec_pakuj(17,tab4);
        int[] tab5 = {13,4,12,10,10};
        System.out.print(5);
        iter_pakuj(25,tab5);
        rec_pakuj(25,tab5);
        int[] tab6 = {7,11,11,13,7};
        System.out.print(6);
        iter_pakuj(14,tab6);
        rec_pakuj(14,tab6);
        int[] tab7 = {12,2,12,5,2};
        System.out.print(7);
        iter_pakuj(30,tab7);
        rec_pakuj(30,tab7);
        int[] tab8 = {18,4,11,13,11};
        System.out.print(8);
        iter_pakuj(13,tab8);
        rec_pakuj(13,tab8);
        int[] tab9 = {6,17,3,6,2};
        System.out.print(9);
        iter_pakuj(27,tab9);
        rec_pakuj(27,tab9);
    }

    public static void test6(){
        int[] tab0 = {3,18,3,13,12};
        System.out.print(0);
        iter_pakuj(21,tab0);
        rec_pakuj(21,tab0);
        int[] tab1 = {14,1,3,1,9};
        System.out.print(1);
        iter_pakuj(18,tab1);
        rec_pakuj(18,tab1);
        int[] tab2 = {4,1,8,1,2};
        System.out.print(2);
        iter_pakuj(32,tab2);
        rec_pakuj(32,tab2);
        int[] tab3 = {11,16,13,9,3};
        System.out.print(3);
        iter_pakuj(30,tab3);
        rec_pakuj(30,tab3);
        int[] tab4 = {14,17,6,2,15};
        System.out.print(4);
        iter_pakuj(17,tab4);
        rec_pakuj(17,tab4);
        int[] tab5 = {19,13,4,10,4};
        System.out.print(5);
        iter_pakuj(20,tab5);
        rec_pakuj(20,tab5);
        int[] tab6 = {8,5,14,6,2};
        System.out.print(6);
        iter_pakuj(16,tab6);
        rec_pakuj(16,tab6);
        int[] tab7 = {18,6,14,8,10};
        System.out.print(7);
        iter_pakuj(12,tab7);
        rec_pakuj(12,tab7);
        int[] tab8 = {14,4,1,18,14};
        System.out.print(8);
        iter_pakuj(25,tab8);
        rec_pakuj(25,tab8);
        int[] tab9 = {16,3,2,3,11};
        System.out.print(9);
        iter_pakuj(31,tab9);
        rec_pakuj(31,tab9);
    }

    */
}
