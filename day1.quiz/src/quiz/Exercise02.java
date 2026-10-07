package quiz;

public class Exercise02 {

	public static void main(String[] args) {
		HomeController homeController = new HomeController();
		homeController.applyScene("MORNING");
		homeController.applyScene("AWAY");
		homeController.applyScene("NIGHT");
		homeController.dailyReport();
	}

}

interface SmartDevice {
    void turnOn();
    void turnOff();
    void setBrightness(int level);
    void setTemperature(int celsius);
}

class Light implements SmartDevice {
    public void turnOn()  { System.out.println("Light on"); }
    public void turnOff() { System.out.println("Light off"); }
    public void setBrightness(int level) {
        System.out.println("Brightness " + level + "%");
    }
    public void setTemperature(int celsius) { } // no-op
}

class BasicLight extends Light {
    @Override
    public void setBrightness(int level) {
        if (level != 0 && level != 100) {
            throw new IllegalArgumentException("Only 0 or 100 supported");
        }
        super.setBrightness(level);
    }
}

class Thermostat implements SmartDevice {
    public void turnOn()  { System.out.println("Heating on"); }
    public void turnOff() { System.out.println("Heating off"); }
    public void setBrightness(int level) { } // no-op
    public void setTemperature(int celsius) {
        System.out.println("Temp set to " + celsius + "°C");
    }
}

class FileLogger {
    void write(String line) {
        System.out.println("[home.log] " + line);
    }
}

class HomeController {
    private Light light = new BasicLight();
    private Thermostat thermostat = new Thermostat();
    private FileLogger logger = new FileLogger();
    private int scenesRun = 0;

    void applyScene(String scene) {
        if (scene.equals("MORNING")) {
            light.turnOn();
            light.setBrightness(100);
            thermostat.setTemperature(22);
        } else if (scene.equals("NIGHT")) {
            light.setBrightness(20);
            thermostat.setTemperature(18);
        } else if (scene.equals("AWAY")) {
            light.turnOff();
            thermostat.turnOff();
        }
        scenesRun++;
        logger.write("Scene applied: " + scene);
    }

    String dailyReport() {
        return "=== Daily Report ===\nScenes run: "
            + scenesRun;
    }
}
