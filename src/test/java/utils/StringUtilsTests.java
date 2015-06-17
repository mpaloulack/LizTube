package utils;

import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.config.JpaConfigs;
import com.liztube.utils.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
public class StringUtilsTests {

    @Test
    public void decodeUrl_should_return_url_string_uncoded(){
        String result = StringUtils.UrlDecoder("hello%2C+how+are+you+%3F");
        assertThat(result).isEqualTo("hello, how are you ?");
    }

    @Test
    public void base64(){
        String encodedString = StringUtils.Base64Encode("éé");
        assertThat(encodedString).isEqualTo("w6nDqQ==");
        String decodedString = StringUtils.Base64Decode(encodedString);
        assertThat(decodedString).isEqualTo("éé");
    }

    @Test
    public void base64_2(){
        assertThat(StringUtils.Base64Decode("b2sgw6A=")).isEqualTo("ok à");
    }
}
