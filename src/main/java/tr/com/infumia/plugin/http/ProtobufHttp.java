package tr.com.infumia.plugin.http;

import com.google.protobuf.GeneratedMessageV3;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class ProtobufHttp {

  @NotNull
  private final HttpClient client;

  @NotNull
  static ProtobufHttp create() {
    return new ProtobufHttp(HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_2)
      .connectTimeout(Duration.ofSeconds(20))
      .build());
  }

  boolean delete(@NotNull final String url) {
    final var request = HttpRequest.newBuilder()
      .DELETE()
      .uri(URI.create(url))
      .build();
    try {
      return this.client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Nullable <T extends GeneratedMessageV3> T get(@NotNull final String url,
                                                          @NotNull final T defaultInstance) {
    final var request = HttpRequest.newBuilder()
      .GET()
      .uri(URI.create(url))
      .build();
    try {
      final var response = this.client.send(request, HttpResponse.BodyHandlers.ofInputStream());
      if (response.statusCode() == 200) {
        //noinspection unchecked
        return (T) defaultInstance.getParserForType().parseFrom(response.body());
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Nullable <T extends GeneratedMessageV3, B extends GeneratedMessageV3> T post(@NotNull final String url,
                                                                                @NotNull final T defaultInstance,
                                                                                @NotNull final B body) {
    final var request = HttpRequest.newBuilder()
      .POST(HttpRequest.BodyPublishers.ofByteArray(body.toByteArray()))
      .uri(URI.create(url))
      .build();
    try {
      final var response = this.client.send(request, HttpResponse.BodyHandlers.ofInputStream());
      if (response.statusCode() == 200) {
        //noinspection unchecked
        return (T) defaultInstance.getParserForType().parseFrom(response.body());
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  <T extends GeneratedMessageV3> boolean post(@NotNull final String url, @NotNull final T body) {
    final var request = HttpRequest.newBuilder()
      .POST(HttpRequest.BodyPublishers.ofByteArray(body.toByteArray()))
      .uri(URI.create(url))
      .build();
    try {
      return this.client.send(request, HttpResponse.BodyHandlers.ofInputStream()).statusCode() == 200;
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
