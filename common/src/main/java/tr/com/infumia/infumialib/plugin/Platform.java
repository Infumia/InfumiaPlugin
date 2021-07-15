package tr.com.infumia.infumialib.plugin;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface Platform {

  @NotNull
  Collection<String> getKnownPermissions();

  @NotNull
  Instant getStartTime();

  @NotNull
  Platform.Type getType();

  @NotNull
  Set<UUID> getUniqueConnections();

  enum Type {
    PAPER("Paper"),
    VELOCITY("Velocity");

    private final String friendlyName;

    Type(final String friendlyName) {
      this.friendlyName = friendlyName;
    }

    @NotNull
    public String getFriendlyName() {
      return this.friendlyName;
    }
  }
}
