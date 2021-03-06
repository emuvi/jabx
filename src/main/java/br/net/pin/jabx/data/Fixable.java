package br.net.pin.jabx.data;

import java.lang.reflect.Modifier;

import br.net.pin.jabx.mage.WizChars;

public interface Fixable {
  default public void fixNulls() throws Exception {
    for (var field : this.getClass().getDeclaredFields()) {
      var mods = field.getModifiers();
      if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
        continue;
      }
      var value = field.get(this);
      if (value == null) {
        value = field.getType().getConstructor().newInstance();
        field.set(this, value);
      }
    }
  }

  default public void fixNullsAndEnvs() throws Exception {
    for (var field : this.getClass().getDeclaredFields()) {
      var mods = field.getModifiers();
      if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
        continue;
      }
      var value = field.get(this);
      if (value == null) {
        value = field.getType().getConstructor().newInstance();
        field.set(this, value);
      }
      if (value instanceof String strValue) {
        value = WizChars.replaceEnvVars(strValue);
        field.set(this, value);
      }
    }
  }
}
