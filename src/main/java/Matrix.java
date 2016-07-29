
/*
	public class Matrix {

		// la taille de notre matrice
		private int height, width ;

		// le tableau de son contenu
		private double [][] data ;

		// différents constructeurs
		public Matrix (int height, int width) { ... }
		public Matrix(double[][] data) { ... }
	}
	private static Random rand = ThreadLocalRandom.current() ;

	public static Matrix randomMatrix(int height, int width) {

		Matrix m = new Matrix(height, width) ;
		for (int i = 0 ; i < height ; i++) {
			for (int j = 0 ; j < width ; j++) {
				m.data[i][j] = rand.nextDouble() ;
			}
		}

		return m ;
	}
	public class MatrixRandomValueTask extends RecursiveTask<Boolean> {

	private static ThreadLocalRandom rand = ThreadLocalRandom.current() ;

	// la sous-matrice sur laquelle cette tâche opère
	private Matrix m ;
	private int i0, iF, j0, jF ;

	// constructeurs
	public MatrixRandomValueTask (Matrix m) { ... }
	public MatrixRandomValueTask (Matrix m, int i0, int iF, int j0, int jF) { ... }

	// méthode imposée par RecursiveTask
	public Boolean compute() {

		// la méthode shouldSplit() décide si la sous-matrice courante doit
		// encore être divisée ou pas
		if (shouldSplit()) {

			// ces méthodes réalisent la division, plusieurs stratégies
			// sont possibles
			int iLim = subHeight(m, i0) ;
			int jLim = subWidth(m, j0) ;

			// création de quatre sous-tâches, une par sous-matrice
			MatrixRandomValueTask subTask11 =
				new MatrixRandomValueTask(m, i0, iLim, j0, jLim) ;
			MatrixRandomValueTask subTask21 =
				new MatrixRandomValueTask(m, iLim, iF, j0, jLim) ;
			MatrixRandomValueTask subTask12 =
				new MatrixRandomValueTask(m, i0, iLim, jLim, jF) ;
			MatrixRandomValueTask subTask22 =
				new MatrixRandomValueTask(m, iLim, iF, jLim, jF) ;

			// lancement de chaque tâche
			subTask21.fork() ;
			subTask12.fork() ;
			subTask22.fork() ;

			// exécution du calcul pour cette tâche
			subTask11.process() ;

		} else {

			// traitement de la sous-matrice dans laquelle on se trouve
			process() ;

		}

		return true ;
	}

	public void process() {

		for (int i = i0 ; i < iF ; i++) {
			for (int j = j0 ; j < jF ; j++) {
				m.line(i)[j] = rand.nextDouble() ;
			}
		}
	}
}
}*/
