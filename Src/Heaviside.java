// Définition de la fonction d'activation Seuil.


public class Heaviside extends Activation
{
	public double activation(double x)
	{
		return x < 0 ? 0 : 1;
	}


	public String toString()
	{
		return "Heaviside";
	}
}
