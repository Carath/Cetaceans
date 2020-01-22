// Définit une égalité à un epsilon fixé près, afin de limiter l'influence des erreurs de calculs
// se propageant à cause des nombres à virgule flottante.


abstract public class Precision
{
	private final static double epsilon = 0.000001;


	// Pour éviter que des erreurs de précision faussent les tests:
	public static Boolean egaliteAepsilonPres(double x, double y)
	{
		return (Math.abs(x - y) < epsilon);
	}
}
