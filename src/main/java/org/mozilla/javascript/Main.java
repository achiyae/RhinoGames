package org.mozilla.javascript;

public class Main {
  public static void main(String[] args) {
    var o1 = makeObject1();
    var o2 = makeObject2();
    System.out.println(new EqualObjectGraphs().equalGraphs(o1, o2)); // prints false
  }

  private static Object makeObject1() {
    var o1 = new Object[]{"3"};
    var o2 = new Object[]{"3"};
    return new Object[]{o1, o2};
  }

  private static Object makeObject2() {
    var o1 = new Object[]{"3"};
    return new Object[]{o1, o1};
  }
}
