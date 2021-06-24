import com.google.protobuf.Any;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.TransformedObject;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.resolvers.JacksonYaml;

public final class Test extends TransformedObject {

  public static TestData testData = new TestData(
    "test",
    Map.of(
      "key-1", "value-1",
      "key-2", List.of("values-1", "values-2", "values-3")));

  public static void main(final String[] args) {
    new Test()
      .withFile(Path.of(System.getProperty("user.dir")).resolve("config.yml"))
      .withResolver(new JacksonYaml())
      .withTransformPack(registry -> registry
        .withSerializers(new Serializer()))
      .initiate();
  }

  private static final class Serializer implements ObjectSerializer<TestData> {

    @NotNull
    @Override
    public Optional<TestData> deserialize(@NotNull final TransformedData transformedData,
                                          @Nullable final GenericDeclaration declaration) {
      return Optional.of(
        new TestData(
          transformedData.get("test", String.class).orElseThrow(),
          transformedData.getAsMap("values", String.class, Object.class).orElseThrow()
        )
      );
    }

    @NotNull
    @Override
    public Optional<TestData> deserialize(@NotNull final TestData field, @NotNull final TransformedData transformedData, @Nullable final GenericDeclaration declaration) {
      return this.deserialize(transformedData, declaration);
    }

    @Override
    public void serialize(@NotNull final TestData testData, @NotNull final TransformedData transformedData) {
      testData.serialize(transformedData);
    }

    @Override
    public boolean supports(@NotNull final Class<?> cls) {
      return cls == TestData.class;
    }
  }

  @RequiredArgsConstructor
  public static final class TestData {

    private final String test;

    private final Map<String, Object> values;

    public void serialize(@NotNull final TransformedData data) {
      data.add("test", this.test, String.class);
      data.addAsMap("values", this.values, String.class, Object.class);
    }
  }
}
