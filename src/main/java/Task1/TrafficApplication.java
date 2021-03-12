package Task1;

import Task1.Model.CarTraffic;
import Task1.Service.CarService;
import Task1.Service.TrafficService;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrafficApplication implements CommandLineRunner {

    private TrafficService ts;
    private CarService cs;

    public TrafficApplication(CarService cs, TrafficService ts) {
        this.cs = cs;
        this.ts = ts;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TrafficApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run();
    }

    @Override
    public void run(String... args) throws Exception {
        ts.runModeling(cs, new CarTraffic(100));
    }
}
