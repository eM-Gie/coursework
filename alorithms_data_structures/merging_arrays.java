//Marianna Gawron

import java.util.Scanner;

// kod sklada sie z klasy source w ktorej jest metoda wejscie, pobierajaca od uzytkownika dane wejsciowe
// wywyoluje ona rowniez metode sluzaca do scalania posortowanych tablic liczb calkowitych
// metoda scalTablice scala tablice intow korzystajac z zaimplementowanych wyyzej klas MinKopiec i Node

// zlozonosc programu jest rowna k * n * log(n)
// gdzie n jest iloscia tablic do scalenia a k maksymalna iloscia elementow tablicy

class Node {
    int elem; // The element to be stored
    int i;// indeks w tablicy skad zostal wziety
    int j; // indeks nastepnego elementu do wziecia

    public Node(int e, int i, int j){
        this.elem = e;
        this.i = i;
        this.j = j;
    }
};

class MinKopiec {
    int rozmiar_kopca;
    Node[] tab_z_kopca; // przechowuje wlasciwe elementy tablicy


    public MinKopiec(Node tab[], int r) { // tworzy kopiec z tablicy elementow
        rozmiar_kopca = r;
        tab_z_kopca = tab;
        int i = (rozmiar_kopca - 1) / 2;
        while (i >= 0) {
            warunek_kopca(i);
            i--;
        }
    }

    // sprawdza warunkek kopca przechodzac dla podanego elementu, jezeli sie nie zgadza to sprawdza dalej
    void warunek_kopca(int elem) {
        int lewy = (2 * elem + 1);    //  to jest pozycja lewego dziecka elementu i
        int prawy = (2 * elem + 2);
        int najmniejszy = elem;
        if ( lewy < rozmiar_kopca && tab_z_kopca[lewy].elem < tab_z_kopca[elem].elem )
            najmniejszy = lewy;
        if ( prawy < rozmiar_kopca && tab_z_kopca[prawy].elem < tab_z_kopca[najmniejszy].elem )
            najmniejszy = prawy;

        if (najmniejszy != elem) {    // warnek kopca jest spelniony jezeli elem jest najmniejszym elementem
            swap(tab_z_kopca, elem, najmniejszy);
            warunek_kopca(najmniejszy);
        }
    }



    // zmieniamy pierwszy korzen kopca (tab[0]) potem sprawdzamy warynek kopca
    void replaceMin(Node root) {
        tab_z_kopca[0] = root;
        warunek_kopca(0);
    }

    // zamienia miejscami elementy tablicy o wskazanych indeksach
    void swap(Node[] tab, int i, int j) {
        Node temp = tab[i];
        tab[i] = tab[j];
        tab[j] = temp;
    }




}

public class Source {
    public static void printArray(int tab[]) {
        for (int i = 0; i < tab.length; i++)
            System.out.print(tab[i] + " ");
        System.out.println();
    }

    //funkcja wlasciwa
    //zamienia tablice dwuwymiarowa na kopce z ktorych nastepnie pobiera najmniejsze wartosci i wpisuje je do tablicy wyjsciowej wynik
    static int[] scalTablice(int[][] tab, int rozm) {
        Node[] hTab = new Node[rozm];
        int rozmiarWyniku = 0;
        for (int i = 0; i < tab.length; i++) {
            Node node = new Node(tab[i][0], i, 1); // tworze tablice hArr z pierwzszego rzedu podanej tabelki
            hTab[i] = node;
            rozmiarWyniku += tab[i].length;
        }

        MinKopiec minikopiec = new MinKopiec(hTab, rozm);

        int[] wynik = new int[rozmiarWyniku];


        for (int i = 0; i < rozmiarWyniku; i++) {

            Node root = minikopiec.tab_z_kopca[0]; // pobieram najmniejsza wartosc z kopca
            wynik[i] = root.elem;


            // szukam nowego korzenia, podmieniam go
            if (root.j < tab[root.i].length)
                root.elem = tab[root.i][root.j++];
            else
                root.elem = Integer.MAX_VALUE; // jezeli root byl ostatnim elementem kopca

            minikopiec.replaceMin(root);
        }

        return wynik;

    }


    public static void wejscie() {
        Scanner scanner = new Scanner(System.in);
        int sets = scanner.nextInt();
        for (int i = 0; i < sets; i++) {
            int n = scanner.nextInt();

            int d = 0;
            int[] temp = new int[n]; // przechowywuje dlugosci poszczegolnych tablic, ktore sa do scalenia
            while (d < n) {
                temp[d] = scanner.nextInt();
                d++;
            }

            int[][] tab = new int[n][];

            for (int j = 0; j < n; j++) {
                tab[j] = new int[temp[j]];
                for (int k = 0; k < temp[j]; k++) {
                    tab[j][k] = scanner.nextInt();
                }
            }
            int[] wynik = scalTablice(tab, n);
            printArray(wynik);
        }
    }

    public static void main(String[] args) {
        wejscie();
    }
}



/*

JAWNY

1
4
1 7 3 10 0
1 3 5 7 9 11
13
2 4 6
1 2 3 4 5 6 7 8 9 10

MOJE


1
2
7 5
8 11 11 14 17 17 17
1 5 13 16 17


1
3
2 10 6
1 9
0 1 1 3 4 9 10 14 17 18
0 0 3 4 18 19



1
19
16 15 1 19 4 10 6 10 15 5 17 6 11 11 6 2 11 5 19
0 3 4 5 5 6 8 10 11 11 12 12 13 13 13 14
0 1 2 2 3 3 8 10 12 13 14 17 17 17 18
18
0 1 2 2 5 6 8 10 10 10 11 14 14 14 16 16 18 19 19
2 3 7 15
4 4 7 8 8 10 12 15 16 18
4 7 11 13 13 15
0 6 6 6 7 11 13 13 18 19
0 0 1 1 4 6 7 7 8 8 9 10 16 16 17
2 5 13 17 19
1 1 5 5 6 8 9 12 15 17 17 18 18 18 18 19 19
0 2 3 4 5 12
0 0 0 1 1 7 12 13 13 15 15
2 4 6 6 9 10 11 11 12 13 13
2 7 7 13 13 15
4 13
0 1 3 8 8 8 9 12 14 16 19
9 12 14 14 15
1 1 1 2 7 9 10 10 11 11 12 12 14 17 17 18 18 19 19



 */