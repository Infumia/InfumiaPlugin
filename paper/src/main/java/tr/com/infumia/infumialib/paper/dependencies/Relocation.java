package tr.com.infumia.infumialib.paper.dependencies;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Relocation {

  private static final String RELOCATION_PREFIX = "tr.com.infumia.infumia.libs.";

  @NotNull
  @ToString.Include
  @EqualsAndHashCode.Include
  private final String pattern;

  @NotNull
  @ToString.Include
  @EqualsAndHashCode.Include
  private final String relocatedPattern;

  @NotNull
  public static Relocation of(@NotNull final String relocationPrefix, @NotNull final String id,
                              @NotNull final String pattern) {
    return new Relocation(pattern.replace("{}", "."), relocationPrefix + id);
  }

  @NotNull
  public static Relocation of(@NotNull final String id, @NotNull final String pattern) {
    return Relocation.of(Relocation.RELOCATION_PREFIX, id, pattern);
  }
}
