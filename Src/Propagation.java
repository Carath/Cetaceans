// Fonctions pour la propagation des entrées dans le réseau de neurones. Modification des matrices de poids et biais.


public class Propagation
{
	// Single layer only!
	// Inputs, outputs, bias: row matrices.

	public static double[][] output(double[][] input, double[][] network, double[][] bias, Activation fun)
	{
		return Matrix.map(fun, Matrix.sum(Matrix.product(input, network), bias));
	}


	// Terme de correction, en calcul intermédiaire -> optimisation.
	public static double[][] correction(double[][] answer, double[][] output, double learning_speed)
	{
		return Matrix.scal(learning_speed, Matrix.diff(answer, output));
	}


	public static double[][] new_bias(double[][] bias, double[][] correction)
	{
		return Matrix.sum(bias, correction);
	}


	public static double[][] new_network(double[][] network, double[][] input, double[][] correction)
	{
		return Matrix.sum(network, Matrix.product(Matrix.transpose(input), correction));
	}
}
