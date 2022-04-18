package br.net.pin.jabx.flow;

public interface Pace {

  public void log(String message);

  public void log(Throwable error);

  public void waitIfPausedAndThrowIfStopped() throws Exception;

}
