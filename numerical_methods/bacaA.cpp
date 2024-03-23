// Marianna Gawron
#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

void printZZeros(){
    cout << 0.<< " " << 0. << " " << 0. << endl;
}


void bedziegit(float p, float S) {
    float eps =  1.0e-25;
    float a1, a2, a3, q, delta=0., a,b = 0.;
    const float dt = cbrt(p);

    if(abs(p)!=0.) {

        b = -(S/ dt - 1); // a = c = 1 // jezeli a<1 czy wtedy tez wykonujemy dzielenie?
        a = 1;

        if ((b-2*a)*(b+2*a)<0) {
            printZZeros();
            return;
        }

        delta = sqrt(abs(b - 2* a)) * sqrt(abs(b + 2* a));

    }else{
        printZZeros();
        return;
    }

    if (delta < 0 ) {
        printZZeros();
        return;
    }

    if (b>0) q = (-b/2/a - delta / 2/a);
    else q = (-b/2/a + delta / 2/a);

    if (dt/q >dt*q) {
        a1 = dt/q;
        a2 = dt;
        a3 = dt*q;
    } else {
        a3 = dt/q;
        a2 = dt;
        a1 = dt*q;
    }
    if (abs(a1) < 0) {
        q = 0.;
        a1 = abs(a1);
    }

    cout << a1 << " " << a2 << " " << a3 << endl;

}


void input(){
    int N;
    cin >> N;
    for (int i=0; i<N; i++){
        float S, p;
        cin >> p >> S;
        bedziegit(p,S);
    }
}

int main(){
    cout << setprecision(10) << scientific;
    input();

}

/*
 *
9
1.0000000000e+00 3.0000000000e+00
6.4000000000e+01 2.1000000000e+01
-6.4000000000e+01 -2.1000000000e+01
6.4000000000e+01 -1.3000000000e+01
-6.4000000000e+01 1.3000000000e+01
1.0000000150e+30 1.0000000200e+20
1.0000000150e+30 -1.0000000200e+20
0.0000000000e+00 0.0000000000e+00
1.0000000000e+02 1.0000000000e+00
 */