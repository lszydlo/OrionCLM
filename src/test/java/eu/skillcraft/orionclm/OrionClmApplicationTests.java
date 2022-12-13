package eu.skillcraft.orionclm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrionClmApplicationTests {

  @Test
  void contextLoads() {

    Assertions.assertThat("").isGreaterThan("sdf").isEqualTo("");

  }

}
