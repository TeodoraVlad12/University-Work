'''
Problem 10- first set
A group of n (n<=10) persons, numbered from 1 to n are placed on a row of chairs,
     but between every two neighbor persons (e.g. persons 3 and 4, or persons 7 and 8) some conflicts appeared.
Display all the possible modalities to replace the persons, such that between any two persons in conflict stay one or at most two other persons.
'''

def Solution(n):
    #Verifies if every two numbers that are next two each other are not consecutive and if between any two consecutive numbers are min one and max two other numbers.
    #Input: n- integer (the number of elements)
    #Output: True if the conditions are verified and False otherwise.
    global perm
    global list
    for j in range (0,n):
        list[j]=0

    ok=1
    for i in range (0,n-1):
        if (perm[i+1] == perm[i]-1) or (perm[i+1] == perm[i]+1):      #Verifies if the persons in conflict (consecutive numbers) do not stay next to each other.
            ok=0

    nr=0
    for k in range (0,n-2):                                           #Counts between how many two persons in conflict stay just one other person.
        if perm[k]+1==perm[k+2]:
            if list[k]==0:
                nr+=1
                list[k]=1

    for l in range (0,n-3):                                           #Counts between how many two persons in conflict stay two other persons.
        if (perm[l]+1==perm[l+3]) or (perm[l]-1==perm[l+3]):
            if list[l]==0:
                nr+=1
                list[l]=1

    if ok == 1 and nr>=n-3:                                           #Verifies if the conditions are satisfied for each person.
        return True
    else:
        return False


def create_list(n):
    #Creates lists of n elements that are initially zero.
    #Input: n- the number of elements.
    #Output: the created lists.

    global perm
    global list
    perm=[]
    list=[]
    for i in range (0,n):
        perm.append(0)
        list.append(0)


def backtracking(k,n):
    #Creates all the permutations of n numbers and displays just the ones that satisfy the conditions.
    #Input: k,n - integers (k-the position in the list that the algorithm fills at every step).
    #Output: the permutations that check all the conditions.

    global perm

    if k > n-1:
        return None

    for i in range (1,n+1):
        perm[k] = i
        ok=1
        for j in range(0, k):
            if (perm[k] == perm[j]):
                ok=0

        if ok==1:
            if k == n-1:
                if Solution(n):
                    print(perm)
            else:
                backtracking(k+1,n)


def main():
    global perm
    n=int(input("Enter your number: "))
    create_list(n)
    k=0
    backtracking(k,n)



main ()




#O(n!)
