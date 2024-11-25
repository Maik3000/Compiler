package compiler.irt;

public class TempGenerator {
    private static int tempCount = 0;

    public static String newTemp() {
        return "t" + tempCount++;
    }
}