package org.example;

class AST {
    ASTNode root;

    AST() {
        this.root = new ASTNode();
    }

    void printAST(ASTNode curr, int depth) {
        if (curr != null) {
            System.out.println("depth: " + depth);
            if (curr.value != null) System.out.println(curr.value);
            if (curr.intValue != null) System.out.println(curr.intValue);

            if (curr.left != null) printAST(curr.left, depth + 1);
            if (curr.right != null) printAST(curr.right, depth + 1);
        }
    }
}
