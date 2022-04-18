package br.net.pin.jabx.flow;

import br.net.pin.jabx.mage.WizLog;

public class PaceCmd implements Pace {

  @Override
  public void log(String message) {
    WizLog.info(message);
  }

  @Override
  public void log(Throwable error) {
    WizLog.erro(error);
  }

  @Override
  public void waitIfPausedAndThrowIfStopped() throws Exception {}
  
}
