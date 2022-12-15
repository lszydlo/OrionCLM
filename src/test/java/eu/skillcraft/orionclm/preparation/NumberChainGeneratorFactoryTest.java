package eu.skillcraft.orionclm.preparation;

import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.Assertions.assertThat;

import eu.skillcraft.orionclm.preparation.NumberChainGeneratorFactory.ChainNumberGenerator;
import java.time.Clock;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberChainGeneratorFactoryTest {

  private SequencePort sequence = Mockito.mock(SequencePort.class);
  private ConfigPort config = Mockito.mock(ConfigPort.class);
  private AuthPort auth = Mockito.mock(AuthPort.class);

  @Test
  void should_create_number() {
    // Given
    Mockito.when(sequence.next()).thenReturn(33);
    Mockito.when(config.getPrefix()).thenReturn("PZU");
    Mockito.when(config.getUserNumberConfig(Mockito.any())).thenReturn(new NumberConfig(List.of("PREFIX")));
    Mockito.when(auth.isAuditor()).thenReturn(true);
    Clock clock = Clock.fixed(ofEpochMilli(0), ZoneId.systemDefault());

    NumberChainGeneratorFactory factory = new NumberChainGeneratorFactory(auth, config,sequence, clock);

    //When
    ContractNumberB number = factory.create().generate("sales");

    // Then
    assertThat(number.number).isEqualTo("PZU 33 1970/1");
  }


  @Test
  void should_generate_with_generator() {

    ChainNumberGenerator generator = new ChainNumberGenerator(
        33, new NumberConfig(List.of("PREFIX")), "PZU", YearMonth.of(1970,1)
    );

    ContractNumberB number = generator.generate("sales");

    assertThat(number.number).isEqualTo("PZU 33 1970/1");


  }
}
