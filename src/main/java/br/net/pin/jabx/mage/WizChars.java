package br.net.pin.jabx.mage;

public class WizChars {

  public static boolean isEmpty(String theString) {
    return theString == null || theString.isEmpty();
  }

  public static boolean isNotEmpty(String theString) {
    return theString != null && !theString.isEmpty();
  }

  public static String firstNonEmpty(String... ofStrings) {
    if (ofStrings == null) {
      return "";
    }
    for (String chars : ofStrings) {
      if (isNotEmpty(chars)) {
        return chars;
      }
    }
    return "";
  }

  public static String sum(String withUnion, String... allStrings) {
    return sum(withUnion, null, allStrings);
  }

  public static String sum(String withUnion, StringBuilder andBuilder,
      String... allStrings) {
    if (allStrings == null) {
      return null;
    }
    if (withUnion == null) {
      withUnion = "";
    }
    var atLeastOne = false;
    var result = andBuilder != null ? andBuilder : new StringBuilder();
    for (String chars : allStrings) {
      if (isNotEmpty(chars)) {
        if (atLeastOne) {
          result.append(withUnion);
        } else {
          atLeastOne = true;
        }
        result.append(chars);
      }
    }
    return result.toString();
  }

  public static String insertSpaceInUppers(String fromString) {
    if (fromString == null) {
      return null;
    }
    String result = fromString;
    for (int i = 'A'; i <= 'Z'; i++) {
      result = result.replace(((char) i) + "", " " + ((char) i));
    }
    return result.trim();
  }

  public static boolean isFirstUpper(String inChars) {
    if (inChars == null) {
      return false;
    }
    if (inChars.length() > 0) {
      String first = inChars.substring(0, 1);
      return first.toUpperCase().equals(first);
    } else {
      return false;
    }
  }

  public static String toUpperOnlyFirstChar(String inChars) {
    StringBuilder result = new StringBuilder();
    if (inChars.length() > 0) {
      result.append(inChars.substring(0, 1).toUpperCase());
    }
    if (inChars.length() > 1) {
      result.append(inChars.substring(1).toLowerCase());
    }
    return result.toString();
  }

  public static String toUpperFirstChar(String inChars) {
    StringBuilder result = new StringBuilder();
    if (inChars.length() > 0) {
      result.append(inChars.substring(0, 1).toUpperCase());
    }
    if (inChars.length() > 1) {
      result.append(inChars.substring(1));
    }
    return result.toString();
  }

  public static String getFromDoubleQuotes(String inChars) {
    if (inChars == null) {
      return inChars;
    }
    if (inChars.length() >= 2) {
      if (inChars.charAt(0) == '"' && inChars.charAt(inChars.length() - 1) == '"') {
        return inChars.substring(1, inChars.length() - 1);
      } else {
        return inChars;
      }
    } else {
      return inChars;
    }
  }

  public static Number getNumber(String ofString) {
    if (ofString == null) {
      return null;
    }
    if (ofString.contains(".")) {
      return Double.parseDouble(ofString);
    } else {
      return Integer.parseInt(ofString);
    }
  }

  public static String getLetters(String ofString) {
    if (ofString == null) {
      return null;
    }
    var builder = new StringBuilder();
    for (char ch : ofString.toCharArray()) {
      if (Character.isLetter(ch)) {
        builder.append(ch);
      }
    }
    return builder.toString();
  }

  public static String getNonLetters(String ofString) {
    if (ofString == null) {
      return null;
    }
    var builder = new StringBuilder();
    for (char ch : ofString.toCharArray()) {
      if (!Character.isLetter(ch)) {
        builder.append(ch);
      }
    }
    return builder.toString();
  }

  public static String getNonLettersAndNonDigits(String ofString) {
    if (ofString == null) {
      return null;
    }
    var builder = new StringBuilder();
    for (char ch : ofString.toCharArray()) {
      if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
        builder.append(ch);
      }
    }
    return builder.toString();
  }

  public static String fill(char withChar, int untilLength) {
    return fill(null, withChar, untilLength, true);
  }

  public static String fill(String theString, char withChar, int untilLength) {
    return fill(theString, withChar, untilLength, false);
  }

  public static String fillAtStart(String theString, char withChar, int untilLength) {
    return fill(theString, withChar, untilLength, true);
  }

  public static String fill(String theString, char withChar, int untilLength,
      boolean atStart) {
    StringBuilder result = new StringBuilder();
    int diference = untilLength - (theString != null ? theString.length() : 0);
    if (!atStart && theString != null) {
      result.append(theString);
    }
    for (int i = 0; i < diference; i++) {
      result.append(withChar);
    }
    if (atStart && theString != null) {
      result.append(theString);
    }
    return result.toString();
  }

  public static boolean contains(String inChars, Character... anyChar) {
    if (isNotEmpty(inChars) && anyChar != null && anyChar.length > 0) {
      for (int i = 0; i < inChars.length(); i++) {
        if (WizArray.has(inChars.charAt(i), anyChar)) {
          return true;
        }
      }
    }
    return false;
  }

  public static String replaceAll(String source, String[] from, String[] to) {
    if (from == null || source == null || source.isEmpty()) {
      return source;
    }
    for (int i = 0; i < from.length; i++) {
      var newValue = to == null || i >= to.length ? "" : to[i];
      source = source.replace(from[i], newValue);
    }
    return source;
  }

  public static String replaceControlFlow(String inChars) {
    if (isEmpty(inChars)) {
      return inChars;
    }
    inChars = inChars.replace("\\", "\\\\");
    inChars = inChars.replace("\r", "\\r");
    inChars = inChars.replace("\n", "\\n");
    inChars = inChars.replace("\t", "\\t");
    inChars = inChars.replace("\f", "\\f");
    inChars = inChars.replace("\b", "\\b");
    return inChars;
  }

  public static String remakeControlFlow(String inChars) {
    if (isEmpty(inChars)) {
      return inChars;
    }
    inChars = inChars.replace("\\b", "\b");
    inChars = inChars.replace("\\f", "\f");
    inChars = inChars.replace("\\t", "\t");
    inChars = inChars.replace("\\n", "\n");
    inChars = inChars.replace("\\r", "\r");
    inChars = inChars.replace("\\\\", "\\");
    return inChars;
  }
}
