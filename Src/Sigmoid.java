// Définition de la fonction d'activation Sigmoïde.


public class Sigmoid extends Activation
{
	public double activation(double x)
	{
		return 1. / (1 + Math.exp(-x));
	}


	public String toString()
	{
		return "Sigmoid";
	}
}
