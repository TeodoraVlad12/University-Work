from random import randint
from graph import Graph
from service import write_graph_to_file, read_graph_from_file

class UI:
    def __init__(self):
        self._graphs = []
        self._current = None



    def generate_random(self, vertices, edges):
        if edges > vertices * vertices:
            raise ValueError("Too many edges!")
        graph = Graph(vertices, 0)
        i = 0
        while i < edges:
            x = randint(0, vertices - 1)
            y = randint(0, vertices - 1)
            cost = randint(0, 500)
            if graph.add_edge(x, y, cost):
                i += 1
        return graph

    def add_empty_graph(self):
        if self._current is None:
            self._current = 0
        graph = Graph(0, 0)
        self._graphs.append(graph)
        self._current = len(self._graphs) - 1


    def create_random_graph_ui(self):
        vertices = int(input("Enter the number of vertices: "))
        edges = int(input("Enter the number of edges: "))
        graph = self.generate_random(vertices, edges)
        if self._current is None:
            self._current = 0
        self._graphs.append(graph)
        self._current = len(self._graphs) - 1


    def read_graph_from_file_ui(self):
        filename = input("Enter the name of the file: ")
        if self._current is None:
            self._current = 0
        graph = read_graph_from_file(filename)
        self._graphs.append(graph)
        self._current = len(self._graphs) - 1

    def write_graph_to_file_ui(self):
        current_graph = self._graphs[self._current]
        output_file = "output" + str(self._current) + ".txt"
        write_graph_to_file(current_graph, output_file)

    def switch_graph_ui(self):
        print("You are on the graph number: {}".format(self._current))
        print("Available graphs: 0 - {}".format(str(len(self._graphs) - 1)))
        number = int(input("Enter the graph number you want to switch to: "))
        if not 0 <= number < len(self._graphs):
            raise ValueError("Trying to switch to a non existing graph!")
        self._current = number

    def get_number_of_vertices_ui(self):
        print("The number of vertices is: {}.".format(self._graphs[self._current].number_of_vertices))
        print('\n')

    def get_number_of_edges_ui(self):
        print("The number of edges is: {}.".format(self._graphs[self._current].number_of_edges))
        print('\n')


    def list_all_outbound(self):
        for edge, cost in self._graphs[self._current].parse_outbound():
            line = "({}, {}) : {}".format(edge[0], edge[1], cost)
            print(line)
        print('\n')
        '''for x in self._graphs[self._current].parse_vertices():
            line = str(x) + " :"
            for y in self._graphs[self._current].parse_outbound(x):
                line = line + " " + str(y)
            print(line)
        print('\n')'''


    def list_outbound(self):
        vertex = int(input("Enter the vertex: "))
        line = str(vertex) + " :"
        for edge, cost in self._graphs[self._current].parse_outbound(vertex):
            line = line + " " + "({}, {})".format(edge[0], edge[1])
        print(line)
        print('\n')
        '''vertex = int(input("Enter the vertex: "))
        line = str(vertex) + " :"
        for y in self._graphs[self._current].parse_outbound(vertex):
            line = line + " " + "({}, {})".format(str(vertex), str(y))
        print(line)
        print('\n')'''


    def list_all_inbound(self):
        for edge, cost in self._graphs[self._current].parse_inbound():
            line = "({}, {}) : {}".format(edge[0], edge[1], cost)
            print(line)
        print('\n')
        '''for x in self._graphs[self._current].parse_vertices():
            line = str(x) + " :"
            for y in self._graphs[self._current].parse_inbound(x):
                line = line + " " + str(y)
            print(line)
        print('\n')'''


    def list_inbound(self):
        vertex = int(input("Enter the vertex: "))
        line = str(vertex) + " :"
        for edge, cost in self._graphs[self._current].parse_inbound(vertex):
            line = line + " " + "({}, {})".format(edge[0], edge[1])
        print(line)
        print('\n')
        '''vertex = int(input("Enter the vertex: "))
        line = str(vertex) + " :"
        for y in self._graphs[self._current].parse_inbound(vertex):
            line = line + " " + "({}, {})".format(str(y), str(vertex))
        print(line)
        print('\n')'''


    def list_all_costs(self):
        for key in self._graphs[self._current].parse_cost():
            line = "({}, {})".format(key[0], key[1]) + " :" + str(self._graphs[self._current].dict_cost[key])
            print(line)
        print('\n')


    def parse_all_vertices(self):
        for vertex in self._graphs[self._current].parse_vertices():
            print("{}".format(vertex))
        print('\n')


    def add_vertex_ui(self):
        vertex = int(input("Enter the new vertex: "))
        added = self._graphs[self._current].add_vertex(vertex)
        if added:
            print("Vertex added successfully!")
            print('\n')

        else:
            print("Cannot add this vertex, it already exists!")
            print('\n')


    def delete_vertex_ui(self):
        vertex = int(input("Enter the vertex to be deleted: "))
        deleted = self._graphs[self._current].remove_vertex(vertex)
        if deleted:
            print("Vertex deleted successfully!")
            print('\n')
        else:
            print("Cannot delete this vertex, it does not exist!")
            print('\n')


    def add_edge_ui(self):
        print("Add an edge (an edge is (vertex1, vertex2))")
        vertex_x = int(input("Enter vertex1 = "))
        vertex_y = int(input("Enter vertex2 = "))
        cost = int(input("Enter the cost of the edge: "))
        added = self._graphs[self._current].add_edge(vertex_x, vertex_y, cost)
        if added:
            print("Edge added successfully!")
            print('\n')
        else:
            print("Cannot add this edge, it already exists!")
            print('\n')


    def remove_edge_ui(self):
        print("Remove an edge (an edge is (vertex1, vertex2))")
        vertex_x = int(input("Enter vertex1 = "))
        vertex_y = int(input("Enter vertex2 = "))
        deleted = self._graphs[self._current].remove_edge(vertex_x, vertex_y)
        if deleted:
            print("Edge deleted successfully!")
            print('\n')
        else:
            print("Cannot remove this edge, it does not exist!")
            print('\n')


    def modify_cost_ui(self):
        print("Modify the cost of an edge (an edge is (vertex1, vertex2))")
        vertex_x = int(input("Enter vertex1 = "))
        vertex_y = int(input("Enter vertex2 = "))
        cost = int(input("Enter the cost of the edge: "))
        mod = self._graphs[self._current].change_cost(vertex_x, vertex_y, cost)
        if mod:
            print("Cost modified successfully!")
            print('\n')
        else:
            print("Cannot modify the cost, the edge does not exist!")
            print('\n')


    def get_in_degree_ui(self):
        vertex = int(input("Enter the vertex:"))
        degree = self._graphs[self._current].in_degree(vertex)
        if degree == -1:
            print("The vertex does not exist!")
            print('\n')
        else:
            print("The in degree of the vertex {} is {}.".format(vertex, degree))
            print('\n')


    def get_out_degree_ui(self):
        vertex = int(input("Enter the vertex:"))
        degree = self._graphs[self._current].out_degree(vertex)
        if degree == -1:
            print("The vertex does not exist!")
            print('\n')

        else:
            print("The out degree of the vertex {} is {}.".format(vertex, degree))
            print('\n')


    def check_if_edge_ui(self):
        vertex_x = int(input("Enter vertex1 = "))
        vertex_y = int(input("Enter vertex2 = "))
        edge = self._graphs[self._current].find_if_edge(vertex_x, vertex_y)
        if edge is not False:
            print("({}, {}) is an edge and its cost is {}!".format(vertex_x, vertex_y, edge))
            print('\n')
        else:
            print("({}, {}) is not an edge!".format(vertex_x, vertex_y))
            print('\n')


    def copy_current_graph_ui(self):
        copy = self._graphs[self._current].make_copy()
        self._graphs.append(copy)

    def BFS(self):
        source=input("Enter starting vertex: ")
        end=input("Enter ending vertex: ")
        path = self._graphs[self._current].BFS(int(source),int( end))
        if path==False:
            print("There is no path!")
        else:
            s=""
            for index in range(len(path)-1):
                s+=str(path[index])
                s+=" -> "
            s+=str(path[len(path)-1])
            print("\nThe shortest path is: ", s,"\n")

    def Dijkstra(self):
        source = input("Enter starting vertex: ")
        end = input("Enter ending vertex: ")
        previous_nodes, shortest_path = self._graphs[self._current].Dijkstra(int(source))
        path, cost = self._graphs[self._current].returnDijkstra(previous_nodes, shortest_path, int(source), int(end))
        print("We found a path of cost:", str(cost))
        s = ""
        path.reverse()
        for index in range(len(path) - 1):
            s += str(path[index])
            s += " -> "
        s += str(path[len(path) - 1])
        print("\nThe shortest path is: ", s, "\n")

    def find_minimal_spanning_tree(self):
        start_vertex = int(input("Enter the starting vertex: "))

        mst = self._graphs[self._current].prim_mst(start_vertex)

        print("Minimal Spanning Tree:")
        for v in range(1, self._graphs[self._current].number_of_vertices):
            print(f"Edge: ({mst[v]}, {v})")





    def print_menu(self):
        print(
              "0. EXIT.\n" 
              "1. Create a random graph with specified number of vertices and edges.\n"
              "2. Read the graph from a text file.\n"
              "3. Write the graph in a text file.\n"
              "4. Switch the graph.\n" 
              "5. Get the number of vertices.\n"
              "6. Get the number of edges.\n"
              "7. List the outbound edges of a given vertex.\n"
              "8. List all outbound vertices of the graph.\n"
              "9. List the inbound edges of a given vertex.\n"
              "10. List all inbound vertices of the graph. \n"
              "11. List the edges and their costs.\n"
              "12. Add a vertex.\n"
              "13. Remove a vertex.\n"
              "14. Add an edge.\n"
              "15. Remove an edge.\n"
              "16. Modify the cost of an edge.\n"
              "17. Get the in degree of a vertex.\n"
              "18. Get the out degree of a vertex.\n"
              "19. Check if there is an edge between two given vertices.\n"
              "20. Make a copy of the graph.\n"
              "21. Add an empty graph.\n"
              "22. Parse all the vertices.\n"
              "23. Find the shortest path between two vertices of the current graph using backward Breadth-First search.\n"
              "24. Find the lowest cost walk between two vertices of the current graph using Dijkstra algorithm.\n"
              "25. Write a program that, given an undirected connected graph, constructs a minumal spanning tree using the Prim's algorithm.\n"
              )



    def start(self):
        done = False
        self.add_empty_graph()
        print("The current graph is an empty graph!")
        command_dict = {"1": self.create_random_graph_ui,
                        "2": self.read_graph_from_file_ui,
                        "3": self.write_graph_to_file_ui,
                        "4": self.switch_graph_ui,
                        "5": self.get_number_of_vertices_ui,
                        "6": self.get_number_of_edges_ui,
                        "7": self.list_outbound,
                        "8": self.list_all_outbound,
                        "9": self.list_inbound,
                        "10": self.list_all_inbound,
                        "11": self.list_all_costs,
                        "12": self.add_vertex_ui,
                        "13": self.delete_vertex_ui,
                        "14": self.add_edge_ui,
                        "15": self.remove_edge_ui,
                        "16": self.modify_cost_ui,
                        "17": self.get_in_degree_ui,
                        "18": self.get_out_degree_ui,
                        "19": self.check_if_edge_ui,
                        "20": self.copy_current_graph_ui,
                        "21": self.add_empty_graph,
                        "22": self.parse_all_vertices,
                        "23": self.BFS,
                        "24": self.Dijkstra,
                        "25": self.find_minimal_spanning_tree}
        while not done:
            try:
                self.print_menu()
                option = input("Choose an option from the menu: \n")
                if option == "0":
                    done = True
                    print("Good bye!")
                elif option in command_dict:
                    command_dict[option]()
                else:
                    print("Bad input!\n")
            except ValueError as ve:
                print(str(ve))
            except FileNotFoundError as fnfe:
                print(str(fnfe).strip("[Errno 2] "))




UI().start()

'''
    
        
        
    def Dijkstra(self):
        source = input("Enter starting vertex: ")
        end = input("Enter ending vertex: ")
        previous_nodes, shortest_path = self._graphs[self._current].Dijkstra(int(end))
        path, cost = self._graphs[self._current].returnDijkstra(previous_nodes, shortest_path, int(source), int(end))
        print("We found a path of cost:", str(cost))
        s = ""
        for index in range(len(path) - 1):
            s += str(path[index])
            s += " -> "
        s += str(path[len(path) - 1])
        print("\nThe shortest path is: ", s, "\n")
    
'''
