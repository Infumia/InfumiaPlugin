package tr.com.infumia.infumialib.paper.transformer.resolvers;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;
import tr.com.infumia.infumialib.transformer.postprocessor.SectionSeparator;
import tr.com.infumia.infumialib.transformer.resolvers.HJsonJson;

/**
 * a class that represents json file configuration.
 */
public final class BukkitHJsonJson extends HJsonJson {

  /**
   * ctor.
   *
   * @param commentPrefix the comment prefix.
   * @param sectionSeparator the section separator.
   */
  private BukkitHJsonJson(@NotNull final String commentPrefix, @NotNull final String sectionSeparator) {
    super(commentPrefix, sectionSeparator);
  }

  /**
   * ctor.
   *
   * @param commentPrefix the comment prefix.
   */
  private BukkitHJsonJson(@NotNull final String commentPrefix) {
    super(commentPrefix, SectionSeparator.NONE);
  }

  /**
   * ctor.
   */
  private BukkitHJsonJson() {
    super("# ");
  }

  @Nullable
  @Override
  public <T> T deserialize(@Nullable final Object object, @Nullable final GenericDeclaration genericSource,
                           @NotNull final Class<T> targetClass, @Nullable final GenericDeclaration genericTarget,
                           @Nullable final Object defaultValue) throws TransformException {
    if (object instanceof ConfigurationSection) {
      final var values = BukkitResolverHelper.getMapValues((ConfigurationSection) object, false);
      return super.deserialize(values, GenericDeclaration.of(values), targetClass, genericTarget, defaultValue);
    }
    return super.deserialize(object, genericSource, targetClass, genericTarget, defaultValue);
  }

  @Nullable
  @Override
  public Object serialize(@Nullable final Object value, @Nullable final GenericDeclaration genericType,
                          final boolean conservative) throws TransformException {
    if (value instanceof ConfigurationSection) {
      return BukkitResolverHelper.getMapValues((ConfigurationSection) value, false);
    }
    return super.serialize(value, genericType, conservative);
  }
}
