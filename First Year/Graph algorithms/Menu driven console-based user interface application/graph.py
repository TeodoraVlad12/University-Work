import copy
import sys

class Graph:
    def __init__(self, number_of_vertices, number_of_edges):
        self._number_of_vertices = number_of_vertices
        self._number_of_edges = number_of_edges
        self._dictionary_bound = {}
        self._vertices = []
        self._dict_in = {}
        self._dict_out = {}
        self._dict_cost = {}
        self._neighbors = {}
        self.adjacency_list = [[] for _ in range(number_of_vertices)]


        for index in range(number_of_vertices):
            self._dict_in[index]=[]
            self._dict_out[index]=[]
            self._dictionary_bound[index] = []
            self._vertices.append(index)
            #self._neighbors[index]=[]
    @property
    def dict_cost(self):
        return self._dict_cost

    @property
    def dict_in(self):
        return self._dict_in

    @property
    def dict_out(self):
        return self._dict_out

    @property
    def number_of_vertices(self):
        return self._number_of_vertices

    @property
    def number_of_edges(self):
        return self._number_of_edges

    @property
    def vertices(self):
        return self._vertices  # getter for vertices

    @property
    def dictionary_bound(self):  # getter for the list of bound vertices
        return self._dictionary_bound

    def parse_vertices(self):  # iterator for the vertices
        for v in self._vertices:
            yield v

    def parse_bound(self, x):  # iterator for the bound vertices
        for y in self._dictionary_bound[x]:
            yield y

#Add:
    def add_vertex(self, vertex):
        if vertex in self._dict_in.keys() and vertex in self._dict_out.keys():
            return False
        else:
            self._dict_in[vertex] = []
            self._dict_out[vertex] = []
            self._vertices.append(vertex)
            self._number_of_vertices += 1
            self.vertices.append(vertex)
            return True

    def add_edge(self, vertex1, vertex2, cost):
        self.adjacency_list[vertex1].append((vertex2, cost))
        self.adjacency_list[vertex2].append((vertex1, cost))
        if (vertex1, vertex2) in self._neighbors or (vertex1, vertex2) in self._neighbors:
            return False
        self._neighbors[(vertex1, vertex2)] = cost
        return True
        '''if vertex1 in self.dict_in[vertex2]:
            return False
        elif vertex2 in self.dict_out[vertex1]:
            return False
        elif (vertex1, vertex2) in self._dict_cost.keys():
            return False
        self._dict_in[vertex2].append(vertex1)
        self._dict_out[vertex1].append(vertex2)
        self._dict_cost[(vertex1, vertex2)] = cost
        self._number_of_edges += 1'''



#Remove:
    def remove_vertex(self, vertex):
        if vertex not in self._dict_in.keys() and vertex not in self._dict_out.keys():
            return False
        self._dict_in.pop(vertex)
        self._dict_out.pop(vertex)
        for key in self._dict_in.keys():
            if vertex in self._dict_in[key]:
                self._dict_in[key].remove(vertex)
            elif vertex in self._dict_out[key].remove(vertex):
                self._dict_out[key].remove(vertex)
        keys = list(self._dict_cost.keys())
        for key in keys:
            if key[0] == vertex or key[1] == vertex :
                self._dict_cost.pop(vertex)
                self._number_of_edges -= 1
        self._vertices.remove(x)
        self._number_of_vertices -= 1
        return True

    def remove_edge(self, x, y):
        if (x, y) in self._neighbors:
            del self._neighbors[(x, y)]
            return True
        elif (y, x) in self._neighbors:
            del self._neighbors[(y,x)]
            return True
        return False
        '''if x not in self._dict_in.keys() or y not in self._dict_in.keys() or x not in self._dict_out.keys() or y not in self._dict_out.keys():
            return False
        if x not in self._dict_in[y]:
            return False
        elif y not in self._dict_out[x]:
            return False
        elif (x, y) not in self._dict_cost.keys():
            return False
        self._dict_in[y].remove(x)
        self._dict_out[x].remove(y)
        self._dict_cost.pop((x, y))
        self._number_of_edges -= 1
        return True'''

#Returning the cost:
    def find_if_edge(self, x, y):
        if x in self._dict_in[y]:
            return self._dict_cost[(x, y)]
        elif y in self._dict_out[x]:
            return self._dict_cost[(x, y)]
        return False

#Changing cost:
    def change_cost(self, x, y, new_cost):
        if (x, y) not in self._dict_cost.keys():
            return False
        self._dict_cost[(x, y)] = new_cost
        return True
#Copy:
    def make_copy(self):
        return copy.deepcopy(self)

#Degrees:
    def in_degree(self, x):
        if x not in self._dict_in.keys():
            return -1
        return len(self._dict_in[x])

    def out_degree(self, x):
        if x not in self._dict_out.keys():
            return -1
        return len(self._dict_out[x])


#Parsing:
    def parse_vertices(self):
        vertices = list(self._dict_in.keys())
        for vertex in vertices:
            yield vertex

    def parse_inbound(self, x):
        return [neighbor[0] for neighbor in self._neighbors if neighbor[1] == x]
        #for y in self._dict_in[x]:
            #yield y

    def parse_outbound(self, x):
        return [neighbor[1] for neighbor in self._neighbors if neighbor[0] == x]
        #for y in self._dict_out[x]:
            #yield y

    def parse_cost(self):
        keys = list(self._dict_cost.keys())
        for key in keys:
            yield key

    def BFS(self, source, end):
        """
        :param source: the starting vertex
        :param end: the ending vertex
        :return: False if there is no path between them, a list representing the shortest path otherwise
        """
        queue =[]
        used=[]
        parents = []
        #d = []

        for i in range(self._number_of_vertices):
            used.append(False)
            parents.append(0)
           # d.append(0)

        queue.append(end)
        used[end]=True
        parents[end]=-1
        while queue:
            v = queue.pop(0)
            for u in self._dict_in[v]:
                if used[u]==False:
                    used[u] = True
                    queue.append(u)
                    #d[u] = d[v] + 1
                    parents[u] = v

        if not used[source]:
            return False
        else:
            path = []
            v = source
            while v != -1:
                path.append(v)
                v=parents[v]
            return path

    def Dijkstra(self, start):
        unvisited_nodes = self.vertices

        shortest_path = {}
        previous_nodes = {}

        max_value = sys.maxsize
        for node in unvisited_nodes:
            shortest_path[node] = max_value
        shortest_path[start] = 0

        while unvisited_nodes:
            current_min_node = None
            for node in unvisited_nodes:
                if current_min_node == None:
                    current_min_node = node
                elif shortest_path[node] < shortest_path[current_min_node]:
                    current_min_node = node

            for neigh in self._dict_out[current_min_node]:
                tentative_value = shortest_path[current_min_node] + self.find_if_edge(current_min_node, neigh)
                if tentative_value < shortest_path[neigh]:
                    shortest_path[neigh] = tentative_value
                    previous_nodes[neigh] = current_min_node

            unvisited_nodes.remove(current_min_node)

        return previous_nodes, shortest_path

    def prim_mst(self, start_vertex):
        visited = [False] * self.number_of_vertices
        key = [float('inf')] * self.number_of_vertices
        parent = [None] * self.number_of_vertices

        key[start_vertex] = 0   #indicating that it is the first vertex added to the minimum spanning tree.

        for _ in range(self.number_of_vertices):  # all vertices are included in the minimum spanning tree.
            min_key = float('inf')
            min_vertex = -1

            for v in range(self.number_of_vertices):
                if not visited[v] and key[v] < min_key:
                    min_key = key[v]
                    min_vertex = v

            visited[min_vertex] = True  #minimum vertex as visited.

            for neighbor, weight in self.adjacency_list[min_vertex]:  #iterates over the neighbors and weights of the minimum vertex
                if not visited[neighbor] and weight < key[neighbor]:
                    key[neighbor] = weight   #the key and parent values for the neighbor are updated.
                    parent[neighbor] = min_vertex

        return parent  #contains the minimum spanning tree, where each element represents the parent of the corresponding vertex.



    def returnDijkstra(self, previous_nodes, shortest_path, start_node, target_node):
        path = []
        node = target_node
        while node != start_node:
            path.append(node)
            node = previous_nodes[node]
        path.append(start_node)

        return path, shortest_path[target_node]







'''

 def Dijkstra(self, end):

        unvisited_nodes = self.vertices

        shortest_path = {}
        previous_nodes = {}

        max_value = sys.maxsize
        for node in unvisited_nodes:
            shortest_path[node] = max_value
        shortest_path[end] = 0

        while unvisited_nodes:
            current_min_node = None
            for node in unvisited_nodes:
                if current_min_node == None:
                    current_min_node = node
                elif shortest_path[node] < shortest_path[current_min_node]:
                    current_min_node = node

            for neigh in self._dict_in[current_min_node]:
                tentative_value = shortest_path[current_min_node] + self.find_if_edge(current_min_node, neigh)
                if tentative_value < shortest_path[neigh]:
                    shortest_path[neigh] = tentative_value
                    previous_nodes[neigh] = current_min_node

            unvisited_nodes.remove(current_min_node)

        return previous_nodes, shortest_path

    def returnDijkstra(self, previous_nodes, shortest_path, start_node, target_node):
        path = []
        node = start_node
        while node != target_node:
            path.append(node)
            node = previous_nodes[node]
        path.append(target_node)

        return path, shortest_path[target_node]




'''

