package org.mozilla.javascript;

public class Main {
  public static void main(String[] args) {
    testTransitive();
  }

  public static void testTransitive() {
    var o1 = makeTransitive1();
    var o2 = makeTransitive2();
    System.out.println(new EqualObjectGraphs().equalGraphs(o1, o2)); // prints false
  }

  private static Object makeTransitive1() {
    Object o1 = new Object[]{"3"};
    Object o2 = new Object[]{"3"};
    return new Object[]{o1, o2};
  }

  private static Object makeTransitive2() {
    Object o1 = new Object[]{"3"};
    return new Object[]{o1, o1};
  }
}
