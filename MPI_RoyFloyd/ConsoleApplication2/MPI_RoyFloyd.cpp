#include"stdafx.h"
#include<mpi.h>
#include<iostream>
#include<time.h>
#include<Windows.h>
#define N 5
#define INF 99999
using namespace std;

int matr[N][N] = {
	0,3,9,8,3,
	5,0,1,4,2,
	6,6,0,4,5,
	2,9,2,0,7,
	7,9,3,2,0
};

int main(int argc, char** argv)
{
	int size, rank, rc;

	rc = MPI_Init(&argc, &argv);
	if (rc != MPI_SUCCESS)
	{
		cout << "Error starting MPI program. Terminating.\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);


	for (int k = 0; k < N; k++)
	{
		for (int j = 0; j < N; j++)
		{
			if (matr[rank][k] + matr[k][j] < matr[rank][j])
				matr[rank][j] = matr[rank][k] + matr[k][j];
			
		}
		for (int i = 0; i < N; i++) {
			cout << "proc " << rank << " sent " << matr[rank][i] << endl;
		}
	}

	if (rank == 0)
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				if (matr[i][j] == INF)
					cout << "INF ";
				else
					cout << matr[i][j] << " ";
			}
			cout << endl;
		}

	MPI_Finalize();
	return 0;
}