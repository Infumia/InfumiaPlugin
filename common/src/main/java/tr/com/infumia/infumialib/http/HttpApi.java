package tr.com.infumia.infumialib.http;

import com.google.protobuf.GeneratedMessageV3;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HttpApi {

  @NotNull
  private final String address;

  @NotNull
  private final String path;

  protected final boolean delete(@NotNull final String path) {
    return ProtobufHttp.create().delete(this.path(path));
  }

  @Nullable
  protected final <T extends GeneratedMessageV3> T get(@NotNull final String path, @NotNull final T defaultInstance) {
    return ProtobufHttp.create().get(this.path(path), defaultInstance);
  }

  protected final <T extends GeneratedMessageV3> boolean post(@NotNull final String path,
                                                              @NotNull final T defaultInstance) {
    return ProtobufHttp.create().post(this.path(path), defaultInstance);
  }

  @Nullable
  protected final <T extends GeneratedMessageV3, B extends GeneratedMessageV3> T post(@NotNull final String path,
                                                                                      @NotNull final T defaultInstance,
                                                                                      @NotNull final B body) {
    return ProtobufHttp.create().post(this.path(path), defaultInstance, body);
  }

  private String path(@NotNull final String s) {
    return String.format("%s/%s/%s", this.address, this.path, s);
  }
}
