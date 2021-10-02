package tyates.aesthetic.fractals.math;


import java.util.Random;
import java.util.Set;

public class DynamicExpressionNode {
    static final Set<String> UNARY_OPERATORS = Set.of("sin", "cos", "abs");
    static final Set<String> BINARY_OPERATORS = Set.of("+", "-", "*", "/");
    static final Set<String> VARIABLES = Set.of("x", "y");

    private final String value;
    private DynamicExpressionNode parent;
    private DynamicExpressionNode left;
    private DynamicExpressionNode right;

    public DynamicExpressionNode(final String value) {
        this.value = value.toLowerCase();
        if (!VARIABLES.contains(this.value)) {
            try {
                Double.parseDouble(this.value);
            } catch (final NumberFormatException e) {
                throw new IllegalArgumentException(String.format("Value %s is not a valid leaf value", this.value));
            }
        }

        this.left = null;
        this.right = null;
    }

    public DynamicExpressionNode(final String value, final DynamicExpressionNode left) {
        this.value = value.toLowerCase();
        if (!UNARY_OPERATORS.contains(this.value)) {
            throw new IllegalArgumentException(String.format("Value %s is not a valid unary value", this.value));
        }

        this.left = left;
        this.left.setParent(this);
        this.right = null;
    }

    public DynamicExpressionNode(final String value, final DynamicExpressionNode left, final DynamicExpressionNode right) {
        this.value = value.toLowerCase();
        if (!BINARY_OPERATORS.contains(this.value)) {
            throw new IllegalArgumentException(String.format("Value %s is not a valid binary value", this.value));
        }

        this.left = left;
        this.left.setParent(this);
        this.right = right;
        this.right.setParent(this);
    }

    public void setParent(final DynamicExpressionNode parent) {
        this.parent = parent;
    }

    public boolean isOperator() {
        return UNARY_OPERATORS.contains(value) || BINARY_OPERATORS.contains(value);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public DynamicExpressionNode getLeft() {
        return left;
    }

    public DynamicExpressionNode getRight() {
        return right;
    }

    public String getValue() {
        return value;
    }

    public double getNumericValue(final double x, final double y) {
        if (isOperator()) {
            throw new IllegalStateException("Node %s cannot be turned into a numeric value");
        }

        try {
            return Double.parseDouble(this.value);
        } catch (final NumberFormatException e) {
            if ("x".equalsIgnoreCase(value)) {
                return x;
            } else if ("y".equalsIgnoreCase(value)) {
                return y;
            } else {
                throw new IllegalStateException("Node %s unknown value");
            }
        }
    }

    public static String getRandomVariable() {
        final String[] objects = VARIABLES.toArray(new String[0]);
        return objects[new Random().nextInt(objects.length)];
    }

    public static String getRandomUnaryOperator() {
        final String[] objects = UNARY_OPERATORS.toArray(new String[0]);
        return objects[new Random().nextInt(objects.length)];
    }

    public static String getRandomBinaryOperator() {
        final String[] objects = BINARY_OPERATORS.toArray(new String[0]);
        return objects[new Random().nextInt(objects.length)];
    }

    public static String getRandomConstant() {
        return Double.toString(new Random().nextDouble() * 4.0 - 2.0);
    }
}
