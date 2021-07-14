package tr.com.infumia.infumialib.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class MoreFiles {

  @NotNull
  public Path createDirectoriesIfNotExists(final Path path) throws IOException {
    if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
      return path;
    }
    try {
      Files.createDirectories(path);
    } catch (final FileAlreadyExistsException ignored) {
      // ignored.
    }
    return path;
  }

  @NotNull
  public Path createDirectoryIfNotExists(final Path path) throws IOException {
    if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
      return path;
    }
    try {
      Files.createDirectory(path);
    } catch (final FileAlreadyExistsException ignored) {
      // ignore
    }
    return path;
  }

  @NotNull
  public Path createFileIfNotExists(final Path path) throws IOException {
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    return path;
  }

  public void deleteDirectory(final Path path) throws IOException {
    if (!Files.exists(path) || !Files.isDirectory(path)) {
      return;
    }
    @Cleanup final DirectoryStream<Path> contents = Files.newDirectoryStream(path)
    for (final Path file : contents) {
      if (Files.isDirectory(file)) {
        MoreFiles.deleteDirectory(file);
      } else {
        Files.delete(file);
      }
    }
    Files.delete(path);
  }
}
