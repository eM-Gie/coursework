//Marianna Gawron
#include <iostream>
#include <cmath>
#include "funkcja.h"
#include <iomanip>

using namespace std;


struct Jet{

    double val;
    double der_x, der_y;
    double der_xx, der_xy, der_yy;

    Jet(){
        val = 0;
        der_x=0;
        der_y=0;
        der_xx=0;
        der_xy=0;
        der_yy=0;
    }

    Jet(double val1,double der_x1,double der_y1, double der_xx1,double der_xy1,double der_yy1){
        val = val1;
        der_x=der_x1;
        der_y=der_y1;
        der_xx=der_xx1;
        der_xy=der_xy1;
        der_yy=der_yy1;
    }

    Jet (const Jet& other){
        val = other.val;
        der_x = other.der_x;
        der_y = other.der_y;
        der_xx = other.der_xx;
        der_yy = other.der_yy;
        der_xy = other.der_xy;
    }

    // operator przypisania
    Jet& operator=( const Jet& other)= default;

};

//miedzy Jetem a Jetem
Jet operator+(const Jet& u, const Jet& v){
    double val = u.val + v.val;
    double der_x = u.der_x + v.der_x,
            der_y = u.der_y + v.der_y;
    double der_xx = u.der_xx + v.der_xx,
            der_xy = u.der_xy + v.der_xy,
            der_yy = u.der_yy + v.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator-(const Jet& u, const Jet& v){
    double val = u.val - v.val;
    double der_x = u.der_x - v.der_x,
            der_y = u.der_y - v.der_y;
    double der_xx = u.der_xx - v.der_xx,
            der_xy = u.der_xy - v.der_xy,
            der_yy = u.der_yy - v.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator*(const Jet& u, const Jet& v){
    double val = u.val * v.val;
    double der_x = u.der_x * v.val + v.der_x * u.val,
            der_y = v.val * u.der_y + u.val * v.der_y;
    double der_xx = u.val * v.der_xx + 2 * u.der_x * v.der_x + v.val*u.der_xx,
            der_xy =u.val * v.der_xy + u.der_x * v.der_y + u.der_y * v.der_x + v.val*u.der_xy,
            der_yy = u.val*v.der_yy + 2*u.der_y*v.der_y + v.val*u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);

}
Jet operator/(const Jet& u, const Jet& v){ // nie mam zrobionego operatora dzielenia
    double val = u.val / v.val;
    double der_x = u.der_x/v.val - u.val*v.der_x/v.val/v.val,
            der_y = u.der_y/v.val - u.val*v.der_y/v.val/v.val;
    double  der_xx = -2*u.der_x*v.der_x/v.val/v.val + 2*u.val*v.der_x*v.der_x/v.val/v.val/v.val + u.der_xx/v.val - u.val*v.der_xx/v.val/v.val,
            der_xy = -v.der_y*u.der_x/v.val/v.val - u.der_y*v.der_x/v.val/v.val + 2*u.val*v.der_y*v.der_x/v.val/v.val/v.val + u.der_xy/v.val - u.val*v.der_xy/v.val/v.val,
            der_yy = -2*u.der_y*v.der_y/v.val/v.val + 2*u.val*v.der_y*v.der_y/v.val/v.val/v.val + u.der_yy/v.val - u.val*v.der_yy/v.val/v.val;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);

}

/**/
//miedzy stala a Jetem // tutaj moze byc blad bo powinnismy mnozyc dwa jety z czego jeden jest (c,0,0,0,0,0)
Jet operator+(double c,const Jet& u){
    Jet a(c,0,0,0,0,0);
    return a+u;
//    double val = c + u.val;
//    double der_x = u.der_x ,
//            der_y = u.der_y;
//    double der_xx = u.der_xx,
//            der_xy = u.der_xy,
//            der_yy = u.der_yy;
//
//    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator-(double c,const Jet& u){
    Jet a(c,0,0,0,0,0);
    return a-u;
    double val = c - u.val;
    double der_x = u.der_x ,
            der_y = u.der_y;
    double der_xx = u.der_xx,
            der_xy = u.der_xy,
            der_yy = u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator*(double c,const Jet& u){
    Jet a(c,0,0,0,0,0);
    return a*u;
    double val = c * u.val;
    double der_x = c * u.der_x ,
            der_y = c * u.der_y;
    double der_xx = c * u.der_xx,
            der_xy = c * u.der_xy,
            der_yy = c * u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator/(double c,const Jet& u){
    Jet a(c,0,0,0,0,0);
    return a/u;

    double val = c / u.val;
    double der_x = c / u.der_x ,
            der_y = c / u.der_y;
    double der_xx = c / u.der_xx,
            der_xy = c / u.der_xy,
            der_yy = c / u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}

Jet operator+(const Jet& u,double c){
    Jet a(c,0,0,0,0,0);
    return u+a;
    double val = c + u.val;
    double der_x = u.der_x ,
            der_y = u.der_y;
    double der_xx = u.der_xx,
            der_xy = u.der_xy,
            der_yy = u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator-(const Jet& u,double c){
    Jet a(c,0,0,0,0,0);
    return u-a;
    double val = u.val - c;
    double der_x = u.der_x ,
            der_y = u.der_y;
    double der_xx = u.der_xx,
            der_xy = u.der_xy,
            der_yy = u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator*(const Jet& u,double c){
    Jet a(c,0,0,0,0,0);
    return u*a;

    double val = c * u.val;
    double der_x = c * u.der_x ,
            der_y = c * u.der_y;
    double der_xx = c * u.der_xx,
            der_xy = c * u.der_xy,
            der_yy = c * u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet operator/(const Jet& u,double c){
    Jet a(c,0,0,0,0,0);
    return u/a;

    double val = u.val/c;
    double der_x = u.der_x/c ,
            der_y = u.der_y/c;
    double der_xx = u.der_xx/c,
            der_xy = u.der_xy/c,
            der_yy = u.der_yy/c;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
//other
Jet operator-(Jet const u){
    Jet a(-1,0,0,0,0,0);
    return u*a;

    int c = -1;
    double val = c * u.val;
    double der_x = c * u.der_x ,
            der_y = c * u.der_y;
    double der_xx = c * u.der_xx,
            der_xy = c * u.der_xy,
            der_yy = c * u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}

//funkcji elementarnych sin, cos, exp
Jet sin(const Jet& u){
    double val =sin(u.val);
    double der_x = u.der_x * cos(u.val) ,
            der_y = u.der_y * cos(u.val);
    double  der_xx = -sin(u.val)*u.der_x*u.der_x + cos(u.val)*u.der_xx,
            der_xy = -sin(u.val)*u.der_x*u.der_y + cos(u.val)*u.der_xy,
            der_yy = -sin(u.val)*u.der_y*u.der_y + cos(u.val)*u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet cos(const Jet& u){
    double val =cos(u.val);
    double  der_x = -u.der_x * sin(u.val) ,
            der_y = -u.der_y * sin(u.val);
    double  der_xx = -cos(u.val)*u.der_x*u.der_x - sin(u.val)*u.der_xx,
            der_xy = -cos(u.val)*u.der_x*u.der_y - sin(u.val)*u.der_xy,
            der_yy = -cos(u.val)*u.der_y*u.der_y - sin(u.val)*u.der_yy ;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}
Jet exp(const Jet& u){
    double val =exp(u.val);
    double der_x = u.der_x*exp(u.val) ,
            der_y = u.der_y * exp(u.val);
    double der_xx = exp(u.val)*u.der_x*u.der_x + exp(u.val)*u.der_xx,
            der_xy = exp(u.val)*u.der_x*u.der_y + exp(u.val)*u.der_xy,
            der_yy =exp(u.val)*u.der_y*u.der_y + exp(u.val)*u.der_yy;

    return Jet(val,der_x,der_y, der_xx,der_xy,der_yy);
}

void run(){
    Jet x,y;
    x.val = 10, y.val = 10;
    x.der_x =1;
    y.der_y=1;
    Jet wynik = funkcja(x,y);
    cout << wynik.val << " " << wynik.der_x << " " << wynik.der_y << " " << wynik.der_xx << " " << wynik.der_xy << " " << wynik.der_yy<<endl;
}

void input(){
    int N;
    cin>> N;
    for (int i=0; i<N; i++){
        Jet x,y;
        cin>>x.val>>y.val;
        x.der_x =1;
        y.der_y=1;

        Jet wynik = funkcja(x,y);

        std::cout << std::setprecision(17) << std::fixed;
        cout << wynik.val << " " << wynik.der_x << " " << wynik.der_y << " " << wynik.der_xx << " " << wynik.der_xy << " " << wynik.der_yy<<endl;
    }
}
//int main(){
////    run();
//    input();
//
//
//}

/*

4
0.0 1.0
1.0 −1.0
−2.0 2.0
10.0 0.1

 */