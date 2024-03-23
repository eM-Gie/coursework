import argparse
import sympy as sp

def newton_method(function, initial_guess, step_size, max_steps, tolerance):
    x = sp.symbols('x')
    derivative = sp.diff(function, x)

    for step in range(max_steps):
	print(f"Iteration 0: x = {initial_guess}")

	function_value = function.subs(x, initial_guess)
	derivative_value = derivative.subs(x, initial_guess)

	if abs(function_value) < tolerance:
        	print(f"Converged to root x = {initial_guess} in {step + 1} iterations.")

		return initial_guess
        print(f"Iteration {step + 1}: x = {initial_guess}")

        initial_guess = initial_guess - step_size * function_value / derivative_value

    return None

def main():
    parser = argparse.ArgumentParser(description="Newton's method for finding roots of a function.")
    parser.add_argument("function", help="The function for which to find the root, e.g., 'x**2+x+1'")
    parser.add_argument("-g", "--initial_guess", type=float, default=0.0, help="Initial guess for the root.")
    parser.add_argument("-s", "--step_size", type=float, default=0.01, help="Step size in the derivative calculation.")
    parser.add_argument("-m", "--max_steps", type=int, default=100, help="Maximum number of steps in the Newton method.")
    parser.add_argument("-t", "--tolerance", type=float, default=1e-6, help="Tolerance for considering the root found.")
    args = parser.parse_args()

    function = sp.sympify(args.function)
    root = newton_method(function, args.initial_guess, args.step_size, args.max_steps, args.tolerance)

    if root is not None:
        print(f"Root found: {root}")
    else:
        print("Root not found within the specified parameters.")

if __name__ == "__main__":
    main()
