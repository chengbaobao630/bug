package cc.home.taobao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by cheng on 2017/3/28 0028.
 */
@Aspect
public class CtrAspect {

    @Pointcut("@annotation(org.springframework.stereotype.Controller)")
    public void pointcut(){

    }

    @Before(value = "pointcut()")
    public void beforeCtr(JoinPoint joinPoint){
        LocalTime localTime=LocalTime.now();
        String format = localTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        System.out.println(format);
    }

    public static void main(String[] args) {

        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);   // legacy java.util.Date
        System.out.println(legacyDate.toString());
        DateTimeFormatter chinaFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.CHINA);
        String format = LocalTime.now().format(chinaFormatter);
        System.out.println(format);

        chinaFormatter=
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime dateTime =LocalDateTime.now(Clock.systemDefaultZone());
        System.out.println(dateTime.format(chinaFormatter));

        Optional<String> optional = Optional.ofNullable("null");
        if (optional.isPresent()) // true
        System.out.println(optional.get());     // "bam"
        System.out.println(optional.orElse("fallback"));        // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"

        Optional<Object> o = optional.flatMap(s -> Optional.of("asdf"));
        System.out.println(o.orElse("ads"));

        o = optional.map(s -> "asdf");
        System.out.println(o.orElse("ads"));
    }


    @AfterThrowing(value = "pointcut()",throwing = "e")
    public void afterThrowing(Exception e){
        StackTraceElement[] stackTrace = e.getStackTrace();
        // 将异常信息记录
        System.out.println("-->" + stackTrace[0].toString());
    }
}
