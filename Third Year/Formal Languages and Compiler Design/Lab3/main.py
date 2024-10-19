from SymbolTable import *

if __name__ == '__main__':
    SymbolTableIdentifiers = SymbolTable(10)
    SymbolTableConstants = SymbolTable(10)

    # Testing Identifier Table
    print("Testing Identifier Table (SymbolTableIdentifiers):")
    SymbolTableIdentifiers.Add("var1")
    print("Does 'var1' exist?", SymbolTableIdentifiers.HasValue("var1"))
    print("Position of 'var1' =", SymbolTableIdentifiers.GetValuePosition("var1"))

    SymbolTableIdentifiers.Add("va1r")
    print("Does 'va1r' exist?", SymbolTableIdentifiers.HasValue("va1r"))
    print("Position of 'va1r' =", SymbolTableIdentifiers.GetValuePosition("va1r"))

    SymbolTableIdentifiers.Add("var2")
    print("Does 'var2' exist?", SymbolTableIdentifiers.HasValue("var2"))
    print("Position of 'var2' =", SymbolTableIdentifiers.GetValuePosition("var2"))

    # Testing Constant Table
    print("\nTesting Constant Table (SymbolTableConstants):")
    SymbolTableConstants.Add("42")
    print("Does '42' exist?", SymbolTableConstants.HasValue("42"))
    print("Position of '42' =", SymbolTableConstants.GetValuePosition("42"))

    SymbolTableConstants.Add("42")
    print("Does '42' exist after duplicate addition?", SymbolTableConstants.HasValue("42"))
    print("Position of '42' =", SymbolTableConstants.GetValuePosition("42"))

    SymbolTableConstants.Add("3")
    print("Does '3' exist?", SymbolTableConstants.HasValue("3"))
    print("Position of '3' =", SymbolTableConstants.GetValuePosition("3"))

    # Print final states of the tables
    print("\nFinal SymbolTableIdentifiers:", SymbolTableIdentifiers)
    print("Final SymbolTableConstants:", SymbolTableConstants)

    # Testing deletion
    SymbolTableConstants.Add("27")
    SymbolTableConstants.Delete("42")
    print("\nAfter deletion of '42':")
    print(SymbolTableConstants)
