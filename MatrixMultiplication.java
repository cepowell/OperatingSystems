// Cait Powell
// CS 222 Lab 4
// 26 October 2015

package millsF15;

public class MatrixMultiplication {

	private static final int M = 3;
	private static final int N = 3;

	static WThread[][] threads = new WThread[M][N];

	public static void main(String Args[]) {

		int[][] A = {{1,4}, {2,5}, {3,6}};
		int[][] B = {{8,7,6}, {5,4,3}};
		int[][] C = new int[M][N];

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				threads[i][j] = new WThread(i, j, A, B, C);
				threads[i][j].start();
			}
		}

		for (int x = 0; x < M; x++) {
			for (int y = 0; y < N; y++)
				try {
					threads[x][y].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

		System.out.println("Matrix Multiplication Results");

		for (int i = 0; i < C[0].length; i++) {
			for (int j = 0; j < C.length; j++) {
				System.out.print(C[i][j] + " ");
			}

			System.out.print("\n");
		}

	}

}

class WThread extends Thread {

	private int row;
	private int col;
	private int[][] A;
	private int[][] B;
	private int[][] C;

	public WThread(int r, int co, int[][] a, int[][] b, int[][] c) {
		row = r;
		col = co;
		A = a;
		B = b;
		C = c;

	}

	public void run() {
		for (int z = 0; z < A[0].length; z++) {
			C[row][col] += A[row][z] * B[z][col];
		}
	}

}
