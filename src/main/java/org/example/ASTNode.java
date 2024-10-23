package org.example;

class ASTNode {
    String value;
    Integer intValue;
    ASTNode parent;
    ASTNode left;
    ASTNode right;

    ASTNode() {
    }

    ASTNode(ASTNode parent) {
        this.parent = parent;
    }

    ASTNode(ASTNode parent, Integer intValue) {
        this.parent = parent;
        this.intValue = intValue;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean addChild(ASTNode child) {
        if (this.left == null) {
            this.left = child;
            return true;
        } else if (this.right == null) {
            this.right = child;
            return true;
        }
        else {
            return false;
        }
    }
}
