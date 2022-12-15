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

    return new ChainNumberGenerator(sequencePort.next(), numberConfig, configPort.getPrefix(),
        YearMonth.now(clock),
        configPort.isDemo());
  }

  static class ChainNumberGenerator implements NumberGenerator {

    private final NumberToken numberToken;

    public ChainNumberGenerator(
        Integer seq, NumberConfig numberConfig, String prefix, YearMonth yearMonth, boolean isDemo) {

      NumberToken basicToken = new BasicToken(seq);

      basicToken
          .next(new PrefixToken(numberConfig, prefix))
          .next(new DateToken(numberConfig, yearMonth))
          .next(new DemoToken(numberConfig, isDemo));
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

  private static class PrefixToken extends NumberTokenTemplate {

    private final String prefix;

    public PrefixToken(NumberConfig numberConfig, String prefix) {
      super(numberConfig, "PREFIX");
      this.prefix = prefix;
    }

    @Override
    protected ContractNumberB getNumber(ContractNumberB numberB) {
      return numberB.addPrefix(prefix);
    }

  }

  private static class DateToken extends NumberTokenTemplate {

    private final YearMonth now;

    public DateToken(NumberConfig numberConfig, YearMonth now) {
      super(numberConfig, "DATE");
      this.now = now;
    }

    protected ContractNumberB getNumber(ContractNumberB numberB) {
      return numberB.addPostfix(now.getYear() + "/" + now.getMonthValue());
    }

  }

  private static class DemoToken extends NumberTokenTemplate {

    private final boolean isDemo;

    public DemoToken(NumberConfig numberConfig, boolean isDemo) {
      super(numberConfig, "DEMO");
      this.isDemo = isDemo;
    }

    protected ContractNumberB getNumber(ContractNumberB numberB) {
      return isDemo ? numberB.addPrefix("DEMO/") : numberB;
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
