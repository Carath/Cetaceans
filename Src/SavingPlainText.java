// Obsolète, fait la même chose que SavingBinary.java en fichiers .txt, ce qui crée des fichiers de plus grande taille,
// et est plus lent à fonctionner.


import java.io.File;

import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.LineNumberReader;


public class SavingPlainText
{
	 // Creates a (or several if nested) folder at the given adress. Does not override existing folders.
	public static void createFolder(String foldername)
	{
		File dir = new File(foldername);
		dir.mkdirs();
	}


	// Writes in a text file a matrix of doubles (one value per line). Override an existing file with the same name.
	public static void writeMatrix(double[][] matrix, String filename)
	{
		try
		{
			FileWriter writer = new FileWriter(filename);
			BufferedWriter buffered_writer = new BufferedWriter(writer); // BufferedReader: for performance.

			for (int i = 0; i < Matrix.rows(matrix); ++i)
			{
				for (int j = 0; j < Matrix.cols(matrix); ++j)
				{
					buffered_writer.write(Double.toString(matrix[i][j]));
					buffered_writer.newLine();
				}
			}

			buffered_writer.close();
			writer.close();

			// System.out.println(filename + " -> écriture réussie.\n");
		}

		catch (Exception e)
		{
			System.out.println("Impossible d'écrire dans '" + filename + "'.\n");
			throw new RuntimeException();
		}
	}


	// Reads from a text file a matrix of doubles (one value per line):
	public static double[][] readMatrix(int rows, int cols, String filename)
	{
		try
		{
			FileReader reader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(reader); // BufferedReader: for performance.

			double[][] matrix = new double[rows][cols];

			String str;
			int index = 0;

			while ((str = bufferedReader.readLine()) != null)
			{
				if (str.length() != 0)
				{
					matrix[index / cols][index % cols] = Double.parseDouble(str);
					++index;
				}
			}

			bufferedReader.close();
			reader.close();

			// System.out.println(filename + " -> lecture réussie.\n");

			double[][] empty = {};

			return index != 0 ? matrix : empty;
		}

		catch (Exception e)
		{
			System.out.println("Fichier '" + filename + "' non trouvé.\n");
			throw new RuntimeException();
		}
	}


	// Counts the number of lines in a text file.
	public static int lineNumber(String filename)
	{
		try
		{
			FileReader reader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(reader); // BufferedReader: for performance.
			LineNumberReader lineNumberReader = new LineNumberReader(bufferedReader);

			int lineNumber = 0;
			int data = lineNumberReader.read();

			while(data != -1)
			{
				data = lineNumberReader.read();
				lineNumber = lineNumberReader.getLineNumber();
			}

			lineNumberReader.close();
			bufferedReader.close();
			reader.close();

			return lineNumber;
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

		String filename = "../Saves/test_matrix_file.txt";

		writeMatrix(mat, filename);

		double[][] mat_read = readMatrix(2, 3, filename);

		Matrix.print(mat_read);

		System.out.println("Nombre de lignes: " + lineNumber(filename));
	}
}
