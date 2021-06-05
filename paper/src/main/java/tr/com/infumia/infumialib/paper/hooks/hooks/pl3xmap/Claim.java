package tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap;

import java.util.Collection;
import java.util.Map;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

public interface Claim {

  @NotNull
  Map<Integer, Claim> getChildren();

  @NotNull
  Chunk getChunk();

  int getId();

  long getLastDate();

  double getMaxX();

  double getMaxZ();

  @NotNull
  Collection<Member> getMembers();

  double getMinX();

  double getMinZ();

  @NotNull
  String getOwner();

  int getParentId();

  boolean isPvp();

  interface Member {

    @NotNull
    String getName();
  }
}
