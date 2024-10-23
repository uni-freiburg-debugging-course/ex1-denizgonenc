package org.example;

import java.util.ArrayList;

class Parser {

    static AST parse(ArrayList<String> tokens) throws Exception {
        AST ast = new AST();
        ASTNode currNode = ast.root;

        for (String token : tokens) {
            try {
                Integer value = Integer.parseInt(token);
                if (currNode.value != null) {
                    if (!currNode.addChild(new ASTNode(currNode, value))){
                        if (currNode.value != null) {
                            throw new Exception("Parser error: Too many operands in expression", null);
                        }
                        else {
                            throw new Exception("Parser error: More than one number with no operator", null);
                        }
                    }
                }
                else {
                    currNode.intValue = value;
                }

            } catch (NumberFormatException _) {
                switch (token) {
                    case "left-parentheses":
                        // begin new expression
                        if (ast.root.value != null) {
                            ASTNode nextNode = new ASTNode(currNode);
                            if (!currNode.addChild(nextNode)) {
                                throw new Exception("Parser error: Too many operands in expression", null);
                            }
                            currNode = nextNode;
                        }
                        break;
                    case "simplify":
                    case "addition":
                    case "subtraction":
                    case "multiplication":
                        if (currNode.value == null) {
                            currNode.value = token;
                        }
                        else {
                            throw new Exception("Parser error: Too many operators in expression", null);
                        }
                        break;
                    case "right-parentheses":
                        currNode = currNode.parent;
                }
            }
        }
        return ast;
    }

    static int evaluate(ASTNode curr) throws Exception {
        if (curr != null) {
            if (curr.intValue != null) {
                return curr.intValue;
            }
            else if (curr.value == null) {
                throw new Exception("Parser error: No operands", null);
            }
            else {
                Integer left = null;
                Integer right = null;
                if (curr.left != null) left = evaluate(curr.left);
                if (curr.right != null) right = evaluate(curr.right);

                switch (curr.value) {
                    case "addition":
                        if (left != null && right != null) {
                            return left + right;
                        }
                        break;
                    case "subtraction":
                        if (left != null && right != null) {
                            return left - right;
                        }
                        else if (left != null) return -left;
                        break;
                    case "multiplication":
                        if (left != null && right != null) {
                            return left * right;
                        }
                        break;
                }

                if (left != null) {
                    return left;
                } else throw new Exception("Unknown error", null);
            }
        } else throw new Exception("Parser error: No expressions", null);
    }
}
