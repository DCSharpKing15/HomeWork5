public class Counter {
    private int counter;

    public Counter() {
        this.counter = 0;
    }

    // add number to current count.
    public void increase(int number) {
        counter += number;
    }

    // subtract number from current count.
    public void decrease(int number) {
        counter -= number;
    }

    // get current count.
    public int getValue() {
        return this.counter;
    }
}
