// Savegarde et chargement de matrices de double dans des fichiers binaires. Utile pour sauvegarder/charger
// les matrices de poids et de biais, ainsi que les questions apprises avec leur réponse.


import java.io.File;

import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;


public class SavingBinary
{
	// Creates a (or several if nested) folder at the given adress. Does not override existing folders.
	public static void createFolder(String foldername)
	{
		File dir = new File(foldername);
		dir.mkdirs();
	}


	public static long getFileSize(String filename) // in bytes!
	{
		File file = new File(filename);
		long length = file.length();
		return length;
	}


	// Returns the number of doubles written in a binary file.
	public static long doubleNumber(String filename)
	{
		return getFileSize(filename) / Double.BYTES;
	}


	// Writes in a binary file a matrix of doubles. Override an existing file with the same name.
	public static void writeMatrix(double[][] matrix, String filename)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(filename);
			BufferedOutputStream bos = new BufferedOutputStream(fos); // for performance.
			DataOutputStream dos = new DataOutputStream(bos);

			for (int i = 0; i < Matrix.rows(matrix); ++i)
			{
				for (int j = 0; j < Matrix.cols(matrix); ++j)
					dos.writeDouble(matrix[i][j]);
			}

			dos.close();
			bos.close();
			fos.close();

			// System.out.println(filename + " -> écriture réussie.\n");
		}

		catch (Exception e)
		{
			System.out.println("Impossible d'écrire dans '" + filename + "'.\n");
			throw new RuntimeException();
		}
	}


	// Reads from a binary file a matrix of doubles:
	public static double[][] readMatrix(int rows, int cols, String filename)
	{
		try
		{
			if (doubleNumber(filename) < 1)
			{
				double[][] empty = {};
				return empty;
			}

			FileInputStream fis = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(fis); // for performance.
			DataInputStream dis = new DataInputStream(bis);

			double[][] matrix = new double[rows][cols];

			for (int i = 0; i < rows * cols; ++i)
				matrix[i / cols][i % cols] = dis.readDouble();

			dis.close();
			bis.close();
			fis.close();

			// System.out.println(filename + " -> lecture réussie.\n");

			return matrix;
		}

		catch (Exception e)
		{
			System.out.println("Fichier '" + filename + "' non trouvé.\n");
			throw new RuntimeException();
		}
	}


	public static void main(String[] args)
	{
		createFolder("../Saves");

		double[][] mat = { {1, 2, 3}, {4, 5, 6} };

		String filename = "../Saves/test_matrix_file.bin";

		writeMatrix(mat, filename);

		double[][] mat_read = readMatrix(2, 3, filename);

		Matrix.print(mat_read);

		System.out.println("\nNombre de doubles: " + doubleNumber(filename));
	}
}
