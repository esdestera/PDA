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
	srand(time(NULL));

	MPI_Init(&argc, &argv);

	int rank;
	int next, previous;
	int received = 0;
	int leader[2];
	int number = generateNumber();
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

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
	MPI_Recv(&received, 1, MPI_INT, previous, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
	printf("Received %d", received);
	leader[0] = rank;
	if (received > number)
	{
		leader[1] = received;				
	}
	else
	{
		leader[1] = number;
	}
	MPI_Isend(&leader, 1, MPI_INT, next, 0, MPI_COMM_WORLD, &request);
	printf("Leader is  %d with number ", leader[0], leader[1]);

	MPI_Finalize();
	return 0;
}

