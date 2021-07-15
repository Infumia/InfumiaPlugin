package tr.com.infumia.infumialib.paper.dependencies;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Documented
@Repeatable(MavenLibraries.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MavenLibrary {

  @NotNull
  String artifactId();

  @NotNull
  String groupId();

  @NotNull
  Repository repo() default @Repository(url = "https://repo1.maven.org/maven2");

  @NotNull
  String version();
}
