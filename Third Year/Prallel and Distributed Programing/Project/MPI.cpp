
mpiexec -n 4 Lab7MPI.exe input.in
#include <mpi.h>
#include <iostream>
#include <vector>
#include <string>
#include <queue>
#include <unordered_set>
#include <chrono>
#include <fstream>

const int SIZE = 4;
const int TAG_WORK = 1;
const int TAG_SOLUTION = 2;
const int TAG_REQUEST = 3;
const int TAG_TERMINATE = 4;
const int BATCH_SIZE = 1000;  // Number of states to send in each work batch

struct State {
    std::vector<std::vector<int>> board;
    int free_i, free_j;
    std::vector<std::string> path;

    State(const std::vector<std::vector<int>>& b, int fi, int fj, const std::vector<std::string>& p)
        : board(b), free_i(fi), free_j(fj), path(p) {}

    std::string toString() const {
        std::string result;
        for (const auto& row : board) {
            for (int val : row) {
                result += std::to_string(val) + ",";
            }
        }
        return result;
    }

    // Serialize state for MPI communication
    std::vector<int> serialize() const {
        std::vector<int> data;
        // Board data
        for (const auto& row : board) {
            data.insert(data.end(), row.begin(), row.end());
        }
        // Position of empty tile
        data.push_back(free_i);
        data.push_back(free_j);
        // Path length
        data.push_back(path.size());
        // Path directions (encoded as numbers 0-3)
        for (const auto& dir : path) {
            if (dir == "right") data.push_back(0);
            else if (dir == "down") data.push_back(1);
            else if (dir == "left") data.push_back(2);
            else if (dir == "up") data.push_back(3);
        }
        return data;
    }

    // Deserialize state from MPI communication
    static State deserialize(const std::vector<int>& data) {
        std::vector<std::vector<int>> board(SIZE, std::vector<int>(SIZE));
        int idx = 0;

        // Reconstruct board
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = data[idx++];
            }
        }

        // Get empty tile position
        int free_i = data[idx++];
        int free_j = data[idx++];

        // Reconstruct path
        int path_length = data[idx++];
        std::vector<std::string> path;
        const std::vector<std::string> dirNames = { "right", "down", "left", "up" };
        for (int i = 0; i < path_length; i++) {
            path.push_back(dirNames[data[idx + i]]);
        }

        return State(board, free_i, free_j, path);
    }
};

class ParallelPuzzleSolver {
private:
    const std::vector<std::pair<int, int>> directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
    const std::vector<std::string> dirNames = { "right", "down", "left", "up" };
    int rank;
    int world_size;
    std::unordered_set<std::string> visited;

public:
    ParallelPuzzleSolver(int r, int size) : rank(r), world_size(size) {}

    bool isGoal(const std::vector<std::vector<int>>& board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int expected = (i * SIZE + j + 1) % (SIZE * SIZE);
                if (board[i][j] != (expected == 0 ? 0 : expected)) {
                    return false;
                }
            }
        }
        return true;
    }

    void printBoard(const std::vector<std::vector<int>>& board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                std::cout << board[i][j] << "\t";
            }
            std::cout << std::endl;
        }
        std::cout << std::endl;
    }

    std::vector<State> generateNextStates(const State& current) {
        std::vector<State> next_states;

        for (size_t i = 0; i < directions.size(); i++) {
            int new_i = current.free_i + directions[i].first;
            int new_j = current.free_j + directions[i].second;

            if (new_i >= 0 && new_i < SIZE && new_j >= 0 && new_j < SIZE) {
                std::vector<std::vector<int>> new_board = current.board;
                std::swap(new_board[current.free_i][current.free_j],
                    new_board[new_i][new_j]);

                auto new_path = current.path;
                new_path.push_back(dirNames[i]);

                State new_state(new_board, new_i, new_j, new_path);
                if (visited.find(new_state.toString()) == visited.end()) {
                    next_states.push_back(new_state);
                }
            }
        }

        return next_states;
    }

    void worker_process() {
        std::queue<State> local_queue;
        bool solution_found = false;

        while (!solution_found) {
            // Request work if local queue is empty
            if (local_queue.empty()) {
                int request = 1;
                MPI_Send(&request, 1, MPI_INT, 0, TAG_REQUEST, MPI_COMM_WORLD);

                // Receive response
                MPI_Status status;
                int size;
                MPI_Probe(0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

                if (status.MPI_TAG == TAG_TERMINATE) {
                    break;  // No more work available
                }

                // Receive work batch
                MPI_Get_count(&status, MPI_INT, &size);
                std::vector<int> buffer(size);
                MPI_Recv(buffer.data(), size, MPI_INT, 0, TAG_WORK, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                State new_state = State::deserialize(buffer);
                local_queue.push(new_state);
            }

            // Process local queue
            while (!local_queue.empty() && !solution_found) {
                State current = local_queue.front();
                local_queue.pop();

                if (isGoal(current.board)) {
                    // Send solution to master
                    auto serialized = current.serialize();
                    MPI_Send(serialized.data(), serialized.size(), MPI_INT, 0, TAG_SOLUTION, MPI_COMM_WORLD);
                    solution_found = true;
                    break;
                }

                // Generate and process next states
                auto next_states = generateNextStates(current);
                for (const auto& state : next_states) {
                    visited.insert(state.toString());
                    local_queue.push(state);
                }
            }
        }
    }

    void master_process(const std::vector<std::vector<int>>& initial, int free_i, int free_j) {
        auto start_time = std::chrono::high_resolution_clock::now();

        // Initialize the first state
        State initial_state(initial, free_i, free_j, std::vector<std::string>());
        std::queue<State> work_queue;
        work_queue.push(initial_state);
        visited.insert(initial_state.toString());

        int active_workers = world_size - 1;
        bool solution_found = false;
        State solution_state = initial_state;

        while (active_workers > 0 && !solution_found) {
            MPI_Status status;
            std::vector<int> buffer;

            // Receive any message from workers
            MPI_Probe(MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

            if (status.MPI_TAG == TAG_REQUEST) {
                // Worker requesting work
                int request;
                MPI_Recv(&request, 1, MPI_INT, status.MPI_SOURCE, TAG_REQUEST, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                if (!work_queue.empty()) {
                    // Send work to worker
                    auto serialized = work_queue.front().serialize();
                    work_queue.pop();
                    MPI_Send(serialized.data(), serialized.size(), MPI_INT, status.MPI_SOURCE, TAG_WORK, MPI_COMM_WORLD);
                }
                else {
                    // No more work available
                    int terminate = 1;
                    MPI_Send(&terminate, 1, MPI_INT, status.MPI_SOURCE, TAG_TERMINATE, MPI_COMM_WORLD);
                    active_workers--;
                }
            }
            else if (status.MPI_TAG == TAG_SOLUTION) {
                // Worker found a solution
                int size;
                MPI_Get_count(&status, MPI_INT, &size);
                buffer.resize(size);
                MPI_Recv(buffer.data(), size, MPI_INT, status.MPI_SOURCE, TAG_SOLUTION, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                solution_state = State::deserialize(buffer);
                solution_found = true;

                // Send termination signal to all workers
                for (int i = 1; i < world_size; i++) {
                    int terminate = 1;
                    MPI_Send(&terminate, 1, MPI_INT, i, TAG_TERMINATE, MPI_COMM_WORLD);
                }
                active_workers = 0;
            }
        }

        auto end_time = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time);

        if (solution_found) {
            std::cout << "\nSolution found!" << std::endl;
            std::cout << "Number of moves: " << solution_state.path.size() << std::endl;
            std::cout << "Time taken: " << duration.count() << " milliseconds" << std::endl;

            // Print solution path
            std::cout << "\nSolution path:" << std::endl;
            State current = initial_state;
            printBoard(current.board);

            for (size_t i = 0; i < solution_state.path.size(); i++) {
                std::cout << "Move " << i + 1 << ": " << solution_state.path[i] << std::endl;

                // Apply the move
                for (size_t j = 0; j < directions.size(); j++) {
                    if (dirNames[j] == solution_state.path[i]) {
                        int new_i = current.free_i + directions[j].first;
                        int new_j = current.free_j + directions[j].second;
                        std::swap(current.board[current.free_i][current.free_j],
                            current.board[new_i][new_j]);
                        current.free_i = new_i;
                        current.free_j = new_j;
                        break;
                    }
                }

                printBoard(current.board);
            }
        }
        else {
            std::cout << "No solution found after " << duration.count() << " milliseconds" << std::endl;
        }
    }
};

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);

    int rank, world_size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    if (argc != 2) {
        if (rank == 0) {
            std::cout << "Usage: " << argv[0] << " <input_file>" << std::endl;
        }
        MPI_Finalize();
        return 1;
    }

    ParallelPuzzleSolver solver(rank, world_size);

    if (rank == 0) {
        // Master process reads input and coordinates
        std::ifstream file(argv[1]);
        std::vector<std::vector<int>> initial(SIZE, std::vector<int>(SIZE));
        int free_i = -1, free_j = -1;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                file >> initial[i][j];
                if (initial[i][j] == 0) {
                    free_i = i;
                    free_j = j;
                }
            }
        }

        solver.master_process(initial, free_i, free_j);
    }
    else {
        // Worker processes
        solver.worker_process();
    }

    MPI_Finalize();
    return 0;
}















//doesnt use mpi ok because only the master does stuff and the workers just wait for the final signal
//#include <mpi.h>
//#include <iostream>
//#include <vector>
//#include <string>
//#include <queue>
//#include <unordered_set>
//#include <chrono>
//#include <fstream>
//
//const int SIZE = 4;
//
//struct State {
//    std::vector<std::vector<int>> board;
//    int free_i, free_j;
//    std::vector<std::string> path;
//
//    State(const std::vector<std::vector<int>>& b, int fi, int fj, const std::vector<std::string>& p)
//        : board(b), free_i(fi), free_j(fj), path(p) {}
//
//    std::string toString() const {
//        std::string result;
//        for (const auto& row : board) {
//            for (int val : row) {
//                result += std::to_string(val) + ",";
//            }
//        }
//        return result;
//    }
//};
//
//class PuzzleSolver {
//public:
//    const std::vector<std::pair<int, int>> directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
//    const std::vector<std::string> dirNames = { "right", "down", "left", "up" };
//
//    int getManhattanDistance(const std::vector<std::vector<int>>& board) {
//        int distance = 0;
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                if (board[i][j] != 0) {
//                    int target_i = (board[i][j] - 1) / SIZE;
//                    int target_j = (board[i][j] - 1) % SIZE;
//                    distance += std::abs(i - target_i) + std::abs(j - target_j);
//                }
//            }
//        }
//        return distance;
//    }
//
//    State* solve(const std::vector<std::vector<int>>& initial, int free_i, int free_j) {
//        std::cout << "Starting solver...\nInitial state:" << std::endl;
//        printBoard(initial);
//
//        std::queue<State*> queue;
//        std::unordered_set<std::string> visited;
//
//        State* initialState = new State(initial, free_i, free_j, std::vector<std::string>());
//        queue.push(initialState);
//        visited.insert(initialState->toString());
//
//        int nodes_explored = 0;
//
//        while (!queue.empty()) {
//            State* current = queue.front();
//            queue.pop();
//
//            if (nodes_explored % 1000 == 0) {
//                std::cout << "Explored " << nodes_explored << " nodes. Current state:" << std::endl;
//                printBoard(current->board);
//                std::cout << "Manhattan distance: " << getManhattanDistance(current->board) << std::endl;
//            }
//
//            if (isGoal(current->board)) {
//                std::cout << "Found solution after exploring " << nodes_explored << " nodes!" << std::endl;
//                return current;
//            }
//
//            for (size_t i = 0; i < directions.size(); i++) {
//                int new_i = current->free_i + directions[i].first;
//                int new_j = current->free_j + directions[i].second;
//
//                if (new_i >= 0 && new_i < SIZE && new_j >= 0 && new_j < SIZE) {
//                    std::vector<std::vector<int>> new_board = current->board;
//                    std::swap(new_board[current->free_i][current->free_j],
//                        new_board[new_i][new_j]);
//
//                    State* new_state = new State(new_board, new_i, new_j, current->path);
//                    new_state->path.push_back(dirNames[i]);
//
//                    std::string state_str = new_state->toString();
//                    if (visited.find(state_str) == visited.end()) {
//                        visited.insert(state_str);
//                        queue.push(new_state);
//                    }
//                    else {
//                        delete new_state;
//                    }
//                }
//            }
//
//            nodes_explored++;
//            if (nodes_explored > 100000) {
//                std::cout << "Search limit reached." << std::endl;
//                return nullptr;
//            }
//        }
//
//        return nullptr;
//    }
//
//    bool isGoal(const std::vector<std::vector<int>>& board) {
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                int expected = (i * SIZE + j + 1) % (SIZE * SIZE);
//                if (board[i][j] != (expected == 0 ? 0 : expected)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    void printBoard(const std::vector<std::vector<int>>& board) {
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                std::cout << board[i][j] << "\t";
//            }
//            std::cout << std::endl;
//        }
//        std::cout << std::endl;
//    }
//};
//
//void master_process(const std::string& filename) {
//    std::ifstream file(filename);
//    std::vector<std::vector<int>> initial(SIZE, std::vector<int>(SIZE));
//    int free_i = -1, free_j = -1;
//
//    for (int i = 0; i < SIZE; i++) {
//        for (int j = 0; j < SIZE; j++) {
//            file >> initial[i][j];
//            if (initial[i][j] == 0) {
//                free_i = i;
//                free_j = j;
//            }
//        }
//    }
//
//    auto start_time = std::chrono::high_resolution_clock::now();
//
//    PuzzleSolver solver;
//    State* solution = solver.solve(initial, free_i, free_j);
//
//    auto end_time = std::chrono::high_resolution_clock::now();
//    auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time);
//
//    if (solution) {
//        std::cout << "\nSolution found!" << std::endl;
//        std::cout << "Number of moves: " << solution->path.size() << std::endl;
//        std::cout << "Time taken: " << duration.count() << " milliseconds" << std::endl;
//
//        std::cout << "\nSolution path:" << std::endl;
//        State current(initial, free_i, free_j, std::vector<std::string>());
//        solver.printBoard(current.board);
//
//        for (size_t i = 0; i < solution->path.size(); i++) {
//            std::cout << "Move " << i + 1 << ": " << solution->path[i] << std::endl;
//
//            for (size_t j = 0; j < solver.directions.size(); j++) {
//                if (solver.dirNames[j] == solution->path[i]) {
//                    int new_i = current.free_i + solver.directions[j].first;
//                    int new_j = current.free_j + solver.directions[j].second;
//                    std::swap(current.board[current.free_i][current.free_j],
//                        current.board[new_i][new_j]);
//                    current.free_i = new_i;
//                    current.free_j = new_j;
//                    break;
//                }
//            }
//
//            solver.printBoard(current.board);
//        }
//
//        delete solution;
//
//        // Signal other processes to terminate
//        int terminate_signal = 1;
//        int world_size;
//        MPI_Comm_size(MPI_COMM_WORLD, &world_size);
//
//        // Send termination signal to all worker processes
//        for (int i = 1; i < world_size; i++) {
//            MPI_Send(&terminate_signal, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//        }
//
//        MPI_Finalize();
//        exit(0);  // Exit after finding and printing the solution
//    }
//    else {
//        std::cout << "No solution found after " << duration.count() << " milliseconds" << std::endl;
//    }
//}
//
//void worker_process() {
//    int terminate_signal;
//    MPI_Status status;
//
//    // Wait for termination signal from master
//    MPI_Recv(&terminate_signal, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//
//    MPI_Finalize();
//    exit(0);
//}
//
//int main(int argc, char** argv) {
//    MPI_Init(&argc, &argv);
//
//    int rank;
//    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//    if (argc != 2) {
//        if (rank == 0) {
//            std::cout << "Usage: " << argv[0] << " <input_file>" << std::endl;
//        }
//        MPI_Finalize();
//        return 1;
//    }
//
//    if (rank == 0) {
//        master_process(argv[1]);
//    }
//    else {
//        worker_process();
//    }
//
//    MPI_Finalize();
//    return 0;
//}
//























//#include <mpi.h>
//#include <iostream>
//#include <vector>
//#include <queue>
//#include <cmath>
//#include <climits>
//#include <fstream>
//#include <sstream>
//#include <algorithm>
//
//const int SIZE = 4;
//
//struct Matrix {
//    std::vector<std::vector<int>> values;
//    int manhattan;
//    int steps;
//    int free_i, free_j;
//    std::string move;
//    Matrix* parent;
//
//    Matrix(std::vector<std::vector<int>> vals, int steps, int free_i, int free_j, Matrix* parent, std::string move)
//        : values(vals), steps(steps), free_i(free_i), free_j(free_j), parent(parent), move(move) {
//        calculateManhattan();
//    }
//
//    void calculateManhattan() {
//        manhattan = 0;
//        for (int i = 0; i < SIZE; ++i) {
//            for (int j = 0; j < SIZE; ++j) {
//                if (values[i][j] != 0) {
//                    int target_i = (values[i][j] - 1) / SIZE;
//                    int target_j = (values[i][j] - 1) % SIZE;
//                    manhattan += abs(i - target_i) + abs(j - target_j);
//                }
//            }
//        }
//    }
//
//    std::vector<Matrix*> generateMoves() {
//        std::vector<Matrix*> moves;
//        std::vector<std::pair<int, int>> directions = { {0, -1}, {-1, 0}, {0, 1}, {1, 0} };
//        std::vector<std::string> moveNames = { "left", "up", "right", "down" };
//
//        for (int k = 0; k < 4; ++k) {
//            int new_i = free_i + directions[k].first;
//            int new_j = free_j + directions[k].second;
//
//            if (new_i >= 0 && new_i < SIZE && new_j >= 0 && new_j < SIZE) {
//                std::vector<std::vector<int>> newValues = values;
//                std::swap(newValues[free_i][free_j], newValues[new_i][new_j]);
//                moves.push_back(new Matrix(newValues, steps + 1, new_i, new_j, this, moveNames[k]));
//            }
//        }
//        return moves;
//    }
//
//    bool isGoal() const {
//        int count = 1;
//        for (int i = 0; i < SIZE; ++i) {
//            for (int j = 0; j < SIZE; ++j) {
//                if (i == SIZE - 1 && j == SIZE - 1) {
//                    if (values[i][j] != 0) return false;
//                }
//                else {
//                    if (values[i][j] != count++) return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    // Function to print the matrix
//    void printMatrix() const {
//        for (int i = 0; i < SIZE; ++i) {
//            for (int j = 0; j < SIZE; ++j) {
//                std::cout << values[i][j] << " ";
//            }
//            std::cout << std::endl;
//        }
//    }
//};
//
//
//
//// Utility function to read the initial matrix state from file.
//Matrix* readMatrixFromFile(const std::string& filename) {
//    std::ifstream file(filename);
//    if (!file.is_open()) {
//        std::cerr << "Error: Could not open input.in" << std::endl;
//        MPI_Abort(MPI_COMM_WORLD, 1);
//    }
//    std::vector<std::vector<int>> values(SIZE, std::vector<int>(SIZE));
//    int free_i, free_j;
//
//    for (int i = 0; i < SIZE; ++i) {
//        for (int j = 0; j < SIZE; ++j) {
//            file >> values[i][j];
//            if (values[i][j] == 0) {
//                free_i = i;
//                free_j = j;
//            }
//        }
//    }
//
//    Matrix* matrix = new Matrix(values, 0, free_i, free_j, nullptr, "");
//    matrix->printMatrix();  // Print the matrix after reading it
//    return matrix;
//}
//
//// Add this function to print the solution path
//void printSolutionPath(Matrix* solution) {
//    std::vector<Matrix*> path;
//    Matrix* current = solution;
//
//    // Build path from solution back to root
//    while (current != nullptr) {
//        path.push_back(current);
//        current = current->parent;
//    }
//
//    // Print path from start to solution
//    std::cout << "\nSolution found in " << path.size() - 1 << " steps!\n" << std::endl;
//    for (int i = path.size() - 1; i >= 0; i--) {
//        std::cout << "Step " << path.size() - 1 - i << " (" << path[i]->move << "):" << std::endl;
//        path[i]->printMatrix();
//        std::cout << std::endl;
//    }
//}
//
//void searchMaster(Matrix* root) {
//    int world_size, world_rank;
//    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
//    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
//
//    int bound = root->manhattan;
//    bool solutionFound = false;
//    Matrix* solution = nullptr;
//
//    while (!solutionFound) {
//        std::vector<Matrix*> moves = root->generateMoves();
//        int moveCount = moves.size();
//
//       /* std::cout << "\nGenerating " << moveCount << " possible moves:" << std::endl;
//        for (int i = 0; i < moveCount; i++) {
//            std::cout << "\nMove " << i + 1 << " (" << moves[i]->move << "):" << std::endl;
//            moves[i]->printMatrix();
//        }*/
//
//        // Distribute moves to workers
//        for (int i = 1; i < world_size && i <= moveCount; ++i) {
//            std::vector<int> flatMatrix;
//            for (const auto& row : moves[i - 1]->values) {
//                flatMatrix.insert(flatMatrix.end(), row.begin(), row.end());
//            }
//
//            int matrixSize = SIZE * SIZE;
//            MPI_Send(&matrixSize, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(flatMatrix.data(), matrixSize, MPI_INT, i, 1, MPI_COMM_WORLD);
//
//            int positions[2] = { moves[i - 1]->free_i, moves[i - 1]->free_j };
//            MPI_Send(positions, 2, MPI_INT, i, 2, MPI_COMM_WORLD);
//
//            // Send steps and move information
//            int stepData[2] = { moves[i - 1]->steps, static_cast<int>(moves[i - 1]->move.length()) };
//            MPI_Send(stepData, 2, MPI_INT, i, 3, MPI_COMM_WORLD);
//            MPI_Send(moves[i - 1]->move.c_str(), moves[i - 1]->move.length(), MPI_CHAR, i, 4, MPI_COMM_WORLD);
//        }
//
//        // Receive results from workers
//        int minBound = INT_MAX;
//        for (int i = 1; i < world_size && i <= moveCount; ++i) {
//            int workerResult;
//            MPI_Recv(&workerResult, 1, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//            if (workerResult == -1) {
//                solutionFound = true;
//                solution = moves[i - 1];  // Store the solution matrix
//                break;
//            }
//            minBound = std::min(minBound, workerResult);
//        }
//
//        if (!solutionFound) {
//            bound = minBound;
//        }
//    }
//
//    // Notify workers to terminate
//    for (int i = 1; i < world_size; ++i) {
//        int matrixSize = -1;
//        MPI_Send(&matrixSize, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//    }
//
//    if (solutionFound && solution != nullptr) {
//        printSolutionPath(solution);
//    }
//    else {
//        std::cout << "No solution found." << std::endl;
//    }
//}
//
//void searchWorker() {
//    while (true) {
//        // Receive matrix size first
//        int matrixSize;
//        MPI_Recv(&matrixSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//        // Check for termination signal
//        if (matrixSize == -1) break;
//
//        // Receive matrix data
//        std::vector<int> flatMatrix(matrixSize);
//        MPI_Recv(flatMatrix.data(), matrixSize, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//        // Receive free space position
//        int positions[2];
//        MPI_Recv(positions, 2, MPI_INT, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//        // Receive steps and move information
//        int stepData[2];
//        MPI_Recv(stepData, 2, MPI_INT, 0, 3, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//        std::vector<char> moveBuffer(stepData[1] + 1, '\0');
//        MPI_Recv(moveBuffer.data(), stepData[1], MPI_CHAR, 0, 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//        std::string move(moveBuffer.data());
//
//        // Convert received 1D data into 2D vector
//        std::vector<std::vector<int>> values(SIZE, std::vector<int>(SIZE));
//        for (int i = 0; i < SIZE; ++i) {
//            for (int j = 0; j < SIZE; ++j) {
//                values[i][j] = flatMatrix[i * SIZE + j];
//            }
//        }
//
//        // Create the Matrix object with steps and move information
//        Matrix* receivedMatrix = new Matrix(values, stepData[0], positions[0], positions[1], nullptr, move);
//
//       /*std::cout << "Worker received matrix (Move: " << move << ", Steps: " << stepData[0] << "):" << std::endl;
//        receivedMatrix->printMatrix();
//        std::cout << std::endl;*/
//
//        // Check if the received matrix is the goal state
//        if (receivedMatrix->isGoal()) {
//            int goalSignal = -1;
//            MPI_Send(&goalSignal, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//        }
//        else {
//            int bound = receivedMatrix->manhattan;
//            MPI_Send(&bound, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//        }
//
//        delete receivedMatrix;
//    }
//}
//
//
//int main(int argc, char** argv) {
//    MPI_Init(&argc, &argv);
//
//    int world_rank;
//    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
//
//    if (world_rank == 0) {
//        Matrix* root = readMatrixFromFile("input.in");
//        std::cout << "Master process reading the matrix:" << std::endl;
//        root->printMatrix();
//        searchMaster(root);
//    }
//    else {
//        searchWorker();
//    }
//
//    MPI_Finalize();
//    return 0;
//}
//
//
////mpiexec -n 4 Lab7MPI.exe
//
