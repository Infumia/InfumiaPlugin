package tr.com.infumia.infumialib.http;

import com.google.protobuf.GeneratedMessageV3;
import java.util.concurrent.CompletableFuture;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AsyncHttpApi {

  @NotNull
  private final String address;

  @NotNull
  private final String path;

  @NotNull
  protected final CompletableFuture<Boolean> delete(@NotNull final String path) {
    return CompletableFuture.supplyAsync(() -> ProtobufHttp.create().delete(this.path(path)));
  }

  @NotNull
  protected final <T extends GeneratedMessageV3> CompletableFuture<T> get(@NotNull final String path,
                                                                          @NotNull final T defaultInstance) {
    return CompletableFuture.supplyAsync(() -> ProtobufHttp.create().get(this.path(path), defaultInstance));
  }

  @NotNull
  protected final <T extends GeneratedMessageV3> CompletableFuture<Boolean> post(@NotNull final String path,
                                                                                 @NotNull final T defaultInstance) {
    return CompletableFuture.supplyAsync(() -> ProtobufHttp.create().post(this.path(path), defaultInstance));
  }

  @NotNull
  protected final <T extends GeneratedMessageV3, B extends GeneratedMessageV3> CompletableFuture<T> post(
    @NotNull final String path, @NotNull final T defaultInstance, @NotNull final B body) {
    return CompletableFuture.supplyAsync(() -> ProtobufHttp.create().post(this.path(path), defaultInstance, body));
  }

  private String path(@NotNull final String s) {
    return String.format("%s/%s/%s", this.address, this.path, s);
  }
}
