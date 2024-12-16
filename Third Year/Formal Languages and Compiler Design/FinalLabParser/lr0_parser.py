from grammar import Grammar
from parser_output import ParserOutput


class LR0Parser:
    def __init__(self, grammar):
        self.grammar = grammar
        self.action_table, self.goto_table = self.grammar.generate_parsing_table()
        self.stack = []  # Stack for parser's states
        self.output = ParserOutput()

    def parse(self, input_string):
        self.stack.append(0)  # Initial state
        input_string += '$'  # End of input marker

        idx = 0
        while True:
            current_state = self.stack[-1]
            current_symbol = input_string[idx]

            action = self.action_table.get((current_state, current_symbol))

            if action is None:
                print("Error: No action defined for state", current_state, "and symbol", current_symbol)
                break

            if action.startswith('shift'):
                # Shift action
                new_state = int(action.split()[1])
                self.stack.append(new_state)
                self.output.add_node(current_symbol)  # Add terminal node
                idx += 1  # Move to next input symbol

            elif action.startswith('reduce'):
                # Reduce action
                production = action.split(' ', 1)[1]
                lhs, rhs = production.split(' -> ')
                rhs_len = len(rhs.split())

                # Pop the stack and ascend the tree in the output
                for _ in range(rhs_len):
                    self.stack.pop()
                    self.output.ascend_tree()

                self.output.add_node(lhs)  # Add nonterminal node after reduction
                goto_state = self.goto_table.get((self.stack[-1], lhs))
                if goto_state is not None:
                    self.stack.append(goto_state)

            elif action == 'accept':
                print("Parsing completed successfully.")
                break

            else:
                print("Error: Undefined action", action)
                break

        return self.output


grammar1 = Grammar()
grammar1.read_from_file('g1.txt')
parser1 = LR0Parser(grammar1)
parser_output1 = parser1.parse('ab')
parser_output1.print_to_file('output1.txt')
#shift a, add a to output tree
#reduce L -> a, replace a with L in the output tree
#shift b, add b to the output tree
#reduce R -> b, add R to the output tree
#reduce S -> LR
#ParserOutput focuses on intermediate outputs of reductions and the last reduction should output L as the result of ab

grammar2 = Grammar()
grammar2.read_from_file('g2.txt')
parser2 = LR0Parser(grammar2)
parser_output2 = parser2.parse('010')
parser_output2.print_to_file('output2.txt')
#shift 0
#reduction S -> 0
#shift 1
#to apply the production S -> 0 S 0 it would require the last 0 in the input and as a result
#the parser outputs the last successfully reduced symbol which is 0 (S->0)