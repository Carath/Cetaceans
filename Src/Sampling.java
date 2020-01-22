// Import d'un fichier son, découpage en tronçons de taille une puissance de 2, et application de la FFT sur chaque tronçons,
// afin de les fournir comme entrées au réseau de neurones.


public class Sampling
{
	// Vérifie si un entier est une puissance de 2.
	public static boolean isPowerOfTwo(int n)
	{
		if (n <= 0)
			return false;

		while (n % 2 == 0)
			n /= 2;

		return n == 1;
	}


	// Crée à partir d'un fichier .wav en mono, un tableau d'échantillons en fréquences à fournir au réseau de neurones.
	// powerOfTwo DOIT être une puissance de 2!
	public static double[][] sampling(int powerOfTwo, String filename)
	{
		if (!isPowerOfTwo(powerOfTwo))
		{
			System.out.printf("\nLa taille de l'échantillon n'est pas une puissance de 2: %d\n\n", powerOfTwo);
			throw new RuntimeException();
		}

		try
		{
			// Importe un fichier son de type .wav en mono.
			Son son = new Son(filename);

			// Nombre d'entrées à fournir au réseau de neurone pour cet échantillon:
			int nb_paquets = son.donnees().length / powerOfTwo;

			if (nb_paquets == 0)
			{
				System.out.printf("Taille de paquets trop grande.\n\n");
				throw new RuntimeException();
			}

			// On divise le son en paquets de taille une puissance de 2, et on le passe en complexe:
			Complexe[][] signal = new Complexe[nb_paquets][powerOfTwo];

			for (int i = 0; i < nb_paquets * powerOfTwo; ++i) // La fin du son est perdue.
				signal[i / powerOfTwo][i % powerOfTwo] = new ComplexeCartesien(son.donnees()[i], 0.);

			// On applique la FFT sur chaque paquet:
			for (int i = 0; i < nb_paquets; ++i)
				signal[i] = FFTCplx.appliqueSur(signal[i]);

			// On repasse en réel grâce au module:
			double[][] data = new double[nb_paquets][powerOfTwo];

			for (int i = 0; i < nb_paquets; ++i)
			{
				for (int j = 0; j < powerOfTwo; ++j)
					data[i][j] = signal[i][j].mod();
			}

			System.out.println("Sample récupérée: " + nb_paquets + " échantillons.");

			// Le résultat va être fourni au réseau de neurones comme entrées.
			return data;
		}

		catch (Exception e)
		{
			System.out.println("\n-> Impossible de trouver le fichier '" + filename + "', ou le fichier n'est pas en mono.");
			return null;
		}
	}


	public static void main (String[] args)
	{
		double[][] sample = sampling(16, "../Audio/toLearn/Beluga_1.wav");

		double[][] first_sample = { sample[0] };

		System.out.println("\nPremier échantillon:\n");
		Matrix.print(first_sample);
	}
}
