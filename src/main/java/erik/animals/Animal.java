package erik.animals;

public abstract class Animal {
    protected String type;
    protected double weight;
    protected int maxAmountOnOneField;
    protected int speedPerCycle;
    protected double kgToBeFull;
    protected byte health = 100;

    public Animal() {

    }

    public byte getHealth() {
        return health;
    }

    public void setHealth(byte health) {
        this.health = health;
    }

    public double getWeight() {
        return weight;
    }

    public int getSpeedPerCycle() {
        return speedPerCycle;
    }

    public int getMaxAmountOnOneField() {
        return maxAmountOnOneField;
    }

    public double getKgToBeFull() {
        return kgToBeFull;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setMaxAmountOnOneField(int maxAmountOnOneField) {
        this.maxAmountOnOneField = maxAmountOnOneField;
    }

    public void setSpeedPerCycle(int speedPerCycle) {
        this.speedPerCycle = speedPerCycle;
    }

    public void setKgToBeFull(double kgToBeFull) {
        this.kgToBeFull = kgToBeFull;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String transformFaces(Animal animal) {
        return switch (animal.getType()) {
            case "boar" -> "\uD83D\uDC17"; //🐗

            case "buffalo" -> "\uD83D\uDC03";//🐃

            case "deer" -> "\uD83E\uDD8C"; //🦌

            case "duck" -> "\uD83E\uDD86";//🦆

            case "goat" -> "\uD83D\uDC10"; // 🐐

            case "horse" -> "\uD83D\uDC0E";//🐎

            case "mouse" -> "\uD83D\uDC01";//🐁

            case "rabbit" -> "\uD83D\uDC07";//🐇

            case "sheep" -> "\uD83D\uDC11";//🐑

            case "bear" -> "\uD83D\uDC3B";//🐻

            case "eagle" -> "\uD83E\uDD85";
            case "fox" -> "\uD83E\uDD8A";//🦊

            case "snake" -> "\uD83D\uDC0D";//🐍

            case "wolf" -> "\uD83D\uDC3A";//🐺

            case "caterpillar" -> "\uD83D\uDC1B";//🐛

            case "plant" -> "\uD83E\uDD6C";//🥬

            default -> "\uD83D\uDC27";//🐧
        };
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type='" + type + '\'' +
                ", weight=" + weight +
                ", maxAmountOnOneField=" + maxAmountOnOneField +
                ", speedPerCycle=" + speedPerCycle +
                ", kgToBeFull=" + kgToBeFull +
                '}';
    }
}
