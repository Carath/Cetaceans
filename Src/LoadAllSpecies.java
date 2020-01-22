// Chargement du réseau de neurone enregistré lors d'une exécution de SaveAllSpecies.java, et vérification sur des exemples.


public class LoadAllSpecies
{
	public static void showcase(NeuronNetwork neurons, String filename)
	{
		double[][] level = neurons.recognition_level(filename);

		System.out.printf("\nNiveau de reconnaissance pour le fichier '%s':\n\n", filename);

		for (Cetaces c : Cetaces.values())
			System.out.printf("%-10s\t\t%.2f %%\n", c, 100 * level[0][c.ordinal()]); // all species.
		System.out.println();
	}


	public static void main (String[] args)
	{
		System.out.printf("On cherche à répondre à identifier chaque espèce enregistrée.\n");


		// Chargement:

		NeuronNetwork neurons = NeuronNetwork.load("Save_AllSpecies");


		// Reconnaissance:

		// Chaque pourcentage correspond au niveau de reconnaissance d'un seul neurone!

		showcase(neurons, "../Audio/toTest/BaleineBec_5.wav");
		showcase(neurons, "../Audio/toTest/BaleineBleue_3.wav");
		showcase(neurons, "../Audio/toTest/BaleineBosse_4.wav");
		showcase(neurons, "../Audio/toTest/Beluga_6.wav");
		showcase(neurons, "../Audio/toTest/Bruit_2.wav");
		showcase(neurons, "../Audio/toTest/Cachalot_3.wav");
		showcase(neurons, "../Audio/toTest/Orque_3.wav");
	}
}
