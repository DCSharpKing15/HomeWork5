import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;

    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.animation = animation;
        this.key = key;
        this.sensor = sensor;
    }
    // ...
    // think about the implementations of doOneFrame and shouldStop.


    @Override
    public boolean shouldStop() {
        return false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {

    }
}
