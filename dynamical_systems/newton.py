
import argparse
import sympy

def parse_args():
        parser = argparse.ArgumentParser()
        parser.add_argument('fun', help = 'the funciton')
        parser.add_argument('x0', help = 'initial guess')
        parser.add_argument('ss', help = 'step size')
        parser.add_argument('nus', help = 'number of steps')
        parser.add_argument('h', help = 'dokladnosc')
        return parser.parse_args()

def do_sth(fun_str, x0):
        exp = sympy.sympify(fun_str)
        print("the result is:", exp.subs('x',x0))


def new_meth(fun_str, x0, step_size, num_of_steps, h):
        x = sympy.symbols('x')
        fun = sympy.sympify(fun_str)
        der = sympy.diff(fun, x)
    
        for step in range(num_of_steps):
               fun_val = fun.subs(x, x0)
               der_val = der.subs(x,x0)
        
               if abs(fun_val) < h:
                      print("converges to root: ", x0)
                      return x0
               else:
                      x0 = x0 - step_size * fun_val/ der_val


def main():
        args = parse_args()
	
        new_meth(args.fun, int(args.x0), float(args.ss), int(args.nus), float(args.h))


if __name__ == '__main__':
        main()
