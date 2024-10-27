from SymbolTable import *
from Scanner import *
from PIF import *

if __name__ == '__main__':
    SymbolTableIdentifiers = SymbolTable(10)
    SymbolTableConstants = SymbolTable(10)
    Scanner = Scanner()
    Pif = PIF()

    program = "p2.txt"
    error = ""
    Scanner.ReadTokens()

    with open(program, 'r', encoding='utf-8-sig') as file:
        lineIndex = 1
        for line in file:
            tokens = Scanner.GetTokensFromLine(line.strip())
            print(tokens)
            for i in range(len(tokens)):
                if tokens[i] in Scanner.GetAllTokens():
                    if tokens[i] == ' ':
                        continue
                    Pif.Add(tokens[i], (-1, -1))
                elif IsIdentifier(tokens[i]):
                    SymbolTableIdentifiers.Add(tokens[i])
                    identifier = tokens[i]
                    Pif.Add("id", SymbolTableIdentifiers.GetValuePosition(identifier))
                elif IsConstant(tokens[i]):
                    SymbolTableConstants.Add(tokens[i])
                    constant = tokens[i]
                    Pif.Add("const", SymbolTableConstants.GetValuePosition(constant))
                else:
                    error += 'Lexical error: ' + tokens[i] + " at line " + str(lineIndex) + "\n"
            lineIndex += 1

    with open('PIF.out', 'w') as w:
        w.write(str(Pif))

    with open('st.out', 'w') as w:
        w.write("Symbol table identifiers: \n" + str(SymbolTableIdentifiers))
        w.write("\nSymbol table constants: \n" + str(SymbolTableConstants))

    if error == "":
        print("Good lexically")
    else:
        print(error)

