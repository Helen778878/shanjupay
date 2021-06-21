package host.helen.shanjupay.merchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname MerchantApp
 * @Description MerchantApp
 * @Date 2021/6/14 3:59 PM
 * @Created by helen
 */
@SpringBootApplication
@Slf4j
public class MerchantApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MerchantApp.class,args);
        } catch (Exception e) {
           log.error("SpringBootApplication ERROR",e);
        }
    }

}
