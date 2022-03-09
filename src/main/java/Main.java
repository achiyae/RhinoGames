import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.tools.shell.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    Context cx = Context.enter();
    try {
      var scope = cx.initStandardObjects();
      String script =
          "var a1 = {a:'a'};\n" +
              "var a2 = {a:'a'};\n" +
              "var a3 = {a:'a'};\n" +
              "Object.is(a1,a2);";
      cx.evaluateString(scope, script, "code.js", 1, null);
      var a1 = (NativeObject) Context.jsToJava(scope.get("a1"), NativeObject.class);
      var a2 = (NativeObject) Context.jsToJava(scope.get("a2"), NativeObject.class);
      var a3 = (NativeObject) Context.jsToJava(scope.get("a3"), NativeObject.class);
      System.out.println(a1.equals(a2));
      System.out.println(a2.equals(a3));
      System.out.println(a2.equals(a1));
    } finally {
      Context.exit();
    }
  }

  private static String getResourceScript() {
    String script;
    try (InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("code.js");
         InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
         BufferedReader br = new BufferedReader(streamReader)) {
      StringBuilder sb = new StringBuilder();
      String line;
      try {
        while ((line = br.readLine()) != null) {
          sb.append(line).append("\n");
        }
      } catch (IOException e) {
        throw new RuntimeException("error while reading javascript from stream", e);
      }
      script = sb.toString();
    } catch (IOException ex) {
      throw new RuntimeException("Error reading resource: 'code.js': " + ex.getMessage(), ex);
    }
    return script;
  }

}
