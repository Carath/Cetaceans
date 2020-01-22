// Fonctions de base sur les matrices (e.g affichage, somme, multiplication, comparaison, etc).


public class Matrix
{
	public static int rows(double[][] mat)
	{
		return mat.length;
	}


	public static int cols(double[][] mat)
	{
		return mat[0].length;
	}


	public static void print(double[][] mat)
	{
		System.out.printf("Matrice %d x %d:\n\n", rows(mat), cols(mat));

		for (int i = 0; i < rows(mat); ++i)
		{
			for (int j = 0; j < cols(mat); ++j)
				System.out.printf("%10.2f", mat[i][j]);
			System.out.println();
		}

		System.out.println();
	}


	public static double[][] zero(int rows, int cols)
	{
		double[][] mat = new double[rows][cols];

		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < cols; ++j)
				mat[i][j] = 0;
		}

		return mat;
	}


	public static double[][] random(int rows, int cols)
	{
		double[][] mat = new double[rows][cols];

		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < cols; ++j)
				mat[i][j] = 2. * Math.random() - 1; // double aléatoire dans [-1, 1[.
		}

		return mat;
	}


	public static double[][] copy(double[][] mat)
	{
		double[][] mat2 = new double[rows(mat)][cols(mat)];

		for (int i = 0; i < rows(mat); ++i)
		{
			for (int j = 0; j < cols(mat); ++j)
				mat2[i][j] = mat[i][j];

		}

		return mat2;
	}


	public static boolean are_equal(double[][] mat1, double[][] mat2)
	{
		if (rows(mat1) != rows(mat2) || cols(mat1) != cols(mat2))
		{
			System.out.printf("Matrices can't be equal - conflicting sizes: %d x %d vs %d x %d\n\n",
				rows(mat1), cols(mat1), rows(mat2), cols(mat2));
			throw new RuntimeException();
		}

		for (int i = 0; i < rows(mat1); ++i)
		{
			for (int j = 0; j < cols(mat1); ++j)
			{
				if (!Precision.egaliteAepsilonPres(mat1[i][j], mat2[i][j])) // égalité à un epsilon près.
					return false;
			}
		}

		return true;
	}


	public static double[][] transpose(double[][] mat)
	{
		double[][] matT = new double[cols(mat)][rows(mat)];

		for (int i = 0; i < rows(mat); ++i)
		{
			for (int j = 0; j < cols(mat); ++j)
				matT[j][i] = mat[i][j];
		}

		return matT;
	}


	public static double[][] scal(double x, double[][] mat)
	{
		double[][] mat2 = new double[rows(mat)][cols(mat)];

		for (int i = 0; i < rows(mat); ++i)
		{
			for (int j = 0; j < cols(mat); ++j)
				mat2[i][j] = x * mat[i][j];

		}

		return mat2;
	}


	public static double[][] sum(double[][] mat1, double[][] mat2)
	{
		if (rows(mat1) != rows(mat2) || cols(mat1) != cols(mat2))
		{
			System.out.printf("Cannot do the sum of matrices - conflicting sizes: %d x %d vs %d x %d\n\n",
				rows(mat1), cols(mat1), rows(mat2), cols(mat2));
			throw new RuntimeException();
		}

		double[][] mat = new double[rows(mat1)][cols(mat1)];

		for (int i = 0; i < rows(mat1); ++i)
		{
			for (int j = 0; j < cols(mat1); ++j)
				mat[i][j] = mat1[i][j] + mat2[i][j];
		}

		return mat;
	}


	public static double[][] diff(double[][] mat1, double[][] mat2)
	{
		return sum(mat1, scal(-1, mat2));
	}


	public static double[][] product(double[][] mat1, double[][] mat2)
	{
		if (cols(mat1) != rows(mat2))
		{
			System.out.printf("Cannot do the product of matrices - conflicting sizes: %d x %d vs %d x %d\n\n",
				rows(mat1), cols(mat1), rows(mat2), cols(mat2));
			throw new RuntimeException();
		}

		double[][] mat = new double[rows(mat1)][cols(mat2)];

		for (int i = 0; i < rows(mat1); ++i)
		{
			for (int j = 0; j < cols(mat2); ++j)
			{
				for (int k = 0; k < cols(mat1); ++k)
					mat[i][j] += mat1[i][k] * mat2[k][j];
			}
		}

		return mat;
	}


	public static double[][] map(Activation fun, double[][] mat)
	{
		double[][] mat2 = new double[rows(mat)][cols(mat)];

		for (int i = 0; i < rows(mat); ++i)
		{
			for (int j = 0; j < cols(mat); ++j)
				mat2[i][j] = fun.activation(mat[i][j]);
		}

		return mat2;
	}


	// Concat two matrices vertically: the first one, then the other.
	public static double[][] concat(double[][] mat1, double[][] mat2)
	{
		if (mat1.length == 0)
			return mat2;

		if (mat2.length == 0)
			return mat1;

		if (cols(mat1) != cols(mat2))
		{
			System.out.printf("Cannot vertically concat matrices - conflicting sizes: %d x %d vs %d x %d\n\n",
				rows(mat1), cols(mat1), rows(mat2), cols(mat2));
			throw new RuntimeException();
		}

		int rows = rows(mat1) + rows(mat2);

		double[][] mat = new double[rows][cols(mat1)];

		for (int i = 0; i < rows(mat1); ++i)
			mat[i] = mat1[i];

		for (int i = 0; i < rows(mat2); ++i)
			mat[rows(mat1) + i] = mat2[i];
			
		return mat;
	}
}
