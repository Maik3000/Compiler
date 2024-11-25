package compiler.irt;

public class LabelGenerator {
    private static int labelCount = 0;

    public static String newLabel() {
        return "L" + labelCount++;
    }
}