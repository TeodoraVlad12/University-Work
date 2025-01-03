#include <iostream>
#include <vector>
#include <queue>
#include <future>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <cmath>
#include <thread>
#include <mutex>
#include <atomic>
#include <chrono>

const int SIZE = 4;

struct Matrix {
    std::vector<std::vector<int>> values;
    int manhattan;
    int steps;
    int free_i, free_j;
    std::string move;
    Matrix* parent;

    Matrix(std::vector<std::vector<int>> vals, int steps, int free_i, int free_j, Matrix* parent, std::string move)
        : values(vals), steps(steps), free_i(free_i), free_j(free_j), parent(parent), move(move) {
        calculateManhattan();
    }

    void calculateManhattan() {
        manhattan = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (values[i][j] != 0) {
                    int target_i = (values[i][j] - 1) / SIZE;
                    int target_j = (values[i][j] - 1) % SIZE;
                    manhattan += abs(i - target_i) + abs(j - target_j);
                }
            }
        }
    }

    std::vector<Matrix*> generateMoves() {
        std::vector<Matrix*> moves;
        std::vector<std::pair<int, int>> directions = { {0, -1}, {-1, 0}, {0, 1}, {1, 0} };
        std::vector<std::string> moveNames = { "left", "up", "right", "down" };

        for (int k = 0; k < 4; ++k) {
            int new_i = free_i + directions[k].first;
            int new_j = free_j + directions[k].second;

            if (new_i >= 0 && new_i < SIZE && new_j >= 0 && new_j < SIZE) {
                std::vector<std::vector<int>> newValues = values;
                std::swap(newValues[free_i][free_j], newValues[new_i][new_j]);
                moves.push_back(new Matrix(newValues, steps + 1, new_i, new_j, this, moveNames[k]));
            }
        }
        return moves;
    }

    std::vector<Matrix*> getPathMatrices() const {
        std::vector<Matrix*> path;
        for (const Matrix* current = this; current != nullptr; current = current->parent) {
            path.push_back(const_cast<Matrix*>(current));
        }
        std::reverse(path.begin(), path.end());
        return path;
    }

    void printMatrix() const {
        for (const auto& row : values) {
            for (int val : row) {
                std::cout << val << " ";
            }
            std::cout << "\n";
        }
    }
};

Matrix* readMatrixFromFile(const std::string& filename) {
    std::ifstream file(filename);
    std::vector<std::vector<int>> values(SIZE, std::vector<int>(SIZE));
    int free_i, free_j;

    for (int i = 0; i < SIZE; ++i) {
        for (int j = 0; j < SIZE; ++j) {
            file >> values[i][j];
            if (values[i][j] == 0) {
                free_i = i;
                free_j = j;
            }
        }
    }

    return new Matrix(values, 0, free_i, free_j, nullptr, "");
}

std::pair<int, Matrix*> search(Matrix* current, int bound, int maxThreads);

std::pair<int, Matrix*> searchParallel(Matrix* root, int bound, int maxThreads) {
    if (maxThreads <= 1)
        return search(root, bound, maxThreads);

    int minBound = INT_MAX;
    std::vector<std::future<std::pair<int, Matrix*>>> futures;

    for (Matrix* move : root->generateMoves()) {
        futures.push_back(std::async(std::launch::async, searchParallel, move, bound, maxThreads / 2));
    }

    for (auto& fut : futures) {
        auto result = fut.get();
        if (result.first == -1)
            return result;
        minBound = std::min(minBound, result.first);
    }

    return { minBound, nullptr };
}

std::pair<int, Matrix*> search(Matrix* current, int bound, int maxThreads) {
    if (current->steps + current->manhattan > bound) {
        return { current->steps + current->manhattan, nullptr };
    }
    if (current->manhattan == 0) {
        return { -1, current };
    }

    int minBound = INT_MAX;
    Matrix* solution = nullptr;

    for (Matrix* move : current->generateMoves()) {
        auto result = search(move, bound, maxThreads);
        if (result.first == -1) {
            return result;
        }
        if (result.first < minBound) {
            minBound = result.first;
        }
    }

    return { minBound, solution };
}

void solveWithThreads(Matrix* root, int maxThreads) {
    int bound = root->manhattan;
    auto start_time = std::chrono::high_resolution_clock::now();

    while (true) {
        auto result = searchParallel(root, bound, maxThreads);
        if (result.first == -1) {
            auto end_time = std::chrono::high_resolution_clock::now();
            auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time);

            std::cout << "Number of steps: " << result.second->steps << "\n";
            std::cout << "Time taken: " << duration.count() << " milliseconds\n";
            std::cout << "Solution path:\n";

            for (Matrix* matrix : result.second->getPathMatrices()) {
                matrix->printMatrix();
                std::cout << "Move: " << matrix->move << "\n\n";
            }
            return;
        }
        bound = result.first;
    }
}

int main(int argc, char** argv) {

    Matrix* root = readMatrixFromFile("input.in");
    solveWithThreads(root, 4); //  4 threads
    return 0;
}





//#include <iostream>
//#include <vector>
//#include <queue>
//#include <future>
//#include <fstream>
//#include <sstream>
//#include <algorithm>
//#include <cmath>
//#include <thread>
//#include <mutex>
//#include <atomic>
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
//};
//
//// Utility function to read the initial matrix state from file.
//Matrix* readMatrixFromFile(const std::string& filename) {
//    std::ifstream file(filename);
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
//    return new Matrix(values, 0, free_i, free_j, nullptr, "");
//}
//
//std::pair<int, Matrix*> search(Matrix* current, int bound, int maxThreads);
//
//std::pair<int, Matrix*> searchParallel(Matrix* root, int bound, int maxThreads) {
//    if (maxThreads <= 1)
//        return search(root, bound, maxThreads);
//
//    int minBound = INT_MAX;
//    std::vector<std::future<std::pair<int, Matrix*>>> futures;
//
//    for (Matrix* move : root->generateMoves()) {
//        futures.push_back(std::async(std::launch::async, searchParallel, move, bound, maxThreads / 2));
//    }
//
//    for (auto& fut : futures) {
//        auto result = fut.get();
//        if (result.first == -1)
//            return result;
//        minBound = std::min(minBound, result.first);
//    }
//
//    return { minBound, nullptr };
//}
//
//std::pair<int, Matrix*> search(Matrix* current, int bound, int maxThreads) {
//    if (current->steps + current->manhattan > bound) {
//        return { current->steps + current->manhattan, nullptr };
//    }
//    if (current->manhattan == 0) {
//        return { -1, current };
//    }
//
//    int minBound = INT_MAX;
//    Matrix* solution = nullptr;
//
//    for (Matrix* move : current->generateMoves()) {
//        auto result = search(move, bound, maxThreads);
//        if (result.first == -1) {
//            return result;
//        }
//        if (result.first < minBound) {
//            minBound = result.first;
//        }
//    }
//
//    return { minBound, solution };
//}
//
//void solveWithThreads(Matrix* root, int maxThreads) {
//    int bound = root->manhattan;
//
//    while (true) {
//        auto result = searchParallel(root, bound, maxThreads);
//        if (result.first == -1) {
//            std::cout << "Solution found in " << result.second->steps << " steps." << std::endl;
//            return;
//        }
//        bound = result.first;
//    }
//}
//
//int main(int argc, char** argv) {
//    Matrix* root = readMatrixFromFile("input.in");
//    std::cout << root->values[0][0];
//    solveWithThreads(root, 4); // Example with 4 threads.
//    return 0;
//}
