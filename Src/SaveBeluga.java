// Apprentissage et sauvegarde d'un perceptron cherchant à reconnaître les Bélugas. Peut prendre environ 15s pour s'exécuter.


public class SaveBeluga
{
	public static void main (String[] args)
	{
		// On cherche à répondre à la question: "Beluga - oui/non?".

		// Paramètres du réseau de neurones:

		int neurons_number = 1;
		int input_size = 2048;

		Activation fun = new Heaviside();
		double learning_speed = 0.15;
		int max_step_number = 1000000;


		// Création du réseau de neurones:

		NeuronNetwork neurons = new NeuronNetwork(neurons_number, input_size, fun, learning_speed, max_step_number);


		// Création d'un échantillon:

		Cetaces[] species_to_recognize = { Cetaces.Beluga };

		neurons.AddToLearnSound(species_to_recognize, Cetaces.Bruit, "../Audio/toLearn/Bruit_1.wav");

		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineABec, "../Audio/toLearn/BaleineBec_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineABec, "../Audio/toLearn/BaleineBec_2.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineABec, "../Audio/toLearn/BaleineBec_3.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineABec, "../Audio/toLearn/BaleineBec_4.wav");

		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineBleue, "../Audio/toLearn/BaleineBleue_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineBleue, "../Audio/toLearn/BaleineBleue_2.wav");

		neurons.AddToLearnSound(species_to_recognize, Cetaces.Beluga, "../Audio/toLearn/Beluga_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Beluga, "../Audio/toLearn/Beluga_2.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Beluga, "../Audio/toLearn/Beluga_3.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Beluga, "../Audio/toLearn/Beluga_4.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Beluga, "../Audio/toLearn/Beluga_5.wav");


		// Apprentissage:

		neurons.learn();


		// Sauvegarde:

		neurons.save("Save_Beluga");
	}
}
