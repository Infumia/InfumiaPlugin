package tr.com.infumia.infumialib.transformer.serializers;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.helper.MongoDatabaseCredentials;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

public final class MongoCredentialsSerializer implements ObjectSerializer<MongoDatabaseCredentials> {

  @NotNull
  @Override
  public Optional<MongoDatabaseCredentials> deserialize(@NotNull final TransformedData transformedData,
                                                        @Nullable final GenericDeclaration declaration) {
    return Optional.of(MongoDatabaseCredentials.of(
      transformedData.get("auth-source", String.class).orElse("admin"),
      transformedData.get("database", String.class).orElse(""),
      transformedData.get("host", String.class).orElse("127.0.0.1"),
      transformedData.get("password", String.class).orElse(""),
      transformedData.get("port", int.class).orElse(27017),
      transformedData.get("uri", String.class).orElse(""),
      transformedData.get("username", String.class).orElse("")));
  }

  @NotNull
  @Override
  public Optional<MongoDatabaseCredentials> deserialize(@NotNull final MongoDatabaseCredentials field,
                                                        @NotNull final TransformedData transformedData,
                                                        @Nullable final GenericDeclaration declaration) {
    return this.deserialize(transformedData, declaration);
  }

  @Override
  public void serialize(@NotNull final MongoDatabaseCredentials helperMongo,
                        @NotNull final TransformedData transformedData) {
    transformedData.add("auth-source", helperMongo.getAuthSource(), String.class);
    transformedData.add("database", helperMongo.getDatabase(), String.class);
    transformedData.add("host", helperMongo.getHost(), String.class);
    transformedData.add("password", helperMongo.getPassword(), String.class);
    transformedData.add("port", helperMongo.getPort(), int.class);
    transformedData.add("uri", helperMongo.getUsername(), String.class);
    transformedData.add("username", helperMongo.getUsername(), String.class);
  }

  @Override
  public boolean supports(@NotNull final Class<?> cls) {
    return MongoDatabaseCredentials.class == cls;
  }
}
