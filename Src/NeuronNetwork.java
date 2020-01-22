// Création d'un réseau de neurones ayant les paramètres donnés (e.g nombre de neurones, fonction d'activation,
// vitesse d'apprentissage, etc). Contient aussi les méthodes d'apprentissage, reconnaissance, sauvegarde,
// et de chargement d'un réseau de neurones.


import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.BufferedReader;


// Classe permettant de créer un réseau de neurones:
public class NeuronNetwork
{
	private int neurons_number;
	private int input_size;

	private Activation fun;
	private double learning_speed; // Must be in ]0, 1[, in practice in [0.05, 0.15].
	private int max_step_number; // Maximum number of steps of learning.

	private double[][] questions_learned = {};
	private double[][] answers_learned = {};

	private double[][] network = {};
	private double[][] bias = {};

	private boolean has_learned = false;


	public NeuronNetwork(int neurons_number, int input_size, Activation fun, double learning_speed, int max_step_number)
	{
		this.neurons_number = neurons_number;
		this.input_size = input_size;
		this.fun = fun;
		this.learning_speed = learning_speed;
		this.max_step_number = max_step_number;
	}


	public void changeParams(Activation fun, double learning_speed, int max_step_number)
	{
		this.fun = fun;
		this.learning_speed = learning_speed;
		this.max_step_number = max_step_number;
	}


	public int neurons_number()
	{
		return this.neurons_number;
	}


	public int input_size()
	{
		return this.input_size;
	}


	public Activation fun()
	{
		return this.fun;
	}


	public double learning_speed()
	{
		return this.learning_speed;
	}


	public int max_step_number()
	{
		return this.max_step_number;
	}


	public int nb_question_learned()
	{
		return this.questions_learned.length;
	}


	public boolean has_learned()
	{
		return this.has_learned;
	}


	public void printNetworkBias()
	{
		Matrix.print(this.network);
		Matrix.print(this.bias);
	}


	// Permet d'ajouter de nouveaux apprentissages:
	public void addToLearn(double[][] toLearn, double[][] good_answers)
	{
		if (toLearn.length != good_answers.length)
		{
			System.out.printf("\nNombres de questions et bonnes réponses différents: %d vs %d.\n\n",
				toLearn.length, good_answers.length);
			throw new RuntimeException();
		}

		if (toLearn.length == 0)
		{
			System.out.printf("\nPas d'apprentissage rentré.\n\n");
			return;
		}

		if (this.input_size() != toLearn[0].length)
		{
			System.out.printf("\nTaille du nouvel apprentissage incompatible: %d vs %d.\n\n",
				this.input_size(), toLearn[0].length);
			throw new RuntimeException();
		}

		// At this point, we can be sure that everything has compatible sizes.

		this.questions_learned = Matrix.concat(toLearn, this.questions_learned);
		this.answers_learned = Matrix.concat(good_answers, this.answers_learned);
	}


	// Permet d'ajouter de nouveaux apprentissages à partir d'un fichier .wav. Ne pas mettre de redondance dans species_to_recognize.
	public void AddToLearnSound(Cetaces[] species_to_recognize, Cetaces species, String filename)
	{
		if (this.neurons_number() != species_to_recognize.length)
		{
			System.out.printf("\nNombre de neurones différent du nombre d'espèces à reconnaître: %d vs %d.\n\n",
				this.neurons_number(), species_to_recognize.length);
			throw new RuntimeException();
		}

		double[][] sample = Sampling.sampling(this.input_size(), filename);

		double[][] good_answers = new double[sample.length][this.neurons_number()];

		for (int i = 0; i < sample.length; ++i)
		{
			int j = 0;

			while (j < species_to_recognize.length)
			{
				if (species == species_to_recognize[j])
					good_answers[i][j] = species == species_to_recognize[j] ? 1 : 0;

				++j;
			}
		}

		this.addToLearn(sample, good_answers);
	}


	// Apprentissage!
	public void learn()
	{
		if (this.nb_question_learned() == 0)
		{
			System.out.println("\nRien à apprendre.\n");
			return;
		}

		System.out.printf("\nNombre total de samples: %d\n", this.nb_question_learned());

		if (!this.has_learned) // First learning.
		{
			this.network = Matrix.random(this.input_size(), this.neurons_number());
			this.bias = Matrix.random(1, this.neurons_number());
		}

		int step = 0, j = 0;

		boolean no_error = true;

		while (step < this.max_step_number)
		{
			if (j == this.nb_question_learned()) // Current pass is finished.
			{
				if (no_error) // Every input has been learned.
				{
					System.out.printf("\nApprentissage terminé (nombre d'étapes: %d).\n\n", step);
					break;
				}
				
				else // A learning pass has to be done once again.
				{
					j = 0;
					no_error = true;
				}
			}

			double[][] input = { this.questions_learned[j] };

			double[][] answer = { this.answers_learned[j] };

			double[][] output = Propagation.output(input, this.network, this.bias, this.fun);

			if (Matrix.are_equal(output, answer)) // Current input is learned.
				++j;

			else // Network and bias are modified until the current input is learned.
			{
				no_error = false;

				double[][] correction = Propagation.correction(answer, output, this.learning_speed);

				this.bias = Propagation.new_bias(this.bias, correction);

				this.network = Propagation.new_network(this.network, input, correction);
			}

			++step;
		}

		if (step == this.max_step_number)
			System.out.println("\nNombre maximal d'étapes d'apprentissage atteint...\n");

		this.has_learned = true;
	}


	// Returns a matrix where each row is the result of a test, each column representing a neuron.
	public double[][] recognize(double[][] tests)
	{
		if (this.nb_question_learned() == 0)
		{
			System.out.println("\nReconnaissance impossible sans apprentissage!\n");
			throw new RuntimeException();
		}

		if (tests.length == 0)
		{
			System.out.println("\nRien à tester.\n");
			throw new RuntimeException();
		}

		if (this.input_size() != tests[0].length)
		{
			System.out.printf("\nTaille des tests incompatible: %d vs %d.\n\n",
				this.input_size(), tests[0].length);
			throw new RuntimeException();
		}

		double[][] outputs = new double[tests.length][this.neurons_number()];

		for (int i = 0; i < tests.length; ++i)
		{
			double[][] test_i = { tests[i] };
			outputs[i] = Propagation.output(test_i, this.network, this.bias, this.fun)[0];
		}

		return outputs;
	}


	// Returns a row matrix filled with the probability of recognition for each neuron.
	public double[][] recognition_level(String filename)
	{
		double[][] sample = Sampling.sampling(this.input_size(), filename);

		double[][] outputs = recognize(sample);

		if (outputs.length == 0)
		{
			System.out.println("\nPas de sortie à reconnaître.\n");
			throw new RuntimeException();
		}

		double[][] level = Matrix.zero(1, this.neurons_number());

		for (int i = 0; i < outputs.length; ++i)
		{
			double[][] output_i = { outputs[i] };
			level = Matrix.sum(level, output_i);
		}

		return Matrix.scal(1. / outputs.length, level);
	}


	// Saves the neuron network's core data to binary files. foldername must not end by an '/'.
	public void save(String foldername)
	{
		String newFolder = "../Saves/" + foldername;
		SavingBinary.createFolder(newFolder);

		try
		{
			FileWriter writer = new FileWriter(newFolder + "/param.txt");
			BufferedWriter buffered_writer = new BufferedWriter(writer); // BufferedReader: for performance.

			buffered_writer.write(Integer.toString(this.neurons_number));
			buffered_writer.newLine();

			buffered_writer.write(Integer.toString(this.input_size));
			buffered_writer.newLine();

			buffered_writer.write(this.fun.toString());
			buffered_writer.newLine();

			buffered_writer.write(Double.toString(this.learning_speed));
			buffered_writer.newLine();

			buffered_writer.write(Integer.toString(this.max_step_number));
			buffered_writer.newLine();

			buffered_writer.close();
			writer.close();
		}

		catch (Exception e)
		{
			System.out.println("Impossible d'écrire dans '" + newFolder + "/param.txt'.\n");
			throw new RuntimeException();
		}

		SavingBinary.writeMatrix(this.questions_learned, newFolder + "/questions_learned.bin");
		SavingBinary.writeMatrix(this.answers_learned, newFolder + "/answers_learned.bin");

		SavingBinary.writeMatrix(this.network, newFolder + "/network.bin");
		SavingBinary.writeMatrix(this.bias, newFolder + "/bias.bin");

		System.out.printf("\nSauvegarde dans '/Saves/%s' réussie.\n\n", foldername);
	}


	// Loads the neuron network's core data from binary files. foldername must not end by an '/'.
	public static NeuronNetwork load(String foldername)
	{
		NeuronNetwork neurons = new NeuronNetwork(0, 0, new Heaviside(), 0, 0);

		String folder = "../Saves/" + foldername;

		try
		{
			FileReader reader = new FileReader(folder + "/param.txt");
			BufferedReader bufferedReader = new BufferedReader(reader); // BufferedReader: for performance.

			neurons.neurons_number = Integer.parseInt(bufferedReader.readLine());
			neurons.input_size = Integer.parseInt(bufferedReader.readLine());
			neurons.fun = Activation.get_fun(bufferedReader.readLine());
			neurons.learning_speed = Double.parseDouble(bufferedReader.readLine());
			neurons.max_step_number = Integer.parseInt(bufferedReader.readLine());

			bufferedReader.close();
			reader.close();
		}

		catch (Exception e)
		{
			System.out.println("Fichier '" + folder + "/param.txt' non trouvé.\n");
			throw new RuntimeException();
		}

		// Nombre de double dans le fichier '/answers_learned.bin':
		int nb_values = (int) SavingBinary.doubleNumber(folder + "/answers_learned.bin");

		int nb_answer_learned = nb_values / neurons.neurons_number;

		neurons.questions_learned = SavingBinary.readMatrix(nb_answer_learned, neurons.input_size(), folder + "/questions_learned.bin");

		neurons.answers_learned = SavingBinary.readMatrix(nb_answer_learned, neurons.neurons_number(), folder + "/answers_learned.bin");

		neurons.network = SavingBinary.readMatrix(neurons.input_size(), neurons.neurons_number(), folder + "/network.bin");

		neurons.bias = SavingBinary.readMatrix(1, neurons.neurons_number(), folder + "/bias.bin");

		neurons.has_learned = neurons.network.length != 0;

		System.out.printf("\nChargement depuis '/Saves/%s' réussi.\n\n", foldername);

		if (!neurons.has_learned)
			System.out.println("\nAuncun apprentissage n'a été effectué.\n");

		return neurons;
	}
}
