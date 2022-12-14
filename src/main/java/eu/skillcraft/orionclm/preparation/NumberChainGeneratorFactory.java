package eu.skillcraft.orionclm.preparation;

import java.time.Clock;
import java.time.YearMonth;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberChainGeneratorFactory {
  private final AuthPort authPort;
  private final ConfigPort configPort;
  private final SequencePort sequencePort;
  private final Clock clock;


  NumberGenerator create() {
    NumberConfig numberConfig = configPort.getUserNumberConfig(authPort.userId());

    NumberToken numberToken = new BasicToken()
        .next(new PrefixToken(numberConfig, configPort.getPrefix()))
        .next(new DateToken(numberConfig, YearMonth.now(clock)));
    return new ChainNumberGenerator(numberToken);
  }

  static class ChainNumberGenerator implements NumberGenerator {

    private final NumberToken numberToken;

    public ChainNumberGenerator(NumberToken numberToken) {
      this.numberToken = numberToken;
    }

    @Override
    public ContractNumberB generate(String type) {
      return numberToken.generate("", new ContractNumberB());
    }
  }


  interface NumberToken {
      ContractNumberB generate(String type, ContractNumberB contractNumber);
      NumberToken next(NumberToken number);
  }

  private class BasicToken implements NumberToken {

    NumberToken next;

    @Override
    public ContractNumberB generate(String type, ContractNumberB contractNumber) {
        ContractNumberB contractNumberB = new ContractNumberB(sequencePort.next().toString());
        return next.generate(type, contractNumberB);
    }

    @Override
    public NumberToken next(NumberToken number) {
      this.next = number;
      return next;
    }
  }

  private class PrefixToken  implements NumberToken {

    private final NumberConfig numberConfig;
    private final String prefix;
    NumberToken next;

    public PrefixToken(NumberConfig numberConfig, String prefix) {
      this.numberConfig = numberConfig;
      this.prefix = prefix;
    }

    @Override
    public ContractNumberB generate(String type, ContractNumberB numberB) {
      if (numberConfig.contains("PREFIX")) {
        ContractNumberB contractNumberB = numberB.addPrefix(prefix);
        return next.generate(type, contractNumberB);
      } else {
        return next.generate(type, numberB);
      }
    }

    @Override
    public NumberToken next(NumberToken number) {
      this.next = number;
      return next;
    }
  }

  private class DateToken  implements NumberToken {

    NumberToken next;

    public DateToken(NumberConfig numberConfig, YearMonth now) {

    }

    @Override
    public ContractNumberB generate(String type, ContractNumberB numberB) {
      return numberB ;
    }

    @Override
    public NumberToken next(NumberToken number) {
      this.next = number;
      return next;
    }
  }
}

class NumberConfig {
  List<String> tokens;

  public boolean contains(String name) {
    return false;
  }
}
