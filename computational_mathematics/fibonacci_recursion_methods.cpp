#include <stdio.h>
#include <iostream>
#include <set>
using namespace std;
int main() {
    string line, s1, s2;
    set<string> S;
    cin >> line;
    while(line!="\n") {
        S.insert(line);
        cout << line;
        cin >> line;
    }
    for(auto i = S.begin(); i != S.end(); i++) {
        int len = (*i).length();
        for(int j = 0; j < len-1; j++) {
            s1 = (*i).substr(0, j+1);
            s2 = (*i).substr(j+1, len-j);
            if(S.find(s1) != S.end() && S.find(s2) != S.end()) {
                cout << (*i) << endl;
                break;
            }

        }
    }
    return 0;
}

/*
a
alien
lien
 */

/*a
alien
born
less
lien
never
nevertheless
new
newborn
the
zebra
 */
/*#include <iostream>
using namespace std;

// dla dowolnego n brak wywolan rekurencyjnych
// w szczegolnosci dla n = 1000 brak wywolan rekurencyjnych.
int fib(int n){

    int a = 1;
    int b = 1;
    int suma = a+b;
    while (a<n){
        cout << a <<endl;
        a = b;
        b = suma;
        suma= a+b;
    }

}

//int fib2(int n){
//
//    fib(n) = fib(n-1) + fib(n-2) = fib(n-2) + fib(n-3) + fib(n-2)
//     int an_1 = fib(n-2);
//    return 2* an_1+ an_1+1;
//}

//rekurencja ogonowa, wywolywana n razy
//w szczegolnosci dla n = 1000 rekurencja wywolana 16 razy
int fib3(int n, int count, int val = 1, int prev = 0)
{
    if(val >=n) return count;
    cout << val << endl;
    count++;
    count = fib3(n, count,val+prev, val);
    return count;
}

int main() {
//    fib(1000);
//    cout<< "count_" << count;
int count = fib3(1000, 0);
    cout <<"rekurencja wywola sie_ "<< count << " _razy";
}
*/