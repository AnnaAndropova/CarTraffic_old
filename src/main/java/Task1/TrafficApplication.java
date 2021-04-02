package Task1;

import Task1.config.ApplicationConfig;
import Task1.config.DumbConfig;
import Task1.model.DumbModel;
import Task1.service.DumbService;
import Task1.service.TrafficService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = DumbConfig.class)
@ComponentScan(basePackageClasses = DumbService.class)
@ComponentScan(basePackageClasses = DumbModel.class)
@PropertySource("classpath:traffic.properties")
public class TrafficApplication {


    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(TrafficApplication.class);
        ApplicationConfig appConfig = context.getBean(ApplicationConfig.class);

        TrafficService ts = context.getBean(TrafficService.class);
        ts.runModeling();
    }

}
