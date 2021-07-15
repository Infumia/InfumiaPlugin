package tr.com.infumia.infumialib.paper.dependencies;

import java.net.MalformedURLException;
import java.net.URL;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Dependency {

  @NotNull
  private final String artifactId;

  @NotNull
  private final String groupId;

  @NotNull
  private final String relocatedPattern;

  @NotNull
  private final String relocationPattern;

  @NotNull
  private final String repoUrl;

  @NotNull
  private final String version;

  @NotNull
  public URL getUrl() throws MalformedURLException {
    var repo = this.repoUrl;
    if (!repo.endsWith("/")) {
      repo += "/";
    }
    repo += "%s/%s/%s/%s-%s.jar";
    return new URL(String.format(repo,
      this.groupId.replace(".", "/"), this.artifactId, this.version, this.artifactId, this.version));
  }
}
