import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) {
    Context cx = Context.enter();
    try {
      Global global = new Global(cx);
      String script = getResourceScript();
      Object result = cx.evaluateString(global, script, "code.js", 1, null);
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
