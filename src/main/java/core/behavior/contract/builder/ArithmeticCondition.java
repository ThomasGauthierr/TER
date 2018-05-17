package core.behavior.contract.builder;

public enum ArithmeticCondition {

    LOWER_THAN,
    LOWER_THAN_OR_EQUAL_TO,
    EQUAL_TO,
    NOT_EQUAL_TO,
    HIGHER_THAN_OR_EQUAL_TO,
    HIGHER_THAN;

    public boolean express(double val1, double val2) {
        switch (this) {
            case EQUAL_TO:
                return val1 == val2;
            case LOWER_THAN:
                return val1 < val2;
            case HIGHER_THAN:
                return val1 > val2;
            case LOWER_THAN_OR_EQUAL_TO:
                return val1 <= val2;
            case HIGHER_THAN_OR_EQUAL_TO:
                return val1 >= val2;
            case NOT_EQUAL_TO:
                return val1 != val2;
        }
        return true;
    }

    public ArithmeticCondition inverse() {
        switch (this) {
            case EQUAL_TO:
                return NOT_EQUAL_TO;
            case LOWER_THAN:
                return HIGHER_THAN_OR_EQUAL_TO;
            case HIGHER_THAN:
                return LOWER_THAN_OR_EQUAL_TO;
            case LOWER_THAN_OR_EQUAL_TO:
                return HIGHER_THAN;
            case HIGHER_THAN_OR_EQUAL_TO:
                return LOWER_THAN;
            case NOT_EQUAL_TO:
                return EQUAL_TO;
        }
        return null;
    }
}
