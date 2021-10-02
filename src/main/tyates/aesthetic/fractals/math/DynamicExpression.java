package tyates.aesthetic.fractals.math;

import java.util.Random;

import static tyates.aesthetic.fractals.math.DynamicExpressionNode.*;

public class DynamicExpression {
    private DynamicExpressionNode root;

    public DynamicExpression() {
        root = new DynamicExpressionNode("+", generateSubExpression(), generateSubExpression());
    }

    public DynamicExpression(final DynamicExpressionNode root) {
        this.root = root;
    }

    private DynamicExpressionNode generateSubExpression() {
        final Random random = new Random();
        final DynamicExpressionNode const1 = new DynamicExpressionNode(getRandomConstant());
        final DynamicExpressionNode var1 = new DynamicExpressionNode(getRandomVariable());
        final DynamicExpressionNode mul1 = new DynamicExpressionNode("*", const1, var1);

        final DynamicExpressionNode unary1 = new DynamicExpressionNode(getRandomUnaryOperator(), mul1);
        final DynamicExpressionNode const2 = new DynamicExpressionNode(getRandomConstant());
        return new DynamicExpressionNode("*", const2, unary1);
    }

    public DynamicExpressionNode getRoot() {
        return root;
    }

    private double evaluate(final DynamicExpressionNode current, final double x, final double y) {
        if (current == null) {
            return 0.0;
        }

        if (current.isLeaf()) {
            return current.getNumericValue(x, y);
        }

        switch (current.getValue()) {
            case "sin":
                return Math.sin(evaluate(current.getLeft(), x, y));
            case "cos":
                return Math.cos(evaluate(current.getLeft(), x, y));
            case "abs":
                return Math.abs(evaluate(current.getLeft(), x, y));
            case "+":
                return evaluate(current.getLeft(), x, y) + evaluate(current.getRight(), x, y);
            case "-":
                return evaluate(current.getLeft(), x, y) - evaluate(current.getRight(), x, y);
            case "*":
                return evaluate(current.getLeft(), x, y) * evaluate(current.getRight(), x, y);
            case "/":
                return evaluate(current.getLeft(), x, y) / evaluate(current.getRight(), x, y);
            default:
                throw new IllegalStateException(String.format("Unknown node operator %s", current.getValue()));
        }
    }

    public double evaluate(final double x, final double y) {
        return evaluate(root, x, y);
    }

    public DynamicExpression cloneExpression() {
        return new DynamicExpression(cloneExpression(root));
    }

    public DynamicExpressionNode cloneExpression(final DynamicExpressionNode currentNode) {
        if (currentNode.isLeaf()) {
            return new DynamicExpressionNode(currentNode.getValue());
        }

        if (currentNode.isUnaryOperator()) {
            return new DynamicExpressionNode(currentNode.getValue(), cloneExpression(currentNode.getLeft()));
        }

        return new DynamicExpressionNode(currentNode.getValue(), cloneExpression(currentNode.getLeft()), cloneExpression(currentNode.getRight()));
    }
}
