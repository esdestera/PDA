// MPI-Leader.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "mpi.h"
#include <stdlib.h>
#include <time.h>
#define NUMBEROFPROCESSES 5

int generateNumber() {
	return rand() % 20 + 1;
}
int main(int argc, char *argv[])
{
	
	MPI_Init(&argc, &argv);

	int rank;
	int next, previous;
	int received[2] = { 0,0 };
	int leader[2];
	int number = generateNumber();
	
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	srand(time((time_t*)rank));

	if (rank < NUMBEROFPROCESSES - 1)
	{
		if (rank < NUMBEROFPROCESSES)
		{
			next = rank + 1;
		}
		else
		{
			next = 0;
		}

		if (rank < NUMBEROFPROCESSES)
		{
			if (rank > 0)
			{
				previous = rank - 1;
			}
			else
			{
				previous = NUMBEROFPROCESSES - 1;
			}
		}
	}
	MPI_Request request;
	if (rank == 0) {
		leader[0] = rank;
		if (received[1] > number)
		{
			leader[1] = received[0];
		}
		else
		{
			leader[1] = number;
		}
		MPI_Isend(&leader, 1, MPI_INT, next, 0, MPI_COMM_WORLD, &request);
		MPI_Recv(&received, 1, MPI_INT, previous, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		printf("Leader %d value %d", received[0], received[1]);


	}
	else {
		MPI_Recv(&received, 1, MPI_INT, previous, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		printf("Received %d", received);
		leader[0] = rank;
		if (received[1] > number)
		{
			leader[1] = received[1];
		}
		else
		{
			leader[1] = number;
		}
		MPI_Isend(&leader, 1, MPI_INT, next, 0, MPI_COMM_WORLD, &request);
		printf("Rank %d value %d", leader[0], leader[1]);

	}

	MPI_Finalize();
	return 0;
}

