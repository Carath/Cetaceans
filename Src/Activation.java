// Classe abstraite servant à pouvoir utiliser de manière générique les fonctions d'activation.


public abstract class Activation
{
	public abstract double activation(double x);


	// A ne surtout pas utiliser dans les fonctions de calcul! Peut impacter les performances -> chargement seulement.
	public static Activation get_fun(String name)
	{
		if (name.equals("Heaviside"))
			return new Heaviside();

		else if (name.equals("Sigmoid"))
			return new Sigmoid();

		else
		{
			System.out.println("\nInvalid function name.\n");
			throw new RuntimeException();
		}
	}


	public static void main(String[] args)
	{
		Heaviside fun1 = new Heaviside();
		Sigmoid fun2 = new Sigmoid();

		double x1 = fun1.activation(0.5);
		double x2 = fun2.activation(0.5);

		System.out.printf("x1 = %f, x2 = %f\n", x1, x2);

		Activation fun = get_fun("Sigmoid");
		System.out.println(fun.toString());
	}
}
