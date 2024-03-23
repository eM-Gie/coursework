//Marianna Gawron -1
import java.util.Scanner;



class Wagon {
    String name;
    Wagon prev, next;

    public Wagon(String n) {// konstruktor
        name = n;
        prev = this;
        next = this;
    }
}

class Train {
    String name;
    Train next;
    Wagon first;

    public Train(String n, Wagon w) {// konstruktor
        name = n;
        first = w;
        // next jest null
    }


    void pushback(Wagon w) {
        w.prev = first.prev;
        first.prev.next = w;
        first.prev = w;
        w.next = first;
    }

    void pushfront(Wagon w) {
        pushback(w);
        first = w;
    }

    Wagon deleteFirst() {
        // cannnot not exist
        Wagon wynik = first;
        if (first.prev == first) {// jezeli w pociagu jest jeden wagon
            first = null;
        } else {
            first.prev.next = first.next;
            first.next.prev = first.prev;
            first = first.next;
        }
        wynik.next = wynik;
        wynik.prev = wynik;
        return wynik;
    }

    Wagon deleteLast() {
        // zakladam ze train exists
        Wagon wynik;

            wynik = first.prev;
            first.prev = first.prev.prev;
            first.prev.next = first;

        wynik.next = wynik;
        wynik.prev = wynik;
        return wynik;
    }


    void union(Trainlist peron, Train t2) {

        Wagon help = first.prev;
        first.prev.next = t2.first;
        t2.first.prev.next = first;
        first.prev = t2.first.prev;
        t2.first.prev = help;

        t2.first = null;
        peron.delete(t2);

    }

} // koniec klasy train

class Trainlist {
    Train first;

    public Trainlist() {
        Wagon w = new Wagon("");
        first = new Train("", w);
        first.next = null;
    }
    void pushfront(Train t){
        t.next = first.next;
        first.next = t;
    }


    void delete(Train t) {
        //zakladamy ze t istnieje
        Train poprzedni = first;
        while (poprzedni.next != t && poprzedni.next != null) {
            poprzedni = poprzedni.next;
        }
        if (t.next != null) { // jezeli t nie jest ostatni na liscie
            poprzedni.next = t.next;
        } else poprzedni.next = null; // jezeli byl ostatni na liscie
    }

    Train locate(String key) { // zwraca ref do pociagu ktory chcemy znalezc. jezeli nie znajdzie to zwraca null
        Train curr = first;
        while ( curr!=null&& !curr.name.equals(key)) {
         //   if (curr.next == null) return null;
            curr = curr.next;
        }
        return curr;
    }
}

public class Source {
    static Scanner sc = new Scanner(System.in);

    static void New(Trainlist peron) {
        String trainName = sc.next();
        String wagonName = sc.next();
        Wagon w = new Wagon(wagonName);

        if (peron.locate(trainName) != null) System.out.println("Train " + trainName + " already exists");
        else {
            Train t = new Train(trainName, w);
            peron.pushfront(t);
        }
    }

    static void InsertFirst(Trainlist peron) {
        String trainName = sc.next();
        String wagonName = sc.next();

        Train t = peron.locate(trainName);
        if (t == null) System.out.println("Train " + trainName + " does not exist");
        else {
            Wagon w = new Wagon(wagonName);
            t.pushfront(w);
        }
    }

    static void InsertLast(Trainlist peron) {
        String trainName = sc.next();
        String wagonName = sc.next();

        Train t = peron.locate(trainName);
        if (t == null) System.out.println("Train " + trainName + " does not exist");
        else {
            Wagon w = new Wagon(wagonName);
            t.pushback(w);
        }
    }

    static void Display(Trainlist peron) {
        String trainName = sc.next();

        Train t = peron.locate(trainName);
        if (t == null) {                // jezeli pociag nie istnieje
            System.out.println("Train " + trainName + " does not exist");

        }else {
            System.out.print(t.name + ":");

            Wagon curr = t.first;
            while (curr.next != t.first) {
                if (curr.next.prev != curr) // potrzebne bo reverse psuje kolejnosc
                    rev(curr.next);
                System.out.print(" " +curr.name);
                curr = curr.next;
            }
            System.out.print(" "+curr.name + "\n");
        }
    }


    static void Trains(Trainlist peron) {
        System.out.print("Trains: ");
        Train curr = peron.first;

        while (curr.next != null) {
            curr = curr.next;
            System.out.print(curr.name+" ");
        }
        System.out.println();
    }

    static void rev(Wagon w){// uzywane przy Reverse i displayu
        Wagon temp = w.next;
        w.next = w.prev;
        w.prev = temp;
    }

    static void Reverse(Trainlist peron) {
        String trainName = sc.next();

        Train t = peron.locate(trainName);// czy istnieje taki pociag
        if (t == null) { // jezeli pociag nie istnieje
            System.out.println("Train " + trainName + " does not exist");

        }else {// jezeli nie jest jednoelementowy
            Wagon prefirst = t.first.prev;
            //zamieniam ostatni z nastepnym
            rev(t.first);
            rev(t.first.next);
            t.first = prefirst;
        }
    }


    static void Union(Trainlist peron) {
        String trainName = sc.next();
        Train t = peron.locate(trainName);
        String trainName2 = sc.next();

        if (t == null) {  // jezeli pociag nie istnieje
            System.out.println("Train " + trainName + " does not exist");
            return;
        }

        Train t2 = peron.locate(trainName2);
        if(t2==null || t2.name.equals(t.name)) {
            System.out.println("Train " + trainName2 + " does not exist");
            return;
        }

        t.union(peron, t2);
    }

    static void DelFirst(Trainlist peron) {
        String trainName = sc.next();
        String newName = sc.next();

        Train t1 = peron.locate(trainName);
        if(t1== null) {
            System.out.println("Train " + trainName + " does not exist");
            return;
        }
        if(peron.locate(newName)!=null) {
            System.out.println("Train "+newName+" already exists");
            return;
        }
        Wagon newWagon = t1.deleteFirst();

        Train t2 = new Train(newName, newWagon);
        peron.pushfront(t2);

        if (t1.first==null) peron.delete(t1);
    }

    static void DelLast(Trainlist peron) {
        String trainName = sc.next();
        Train t1 = peron.locate(trainName);
        if(t1== null) {
            System.out.println("Train " + trainName + " does not exist");
            return;
        }

        String newName = sc.next();
        if(peron.locate(newName)!=null) {
            System.out.println("Train "+newName+" already exists");
            return;
        }

        Wagon newWagon = t1.deleteLast();

        if (t1.first == null) peron.delete(t1);

        Train t2 = new Train(newName, newWagon);
        peron.pushfront(t2);
    }

    static void wejscie() {
        int sets = sc.nextInt();
        for (int i = 0; i < sets; i++) {
            Trainlist peron = new Trainlist();
            int ilePolecen = sc.nextInt();
            for (int j = 0; j < ilePolecen; j++) {
                String polecenie = sc.next();
                switch (polecenie) {
                    case "New":
                        New(peron);
                        break;
                    case "InsertFirst":
                        InsertFirst(peron);
                        break;
                    case "InsertLast":
                        InsertLast(peron);
                        break;
                    case "Display":
                        Display(peron);
                        break;
                    case "Trains":
                        Trains(peron);
                        break;
                    case "Reverse":
                        Reverse(peron);
                        break;
                    case "Union":
                        Union(peron);
                        break;
                    case "DelFirst":
                        DelFirst(peron);
                        break;
                    case "DelLast":
                        DelLast(peron);
                        break;
                }
            }
        }
    }



    public static void main(String[] args) {
        //   testTrian();
        wejscie();
        //      testPeron();
        //   test3();
    }

}


/*TESTY

1
3
New t1 w1
Display t1
InsertFirst t1 w2

1
13
New T1 W1
Display T1
New T1 W0
InsertLast T1 W2
Display T1
DelLast T1 T2
Display T1
Display T2
DelFirst T1 T3
Display T3
Display T2
Display T1
Trains



   1
    7
    New t1 w1
    InsertFirst t1 w2
    InsertFirst t1 w1
    Display t1
    Display t2
    InsertLast t1 w1
    Display t1

1
6
    New t1 w1
    InsertFirst t1 w2
    InsertFirst t1 w3
    Display t1
    Union t1 t1
    Display t1

       1
        13
    New t1 w1
    InsertLast t1 w2
    InsertLast t1 w3
    Display t1
    New t2 w21
    InsertLast t2 w22
    InsertLast t2 w23
    Display t2
    Union t1 t2
    Display t1
    Reverse t1
    Display t1
    Display t2


1
14
    New t1 w1
    InsertFirst t1 w2
    InsertFirst t1 w3
    Display t1
    InsertLast t1 w4
    InsertLast t2 w5
    Display t1
    Reverse t1
    Display t1
        Reverse t1
    Display t1
    Union t1 t2
    Display t2
    Display t1

1
8
    New t1 w1
    Display t1

Reverse t1
Display t1
    InsertFirst t1 w3
Display t1
Reverse t1
Display t1

    1
5
New t1 w1
Display t1
Union t1 t2
Display t1
Display t2
 */

/*
1
5
New t1 w1
Display t1
DelLast t1 t2
InsertLast t1 w2
Display t1
 */


/*
//pierwszy przypadek
1
11
New t1 w1
InsertLast t1 w2
InsertLast t1 w3
Display t1
New t2 w21
InsertLast t2 w22
InsertLast t2 w23
Display t2
Union t1 t2
Display t1
Display t2

//drugi przypadek
1
9
New t1 w1
Display t1
New t2 w21
InsertLast t2 w22
InsertLast t2 w23
Display t2
Union t1 t2
Display t1
Display t2

//trzeci przypadek
1
7
New t1 w1
Display t1
New t2 ww1
Display t2
Union t1 t2
Display t1
Display t2
 */

/*    1
            9
    New t1 w1
    InsertLast t1 w2
    InsertLast t1 w3
    InsertLast t1 w4
    Display t1
    Reverse t1
    Display t1
    Reverse t1
    Display t1
    */
//sprawdza wyjatki
    /*
    1 20
    Trains
    New t1 w1
    New t1 w2
    InsertFirst t2 w2
    InsertLast t2 w2
    Display t1
    Display t2
    Trains
    Reverse t1
    Reverse t2
    Union t1 t2
    Union t1 t1
    DelFirst t1 t2
    DelFirst t1 t1
    DelFirst t2 t1
    DelFirst t2 t3
    DelLast t1 t2
    DelLast t1 t1
    DelLast t2 t1
    DelLast t2 t3

     */

