//Marianna Gawron - 1

import java.util.Scanner;

/*
Program wczytuje dane z wejscia po czym rozwaza kazdy zestawu z osobna.
W zaleznosci od formatu (INF lub ONP) program przeksztalca podane wyrazenie. Jezeli wylapuje bledy to wyrzuca error
Korzystam z pomocniczych stosow, zimplementowanych adekwatnie do potrzeb.
 */
public class Source {

    static int priority(char x) { // priorytety decyduja miedzy innymi o kolejnosci dzialan
        switch (x) {
            case '=':
                return 1;
            case '|':
                return 2;
            case '&':
                return 3;
            case '?':
                return 4;
            case '<':
                return 5;
            case '>':
                return 5;
            case '+':
                return 6;
            case '-':
                return 6;
            case '*':
                return 7;
            case '/':
                return 7;
            case '%':
                return 7;
            case '^':
                return 8;
            case '!':
                return 9;
            case '~':
                return 9;
            case '(':
                return 10;
            case ')':
                return 10;
            default:                   // jezeli operand
                return 11;
        }
    }


    public static String error(char znak) {
        if (znak == 'o')
            return " error\n";
        else return " error\n";
    }

    public static boolean is_o1(char x) {
        return x == '!' || x == '~';
    }

    public static boolean is_o2(char x) {
        return x == '=' || x == '^' || x == '*' || x == '/' || x == '%' || x == '+' || x == '-' || x == '<' || x == '>' || x == '?' || x == '&' || x == '|';
    }

    public static boolean is_operand(char x) {
        return x >= 'a' && x <= 'z';
    }


    public static String toINF(String wejscie) {
        System.out.print("INF:");
        StackArray2 stack = new StackArray2(256);
        int i = 0;

        while (i < wejscie.length()) {
            char curr = wejscie.charAt(i);
            // operand
            if (is_operand(curr)) {
                String temp = "";
                temp += " " + curr;
                stack.push(temp, priority(curr));
            }
            //op1 -- operator jednoargumentowy
            else if (is_o1(curr)) {
                if (stack.isempty()) return error('o');                // error jezeli nie ma w stacku niczego

                String temp = stack.top();
                if (priority(curr) > stack.pr_top()) { // dodajemy nawiasy
                    temp = " (" + temp + " )";
                }
                temp = " " + curr + temp;
                stack.pop();
                stack.push(temp, priority(curr));
            }
            //op2 -- operator dwuargumentowy
            else if (is_o2(curr)) {
                if (stack.isempty()) return error('o');                // error jezeli nie ma w stacku niczego
                String b = stack.top();
                int pr_b = stack.pr_top();
                stack.pop();

                if (stack.isempty()) return error('o');                // error jezeli nie ma w stacku niczego
                String a = stack.top();
                int pr_a = stack.pr_top();
                stack.pop();

                // rozpatruje przypadki kiedy dodawac nawiasy     (a) (b)
                if (priority(curr) > pr_b && pr_b > pr_a || priority(curr) > pr_b && pr_b == pr_a || priority(curr) > pr_a && pr_a > pr_b || priority(curr) == pr_b && pr_b > pr_a   ||  pr_b > priority(curr) && priority(curr) > pr_a ) {
                    if (((priority(curr) == pr_b && pr_b > pr_a) && (curr == '^' || curr == '=')) || pr_b == 11 || pr_b > priority(curr) && priority(curr) > pr_a) {   //  jezeli jest lewostronna         lub       a/b jest operandem to (a) b
                        a = " (" + a + " )";
                    } else {
                        a = " (" + a + " )";
                        b = " (" + b + " )";
                    }
                }
                // rozpatruje przypadki kiedy dodawac nawiasy     a (b)  / ew. (a) b
                else if (pr_a > pr_b && pr_b == priority(curr) || pr_a == priority(curr) && priority(curr) > pr_b || pr_a == priority(curr) && priority(curr) == pr_b || pr_a > priority(curr) && priority(curr) > pr_b) { // dodajemy (b) jezeli jest lewostronna

                    if(pr_a > pr_b && pr_b == priority(curr) && curr!='='&& curr != '^'){ // pierwszy przypadek
                        b = " (" + b + " )";
                    }else if( pr_a == priority(curr) && priority(curr) == pr_b ) { // trzeci przypadel
                        if(curr!='='&& curr != '^') {
                            b = " (" + b + " )";
                        }else  a = " (" + a + " )";
                    }else if(pr_a == priority(curr) && priority(curr) > pr_b  || pr_a > priority(curr) && priority(curr) > pr_b ){
                        b = " (" + b + " )";
                    }
                    // (a) jezeli b> a == curr
                }else if(pr_b>pr_a && pr_a==priority(curr) && (curr == '=' || curr == '^')){
                    a = " (" + a + " )";
                }

                String temp = a + " " + curr + b;
                stack.push(temp, priority(curr));


            }
            i++;

        }
        String wyjscie = stack.top() + "\n";
        stack.pop();
        if (!stack.isempty()) return error('o'); // jezeli jest za duzo operandow, za malo operatorow
        return wyjscie;
    }

    public static String toONP(String wejscie) {
        System.out.print("ONP:");
        String wyjscie = "";
        StackArray stack = new StackArray(256);

        int i = 0, stan = 0, nawiascount = 0;

        while (i < wejscie.length()) {
            char curr = wejscie.charAt(i);

// O1  -  operatory jednoargumentowe
            if (is_o1(curr)) {
                if (stan == 1) return error('i');
                else {
                    stan = 2;
                    // zdejmij ze stosy wszystkie operatory o priorytecie > niz twoj operator i wypisz na wyjscie
                    if (!stack.isempty()) {
                        while (!stack.isempty() && stack.top() != '(' && priority(stack.top()) > priority(curr)) { /// stad odjelam >=
                            wyjscie = wyjscie + stack.top();
                            stack.pop();
                        }
                    }
                    stack.push(curr);  // operator na stack
                }
            }
            // O2  -  operatowy dwuargumentowe
            else if (is_o2(curr)) {
                if (stan != 1) return error('i'); // jestesmy w stan 1
                else {
                    stan = 0;

                    // zdejmij ze stosy wszystkie operatory o priorytecie > // >= niz twoj operator i wypisz na wyjscie
                    if (!stack.isempty()) {
                        if (curr != '=' && curr != '^') // jezeli jest operatorem lewostronnym
                            while (!stack.isempty() && stack.top() != '(' && priority(stack.top()) >= priority(curr)) {
                                char tempp = stack.top();
                                wyjscie = wyjscie + " " + tempp;
                                stack.pop();
                            }
                        else
                            while (!stack.isempty() && stack.top() != '(' && priority(stack.top()) > priority(curr)) {// jezeli operator prawostronny
                                char tempp = stack.top();
                                wyjscie = wyjscie + " " + tempp;
                                stack.pop();
                            }
                    }
                    stack.push(curr);// operator na stos
                }
            }
// (  - nawias otwierajacy
            else if (curr == '(') {
                if (stan == 1) return error('i');
                else {
                    stan = 0;
                    nawiascount++;
                    stack.push(curr);// nawias na stos
                }
            }

// )  -  nawias zamykajacy
            else if (wejscie.charAt(i) == ')') {
                if (nawiascount == 0) return error('i');
                if (stan != 1) return error('i'); // therefore stan = 1
                nawiascount--;

                // zdejmij ze stosu wszystkie operatory i dopisz na wyjscie az do nawiasu otwartego ktory usuwa ze stosu
                if (!stack.isempty()) {
                    while (stack.top() != '(') {
                        wyjscie = wyjscie + " " + stack.top();
                        stack.pop();
                    }
                }
                if (stack.top() == '(') stack.pop();
            }

// OPERAND
            else if (is_operand(curr)) {
                if (stan == 1) return error('i');
                stan = 1;
                wyjscie += " " + curr;
            }

            i++;
        }

        if (nawiascount != 0 || stan != 1) return error('i');


        while (!stack.isempty()) {
            wyjscie = wyjscie + " " + stack.top();
            stack.pop();
        }
        wyjscie += "\n";
        return wyjscie;
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int sets = sc.nextInt();
        sc.nextLine(); // bo jest linia odstepu prawdopodobnie w wejsciu jak sie wczytuje

        for (int i = 0; i < sets; i++) { // dla kazdego zestawu


            String wejscie = sc.nextLine();// pobieramy linie

            if (wejscie.charAt(0) == 'I') System.out.print(toONP(wejscie));
            else System.out.print(toINF(wejscie));

        }
        //  koniec programu
    }
}

class StackArray {
    private char[] elem; // tablica zawierajaca stos
    private int n; // indeks jeden nad szczytem stosu

    //-------------------------------------------------------------
    public StackArray(int size) { // konstruktor - tworzy
        // size - rozmiar tablicy zawierajacej stos
        elem = new char[size]; // tworoymy tablice dla elementow
        n = 0; // na razie brak elementow (rosnie w gore)
    }

    public void push(char x) { // wstawia element na szczyt stosu
        elem[n] = x;
        n++;
    }

    public void pop() { // usuwa element ze szczytu stosu
        elem[n - 1] = 0;
        n--;
    }

    public char top() { // zwraca wartosc na szczycie stosu
        return elem[n - 1];
    }

    public boolean isempty() { // zwraca true, jezeli stos pusty
        return n == 0;
    }


}

class StackArray2 {
    private String[] elem; // tablica zawierajaca stos
    public int[] priorytety;
    public int n = 0; // indeks jeden nad szczytem stosu

    //-------------------------------------------------------------
    public StackArray2(int size) { // konstruktor - Creates
        // rozmiar tablicy zawierajacej stos
        elem = new String[size]; // tworzymy tablice dla elementow
        priorytety = new int[size];
        n = 0; // na razie brak elementow (rosnie w gore)
    }

    public void push(String x, int pr) { // wstawia element na szczyt stosu
        elem[n] = x;
        priorytety[n] = pr;
        n++;
    }

    public void pop() { // usuwa element ze szczytu stosu
        elem[n - 1] = "0";
        priorytety[n - 1] = 0;
        n--;
    }

    public String top() { // zwraca wartosc na szczycie stosu
        return elem[n - 1];
    }

    public int pr_top() {
        return priorytety[n - 1];
    }

    public boolean isempty() { // zwraca true, jeeli stos pusty
        return n == 0;
    }

}



/* testy */

/*
wejscie:
7
ONP: xabc**=
ONP: aaaaaaaaaaa
INF: ---
INF:
INF: a+b
ONP: (cab*+)
ONP: cab+*


5
ONP: ab+cd+e==
INF: (a))
INF: a)
INF: A+b
INF: a+r+


11
ONP: ab^c^
INF: a ^ b ^ c
ONP: abc^^
INF: a ^ b ^ c
ONP: ab*c*
INF: a^(b^c)
INF: (a^b)^c
INF: a^b^c
INF: a * b * c
ONP: abc**
INF: a * ( b * c )

wyjscie:
INF: x = a * ( b * c )
INF: error
ONP: error
ONP: error
ONP:  a b +
INF: c + a * b
INF: c * ( a + b )

INF: a + b = c + d = e
ONP: error
ONP: error
ONP: error
ONP: error

INF: ( a ^ b ) ^ c
ONP: a b c ^ ^
INF: a ^ b ^ c
ONP: a b c ^ ^
INF: a * b * c
ONP: a b c ^ ^
ONP: a b ^ c ^
ONP: a b c ^ ^
ONP: a b * c *
INF: a * ( b * c )
ONP: a b c * *
 */


