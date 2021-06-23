package tr.com.infumia.infumialib.transformer.postprocessor.walkers;

import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.transformer.postprocessor.SectionWalker;

/**
 * an abstract implementation of {@link SectionWalker} for YAML.
 */
public abstract class YamlSectionWalker implements SectionWalker {

  @Override
  public boolean isPath(@NotNull final String line) {
    final var name = this.readName(line);
    return !name.isEmpty() && name.charAt(0) != '-' && name.charAt(0) != '#';
  }

  @Override
  public boolean isPathMultilineStart(@NotNull final String line) {
    final var trimmed = line.trim();
    return !line.isEmpty() &&
      (trimmed.endsWith(">") || trimmed.endsWith(">-") || trimmed.endsWith("|") || trimmed.endsWith("|-"));
  }

  @NotNull
  @Override
  public String readName(@NotNull final String line) {
    return line.split(":", 2)[0].trim();
  }
}
