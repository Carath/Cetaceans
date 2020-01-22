// Chargement du réseau de neurone enregistré lors d'une exécution de SaveBeluga.java, et vérification sur des exemples.


public class LoadBeluga
{
	public static void showcase(NeuronNetwork neurons, String filename)
	{
		double[][] level = neurons.recognition_level(filename);

		System.out.printf("\nNiveau de reconnaissance pour le fichier '%s': %.2f %%\n\n", filename, 100 * level[0][0]);
	}


	public static void main (String[] args)
	{
		System.out.printf("On cherche à répondre à la question: 'Beluga - oui/non?'\n");


		// Chargement:

		NeuronNetwork neurons = NeuronNetwork.load("Save_Beluga");


		// Reconnaissance:

		showcase(neurons, "../Audio/toTest/Beluga_6.wav");

		showcase(neurons, "../Audio/toTest/BaleineBec_5.wav");

		showcase(neurons, "../Audio/toTest/BaleineBleue_3.wav");
	}
}
