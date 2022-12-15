package eu.skillcraft.orionclm.preparation;

public class NumberFactory {

  NumberDecoratorGeneratorFactory factoryDecorator;
  NumberChainGeneratorFactory factoryChain;
  ConfigPort configPort;


  NumberGenerator create() {

    if(configPort.isVIP()) {
      return factoryChain.create();
    } else {
      return factoryDecorator.create();
    }

  }



}
