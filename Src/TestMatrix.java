// Vérification des fonctions de bases sur les matrices.


public class TestMatrix
{
	public static void main(String[] args)
	{
		double[][] mat = { { 1, 2, 3 }, { 4, 5, 6 } };

		System.out.println("mat:");
		Matrix.print(mat);

		System.out.println("transposée de mat:");
		Matrix.print(Matrix.transpose(mat));

		System.out.println("3 * mat");
		Matrix.print(Matrix.scal(3, mat));

		System.out.println("mat + mat:");
		Matrix.print(Matrix.sum(mat, mat));

		System.out.println("(transposée de mat) * mat:");
		Matrix.print(Matrix.product(Matrix.transpose(mat), mat));

		System.out.println("On map Sigmoid à mat:\n");
		Matrix.print(Matrix.map(new Sigmoid(), mat));

		System.out.println("On concatène verticalement mat avec elle-même:");
		Matrix.print(Matrix.concat(mat, mat));

		System.out.println("Doit échouer: mat * mat:");
		Matrix.print(Matrix.product(mat, mat));
	}
}
