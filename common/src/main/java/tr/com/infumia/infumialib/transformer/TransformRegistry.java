package tr.com.infumia.infumialib.transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericPair;
import tr.com.infumia.infumialib.transformer.transformers.TransformerObjectToString;

/**
 * a class that represents transformer registries.
 */
public final class TransformRegistry {

  /**
   * the serializers.
   */
  @NotNull
  private final Set<ObjectSerializer<?>> serializers = new HashSet<>();

  /**
   * the transformers by id.
   */
  @NotNull
  private final Map<GenericPair, Transformer<?, ?>> transformers = new ConcurrentHashMap<>();

  /**
   * gets the serializer from class.
   *
   * @param cls the cls to get.
   *
   * @return obtains object serializer.
   */
  @NotNull
  public Optional<ObjectSerializer<?>> getSerializer(@NotNull final Class<?> cls) {
    return this.serializers.stream()
      .filter(serializer -> serializer.supports(cls))
      .findFirst();
  }

  /**
   * gets the transformers.
   *
   * @param from the from to get.
   * @param to the to to get.
   *
   * @return transformer instance for from to.
   */
  @NotNull
  public Optional<Transformer<?, ?>> getTransformer(@Nullable final GenericDeclaration from,
                                                    @Nullable final GenericDeclaration to) {
    return Optional.ofNullable(this.transformers.get(GenericPair.of(from, to)));
  }

  /**
   * registers the default transformers.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withDefaultTransformers() {
    TransformPack.DEFAULT.accept(this);
    return this;
  }

  /**
   * registers the serializers.
   *
   * @param serializers the serializers to register.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withSerializers(@NotNull final ObjectSerializer<?>... serializers) {
    return this.withSerializers(Set.of(serializers));
  }

  /**
   * registers the serializers.
   *
   * @param serializers the serializers to register.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withSerializers(@NotNull final Collection<ObjectSerializer<?>> serializers) {
    this.serializers.addAll(serializers);
    return this;
  }

  /**
   * accepts the packs.
   *
   * @param packs the packs to accepts.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTransformPacks(@NotNull final TransformPack... packs) {
    for (final var pack : packs) {
      pack.accept(this);
    }
    return this;
  }

  /**
   * registers the given transformer into the pool.
   *
   * @param pair the pair to add.
   * @param transformer the transformer to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTransformer(@NotNull final GenericPair pair,
                                           @NotNull final Transformer<?, ?> transformer) {
    this.transformers.put(pair, transformer);
    return this;
  }

  /**
   * registers the given transformers into the pool.
   *
   * @param transformers the transformers to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTransformers(@NotNull final Transformer<?, ?>... transformers) {
    return this.withTransformers(Set.of(transformers));
  }

  /**
   * registers the given transformers into the pool.
   *
   * @param transformers the transformers to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTransformers(@NotNull final Collection<Transformer<?, ?>> transformers) {
    for (final var transformer : transformers) {
      if (transformer instanceof TwoSideTransformer<?, ?>) {
        this.withTwoSideTransformers((TwoSideTransformer<?, ?>) transformer);
      } else {
        this.withTransformer(transformer.getPair(), transformer);
      }
    }
    return this;
  }

  /**
   * registers the given transformers into the pool.
   *
   * @param transformers the transformers to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTransformersReversedToString(@NotNull final Transformer<?, ?>... transformers) {
    return this.withTransformersReversedToString(Set.of(transformers));
  }

  /**
   * registers the given transformers into the pool.
   *
   * @param transformers the transformers to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTransformersReversedToString(@NotNull final Collection<Transformer<?, ?>> transformers) {
    this.withTransformers(transformers);
    for (final var transformer : transformers) {
      this.withTransformer(transformer.getPair().reverse(), new TransformerObjectToString());
    }
    return this;
  }

  /**
   * registers the given two side transformers into the pool.
   *
   * @param transformers the transformers to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTwoSideTransformers(@NotNull final TwoSideTransformer<?, ?>... transformers) {
    for (final var transformer : transformers) {
      this.withTransformer(transformer.getPair(), transformer);
      this.withTransformer(transformer.reverse().getPair(), transformer.reverse());
    }
    return this;
  }

  /**
   * registers the given two side transformers into the pool.
   *
   * @param transformers the transformers to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformRegistry withTwoSideTransformers(@NotNull final Collection<TwoSideTransformer<?, ?>> transformers) {
    for (final var transformer : transformers) {
      this.withTransformer(transformer.getPair(), transformer);
    }
    transformers.stream()
      .map(TwoSideTransformer::reverse)
      .forEach(transformer -> this.withTransformer(transformer.getPair(), transformer));
    return this;
  }
}
