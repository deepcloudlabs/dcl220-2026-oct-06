package solution;

/**
 * DCL-220 - Module 4: Responsibility Patterns - MEDIATOR (solution)
 *
 * FloorController is the mediator. Devices became two kinds of colleague:
 *   sensors    report an observation and decide nothing
 *   actuators  accept a command and decide nothing
 *
 * All of the policy - setpoint, deadband, window override, setback, and the
 * "boiler and chiller never together" invariant - lives in one method, applyPolicy().
 * Because every observation goes through the same method, the controller always
 * evaluates the CURRENT world state rather than reacting to one input at a time.
 * That is precisely what the sensors could not do: each of them saw one variable.
 *
 * Where the invariant lives now
 * -----------------------------
 * inside applyPolicy(), which is the only code that may command an actuator. Before,
 * three classes could each start a machine independently, so no class was in a
 * position to enforce a rule about two machines. Centralising the interaction is what
 * makes the invariant expressible at all - that, not "fewer references", is the point.
 *
 * Cost to be honest about
 * -----------------------
 * The mediator is now the class that changes whenever a rule changes, and it can grow
 * into a god object. It stays healthy while it only ORCHESTRATES: the moment it starts
 * computing damper curves or PID output, that logic belongs back in a collaborator.
 */
public class BuildingAutomationSolution {

    public static void main(String[] args) {
        var boiler = new Boiler();
        var chiller = new Chiller();
        var damper = new VavDamper();
        var controller = new FloorController(boiler, chiller, damper);

        var temperature = new TemperatureSensor(controller);
        var occupancy = new OccupancySensor(controller);
        var window = new WindowContact(controller);

        System.out.println("-- cold morning, room occupied --");
        occupancy.report(true);
        temperature.report(18.5d);
        System.out.println("   " + controller.state());

        System.out.println("-- somebody opens a window --");
        window.report(true);
        System.out.println("   " + controller.state());

        System.out.println("-- the next temperature reading arrives 30s later --");
        temperature.report(18.2d);
        System.out.println("   " + controller.state() + "  <- window override still holds");

        System.out.println("-- a warm afternoon reading, window still open --");
        temperature.report(26.0d);
        System.out.println("   " + controller.state());

        System.out.println("-- window closed, still warm and occupied --");
        window.report(false);
        System.out.println("   " + controller.state());

        System.out.println("-- everyone leaves --");
        occupancy.report(false);
        System.out.println("   " + controller.state());
    }
}

// ---------------------------------------------------------------- the mediator contract
interface HvacMediator {

    void temperatureChanged(double celsius);

    void occupancyChanged(boolean occupied);

    void windowChanged(boolean open);
}

// ---------------------------------------------------------------- the mediator
final class FloorController implements HvacMediator {

    private static final double SETPOINT_C = 21.0d;
    private static final double DEADBAND_C = 1.0d;

    private final Boiler boiler;
    private final Chiller chiller;
    private final VavDamper damper;

    // The world as last observed. Every rule is evaluated against all of it.
    private double celsius = SETPOINT_C;
    private boolean occupied;
    private boolean windowOpen;

    FloorController(Boiler boiler, Chiller chiller, VavDamper damper) {
        this.boiler = boiler;
        this.chiller = chiller;
        this.damper = damper;
    }

    @Override
    public void temperatureChanged(double celsius) {
        System.out.println("  temperature " + celsius + "C");
        this.celsius = celsius;
        applyPolicy();
    }

    @Override
    public void occupancyChanged(boolean occupied) {
        System.out.println("  occupancy " + occupied);
        this.occupied = occupied;
        applyPolicy();
    }

    @Override
    public void windowChanged(boolean open) {
        System.out.println("  window open=" + open);
        this.windowOpen = open;
        applyPolicy();
    }

    /** The single place where the floor decides what its machines should do. */
    private void applyPolicy() {
        Mode mode = decideMode();

        // Invariant, enforced structurally: at most one machine can be asked to run.
        boiler.running(mode == Mode.HEAT);
        chiller.running(mode == Mode.COOL);
        damper.openTo(switch (mode) {
            case HEAT -> 40;
            case COOL -> 80;
            case OFF -> 0;
        });
    }

    private Mode decideMode() {
        if (windowOpen || !occupied) {
            return Mode.OFF;                       // energy code and setback both win
        }
        if (celsius < SETPOINT_C - DEADBAND_C) {
            return Mode.HEAT;
        }
        if (celsius > SETPOINT_C + DEADBAND_C) {
            return Mode.COOL;
        }
        return Mode.OFF;                           // inside the deadband: do nothing
    }

    String state() {
        return "state: boiler=" + boiler.isRunning() + " chiller=" + chiller.isRunning()
                + " damper=" + damper.openPercent() + "%";
    }

    private enum Mode { HEAT, COOL, OFF }
}

// ---------------------------------------------------------------- colleagues: sensors
final class TemperatureSensor {

    private final HvacMediator mediator;

    TemperatureSensor(HvacMediator mediator) {
        this.mediator = mediator;
    }

    void report(double celsius) {
        mediator.temperatureChanged(celsius);
    }
}

final class OccupancySensor {

    private final HvacMediator mediator;

    OccupancySensor(HvacMediator mediator) {
        this.mediator = mediator;
    }

    void report(boolean occupied) {
        mediator.occupancyChanged(occupied);
    }
}

final class WindowContact {

    private final HvacMediator mediator;

    WindowContact(HvacMediator mediator) {
        this.mediator = mediator;
    }

    void report(boolean open) {
        mediator.windowChanged(open);
    }
}

// ---------------------------------------------------------------- colleagues: actuators
final class Boiler {

    private boolean running;

    void running(boolean shouldRun) {
        if (shouldRun != running) {
            running = shouldRun;
            System.out.println("    boiler  " + (shouldRun ? "START" : "STOP"));
        }
    }

    boolean isRunning() {
        return running;
    }
}

final class Chiller {

    private boolean running;

    void running(boolean shouldRun) {
        if (shouldRun != running) {
            running = shouldRun;
            System.out.println("    chiller " + (shouldRun ? "START" : "STOP"));
        }
    }

    boolean isRunning() {
        return running;
    }
}

final class VavDamper {

    private int openPercent;

    void openTo(int percent) {
        if (percent != openPercent) {
            openPercent = percent;
            System.out.println("    damper  " + percent + "%");
        }
    }

    int openPercent() {
        return openPercent;
    }
}