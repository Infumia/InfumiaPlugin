package tr.com.infumia.infumialib.transformer.postprocessor;

import transformer.postprocessor.Manipulator;
import transformer.postprocessor.SectionWalker;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents post processors.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostProcessor {

  /**
   * the context.
   */
  @NotNull
  private String context;

  /**
   * adds indent to the line.
   *
   * @param line the line to add.
   * @param size the size to add.
   *
   * @return indented line.
   */
  @NotNull
  public static String addIndent(@NotNull final String line, final int size) {
    final var indent = " ".repeat(Math.max(0, size));
    return Arrays.stream(line.split("\n"))
      .map(part -> indent + part)
      .collect(Collectors.joining("\n"))
      + "\n";
  }

  /**
   * counts the indent of the lin.
   *
   * @param line the line to count.
   *
   * @return count of indent.
   */
  public static int countIndent(@NotNull final String line) {
    var whitespaces = 0;
    for (final var c : line.toCharArray()) {
      if (!Character.isWhitespace(c)) {
        return whitespaces;
      }
      whitespaces++;
    }
    return whitespaces;
  }

  /**
   * creates comment from array.
   *
   * @param commentPrefix the comment prefix to create.
   * @param comments the comments to create.
   *
   * @return a newly created comment.
   */
  @NotNull
  public static String createComment(@NotNull final String commentPrefix, @NotNull final String @Nullable [] comments) {
    if (comments == null) {
      return "";
    }
    final var lines = Arrays.stream(comments)
      .map(line -> (line.isEmpty()
        ? "" : line.startsWith(commentPrefix.trim()) ? ""
        : commentPrefix) + line)
      .collect(Collectors.toCollection(ArrayList::new));
    return String.join("\n", lines) + "\n";
  }

  /**
   * creates a post processor instance.
   *
   * @param inputStream the input stream to create.
   *
   * @return a newly created post processor instance.
   */
  @NotNull
  public static PostProcessor of(@NotNull final InputStream inputStream) {
    return PostProcessor.of(PostProcessor.readInput(inputStream));
  }

  /**
   * creates a post processor instance.
   *
   * @param context the context to create.
   *
   * @return a newly created post processor instance.
   */
  @NotNull
  public static PostProcessor of(@NotNull final String context) {
    return new PostProcessor(context);
  }

  /**
   * reads the stream to convert it into string.
   *
   * @param inputStream the input stream to read.
   *
   * @return converted string.
   */
  @NotNull
  private static String readInput(@NotNull final InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
      .lines()
      .collect(Collectors.joining("\n"));
  }

  /**
   * writes the text into the stream.
   *
   * @param outputStream the output stream to write.
   * @param text the text to write.
   */
  @SneakyThrows
  private static void writeOutput(@NotNull final OutputStream outputStream, @NotNull final String text) {
    try (final var out = new PrintStream(outputStream, true, StandardCharsets.UTF_8.name())) {
      out.print(text);
    }
  }

  /**
   * appends context comment.
   *
   * @param prefix the prefix to append.
   * @param comments the comments to append.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor appendContextComment(@NotNull final String prefix, @NotNull final String[] comments) {
    return this.appendContextComment(prefix, "", comments);
  }

  /**
   * appends context comment.
   *
   * @param prefix the prefix to append.
   * @param separator the separator to append.
   * @param comments the comments to append.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor appendContextComment(@NotNull final String prefix, @NotNull final String separator,
                                            final String[] comments) {
    if (comments != null) {
      this.context += separator + PostProcessor.createComment(prefix, comments);
    }
    return this;
  }

  /**
   * prepends the context comment.
   *
   * @param prefix the prefix to prepend.
   * @param comments the comments to prepend.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor prependContextComment(final String prefix, final String[] comments) {
    return this.prependContextComment(prefix, "", comments);
  }

  /**
   * prepends the context comment.
   *
   * @param prefix the prefix to prepend.
   * @param separator the separator to prepend.
   * @param comments the comments to prepend.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor prependContextComment(final String prefix, final String separator, final String[] comments) {
    if (comments != null) {
      this.context = PostProcessor.createComment(prefix, comments) + separator + this.context;
    }
    return this;
  }

  /**
   * removes the line.
   *
   * @param filter the filter to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor removeLines(@NotNull final LineFilter filter) {
    this.context = Arrays.stream(this.context.split("\n"))
      .filter(line -> !filter.test(line))
      .map(line -> line + "\n")
      .collect(Collectors.joining());
    return this;
  }

  /**
   * updates the context.
   *
   * @param manipulator the manipulator to update.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor updateContext(@NotNull final transformer.postprocessor.Manipulator manipulator) {
    this.context = manipulator.apply(this.context);
    return this;
  }

  /**
   * updates the lines.
   *
   * @param manipulator the manipulator to update.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor updateLines(@NotNull final Manipulator manipulator) {
    this.context = Arrays.stream(this.context.split("\n"))
      .map(line -> manipulator.apply(line) + "\n")
      .collect(Collectors.joining());
    return this;
  }

  /**
   * updates the line path.
   *
   * @param walker the walker to update.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor updateLinesPaths(@NotNull final SectionWalker walker) {
    final var lines = this.context.split("\n");
    List<LineInfo> currentPath = new ArrayList<>();
    var lastIndent = 0;
    var level = 0;
    final var builder = new StringBuilder();
    boolean multilineSkip = false;
    for (final var line : lines) {
      final var indent = PostProcessor.countIndent(line);
      final var change = indent - lastIndent;
      final var path = walker.readName(line);
      // skip non-keys
      if (!walker.isPath(line)) {
        builder.append(line).append("\n");
        continue;
      }
      if (currentPath.isEmpty()) {
        currentPath.add(LineInfo.of(path, change, indent));
      }
      if (change > 0) {
        if (!multilineSkip) {
          level++;
          currentPath.add(LineInfo.of(path, change, indent));
        }
      } else {
        if (change != 0) {
          final var lastLineInfo = currentPath.get(currentPath.size() - 1);
          final var step = lastLineInfo.getIndent() / level;
          level -= change * -1 / step;
          currentPath = currentPath.subList(0, level + 1);
          multilineSkip = false;
        }
        if (!multilineSkip) {
          currentPath.set(currentPath.size() - 1, LineInfo.of(path, change, indent));
        }
      }
      if (multilineSkip) {
        builder.append(line).append("\n");
        continue;
      } else if (walker.isPathMultilineStart(line)) {
        multilineSkip = true;
      }
      lastIndent = indent;
      builder
        .append(walker.update(line, currentPath.get(currentPath.size() - 1), currentPath))
        .append("\n");
    }
    this.context = builder.toString();
    return this;
  }

  /**
   * writes the stream.
   *
   * @param outputStream the output stream to write.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PostProcessor write(@NotNull final OutputStream outputStream) {
    PostProcessor.writeOutput(outputStream, this.context);
    return this;
  }
}
