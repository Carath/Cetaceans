// Apprentissage et sauvegarde d'un réseau de 7 neurones cherchant à reconnaître chaque espèce définie.
// Peut prendre environ 30s pour s'exécuter.


public class SaveAllSpecies
{
	public static void main (String[] args)
	{
		// On cherche à répondre à identifier chaque espèce enregistrée".

		// Paramètres du réseau de neurones:

		int neurons_number = Cetaces.values().length; // 1 neurone pour chaque espèce -> 7 neurones.
		int input_size = 2048;

		Activation fun = new Heaviside();
		double learning_speed = 0.2;
		int max_step_number = 1500000;


		// Création du réseau de neurones:

		NeuronNetwork neurons = new NeuronNetwork(neurons_number, input_size, fun, learning_speed, max_step_number);


		// Création d'un échantillon:

		Cetaces[] species_to_recognize = Cetaces.values(); // Toutes les espèces sont à reconnaître.

		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineABec, "../Audio/toLearn/BaleineBec_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineBleue, "../Audio/toLearn/BaleineBleue_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.BaleineBosse, "../Audio/toLearn/BaleineBosse_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Beluga, "../Audio/toLearn/Beluga_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Bruit, "../Audio/toLearn/Bruit_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Cachalot, "../Audio/toLearn/Cachalot_1.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Cachalot, "../Audio/toLearn/Cachalot_2.wav");
		neurons.AddToLearnSound(species_to_recognize, Cetaces.Orque, "../Audio/toLearn/Orque_1.wav");


		// // Apprentissage:

		neurons.learn();


		// // Sauvegarde:

		neurons.save("Save_AllSpecies");
	}
}
