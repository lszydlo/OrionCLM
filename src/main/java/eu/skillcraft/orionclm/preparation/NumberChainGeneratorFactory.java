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

    return new ChainNumberGenerator(sequencePort.next(),numberConfig, configPort.getPrefix(), YearMonth.now(clock));
  }

  static class ChainNumberGenerator implements NumberGenerator {

    private final NumberToken numberToken;

    public ChainNumberGenerator(Integer seq, NumberConfig numberConfig, String prefix, YearMonth yearMonth) {
      NumberToken basicToken = new BasicToken(seq);

      basicToken
          .next(new PrefixToken(numberConfig, prefix))
          .next(new DateToken(numberConfig, yearMonth));
      this.numberToken = basicToken;
    }

    @Override
    public ContractNumberB generate(String type) {
      return numberToken.generate(type, new ContractNumberB());
    }
  }


  interface NumberToken {

    ContractNumberB generate(String type, ContractNumberB contractNumber);

    NumberToken next(NumberToken number);
  }

  private static class BasicToken implements NumberToken {

    private NumberToken next;
    private final Integer seq;

    private BasicToken(Integer seq) {
      this.seq = seq;
    }

    @Override
    public ContractNumberB generate(String type, ContractNumberB contractNumber) {
      ContractNumberB contractNumberB = new ContractNumberB(seq.toString());
      return next.generate(type, contractNumberB);
    }

    @Override
    public NumberToken next(NumberToken number) {
      this.next = number;
      return next;
    }
  }

  private static class PrefixToken implements NumberToken {

    private final NumberConfig numberConfig;
    private final String prefix;
    private NumberToken next;

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

  private static class DateToken implements NumberToken {

    private final NumberConfig numberConfig;
    private final YearMonth now;
    private NumberToken next;

    public DateToken(NumberConfig numberConfig, YearMonth now) {
      this.numberConfig = numberConfig;
      this.now = now;
    }

    @Override
    public ContractNumberB generate(String type, ContractNumberB numberB) {
      return numberB.addPostfix(
          now.getYear() + "/" + now.getMonthValue());
    }

    @Override
    public NumberToken next(NumberToken number) {
      this.next = number;
      return next;
    }
  }
}

class NumberConfig {

  private List<String> tokens;

  public NumberConfig(List<String> tokens) {
    this.tokens = tokens;
  }

  public boolean contains(String name) {
    return tokens.contains(name);
  }
}
