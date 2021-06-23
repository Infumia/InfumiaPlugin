package tr.com.infumia.infumialib.transformer.postprocessor;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine section walkers.
 */
public interface SectionWalker {

  /**
   * checks if the line is a path.
   *
   * @param line the line to check.
   *
   * @return {@code true} if the line is a pathç
   */
  boolean isPath(@NotNull String line);

  /**
   * checks if the line has multiline start.
   *
   * @param line the line to check.
   *
   * @return {@code true} if the line has multiline start.
   */
  boolean isPathMultilineStart(@NotNull String line);

  /**
   * gets the name of the line.
   *
   * @param line the line to get.
   *
   * @return obtained line name.
   */
  @NotNull
  String readName(@NotNull String line);

  /**
   * updates the line.
   *
   * @param line the line to update.
   * @param lineInfo the line info to update.
   * @param path the path to update.
   *
   * @return updated line.
   */
  @NotNull
  String update(@NotNull String line, @NotNull LineInfo lineInfo, @NotNull List<LineInfo> path);
}
