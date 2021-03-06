
#include "stdafx.h"
#include "mpi.h"
#include "stdio.h"
#include "stdlib.h"
#define NUMBEROFPROCESSES 5


int sum = 0;

int main(int argc, char* argv[])
{
	MPI_Init(&argc, &argv);

	int rank;
	int mpi_size;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &mpi_size);
	printf("Rank = %d\n", rank);
	if (rank == 0) {
		int received = 0;
		int array[] = { 1, 2, 3,1,1,2, 3, 4, 5, 1, 2, 3 };
		int size = sizeof(array) / sizeof(int);
		MPI_Send(&size, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(&array, size, MPI_INT, 0, 0, MPI_COMM_WORLD);
		while (rank != 0) {
			MPI_Recv(&received, 1, MPI_INT, rank, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			sum += received;
		}
		printf("Sum = %d\n", sum);

	}
	else {
		int mySum = 0;
		int i;
		int received[19];

		int size;
		MPI_Recv(&size, 1, MPI_INT, rank, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		MPI_Recv(&received, size, MPI_INT, rank, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);		

		for (i = rank; i < mpi_size; i+= rank - 1) {
			mySum += received[i];
		}
		MPI_Send(&mySum, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		printf("Process %d sum %d ", rank, mySum);
	}
	MPI_Finalize();
	return 0;
}
