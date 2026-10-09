package problem;

public class BuildingAutomationProblem {

	 public static void main(String[] args) {
	        var boiler = new Boiler();
	        var chiller = new Chiller();
	        var damper = new VavDamper(); // Variable Air Volume

	        var temperature = new TemperatureSensor(boiler, chiller, damper);
	        var occupancy = new OccupancySensor(boiler, chiller, damper);
	        var window = new WindowContact(boiler, chiller, damper);

	        System.out.println("-- cold morning, room occupied --");
	        occupancy.report(true);
	        temperature.report(18.5d);

	        System.out.println("-- somebody opens a window --");
	        window.report(true);
	        System.out.println("   state: " + state(boiler, chiller, damper));

	        System.out.println("-- the next temperature reading arrives 30s later --");
	        temperature.report(18.2d);
	        System.out.println("   state: " + state(boiler, chiller, damper));
	        System.out.println("   the window is still open and the boiler is running again");

	        System.out.println("-- a warm afternoon reading, window still open --");
	        temperature.report(26.0d);
	        System.out.println("   state: " + state(boiler, chiller, damper));
	    }

	    private static String state(Boiler boiler, Chiller chiller, VavDamper damper) {
	        return "boiler=" + boiler.isRunning() + " chiller=" + chiller.isRunning()
	                + " damper=" + damper.openPercent() + "%";
	    }
	}

	// ---------------------------------------------------------------- actuators
	final class Boiler {
	    private boolean running;

	    void start() { running = true; System.out.println("    boiler  START"); }
	    void stop() { running = false; System.out.println("    boiler  STOP"); }
	    boolean isRunning() { return running; }
	}

	final class Chiller {
	    private boolean running;

	    void start() { running = true; System.out.println("    chiller START"); }
	    void stop() { running = false; System.out.println("    chiller STOP"); }
	    boolean isRunning() { return running; }
	}

	final class VavDamper {
	    private int openPercent;

	    void openTo(int percent) { openPercent = percent; System.out.println("    damper  " + percent + "%"); }
	    int openPercent() { return openPercent; }
	}

	// ---------------------------------------------------------------- sensors that know everything
	final class TemperatureSensor {

	    private static final double SETPOINT = 21.0d;

	    private final Boiler boiler;
	    private final Chiller chiller;
	    private final VavDamper damper;

	    TemperatureSensor(Boiler boiler, Chiller chiller, VavDamper damper) {
	        this.boiler = boiler;
	        this.chiller = chiller;
	        this.damper = damper;
	    }

	    void report(double celsius) {
	        System.out.println("  temperature " + celsius + "C");
	        // Knows nothing about occupancy or windows, so it overrides both.
	        if (celsius < SETPOINT - 1) {
	            boiler.start();
	            chiller.stop();
	            damper.openTo(40);
	        } else if (celsius > SETPOINT + 1) {
	            chiller.start();
	            boiler.stop();
	            damper.openTo(80);
	        }
	    }
	}

	final class OccupancySensor {

	    private final Boiler boiler;
	    private final Chiller chiller;
	    private final VavDamper damper;

	    OccupancySensor(Boiler boiler, Chiller chiller, VavDamper damper) {
	        this.boiler = boiler;
	        this.chiller = chiller;
	        this.damper = damper;
	    }

	    void report(boolean occupied) {
	        System.out.println("  occupancy " + occupied);
	        if (!occupied) {
	            boiler.stop();
	            chiller.stop();
	            damper.openTo(0);
	        }
	        // When occupied it does nothing, and hopes a temperature reading arrives soon.
	    }
	}

	final class WindowContact {

	    private final Boiler boiler;
	    private final Chiller chiller;
	    private final VavDamper damper;

	    WindowContact(Boiler boiler, Chiller chiller, VavDamper damper) {
	        this.boiler = boiler;
	        this.chiller = chiller;
	        this.damper = damper;
	    }

	    void report(boolean open) {
	        System.out.println("  window open=" + open);
	        if (open) {
	            boiler.stop();
	            chiller.stop();
	            damper.openTo(0);
	        }
	        // Its decision survives only until the next temperature reading.
	    }
	}