package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.NumberChainGeneratorFactory.NumberToken;

public abstract class NumberTokenTemplate implements NumberToken {

  NumberToken next;
  private final NumberConfig numberConfig;
  private final String tokenName;

  protected NumberTokenTemplate(NumberConfig numberConfig, String tokenName) {
    this.numberConfig = numberConfig;
    this.tokenName = tokenName;
  }


  @Override
  public ContractNumberB generate(String type, ContractNumberB numberB) {

    if (numberConfig.contains(tokenName)) {
      ContractNumberB numberB1 = getNumber(numberB);
      if (next != null) {
        return next.generate(type, numberB1);
      } else {
        return numberB1;
      }
    } else {
      if (next != null) {
        return next.generate(type, numberB);
      } else {
        return numberB;
      }
    }
  }

  protected abstract ContractNumberB getNumber(ContractNumberB numberB);

  @Override
  public NumberToken next(NumberToken number) {
    this.next = number;
    return next;
  }
}
