from graph import Graph


#Writing/ reading using files:
def write_graph_to_file(graph, file):
    with open(file, "w") as f:
        first_line = f"{graph.number_of_vertices} {graph.number_of_edges}\n"
        f.write(first_line)
        if len(graph.dict_cost) == 0 and len(graph.dict_in) == 0:
            raise ValueError("There is nothing that can be written!")
        for edge, cost in graph.dict_cost.items():
            new_line = f"{edge[0]} {edge[1]} {cost}\n"
            f.write(new_line)
        for vertex in graph.dict_in.keys():
            if len(graph.dict_in[vertex]) == 0 and len(graph.dict_out[vertex]) == 0:
                new_line = f"{vertex}\n"
                f.write(new_line)
    '''file = open(file, "w")
    first_line = str(graph.number_of_vertices) + ' ' + str(graph.number_of_edges) + '\n'
    file.write(first_line)
    if len(graph.dict_cost) == 0 and len(graph.dict_in) == 0:
        raise ValueError("There is nothing that can be written!")
    for edge in graph.dict_cost.keys():
        new_line = "{} {} {}\n".format(edge[0], edge[1], graph.dict_cost[edge])
        file.write(new_line)
    for vertex in graph.dict_in.keys():
        if len(graph.dict_in[vertex]) == 0 and len(graph.dict_out[vertex]) == 0:
            new_line = "{}\n".format(vertex)
            file.write(new_line)
    file.close()'''

def read_graph_from_file(filename):
    with open(filename, "r") as f:
        line = f.readline().strip()
        vertices, edges = line.split(' ')
        graph = Graph(int(vertices), int(edges))
        for _ in range(graph.number_of_edges):
            line = f.readline().strip().split(' ')
            vertex1, vertex2, weight = map(int, line)
            graph.add_edge(vertex1, vertex2, weight)
    return graph
    '''with open(filename, "r") as f:
        line = f.readline().strip()
        vertices, edges = line.split(' ')
        graph = Graph(int(vertices), int(edges))
        line = f.readline().strip()
        while len(line) > 0:
            line = line.split(' ')
            if len(line) == 1:
                graph.dict_in[int(line[0])] = []
                graph.dict_out[int(line[0])] = []
            else:
                graph.dict_in[int(line[1])].append(int(line[0]))
                graph.dict_out[int(line[0])].append(int(line[1]))
                graph.dict_cost[(int(line[0]), int(line[1]))] = int(line[2])
            line = f.readline().strip()
    return graph'''
    '''file = open(filename, "r")
    line = file.readline()
    line = line.strip()
    vertices, edges = line.split(' ')
    graph = Graph(int(vertices), int(edges))
    line = file.readline().strip()
    while len(line) > 0:
        line = line.split(' ')
        if len(line) == 1:
            graph.dict_in[int(line[0])] = []
            graph.dict_out[int(line[0])] = []
        else:
            graph.dict_in[int(line[1])].append(int(line[0]))
            graph.dict_out[int(line[0])].append(int(line[1]))
            graph.dict_cost[(int(line[0]), int(line[1]))] = int(line[2])
        line = file.readline().strip()
    file.close()
    return graph'''
