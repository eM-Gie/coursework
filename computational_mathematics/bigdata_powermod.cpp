#include <iostream>
#include <gmpxx.h>
using namespace std;

using bigInteger = mpz_class; // bardziej czytelnie jest poslugiwac sie bigInteger niz mpz_classx§
// typedef mpz_class bigInteger;

// Ponizszy kod obliczy 1000! mod 1234567890987654321

// Funkcja obliczajaca a^b mod n

bigInteger powerMod(const bigInteger& a, const bigInteger& b, const bigInteger& n){
    bigInteger z= a%n;
    bigInteger y = 1;
    bigInteger m = b;
    while (m>0){
        if(m%2 == 1)
            y = (y*z)% n;
        m = m/2;
        z = (z*z) % n;
    }
    return y;

    /*
    bigInteger a("3");
    bigInteger b("5559060566555523");
    bigInteger n("100000000000000000000000000000000000000000000000000");  //

    cout << powerMod(a, b, n) << "\n";

    return 0;

    */
}


int main(){
//    bigInteger n(1);                           // tworzenie obiektów z intów
//    bigInteger modulus("1234567890987654321"); // lub ze stringów
//
//    for (int i = 2; i <= 1000; ++i)
//        n = n * i;
//
//    cout << "1000!=" << n << endl;
//    cout << "1000! mod 1234567890987654321=" << n % modulus << endl;
 /*
  * liczby do sprawdzenoa
  *1. 624423990187
2. 225898512559

  * */
    bigInteger a("624423990187");
    bigInteger b("3");
    bigInteger n("4");

    cout << powerMod(a, b, n) << "\n";

    return 0;
    return 0;
}