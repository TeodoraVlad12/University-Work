'''
Problem 2- second set
Given the set of positive integers S and the natural number k,
display one of the subsets of S which sum to k.
For example, if S = { 2, 3, 5, 7, 8 } and k = 14, subset { 2, 5, 7 } sums to 14.
'''

'''
The state DP[i][j] will be true if there exists a subset of elements from A[0….i] with sum value = ‘j’.
set = [2,3,5,7,8]
sum = 14
  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4
0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0
3 1 0 1 1 0 1 0 0 0 0 0 0 0 0 0
5 1 0 1 1 0 1 0 0 1 0 1 0 0 0 0
7 1 0 1 1 0 1 0 1 1 1 1 0 1 0 1
8 1 0 1 1 0 1 0 1 1 1 1 1 1 1 1
'''

def isSubsetSum(set, n, sum):
    #Creates a matrix in which the value of subset[i][j] will be true if there is a subset of set[0..j-1] with sum equal to i and
    #if the sum has the value True, it creates a list with the elements of the subsequence corresponding to the sum and displays them.
    #Input: set, n, sum - integers.
    #Output: the subseguence that has the sum of its elements equal to sum.
    global subset
    # The value of subset[i][j] will be true if there is a subset of set[0..j-1] with sum equal to i
    subset = ([[False for i in range(sum + 1)]
               for i in range(n + 1)])

    for i in range(n + 1):
        subset[i][0] = True       # If sum is 0, then answer is true

    for i in range(1, sum + 1):        # If sum is not 0 and set is empty, then answer is false
        subset[0][i] = False

    for i in range(1, n + 1):
        for j in range(1, sum + 1):
            if j < set[i - 1]:
                subset[i][j] = subset[i - 1][j]
            if j >= set[i - 1]:
                subset[i][j] = (subset[i - 1][j] or
                                subset[i - 1][j - set[i - 1]])


    if subset[n][sum]:
        poz=find_first_true(sum,n)
        summation=sum
        subsequence=[]
        while summation!=0:
            subsequence.append(set[poz-1])
            summation-=set[poz-1]
            poz=find_first_true(summation,n)

        subsequence.reverse()
        print(subsequence)
    else:
        print("There are no subsequences having this sum")




def find_first_true(summation,n):
    #Finds the first row on which the summation has the value True on the last column(the column of the sum).
    #Input: summation, n - integers.
    #Ouput: the position.
    global subset
    i = 0
    while i <= n + 1:
        if subset[i][summation]:
            return i
        i+=1



def read_set():
    # Reads the elements of the set and puts them in a list of integers.
    # Input: the elements of the set read from the screen.
    # Output: the array of integers.
    global set
    print("Enter the set: ")
    set = list(map(str, input().split(" ")))
    return set




def main():
    global subset
    global set
    #read_set()
    #sum=input("Enter the sum: ")
    sum = 14
    set = [2,3,5,7,8]
    n = len(set)
    isSubsetSum(set, n, sum)


main()



#O(sum*n)
