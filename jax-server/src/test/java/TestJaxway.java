import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.http.server.PathContainer.parsePath;

/**
 * @Author huaili
 * @Date 2019/5/7 17:46
 * @Description TestJaxway
 **/
@Ignore
@RunWith(JUnit4.class)
public class TestJaxway {

    @Test
    public void testRegx(){

        String testpath = "/sohu";
        PathContainer path = parsePath(testpath);
        ArrayList<PathPattern> pathPatterns = new ArrayList<>();
        PathPatternParser pathPatternParser = new PathPatternParser();

        String regx = "/sohu/**";
        PathPattern pathPattern = pathPatternParser.parse(regx);
        pathPatterns.add(pathPattern);

        Optional<PathPattern> optionalPathPattern = pathPatterns.stream().filter(pattern -> pattern.matches(path)).findFirst();

        System.out.println(optionalPathPattern.isPresent());
    }

}
