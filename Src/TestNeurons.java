// Vérification du bon fonctionnement d'un réseau neurone avec un perceptron. Application aux portes logiques AND,
// OR et XOR. Sauvegarde et chargements aussi vérifiés.


public class TestNeurons
{
	public static void main(String[] args)
	{
		// Paramètres des réseaux de neurones:

		int neurons_number = 1;
		int input_size = 2;

		Activation fun = new Heaviside();
		double learning_speed = 0.1;
		int max_step_number = 1000;


		// Créations des réseaux de neurones:

		NeuronNetwork neurons_AND = new NeuronNetwork(neurons_number, input_size, fun, learning_speed, max_step_number);

		NeuronNetwork neurons_OR = new NeuronNetwork(neurons_number, input_size, fun, learning_speed, max_step_number);

		NeuronNetwork neurons_XOR = new NeuronNetwork(neurons_number, input_size, fun, learning_speed, max_step_number);


		// Données à apprendre:

		double[][] toLearn = { {0, 0}, {0, 1}, {1, 0}, {1, 1}};

		double[][] good_answers_AND = { {0}, {0}, {0}, {1} };

		double[][] good_answers_OR = { {0}, {1}, {1}, {1} };

		double[][] good_answers_XOR = { {0}, {1}, {1}, {0} };


		// On prépare l'apprentissage:

		neurons_AND.addToLearn(toLearn, good_answers_AND);

		neurons_OR.addToLearn(toLearn, good_answers_OR);

		neurons_XOR.addToLearn(toLearn, good_answers_XOR);


		// Apprentissages:

		System.out.print("Apprentissage pour 'AND':");
		neurons_AND.learn();

		System.out.print("Apprentissage pour 'OR':");
		neurons_OR.learn();

		System.out.print("Apprentissage pour 'XOR':");
		neurons_XOR.learn(); // Convergence impossible pour XOR avec 1 seule couche.


		// Sauvegardes:

		neurons_AND.save("neurons_AND");
		neurons_OR.save("neurons_OR");


		// Chargement:

		NeuronNetwork neurons = NeuronNetwork.load("neurons_OR");

		System.out.printf("-> neurons_number: %d, input_size: %d, nb_question_learned: %d\n\n",
			neurons.neurons_number(), neurons.input_size(), neurons.nb_question_learned());


		// Résultats:

		System.out.println("Matrice de poids et biais pour 'AND':\n");
		neurons_AND.printNetworkBias();

		System.out.println("Matrice de poids et biais pour 'OR':\n");
		neurons_OR.printNetworkBias();


		// Vérification:

		System.out.println("Vérification pour 'AND':\n");
		Matrix.print(neurons_AND.recognize(toLearn)); // OK!

		System.out.println("Vérification pour 'OR':\n");
		Matrix.print(neurons_OR.recognize(toLearn)); // OK!


		// Entrées erronées:

		System.out.println("Reconnaissance sur des entrées erronées pour 'AND':\n");

		double[][] essai1 = { {0.5, 0.5} };
		Matrix.print(neurons_AND.recognize(essai1));

		double[][] essai2 = { {0.99, 0.99} };
		Matrix.print(neurons_AND.recognize(essai2));
	}
}
