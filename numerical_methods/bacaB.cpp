//Marianna Gawron
#include <cmath>
using namespace std;

double sieczny(double x0, double x1, double fx0, double fx1){
    return  x1 - fx1 * (x1 - x0) / (fx1 - fx0);
}
double bisekcja(double x0, double x1){

    return  0.5* x1 + 0.5* x0;
}

double findZero (double (*f)(double), double a, double b, int M, double eps, double delta) {
    double c=a,d=b, m;
    double fc= f(a), fd = f(b), fm;
    if (abs(fc)<eps ) return a;
    if (abs(fd)<eps ) return b;
    if(abs(c-d)<delta) return c;

    int count = M-2;

//
//    if (fc*fd>0){ // robimy jeden strzal sieczna
//        m = sieczny(c, d, fc, fd);
//        fm = f(m);
//        count--;
//        if (abs(fm)<eps) return m;
//        //podmiana koncow
//        c = d;fc = fd;
//        d = m;fd = fm;
//    }

    // metoda bisekcji_____________________________________________________
    while (fc*fd<0 && count > 0.4*M){
        if(abs(c-d)<delta) return c;

        m = bisekcja(c, d);
        fm = f(m);
        count--;

        if (abs(fm)<eps) return m;

        // zamiana koncow
        if(fc*fm < 0){
            d = m;fd = fm;
        }else{
            c = m;fc = fm;
        }

        if (abs(fm) <=0.1) break;
    }

    // metoda siecznych___________________________________________
    while(count > 0){
        if (abs(c-d)<delta) return c;

        m = sieczny(c, d, fc, fd);
//        if (m<a || m>b) m = bisekcja(c,d); // sprawdzam czy nie wychodze za przezdial

        fm = f(m);
        count--;
        if (abs(fm)<eps) return m;

        //podmiana koncow
        c = d;fc = fd;
        d = m;fd = fm;
    }

    return abs(fc)<abs(fd) ? c : d;
}